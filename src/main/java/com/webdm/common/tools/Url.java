package com.webdm.common.tools;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;



@SuppressWarnings("static-access")

public class Url {
	
	private String url;
	private Map<String , String> params ;
	public static final String NULLSTRING = "-";
	public static final int NULLINT = -9999;
	private static Map<String, ParamCheck> paramsCheckMap = null;
	private static String parmaSplit = "&";
	private String reqDir;
	private String reqFile;
	//private boolean ignore;//�Ƿ����key�Ĵ�Сд
	//public boolean decodeFlag = false;
//	static{
////
////		System.out.println("����cidУ����");
////		addParamCheck("cid", new CidParamCheck());
////		System.out.println("����verУ����");
////		addParamCheck("ver", new VerParamCheck());
//
//	}
	
	public Url(String url){
		this(url, parmaSplit, false);
	}

	public Url(){}
	/**
	 * 
	 * @param url
	 * @param ignore �Ƿ����key�Ĵ�Сд
	 */
	public Url(String url, boolean ignore){
		this(url, parmaSplit, ignore);
	}
	
	public void parseUrl(String url, String parmaSplitFlag, boolean ignore){
		

		if(parmaSplitFlag != null)
			parmaSplit = parmaSplitFlag;
		
		if(url != null){
			
			if(url.indexOf("?") >= 0){
				//�е�ַ
				String temp[] = url.split("\\?");
				if(temp != null && temp.length >= 2){
					this.url = temp[0];	
					int length = temp.length;
					if(length == 2){
						this.params = this.parse(temp[1], ignore);
					}else{
						StringBuilder stringBuilder = new StringBuilder();
						for(int i = 1 ; i < length ; i++){
							stringBuilder.append(temp[i]).append("&");
							
						}
						
						this.params = this.parse(stringBuilder.substring(0, stringBuilder.length() - 1), ignore);
					}
				}
			}else{
				this.url = this.NULLSTRING;
				this.params = this.parse(url, ignore);
			}
		}else{
			this.url = this.NULLSTRING;
		}
	}
	/**
	 * 
	 * @param url
	 * @param parmaSplitFlag
	 * @param ignore �Ƿ����key�Ĵ�Сд
	 */
	public Url(String url, String parmaSplitFlag, boolean ignore) {
		
		parseUrl(url, parmaSplitFlag, ignore);
	}
	
	public void setVal(String key, String value){

		if(params == null ){
			params=new HashMap<String,String>();
		}
		params.put(key, value);
	}
	
	
	public Map<String, String> getParamsMap(){
		
		return params;
	}
	
	public String join(String arr[], String split, int id){
		
		if(arr != null){
			int len = arr.length;
			if(len > id){
				
				StringBuilder r = new StringBuilder();
				for(int i = id; i < len; i++){
					
					r.append(arr[i]).append(split);
				}
				if(r.length() > split.length()){
					return r.substring(0, r.length() - split.length());
				}else{
					return StringUtil.NULL_STRING;
				}
			}
		}
		return StringUtil.NULL_STRING;
	}
	/**
	 * ��һ�β���Ҫ���ã���ʼ��ʱ�Զ�����
	 * @param url
	 */
	public Map<String , String > parse(String url, boolean ignore){
		
		Map<String , String > map = new HashMap<String , String>();
		if(url != null && url.indexOf("=") > 0){
			String params [] = url.split(parmaSplit);//ȡ�����в���
			String paramArr[] = null;
			ParamCheck paramCheck = null;
			String value = null;
			for(String param : params){
				if(param != null && param.indexOf("=") > 0){
					paramArr = param.split("=");
					if(ignore){
						 paramArr[0] =  paramArr[0].toLowerCase();
					}
					if(paramArr.length >= 2 && !map.containsKey(paramArr[0])){
						if(paramsCheckMap != null && paramsCheckMap.size() > 0)
						paramCheck = paramsCheckMap.get(paramArr[0]);
						value = join(paramArr, "=", 1);
						if(paramCheck != null){
							if(paramCheck.ifAvi(value)){
								
								map.put(paramArr[0], value);
							}
						}else {
							map.put(paramArr[0], value);
						}
					}
				}
			}			
		}else{
			String params [] = url.split(parmaSplit);//ȡ�����в���
			ParamCheck paramCheck = null;
			for(int i=0;i<params.length;i++){
					if(!map.containsKey((i+1))){
						if(paramsCheckMap != null && paramsCheckMap.size() > 0)
						paramCheck = paramsCheckMap.get(params[i]);
						if(paramCheck != null){
							if(paramCheck.ifAvi(params[i])){
								map.put(""+(i+1),params[i]);
							}
						}else {
							map.put(""+(i+1),params[i]);
						}	
					}
			}			
		}
		return map;	
	}
	


	private String decode(String value){
		if(value != null){
			try {
				return URLDecoder.decode(value);
			} catch (Exception e) {
				return value.replace("%20", " ");
			}
		}else {
			return value;
		}
		
	}
	
	/**
	 * ��ȡ������Դ�ļ���
	 * @return
	 */
	public  String getRequestFile(){
		
		if(reqFile == null){
			
			String src = url;
			int end = url.indexOf("?") ;
			if(end != -1){
				src = src.substring(0, end);
			}
			int start = src.lastIndexOf("/");
			if(start > end){
				src = src.substring(start + 1);
			}else{
				src = NULLSTRING;
			}
			reqFile = src;
		}
		
		
		return reqFile;
	}
	
	/**
	 * ��ȡ������ԴĿ¼��
	 * @return
	 */
	public  String getRequestDir(){
		
		
		if(reqDir == null){
			reqDir = NULLSTRING;
			int end = url.lastIndexOf("/") ;
			if(end > 0){
				int start = url.lastIndexOf("/", end - 1) + 1;
				if(start != -1 && end > start){
					reqDir =  url.substring(start , end);
				}
				
			}
			
		}

		return reqDir;
	}
	public boolean ifExist(String key){
		if(key == null){
			return false;
		}else {
			return params.containsKey(key);
		}
	}
	public String getParam(String key){
		
		return getParam(key, NULLSTRING, false, null);
	}
	
	public String getParam(String key, boolean needDocede, String charset){
		
		return getParam(key, NULLSTRING, needDocede, charset);
	}
	
	public String getParam(String key, boolean needDocede){
		
		return getParam(key, NULLSTRING, needDocede, "utf-8");
	}
	private  String  escape (String src)
	 {
	  int i;
	  char j;
	  StringBuffer tmp = new StringBuffer();
	  tmp.ensureCapacity(src.length()*6);

	  for (i=0;i<src.length() ;i++ )
	  {

	   j = src.charAt(i);

	   if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
	    tmp.append(j);
	   else
	    if (j<256)
	    {
	    tmp.append( "%" );
	    if (j<16)
	     tmp.append( "0" );
	    tmp.append( Integer.toString(j,16) );
	    }
	    else
	    {
	    tmp.append( "%u" );
	    tmp.append( Integer.toString(j,16) );
	    }
	  }
	  return tmp.toString();
	 }

	 public static String  unescape (String src)
	 {
	  StringBuffer tmp = new StringBuffer();
	  tmp.ensureCapacity(src.length());
	  int  lastPos=0,pos=0;
	  char ch;
	  while (lastPos<src.length())
	  {
	   pos = src.indexOf("%",lastPos);
	   if (pos == lastPos)
	    {
	    if (src.charAt(pos+1)=='u')
	     {
	     ch = (char)Integer.parseInt(src.substring(pos+2,pos+6),16);
	     tmp.append(ch);
	     lastPos = pos+6;
	     }
	    else
	     {
	     ch = (char)Integer.parseInt(src.substring(pos+1,pos+3),16);
	     tmp.append(ch);
	     lastPos = pos+3;
	     }
	    }
	   else
	    {
	    if (pos == -1)
	     {
	     tmp.append(src.substring(lastPos));
	     lastPos=src.length();
	     }
	    else
	     {
	     tmp.append(src.substring(lastPos,pos));
	     lastPos=pos;
	     }
	    }
	  }
	  return tmp.toString();
	 }
	 
	 
	
	 
	private String getDecodeValue(String value, String charset){
		
		try {
			if(value.indexOf("%") >= 0){
				
				while(value.indexOf("%25") >= 0){
					value = value.replace("%25", "%");
				}
				
				if(value.indexOf("%u") >= 0){
					
					return unescape(value);
				}else{
					
					return StringUtil.decode(value, charset);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
		
	}
	
	
	public String getParam(String key, String defaultValue){
		return getParam(key, defaultValue, false, null);
	}
	public String getParam(String key, String defaultValue, boolean needDocede, String charset){
		
		if(key != null && params != null){
			String value = params.get(key);
			
			if(value != null && !"".equals(value.trim()) && !"undefined".equals(value) && !"null".equals(value)){
				
				if(needDocede){
					
					return getDecodeValue(value ,  charset);
				}else{
					return value.replace("%20", " ");
				}
				
			}else {
				return defaultValue;
			}
			
			
		}else{
			return defaultValue;
		}
	}
	
	public int getIntParam(String key){
		
		return getIntParam(key, NULLINT);
	}
	public int getIntParam(String key, int defaultValue){
		
		if(key != null && params != null){
			String value = params.get(key);
			if(value != null && !"".equals(value.trim()) && !"undefined".equals(value) && !"null".equals(value) && NumberUtil.isNum(value)){
				try {
					return Integer.parseInt(value);
				} catch (Exception e) {
					System.out.println(key + "=" + value + ",e=" + e.toString());
					e.printStackTrace();
					return defaultValue;
					
					
				}
				
			}else{
				return defaultValue ;
				
			}
			 
		}else{
			return defaultValue;
		}
	}
	
	
	public static void addParamCheck(String name , ParamCheck paramCheck){
		if(paramsCheckMap == null){
			
			paramsCheckMap = new HashMap<String, ParamCheck>();
		}
		paramsCheckMap.put(name, paramCheck);
	}
	
	
	public static String getDomainName(String url){
		  //http://v.youku.com/v_show/id_XMzU3ODU3NzEy.html
		   url=url.substring(7,url.length());
		   url=url.substring(0,url.indexOf("/"));
		   String[] strs=url.split("\\.");
		   
		   String domain="";
		   if(strs!=null){
			   if(strs.length>=3){
				   domain=strs[1];//http://v.youku.com/v_show/id_XMzU3ODU3NzEy.html
			   }else{
				   domain=strs[0];//http://youku.com/v_show/id_XMzU3ODU3NzEy.html
			   }
			   
			   System.out.println("domain="+domain+"    url="+url);
		   }
		   
		return domain;
	}

    public String getUrl() {
        return url;
    }

    public static void main(String[] args) {
		
//		addParamCheck("cid", new CidParamCheck());
//		addParamCheck("ver", new VerParamCheck());
//		String value = "60.247.30.1 1289265004 /vq/play?error=0&utime=400&ksp=0&videorate=30309&location=http%3A%2F%2F220.181.23.43%2F445%2F17%2F35%2Fd2oieHlARvarkVfkzpAwCWQ.mp4%3Fvid%3D17602567%26lp%3D8087%26lroot%3D%2F6%26start%3D76%26srchost%3D123.150.196.82%26srcroot%3D%2F5%26s%3D0%26fun%3D1%26tm%3D1289352600%26key%3Def9f854e3ea172646a0641aa9afa564b%26lr%3D0%26nlh%3D0%26check%3D1%26diskid%3D55%26id%3Dku6_vod%26usrip%3D60.247.30.1%26uloc%3D1.90101.0.1%26ext%3D.mp4&gone=154001&split=3&time=419&ver=OUT%202.5.6&cid=NaN&vid=j2D9XNtaGscVoAsg&gutime=100&drag=1&uuid=3cb929e6-36c6-2cb1-df2b-2e2da8a22a26&rnd=0.6414367943070829";
//		String s [] = RegexUtil.getResult(value.toString(),
//				"([\\d\\.]+) (\\d+) /vq/(\\w+)(\\?.*)"
//				,false);
//		System.out.println(Arrays.toString(s));
//		Url url = new Url(s[3]);
//		System.out.println(URLEncoder.encode("&"));
//		System.out.println(url.getParam("ver"));
//		Url url = new Url("http://dc.letv.com/live/init?test=123&test2=%u6d4b%u8bd5s%s");
//		System.out.println(url.getRequestFile());
//		System.out.println(url.getDomainName("http://dc.letv.com/live/init?test=123&test2=%B9%D8%B1%D5%CD%C6%B2%E2%D6%B4%D0%D0"));
//		
//
//		System.out.println(url.getParam("test2", true, "gbk"));

			
		System.out.println(new Url("?Test=1234").getParam("test"));
		//System.out.println(StringUtil.decode("http%3A//video.baidu.com/s%3Fn%3D10%26word%3D%CF%C4%C4%BF%D3%D1%C8%CB%%26f%3D1%26ns%3D0%26se%3D%26site%3Dletv.com%26id%3DC1151657189158855536%26fr%3D"));
	}

	
	public static class CidParamCheck implements ParamCheck{
		private static final String regex = "\\d+"; 
		public boolean ifAvi(String value) {
			if(value != null && value.matches(regex)){
				return true;
			}
			return false;
		}
		
	}
	
	public static class VerParamCheck implements ParamCheck{
		private static final String regex = "^[a-zA-Z]+.*"; 
		public boolean ifAvi(String value) {
			if(value != null && value.matches(regex)){
				return true;
			}
			return false;
		}
		
	}

	public void setReqDir(String reqDir) {
		this.reqDir = reqDir;
	}

	public void setReqFile(String reqFile) {
		this.reqFile = reqFile;
	}
	
	
	
}
