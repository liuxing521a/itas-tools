package com.godwan.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;

import com.godwan.tools.xls.XlsReader;

public class XmlWriter {
	
	/** excel读取者*/
	private XlsReader xlsreader = new XlsReader();
	
	
	public void loadExcel(String dir) throws Exception {
		xlsreader.loadExcel(dir);
	}
	
	public void handler(String classDir) throws IOException {
		File javaFile = new File(getJavaPath(classDir));
		if (javaFile.exists()) {
			javaFile.delete();
		}
		javaFile.createNewFile();
		
		File cppFile = new File(getCppPath(classDir));
		if (cppFile.exists()) {
			cppFile.delete();
		}
		cppFile.createNewFile();
		
		File cppText = new File(getTextPath(classDir));
		if (cppText.exists()) {
			cppText.delete();
		}
		cppText.createNewFile();
		
		HSSFSheet sheet;
		
		sheet = xlsreader.getHSSFSheet("lauguage");
		handlerRow(sheet, javaFile, cppFile, cppText);
	}
	
	private void handlerRow(HSSFSheet sheet, File javaFile, File cppFile, File cppTxt) throws IOException {
		if (sheet == null) {
			throw new RuntimeException("not fount xls file");
		}
		
		StringBuilder javaBuf = new StringBuilder();
		StringBuilder cppBuf = new StringBuilder();
		StringBuilder textBuf = new StringBuilder();
		
		javaBuf.append("package net;");
		javaBuf.append("\n\n");
		javaBuf.append("public enum Language {\n");
		

		cppBuf.append("#pragma once");
		cppBuf.append("\n\n");
		cppBuf.append("struct  CLanguage {");
		
		int total = sheet.getLastRowNum();
		HSSFRow row; 
		String content;
		for (int i = 1; i <= total; i++) {
			row = sheet.getRow(i);
			content = getCell(row, 0);
			if (content == null) {
				continue;
			}
			
			javaBuf.append("\n\t");
			javaBuf.append(content);
			javaBuf.append("(");
			javaBuf.append(i);
			javaBuf.append("),");
			
			cppBuf.append("\n\t");
			cppBuf.append("static const short ");
			cppBuf.append(content);
			cppBuf.append(" = ");
			cppBuf.append(i);
			cppBuf.append(";");

			content = getCell(row, 1);
			textBuf.append(content);
			textBuf.append("^");
		}
		
		javaBuf.append(";");
		javaBuf.append("\n\n\tprivate final short value;");
		javaBuf.append("\n\n\tprivate Language(int value) {\n\t\tthis.value=(short)value;\n\t}");
		javaBuf.append("\n\n\tpublic short getValue() {\n\t\treturn this.value;\n\t}");
		javaBuf.append("\n}");
		cppBuf.append("\n};");
		
		Charset charSet = Charset.forName("UTF-8");
	
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(javaFile), charSet);
		out.write(javaBuf.toString());
		out.flush();
		out.close();

		out = new OutputStreamWriter(new FileOutputStream(cppFile), charSet);
		out.write(cppBuf.toString());
		out.flush();
		out.close();
		
		out = new OutputStreamWriter(new FileOutputStream(cppTxt), charSet);
		out.write(textBuf.toString());
		out.flush();
		out.close();
	}
	
	/**获取单元格内容*/
	public String getCell(HSSFRow row, int index) {
		if (row == null) {
			return "";
		}
		
		HSSFCell cell = row.getCell(index);
		if (cell == null) {
			return "";
		}
		
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue().trim();
	}
	
	private String getJavaPath(String dir) {
		return String.format("%s%sjava%snet%sLanguage.java", dir, File.separator, File.separator, File.separator);
	}
	
	public String getCppPath(String dir) {
		return String.format("%s%scpp%slanguage.h", dir, File.separator, File.separator);
	}
	
	public String getTextPath(String dir) {
		return String.format("%s%scpp%slanguage.text", dir, File.separator, File.separator);
	}

}
