package com.godwan.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class XlsUtils {
	
	public static Properties readFile(String fileName)
	{
		BufferedReader read = null;
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8");
			read = new BufferedReader(isr);

			Properties properties = new Properties();
			properties.load(read);

			return properties;
		} catch (Exception e) {
			throw new RuntimeException("", e);
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (read != null) {
					read.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
