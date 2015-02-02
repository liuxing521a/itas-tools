package net.itas.tools.xls;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.itas.tools.xls.base.g_sheet;
import net.itas.tools.xls.base.g_type;

import org.apache.poi.ss.usermodel.Sheet;

abstract class g_excel {
	
	private static final String xml_title = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/** 解析excel标题，生成对应属性*/
	private g_parseSheetTitle parseTitle;
	
	/** 解析excel内容，生成对应xml*/
	private g_parseSheetText parseText;
	
	g_excel() {
		this.parseTitle = new g_parseSheetTitle();
		this.parseText = new g_parseSheetText();
	}
	
	abstract List<Sheet> loadSheet(File file)throws Exception ;
	
	Map<String, String> g_sheet2Xml(g_type type, File file) throws Exception {
		List<Sheet> sheetList = loadSheet(file);
		
		Map<String, String> xmlList = new HashMap<String, String>();
		for (Sheet sheet : sheetList) {
			g_sheet g_sheet = parseTitle.parse_sheetTitle(sheet);
			
			List<String> xmlTextList = null;
			if (type == g_type.xml_client && g_sheet.getXml_file_name_client() != null) {
				xmlTextList = parseText.parse_sheetTextClient(sheet, g_sheet);
				xmlList.put(g_sheet.getXml_file_name_client(), toXml(xmlTextList, g_sheet.getXml_file_name_client()));
			}
			
			if (type == g_type.xml_server && g_sheet.getXml_file_name_server() != null) {
				xmlTextList = parseText.parse_sheetTextServer(sheet, g_sheet);
				xmlList.put(g_sheet.getXml_file_name_server(), toXml(xmlTextList, g_sheet.getXml_file_name_server()));
			}
		}
		
		return xmlList;
	}
	
	private String toXml(List<String> xmlTextList, String fileName) {
		StringBuffer xmlBuf = new StringBuffer();
		
		xmlBuf.append(xml_title);
		xmlBuf.append("\n\r<" + fileName + "List>");
		
		for (String xmlText : xmlTextList) {
			xmlBuf.append("\n\r\t" + xmlText);
		}
		
		xmlBuf.append("\n\r</" + fileName + "List>");
		return xmlBuf.toString();
	}
	
}
