package net.itas.tools.xls.base;

import net.itas.tools.xls.base.g_sheet.parse_type;

public class g_base {

	/** 服务端xml名称*/
	protected String xml_server_name;
	
	/** 服务端xml名称*/
	protected String xml_client_name;
	
	
	protected void parse_xls_sheet(String name) {
		String sheet_content = split_xls_sheet(name);
		if (sheet_content.isEmpty()) {
			return;
		}
		
		if (sheet_content.indexOf('@') == -1) {
			this.xml_server_name = sheet_content;
			this.xml_client_name = sheet_content;
		} else {
			char[] chs = sheet_content.toCharArray();
			
			boolean isBegin = false;
			
			StringBuffer sBuf = null;
			StringBuffer cBuf = null;
			for (char ch : chs) {
				if (isBegin) {
					if (sBuf != null) sBuf.append(ch);
					if (cBuf != null) cBuf.append(ch);
				}  else if (ch == '@') {
					isBegin = true;
				}  else if (ch == 's') {
					sBuf = new StringBuffer();
				} else if (ch == 'c') {
					cBuf = new StringBuffer();
				} else {
					throw new IllegalArgumentException("xls name format error:" + name);
				}
			}
			if (sBuf != null) this.xml_server_name = sBuf.toString();
			if (cBuf != null) this.xml_client_name = cBuf.toString();
		}
	}
	
	private String split_xls_sheet(String sheetName) {
		char[] chs = sheetName.toCharArray();

		parse_type type = parse_type.unbegin;
		StringBuffer buf = new StringBuffer();
		for (char ch : chs) {
			if (ch == ' ') {
				continue;
			} else if (ch == '(') {
				if (type == parse_type.begin)
					throw new IllegalArgumentException("xls name format error double [:" + sheetName);
				else 
					type = parse_type.begin;
			} else if (ch == ')') {
				if (type == parse_type.end)
					throw new IllegalArgumentException("xls name format error double ]:" + sheetName);
				else 
					type = parse_type.end;
			} else if (type == parse_type.begin) {
				buf.append(ch);
			} else {
				if (type != parse_type.unbegin) {
					throw new IllegalArgumentException("xls name format error:" + sheetName);
				} 
			}
		}
		
		if (type == parse_type.unbegin) {
			return "";
		} else if (type == parse_type.begin) {
			throw new IllegalArgumentException("xls name format error:" + sheetName);
		} else {
			return buf.toString();
		}
	}
	
}
