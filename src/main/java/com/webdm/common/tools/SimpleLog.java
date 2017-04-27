package com.webdm.common.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLog {
	
	public static int level = 2;
	
	public static int LEVEL_DEBUG = 1;
	public static int LEVEL_INFO = 2;
	public static int LEVEL_ERR = 3;
	

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void info(String log){

		if(LEVEL_INFO >= level)
			System.out.println("[ " + formatter.format(new Date()) + " ] [info ] : " + log);
	}
	
	public static void err(String log){

		
		System.err.println("[ " + formatter.format(new Date()) + " ] [error] : " + log);
	}
	public static void debug(String log){

		if(LEVEL_DEBUG >= level)
		System.err.println("[ " + formatter.format(new Date()) + " ] [debug] : " + log);
	}

}
