	package Selenium;
	
	import java.io.FileInputStream;
	import java.io.IOException;
	import org.apache.poi.ss.usermodel.*;
	
	public class Excelutils {
		public static Object[][] getTestData(String filepath,String sheetname) throws IOException{
			FileInputStream fis = new FileInputStream(filepath);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheet(sheetname);
			
			int rowcount=sheet.getPhysicalNumberOfRows();
			int colcount=sheet.getRow(0).getLastCellNum();
			
			Object[][] data = new Object[rowcount-1][colcount];
			
			for(int i=1;i<rowcount;i++) {
				Row row = sheet.getRow(i);
				for(int j=0;j<colcount;j++) {
					data[i-1][j]=row.getCell(j).toString();
				}
			}
			workbook.close();
	        fis.close();
	        return data;
			
		}
		
	}
