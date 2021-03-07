package vaseba;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import vaseba.converter.Converter;
import vaseba.model.OrderOut;
import vaseba.parser.OrderParser;
import vaseba.parser.ParserFactory;

import java.io.File;
import java.util.List;

public class ParserXlsxTest {

    private ClassLoader classLoader = getClass().getClassLoader();
    private ParserFactory factory = ParserFactory.getInstance();
    private Converter converter = Converter.getInstance();

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void executeSuccessTest() {
        File file = new File(classLoader.getResource("test.xlsx").getFile());
        OrderParser parser = factory.getParserByFileName(file.getAbsolutePath());
        List<OrderOut> orders = parser.execute();
        orders.stream().map(converter::convertOutToString).forEach(System.out::println);


    }


}
