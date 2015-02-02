package net.itas.tools;

import net.itas.tools.xls.g_excel2Xml;
import net.itas.tools.xls.base.g_type;


public class ToolsMain {

	private ToolsMain() {
	}

	
	public static void main(String[] args) throws Exception {
		String typeSt = args[0].trim();
		String srcDir = args[1].trim();
		String desDir = args[2].trim();
		
		g_type type;
		if ("server".equals(typeSt)) {
			type = g_type.xml_server;
		} else if ("client".equals(typeSt)) {
			type = g_type.xml_client;
		} else {
			throw new RuntimeException("error generate type:" + typeSt);
		}
		
		g_excel2Xml excel2xml = new g_excel2Xml();
		excel2xml.excel2Xml(type, srcDir, desDir);
	}
}
