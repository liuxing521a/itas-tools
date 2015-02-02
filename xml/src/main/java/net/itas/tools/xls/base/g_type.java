package net.itas.tools.xls.base;


public enum g_type {
	
	xml_server('s'),
	xml_client('c'),
	xml_config('i'),
	txt_langge('l'),
	;
	
	public final char type;
	
	g_type(char ch) {
		this.type = ch;
	}
	
	public static g_type valueOf(char ch) {
		switch (ch) {
		case 's': return xml_server;
		case 'c': return xml_client;
		case 'i': return txt_langge;
		case 'l': return txt_langge;
		default: throw new RuntimeException("supported type:['a','s','c','l','i'], unkown type:" + ch);
		}
	}
}
