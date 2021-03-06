package vaseba.parser;

import vaseba.exception.ParserException;
import vaseba.model.OrderOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public abstract class OrderParser {
	public static final String FILE_NOT_FOUND = "Файл не найден";
	public static final String FILE_IS_EMPTY = "Файл пуст";

	protected final String filename;

	public OrderParser(String filename) {
		this.filename = filename;
	}

	/** Выполняет парсинг-процесс */
	public List<OrderOut> execute() {
		try (BufferedReader input = Files.newBufferedReader(Paths.get(filename))) {
			/** возвращает список ордера */
			return parse(input);
		} catch (IOException e) {
			throw new ParserException(FILE_NOT_FOUND);
		}
	}

	/** Разбирает ввод в список ордера*/
	protected abstract List<OrderOut> parse(BufferedReader input);

}
