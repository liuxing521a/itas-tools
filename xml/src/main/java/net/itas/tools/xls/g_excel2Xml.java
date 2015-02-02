package net.itas.tools.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;

import net.itas.tools.xls.base.g_type;
import net.itas.tools.xls.util.XlsUtils;


public class g_excel2Xml {

	/** excel xls解析器*/
	private g_excel xls_parser;
	
	/** excel xlsx解析器*/
	private g_excel xlsx_parser;
	
	public g_excel2Xml() {
		this.xls_parser = new g_excelXls();
		this.xlsx_parser = new g_excelXlsx();
	}
	
	public void excel2Xml(g_type type, String srcdir, String disdir) throws Exception {
		File file = new File(srcdir);
		if (!file.isDirectory()) {
			throw new FileNotFoundException("dir not exist:" + srcdir);
		}
		
		File[] fileList = file.listFiles();
		for (File f: fileList) {
			if (!f.isFile()) {
				continue;
			}
			
			if (f.getName().endsWith(".xls")) {
				sheet2XmlFile(type, srcdir, xls_parser, f);
				continue;
			}
			
			if (f.getName().endsWith(".xlsx")) {
				sheet2XmlFile(type, srcdir, xlsx_parser, f);
				continue;
			}
		}
	}
	
	
	private void sheet2XmlFile(g_type type, String disdir, g_excel parser, File file) throws Exception {
		Map<String, String> XML = parser.g_sheet2Xml(type, file);
			
		for (Entry<String, String> entry : XML.entrySet()) {
			XlsUtils.writeFile(getFullFilePath(disdir, entry.getKey()) , entry.getValue());
		}
		
	}
	
	private File getFullFilePath(String dir, String fileName) {
		char lastChar = dir.charAt(dir.length() - 1);
		if (lastChar == '/') {
			return new File(dir + fileName + ".xml");
		} else {
			return new File(dir + "/" + fileName + ".xml");
		}
	}
	
}
