package net.itas.tools.xls;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;



class g_excelXls extends g_excel {

	@Override
	List<Sheet> loadSheet(File file)throws Exception  {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(input);

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
