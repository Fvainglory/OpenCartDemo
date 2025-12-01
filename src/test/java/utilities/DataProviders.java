package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
    
    @DataProvider(name = "loginData")
    public String[][] getData() throws IOException {
        String path = System.getProperty("user.dir")+"/testData/loginData.xlsx";
        ExcelUtils excel = new ExcelUtils(path);

        int totalRowNum = excel.getRowCount("sheet1");
        int totalColNum = excel.getColumnCount("sheet1",1);
        String[][] cellData = new String[totalRowNum][totalColNum];

        for (int r = 1; r<=totalRowNum; r++){
            for (int c = 0; c<totalColNum; c++){
                cellData[r-1][c] = excel.getCellData("sheet1", r, c);
            }
        }
        return cellData;
    }
    
}
