package net.itas.tools.xls.base;

import java.util.List;


/** 模版中xml信息*/
public class g_sheet extends g_base {
	
	enum parse_type {
		unbegin,
		begin,
		end,
	}
	
	/** xls sheet名称*/
	private String xls_sheet_name;
	
	/** 属性列表*/
	private List<g_sheetAttr> xls_attribute_list;

	public g_sheet(String sheetName) {
		this.xls_sheet_name = sheetName;
		this.parse_xls_sheet(sheetName);
	}

	public String getXls_sheet_name() {
		return xls_sheet_name;
	}

	public void setXls_sheet_name(String xls_sheet_name) {
		this.xls_sheet_name = xls_sheet_name;
	}

	public String getXml_file_name_server() {
		return this.xml_server_name;
	}

	public String getXml_file_name_client() {
		return this.xml_client_name;
	}

	public List<g_sheetAttr> getXls_attribute_list() {
		return xls_attribute_list;
	}

	public void setXls_attribute_list(List<g_sheetAttr> xls_attribute_list) {
		this.xls_attribute_list = xls_attribute_list;
	}

	@Override
	public String toString() {
		return String.format("sheetName:%s, serverName:%s, clientName:%s\n attributes:%s", 
				xls_sheet_name, xml_server_name, xml_client_name, xls_attribute_list);
	}
}
