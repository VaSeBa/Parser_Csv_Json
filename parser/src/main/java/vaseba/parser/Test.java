package vaseba.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vaseba.model.OrderOut;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class Test extends OrderParser{
    private static XSSFRow row;

    public Test(String filename) {
        super(filename);
    }

    public static void main(String[] args) {
        try{
            FileInputStream in = new FileInputStream(new File("C:\\Users\\VaSeBa\\Desktop\\test.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator <Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                   Cell cell = cellIterator.next();

                   switch (cell.getCellType()) {
                       case NUMERIC:
                           System.out.print(cell.getNumericCellValue() + " \t\t ");
                           break;

                       case STRING:
                           System.out.print(cell.getStringCellValue() + " \t\t ");
                           break;
                   }
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<OrderOut> parse(BufferedReader input) {

        try {
            FileInputStream in = new FileInputStream(new File(String.valueOf(input)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
