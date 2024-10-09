package api.utilities;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	String path;
	public XLUtility(String path)
	{
		this.path = path;
	}
	
	public int getRowCount(String st) throws IOException {
        FileInputStream read = new FileInputStream(path);

       
        XSSFWorkbook wb = new XSSFWorkbook(read);
        XSSFSheet sheet = wb.getSheet(st);
        int rowCount = sheet.getLastRowNum();
        return rowCount;
    }
    public int getCellCount(String st,int ro) throws IOException {
        FileInputStream read = new FileInputStream(path);
        XSSFWorkbook wb = new XSSFWorkbook(read);
        XSSFSheet sheet = wb.getSheet(st);
        XSSFRow row = sheet.getRow(ro);
        wb.close();
        read.close();
        return row.getLastCellNum();
    }
    public String getCellData(String st,int r,int c) throws IOException {
        String data;
       try
       {
           FileInputStream read = new FileInputStream(path);
           XSSFWorkbook wb =   new XSSFWorkbook(read);
           XSSFSheet sheet = wb.getSheet(st);
           if(sheet==null)
           {
               System.out.println(st+"does  not exist");
           }
           XSSFRow row = sheet.getRow(r);
           if(row==null)
           {
               System.out.println(r+"does not exist");
           }

           XSSFCell cell = row.getCell(c);
           if(cell == null)
           {
               System.out.println(c+"does not exist");
           }
           data = cell.toString();
           wb.close();
           read.close();

       }
       catch(Exception e)
       {
           data = "";
       }
       return data;

    }
    public void setCellData(String sheetName, int rowNum, int cellNum, String data) throws IOException {
        XSSFWorkbook workbook;
        boolean isNewWorkbook = false;

        // Try to open the existing workbook, create a new one if it does not exist
        try (FileInputStream fis = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            // File does not exist or cannot be opened, create a new workbook
            workbook = new XSSFWorkbook();
            isNewWorkbook = true;
        }

        // Get or create the sheet
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        // Get or create the row
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        // Get or create the cell
        XSSFCell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }

        // Set the cell value
        cell.setCellValue(data);

        // Write changes to the file
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }

        // Close the workbook
        workbook.close();
    }


}
