package vaseba.parser;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vaseba.model.OrderOut;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class OrderParserXlsx extends OrderParser {
//    public static final String COLUMNS_COUNT_IS_INVALID = "количество столбцов несовпадает - ";

    public OrderParserXlsx(String filename) {
        super(filename);
    }

    @Override
    protected List<OrderOut> parse(BufferedReader input) {
        Iterable <XSSFWorkbook> records;
        try {
            String data = input.readLine();
            FileInputStream in = new FileInputStream(data);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator iterator = sheet.iterator();
            while (iterator.hasNext()) {
                XSSFRow row = (XSSFRow) iterator.next();

                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIterator.next();
                    getCellValue(cell);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    private String getCellValue(XSSFCell cell) {

        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return cell.getStringCellValue();
        }
    }

}
