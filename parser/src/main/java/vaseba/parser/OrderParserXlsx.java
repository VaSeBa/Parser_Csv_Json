package vaseba.parser;

import org.json.JSONException;
import vaseba.exception.ParserException;
import vaseba.model.OrderOut;

import java.io.BufferedReader;
import java.util.List;

public class OrderParserXlsx extends OrderParser{
    public static final String COLUMNS_COUNT_IS_INVALID = "количество столбцов несовпадает - ";

    public OrderParserXlsx(String filename) {
        super(filename);
    }

    @Override
    protected List<OrderOut> parse(BufferedReader input) {
        try {


        } catch (JSONException e) {
            throw new ParserException(COLUMNS_COUNT_IS_INVALID);
        }

        return null;
    }


}
