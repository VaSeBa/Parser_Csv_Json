package vaseba;

import vaseba.converter.Converter;
import vaseba.exception.ParserException;
import vaseba.model.OrderOut;
import vaseba.parser.OrderParser;
import vaseba.parser.ParserFactory;

import java.util.List;
import java.util.stream.Stream;


/** Реализация по шаблону проэктирования "Фабричный метод"
 * https://refactoring.guru/ru/design-patterns/factory-method
 * */
public class App {

	private static final String WRONG_COMMAND = "Неверная команда - укажите файлы для преобразования";
	private static ParserFactory parserFactory;
	private static Converter orderConverter;

    public static void main(String[] args) {
		/** Пытаемся считать файл из терминала  */
    	if (args.length != 0) {
			try {
				/** Создаем экземпляр объекта фабрики и конвертора - определяем формат, проверяем на !=null  */
				parserFactory = ParserFactory.getInstance();
				orderConverter = Converter.getInstance();
				Stream.of(args).distinct().parallel().forEach(App::processFile);
			} catch (ParserException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println(WRONG_COMMAND);
		}
    }


    private static void processFile(String filename) {
		try {
			/** проверяем указаный файл для парсинга */
			OrderParser parser = parserFactory.getParserByFileName(filename);
			/** если все ок - парсим и конвертируем в необходимый вывод */
			List<OrderOut> orders = parser.execute();
			orders.parallelStream().forEach(o -> System.out.println(orderConverter.convertOutToString(o)));
		} catch (Exception e) {
			System.out.println(Converter.buildErrorString(filename, null, e.getMessage()));
		}
	}

}
