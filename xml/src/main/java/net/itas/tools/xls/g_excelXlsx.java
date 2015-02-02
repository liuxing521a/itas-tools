package net.itas.tools.xls;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class g_excelXlsx extends g_excel {

	@Override
	List<Sheet> loadSheet(File file) throws Exception {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			
			List<Sheet> sheetList = new ArrayList<Sheet>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheetList.add(workbook.getSheetAt(i));
			}
			
			return sheetList;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

}
