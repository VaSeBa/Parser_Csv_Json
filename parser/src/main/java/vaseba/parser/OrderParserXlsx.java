package vaseba.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import vaseba.exception.ParserException;
import vaseba.model.OrderOut;

import java.io.*;
import java.util.Iterator;
import java.util.List;


public class OrderParserXlsx extends OrderParser {
    public static final String COLUMNS_COUNT_IS_INVALID = "количество столбцов несовпадает - ";
    private static XSSFRow row;

    public OrderParserXlsx(String filename) {
        super(filename);
    }

    @Override
    protected List<OrderOut> parse(BufferedReader input) {
        Iterator<Row> records;
        try {
            String s = input.readLine();
            FileInputStream in = new FileInputStream(s);
//            input = new BufferedReader(new FileReader(new File("C:\\Users\\VaSeBa\\Desktop\\test.xlsx")));
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            records = sheet.iterator();
            while (records.hasNext()) {
                row = (XSSFRow) records.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;

                        case STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                    }
                }
            }
        } catch (JSONException | IOException e) {
            throw new ParserException(COLUMNS_COUNT_IS_INVALID);
        }

        return (List<OrderOut>) records;
    }



    private OrderOut parseRows (XSSFRow records) {
        OrderOut target;
        if (records.getPhysicalNumberOfCells() == 4){

        }


        return null;
    }


}
