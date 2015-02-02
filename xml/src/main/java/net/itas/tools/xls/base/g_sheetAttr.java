package net.itas.tools.xls.base;


/** 模版xml中属性信息*/
public class g_sheetAttr extends g_base {
	
	/** xls描述名称*/
	private String xls_title_name;
	
	/** 所在excel里面的列*/
	private int sheet_title_column;
	
	public g_sheetAttr(int sheet_title_column, String sheet_title_name) {
		this.xls_title_name = sheet_title_name;
		this.parse_xls_sheet(sheet_title_name);
		this.sheet_title_column = sheet_title_column;
		
	}

	public String get_xls_title_name() {
		return xls_title_name;
	}

	public void set_xls_title_name(String xls_title_name) {
		this.xls_title_name = xls_title_name;
	}

	public String getXml_attribute_server_name() {
		return xml_server_name;
	}

	public String getXml_attribute_client_name() {
		return xml_client_name;
	}

	public int getSheet_title_column() {
		return sheet_title_column;
	}

	public void setSheet_title_column(int sheet_title_column) {
		this.sheet_title_column = sheet_title_column;
	}

	@Override
	public String toString() {
		return String.format("serverAttribute:%s, clientAttribute:%s", xml_server_name, xml_client_name);
	}
}
