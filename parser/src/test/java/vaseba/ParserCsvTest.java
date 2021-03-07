package vaseba;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import vaseba.converter.Converter;
import vaseba.exception.ParserException;
import vaseba.model.OrderOut;
import vaseba.parser.OrderParser;
import vaseba.parser.ParserFactory;
import vaseba.parser.OrderParserCsv;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserCsvTest {

	private ClassLoader classLoader = getClass().getClassLoader();
	private ParserFactory factory = ParserFactory.getInstance();
	private Converter converter = Converter.getInstance();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void fileNotFoundTest() {
		expectedEx.expect(ParserException.class);
		expectedEx.expectMessage(OrderParser.FILE_NOT_FOUND);
		OrderParser parser = factory.getParserByFileName("ooorders.csv");
		parser.execute();
	}

	@Test
	public void emptyFileTest() {
		expectedEx.expect(ParserException.class);
		expectedEx.expectMessage(OrderParser.FILE_IS_EMPTY);
		File file = new File(classLoader.getResource("empty.csv").getFile());
		OrderParser parser = factory.getParserByFileName(file.getAbsolutePath());
		parser.execute();
	}

	@Test
	public void executeSuccessTest() {
		File file = new File(classLoader.getResource("test.csv").getFile());
		OrderParser parser = factory.getParserByFileName(file.getAbsolutePath());
		List<OrderOut> orders = parser.execute();
		orders.stream().map(converter::convertOutToString).forEach(System.out::println);

	}

}
