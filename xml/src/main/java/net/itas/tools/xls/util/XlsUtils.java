package net.itas.tools.xls.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class XlsUtils {

	public static void writeFile(File file, String content) throws IOException 
	{
		OutputStreamWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		}  finally  
		{
			if (bufferedWriter != null)
			{
				bufferedWriter.close();
			}
			
			if (fileWriter != null)
			{
				fileWriter.close();
			}
		}
	}
}
