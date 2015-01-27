package com.hummingbird.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtil {
	public static void saveFile(String filepath,String msg)
	{
		File file=new File(filepath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fileWriter=new FileWriter(file);
			fileWriter.write(msg);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
