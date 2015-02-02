package com.godwan.tools;


public class LanguegeMain {

	
	public static void main(String[] args) throws Exception {
		try {
			String xls_dir = args[0];
			String to_dir = args[1];
			
			XmlWriter xmlWrite = new XmlWriter();
			xmlWrite.loadExcel(xls_dir);
			xmlWrite.handler(to_dir);
		} finally {
			System.exit(0);
		}
	}
}
