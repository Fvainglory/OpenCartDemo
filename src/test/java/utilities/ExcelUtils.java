package utilities;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExcelUtils {
    public FileOutputStream fo; //write
    public FileInputStream fi;  //read
    public XSSFWorkbook wb;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    XSSFCellStyle style;
    //String path = System.getProperty("user.dir")+"utilities/ExcelUtils.java";
    String path;
    public ExcelUtils(String path){
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
        int rowNum = sheet.getLastRowNum();
        wb.close();
        fi.close();
        return rowNum;
    }

    public int getColumnCount(String sheetName, int rowNum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int colNum = row.getLastCellNum();
        wb.close();
        fi.close();
        return colNum;
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        wb =new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        String data;
        DataFormatter df = new DataFormatter();
        try {
            data = df.formatCellValue(cell);
        }catch (Exception e){
            data = "";
        }
        wb.close();
        fi.close();
        return data;
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
/*
        File xfile = new File(path);
        if (!xfile.exists()){   //if file not exists, then create new file
            fo = new FileOutputStream(path);
            wb = new XSSFWorkbook();
            wb.write(fo);
        }

        fi = new FileInputStream(path);
        wb =new XSSFWorkbook(fi);
        if (wb.getSheetIndex(sheetName) == -1){ //if sheet not exists then create new sheet
            sheet = wb.createSheet(sheetName);
        }

        sheet = wb.getSheet(sheetName);
        if (sheet.getRow(rowNum) == null){  //if row not exists then create new row
            row = sheet.createRow(rowNum);
        }
        row = sheet.getRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
*/

            //如果使用 Java 7+，可以用try-with-resources进一步简化资源关闭（自动关闭实现AutoCloseable的资源）
            File xfile = new File(path);
            XSSFWorkbook wb;

            // 读取或创建Workbook
            if (xfile.exists()) {
                try (FileInputStream fi = new FileInputStream(xfile)) {
                    wb = new XSSFWorkbook(fi);
                }
            } else {
                wb = new XSSFWorkbook();
            }

            // 操作Sheet/Row/Cell（同前）
            sheet = wb.getSheet(sheetName) == null ? wb.createSheet(sheetName) : wb.getSheet(sheetName);
            row = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum) : sheet.getRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue(data);

            // 写入文件（自动关闭fo）
            try (FileOutputStream fo = new FileOutputStream(xfile)) {
                wb.write(fo);
            } finally {
                wb.close();
            }

    }

    public void setCellInGreen(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());   //设置单元格的「背景填充色」为绿色, POI 中设置 “填充背景色” 的方法，参数是「颜色索引值」（Excel 内置颜色的编号）
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置单元格的「填充模式」为「实心填充」（关键！不设置这行，背景色可能不生效）
        cell.setCellStyle(style);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }

    public void setCellInRed(String sheetName, int rowNum, int colNum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }

}
