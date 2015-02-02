package net.itas.tools.xls;

import java.util.LinkedList;
import java.util.List;

import net.itas.tools.xls.base.g_sheet;
import net.itas.tools.xls.base.g_sheetAttr;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


final class g_parseSheetText {
	
	public List<String> parse_sheetTextClient(Sheet sheet, g_sheet g_sheet) throws Exception  {
		List<String> rowList = new LinkedList<String>();
		// 第0行为说明标题，此处略过
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String rowXml = g_XmlRowClient(row, g_sheet);
			if (!rowXml.isEmpty()) {
				rowList.add(rowXml);
			}
		}
		
		return rowList;
	}

	public List<String> parse_sheetTextServer(Sheet sheet, g_sheet g_sheet) throws Exception  {
		List<String> rowList = new LinkedList<String>();
		// 第0行为说明标题，此处略过
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String rowXml = g_XmlRowServer(row, g_sheet);
			if (!rowXml.isEmpty()) {
				rowList.add(rowXml);
			}
		}
		
		return rowList;
	}
	
	private String g_XmlRowClient(Row row, g_sheet g_sheet) {
		if (row == null) {
			return "";
		}
		
		
		StringBuilder rBuf = new StringBuilder(50);
		for (g_sheetAttr attr : g_sheet.getXls_attribute_list()) {
			Cell cell = row.getCell(attr.getSheet_title_column());
			if (cell == null) {
				continue;
			}
			
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cellValue = cell.getStringCellValue();
			if (attr.getSheet_title_column() == 0 && cellValue.isEmpty()) {
				break;
			}
			
			if (attr.getXml_attribute_client_name() != null) {
				rBuf.append(attr.getXml_attribute_client_name() + "=\"" + cellValue + "\" ");
			}
		}
		
		String rowS = rBuf.toString();
		return rowS.isEmpty() ? "" : "<" + g_sheet.getXml_file_name_client() + " " + rowS + "/>";
	}
	
	private String g_XmlRowServer(Row row, g_sheet g_sheet) {
		if (row == null) {
			return "";
		}
		
		
		StringBuilder rBuf = new StringBuilder(50);
		for (g_sheetAttr attr : g_sheet.getXls_attribute_list()) {
			Cell cell = row.getCell(attr.getSheet_title_column());
			if (cell == null) {
				continue;
			}
			
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cellValue = cell.getStringCellValue();
			if (attr.getSheet_title_column() == 0 && cellValue.isEmpty()) {
				break;
			}
			
			if (attr.getXml_attribute_server_name() != null) {
				rBuf.append(attr.getXml_attribute_server_name() + "=\"" + cellValue + "\" ");
			}
		}
		
		String rowS = rBuf.toString();
		return rowS.isEmpty() ? "" : "<" + g_sheet.getXml_file_name_server() + " " + rowS + "/>";
	}
}
