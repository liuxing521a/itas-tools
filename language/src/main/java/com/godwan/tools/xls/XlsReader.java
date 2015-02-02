package com.godwan.tools.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XlsReader {

	private Map<String, HSSFSheet> sheetMap;
	
	public XlsReader() {
		sheetMap = new HashMap<String, HSSFSheet>();
	}
	
	public void loadExcel(String dir) throws Exception {
		File file = new File(dir);
		if (!file.isDirectory()) {
			throw new FileNotFoundException("错误的excel根路径:" + dir);
		}
		
		File[] fileList = file.listFiles();
		for (File f: fileList) {
			if (!f.isFile()) {
				continue;
			}
			
			if (!f.getName().endsWith(".xls")) {
				continue;
			}
			
			loadExcel(f);
		}
	}
	
	public void loadExcel(File file) throws Exception {
		// 创建对Excel工作簿文件的引用
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		int size = workbook.getNumberOfSheets();
		
		HSSFSheet sheet;
		for (int i = 0; i < size; i++) {
			sheet = workbook.getSheetAt(i);
			sheetMap.put(sheet.getSheetName(), sheet);
		}
			
	}
	
	public HSSFSheet getHSSFSheet(String sheetName) {
		return sheetMap.get(sheetName);
	}
	
	
}
