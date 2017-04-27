package com.webdm.common.tools;

import java.security.*;

public class Md5 {

	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));

			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		/*String[] ss = { "tvle.123" };
		
		for (int i = 0; i < ss.length; i++) {
			System.out.println(MD5(ss[i]));
		}*/
        String err="e,3,4,";
        	System.out.println(err.substring(0, err.length()-1));
	}
}