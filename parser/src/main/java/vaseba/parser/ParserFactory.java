package vaseba.parser;

import vaseba.exception.ParserException;

import java.io.InputStream;
import java.util.Properties;

public class ParserFactory {

	/** выбор формата файла*/
	public static final String PROPERTIES_FILENAME = "parsers.properties";

	public static final String UNABLE_TO_LOAD = "Не возможно загрузить ";
	public static final String PARSER_BY_NAME = "- разбор по названию ";
	public static final String FILES_ARE_NOT_SUPPORTED = "- файлы не поддерживаются";
	public static final String FILE_FORMAT_NOT_SPECIFIED = "Формат файла не указан";


	private static class SingletonHolder {
		private static final ParserFactory INSTANCE = new ParserFactory();
	}

	private Properties parsers;

	private ParserFactory() {
		try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
			parsers = new Properties();
			parsers.load(in);
		} catch (Exception e) {
			throw new ParserException(UNABLE_TO_LOAD + PROPERTIES_FILENAME);
		}
	}

	public static ParserFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}


	public OrderParser getParserByFileName(String filename) {
		/** проверяем указан ли формат */
		int dotIndex = filename.lastIndexOf('.');
		if ((dotIndex == -1) || (dotIndex == filename.length()-1)) {
			throw new ParserException(FILE_FORMAT_NOT_SPECIFIED);
		}

		/** проверяем указан ли поддерживаемый формат */
		String format = filename.substring(dotIndex+1).toUpperCase();
		String className = parsers.getProperty(format);
		if (className == null) {
			throw new ParserException(format + FILES_ARE_NOT_SUPPORTED);
		}

		OrderParser parser;
		try {
			parser = (OrderParser) Class.forName(className).getDeclaredConstructor(String.class).newInstance(filename);
		} catch (Exception e) {
			throw new ParserException(UNABLE_TO_LOAD + format + PARSER_BY_NAME + className);
		}
		return parser;
	}
}
