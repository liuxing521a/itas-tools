package net.itas.tools.xls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.itas.tools.xls.base.g_sheet;
import net.itas.tools.xls.base.g_sheetAttr;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


final class g_parseSheetTitle {
	
	/** 获取解析模版*/
	public g_sheet parse_sheetTitle(Sheet sheet) throws Exception  {
		List<g_sheetAttr> atributeList = parse_sheetAttr(sheet.getRow(0));

		g_sheet xml_file = new g_sheet(sheet.getSheetName());
		xml_file.setXls_attribute_list(atributeList);
		return xml_file;
	}
	
	private List<g_sheetAttr> parse_sheetAttr(Row titleRow) {
		List<g_sheetAttr> attributeList = new ArrayList<g_sheetAttr>();
		
		Iterator<Cell> it = titleRow.cellIterator();
		
		Cell cell;
		while (it.hasNext()) {
			if ((cell = it.next()) == null) {
				break;
			}
			
			attributeList.add(new g_sheetAttr(cell.getColumnIndex(), cell.getStringCellValue()));
		}
		
		return attributeList;
	}
	
}
