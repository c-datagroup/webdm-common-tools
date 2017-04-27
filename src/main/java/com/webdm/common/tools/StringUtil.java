package com.webdm.common.tools;

import org.apache.log4j.Logger;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class StringUtil {

    private static Logger logger = Logger.getLogger(StringUtil.class);
    public static final String NULL_STRING = "";
    private static final String SEARCH_GBK_ENGINE_REGEX = ".*(3721|iask|sogou|163|baidu|soso|zhongsou).*";
    private static final String SEARCH_TWO_LANGUAGE_ENGINE_REGEX = ".*(yahoo|google).*";
    private static final String SEARCH_TWO_LANGUAGE_ENGINE_REGEX_FLAG = "ie=utf";
    private static String whiteList[] = new String[]{"："};
    private static final String UTF = "utf-8";
    private static final String GBK = "gbk";



    /**
     * toString,为空时返回""
     * @param object
     * @return
     */
    public static String toString(Object object){
        return object == null ? NULL_STRING : object.toString();
    }

    public static String[][] StringToArr(String src){

        if(src != null){
            String arr[] = src.split(";");

            int len = arr.length;
            String[][] result = new String[len][];

            String cur[];

            for(int i = 0; i < len; i++){

                if(arr[i] != null){

                    cur = arr[i].split("=|\\{\\}");
                    if(cur.length == 2){
                        result[i] = cur;
                    }

                }



            }
            return result;
        }
        return new String[][]{{"all","all"}};
    }
    /**
     * list转化成json数组
     * @param list
     * @return
     */
    public static  String listToJsonArr(List<Object[]> list){
        StringBuilder r = new StringBuilder(100);
        if(list != null && list.size() > 0){

            r.append("[");
            int len = list.size() - 1;
            Object[]  obj;
            for(int i = 0; i <= len; i++){
                obj = list.get(i);

                r.append("[").append(StringUtil.ArrtoString(obj, true)).append("]");
                if(i < len){
                    r.append(",");
                }
                //}
            }
            r.append("]");
        }
        return r.toString();
    }
    /**
     *
     * @param objects
     * @param obj
     * @return
     */
    public static String ArrtoString(Object[] objects , boolean needQuote){

        if(objects != null && objects.length >= 1){

            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();
            String value = null;
            for(int i = 0 ; i <length; i++){


                if(!needQuote){//是数字不加""
                    if(objects[i] == null){
                        sBuffer.append(0);
                    }else {
                        sBuffer.append(objects[i].toString());
                    }

                }else {
                    sBuffer.append("\"");

                    if(objects[i] == null){
                        sBuffer.append("");
                    }else {
                        sBuffer.append(objects[i].toString());
                    }

                    sBuffer.append("\"");
                }

                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }

    }

    public static String ArrtoString(Object[] objects , boolean obj , int id){

        if(objects != null && objects.length >= 1){
            boolean numFlag = false;

            if(objects[0] instanceof Number || obj){
                numFlag = true;
            }

            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();
            String value = null;
            for(int i = 0 ; i <length; i++){

                if(i >= id){
                    break;
                }
                if(numFlag){//是数字不加""
                    if(objects[i] == null){
                        sBuffer.append(0);
                    }else {
                        sBuffer.append(objects[i].toString());
                    }

                }else {
                    sBuffer.append("\"");

                    if(objects[i] == null){
                        sBuffer.append("");
                    }else {
                        sBuffer.append(objects[i].toString());
                    }

                    sBuffer.append("\"");
                }

                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }

    }

    public static String ArrtoString(double[] objects , boolean obj){

        if(objects != null && objects.length >= 1){




            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();

            for(int i = 0 ; i <length; i++){


                if(obj){//是数字不加""

                    sBuffer.append(objects[i]);


                }else {
                    sBuffer.append("\"");


                    sBuffer.append(objects[i]);


                    sBuffer.append("\"");
                }

                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }

    }


    public static String ArrtoString(long[] objects , boolean obj){

        if(objects != null && objects.length >= 1){




            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();

            for(int i = 0 ; i <length; i++){


                if(obj){//是数字不加""

                    sBuffer.append(objects[i]);


                }else {
                    sBuffer.append("\"");


                    sBuffer.append(objects[i]);


                    sBuffer.append("\"");
                }

                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }

    }
    /**
     * 将数组变成竖向显示
     * @param objects
     * @param quote 是否需要加引号
     * @return
     */
    public static String ArrtoVerticalString(Object[] objects , boolean quote){

        if(objects != null && objects.length >= 1){


            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();
            for(int i = 0 ; i <length; i++){


                if(!quote){//不加""
                    if(objects[i] == null){
                        sBuffer.append(0);
                    }else {
                        sBuffer.append(join(objects[i].toString() , "\\n"));
                    }

                }else {
                    sBuffer.append("\"");

                    if(objects[i] == null){
                        sBuffer.append("");
                    }else {
                        sBuffer.append(join(objects[i].toString() , "\n"));
                    }

                    sBuffer.append("\"");
                }

                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }



    }

    public static String join(String src , String split){

        if(src != null && split != null){
            int length = src.length();
            char [] c= src.toCharArray();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0 ; i < length ; i++){
                //System.out.println(c[i]);
                if(i == length - 1){
                    stringBuffer.append(c[i]);
                }else {
                    stringBuffer.append(c[i]).append(split);
                }

            }
            return stringBuffer.toString();

        }
        return "";
    }

    public static String join(String src[] , String split){

        if(src != null && split != null && src.length > 0){


            StringBuilder result = new StringBuilder();

            for(String s : src){
                if(s != null && s.trim().length() > 0)
                    result.append(s).append(split);

            }
            if(result.length() > split.length()){
                return result.substring(0, result.length() - split.length());
            }


        }
        return "";
    }
    public static String ArrtoString(int[] objects , boolean obj){
        if(objects != null && objects.length >= 1){

            int length = objects.length;

            StringBuffer sBuffer = new StringBuffer();
            for(int i = 0 ; i <length; i++){




                sBuffer.append(objects[i]);


                if(length > 1 && i < length - 1){
                    sBuffer.append(",");
                }
            }
            return sBuffer.toString();

        }else {
            return "";
        }

    }



    /**
     * 组装日期
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public  static String getFullTimeString(String date , String hour , String minute){

        if(date != null && hour != null && minute != null){

            int hourTemp = NumberUtil.getIntValue(hour);
            int minuteTemp = NumberUtil.getIntValue(minute);

            return new StringBuffer(date).append(" ").append(hourTemp < 10 ? "0" + hourTemp : hourTemp)
                    .append(":").append(minuteTemp < 10 ? "0" + minuteTemp : minuteTemp).toString();
        }else {
            return null;
        }
    }
    public  static String getFullTimeString(String hour , String minute){

        if(hour != null && minute != null){

            int hourTemp = NumberUtil.getIntValue(hour);
            int minuteTemp = NumberUtil.getIntValue(minute);

            return new StringBuffer().append(hourTemp < 10 ? "0" + hourTemp : hourTemp)
                    .append(":").append(minuteTemp < 10 ? "0" + minuteTemp : minuteTemp).toString();
        }else {
            return null;
        }
    }

    public  static String getFullTimeString(Object date , Object hour , Object minute){

        if(date != null && hour != null && minute != null){

            return getFullTimeString(date.toString(), hour.toString(), minute.toString());
        }else {
            return null;
        }
    }

    public  static String getFullTimeString(Object hour , Object minute){

        if(hour != null && minute != null){

            return getFullTimeString(hour.toString(), minute.toString());
        }else {
            return null;
        }
    }

    /**
     * 校验转码后的字符是否正确，不正确则返回正确的
     * @param s
     * @return
     */
    public static String checkString(String src  , String s , String encode){

        if(src != null && s != null && encode != null){

            int length = s.length();
            int c ;
            boolean flag = false; //是否需要重新编码
            for(int i = 0 ; i <length ; i++){
                c = (int)s.charAt(i);
                //System.out.println(s.charAt(i) + "=" +c);//\u4e00-\u9FA5
                if((c < 40959) || (c >65280 && c < 65519)){


                }else {
                    flag = true;
                    break;
                }
            }
            String regex = "[\\p{InCJK Unified Ideographs}&&\\P{Cn}]]";


            if(flag){

                //重新编码
                String e = "gbk";
                if("gbk".equals(encode)){

                    e = "utf-8";

                }

                try{
                    s =  URLDecoder.decode(src	 , e);

                }catch (Exception e1) {
                    logger.error("校验时解析出错，key="+ src +",type" + encode);
                    s = NULL_STRING;
                }

                return s;

            }else {
                return s;
            }
        }else {
            return s;
        }

    }
    /**
     * 解码
     * @param string
     * @return
     */
    public  static String decode(String string , String encode){

        if(string != null){
            if(encode == null){
                encode = UTF;
            }
            try {
                return URLDecoder.decode(string , encode);
            } catch (Exception e) {
                // TODO: handle exception
                //logger.error("解析失败，string=" + string);

                try {
                    return normalUrlDecode(string);
                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                    return string;
                }

            }
        }
        return null;
    }

    public static String[][] urlParams = new String[][]{{"%25", "%26"}, {"%26", "&"}, {"%3a",":"},{"%3f","?"},{"%3d","="}};

    public static String normalUrlDecode(String url){

        if(url != null){

            url = url.toLowerCase();

            if(url.indexOf("%") >= 0){

                for(String[] param : urlParams){

                    while(url.indexOf(param[0]) >= 0){

                        url = url.replace(param[0], param[1]);
                    }
                }
            }


        }
        return url;
    }
    /**
     * 解码
     * @param string
     * @return
     */
    public  static String decodeSearchRef(String string , String ref){

        if(string != null){
            string = string.replace("%25", "%");
            String encode = UTF;
            ref = ref.toLowerCase();

            //System.out.println(ref.matches(".*(3721|iask|sogou|163|baidu|soso|zhongsou).*"));
            if( ref.indexOf(SEARCH_TWO_LANGUAGE_ENGINE_REGEX_FLAG) > 0){

                encode = UTF;

            }else if ((ref.matches(SEARCH_GBK_ENGINE_REGEX))){

                encode = GBK;
            }
            try {
                return URLDecoder.decode(string , encode);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("解析失败，string=" + string);
                return string;
            }
        }
        return NULL_STRING;
    }

    /**
     * utf-8 解码
     * @param string
     * @return
     */
    public  static String decode(String string){

        return decode(string, UTF);
    }

    /**
     * utf-8 解码
     * @param string
     * @return
     */
    public  static String encode(String string , String code){

        if(string != null && code != null){
            try{
                return URLEncoder.encode(string, code);
            }catch (Exception e) {
                // TODO: handle exception
                return string;
            }
        }
        return string;
    }
    public static boolean isEmpty(String s){

        if(s == null || "".equals(s.trim())){

            return true;
        }else {
            return false;
        }

    }

    public static String reverBySplit(String string , String split){

        if(string != null && split != null){

            String stringArray [] =  string.split(split);
            if(stringArray != null && stringArray.length > 0){

                StringBuilder result = new StringBuilder();
                int length = stringArray.length - 1;
                for(int i = length ; i >=0 ; i--){

                    result.append(stringArray[i]);
                    if(i != 0){
                        result.append(",");
                    }
                }
                return result.toString();
            }
        }

        return string;
    }

    public static String decodeAndCheck(String keySrc , String type){

        if(keySrc != null){

            String key = null;

            keySrc = keySrc.replace("%25", "%");
            String encode = UTF;
            type = type.toLowerCase();

            //System.out.println(ref.matches(".*(3721|iask|sogou|163|baidu|soso|zhongsou).*"));
            if( type.indexOf(SEARCH_TWO_LANGUAGE_ENGINE_REGEX_FLAG) > 0){

                encode = UTF;

            }else if ((type.matches(SEARCH_GBK_ENGINE_REGEX))){

                encode = GBK;
            }
            try{
                key = URLDecoder.decode(keySrc , encode);
                //logger.info("src=" + keySrc + ",key="+ key +",encode=" + keyTemp);
                key = StringUtil.checkString(keySrc , key , encode);
                //logger.info("check src=" + keySrc + ",key="+ key);

            }catch (Exception e) {
                logger.error("解析出错，key="+ key +",type" + type);
                key = NULL_STRING;
            }

            return key;

        }else {
            return keySrc;
        }

    }

    public static void replaceStringBulider(StringBuilder stringBulider , String src , String target){

        if(stringBulider != null && src != null && target != null){

            int start = stringBulider.indexOf(src);
            while(start >= 0){
                stringBulider.replace(start, start + src.length()	, target);
                start = stringBulider.indexOf(src);
            }
        }
    }

    /**
     * 字符串是否为空
     * @param string
     * @return
     */
    public static boolean ifEmpty(String string){

        if(string == null || "".equals(string.trim())){
            return true;
        }else {
            return false;
        }
    }

    public static boolean getBooleanValue(Integer value){
        if(value != null && value.intValue() == 1){
            return true;
        }else {
            return false;
        }
    }
    public static void main(String[] args) {
        //StringBuilder stringBuilder = new StringBuilder("111{test}222");
        //replaceStringBulider(stringBuilder, "{test}", "000");
//		System.out.println(normalUrlDecode("http://video.baidu.com/v?word=%D5%E6%CF%E0%BB%9B%D5Z12&ct=301989888&rn=20&pn=0&db=0&s=0&fbl=1024"));
        //System.out.println(decode("http://video.baidu.com/v?word=%D5%E6%CF%E0%BB%9B%D5Z12&ct=301989888&rn=20&pn=0&db=0&s=0&fbl=1024", "gbk"));
        //System.out.println(decodeSearchRef("http://video.baidu.com/v?word=%D5%E6%CF%E0%BB%9B%D5Z12&ct=301989888&rn=20&pn=0&db=0&s=0&fbl=1024", "http://video.baidu.com/v?word=%D5%E6%CF%E0%BB%9B%D5Z12&ct=301989888&rn=20&pn=0&db=0&s=0&fbl=1024"));
        String value="http%3A//rp.baidu.com/rp%3Fwd%3D1806398922%25E6%259D%25A8%25E4%25B8%259E%25E7%2590%25B3%25E2%2594%2583%25E6%2583%25B3%25E5%25B9%25B8%25E7%25A6%258F%26tn%3Drp_tieba_image%26staticpage%3Dhttp%253A%252F%252Ftieba.baidu.com%252Ftb%252Fstatic-pb%252Fhtml%252Frp.html%26ie%3DUTF-8%26oe%3DUTF-8%26t1%3D3016%26d1%3D1345542049656%26is_last_page%3D1%26tieba_name%3D%25E6%259D%25A8%25E4%25B8%259E%25E7%2590%25B3%26tieba_cate1%3D%25E5%25A8%25B1%25E4%25B9%2590%25E6%2598%258E%25E6%2598%259F%26tieba_cate2%3D%25E6%25B8%25AF%25E5%258F%25B0%25E4%25B8%259C%25E5%258D%2597%25E4%25BA%259A%25E6%2598%258E%25E6%2598%259F%26uid%3D9df2bdf58ff828e4b1aaeac8%26tid%3D1806398922%26iframe_id%3Dbd_recommend_1%26rp_id%3D110%26rp_sid%3Dde1f3966ea00628b%26rp_url%3Dhttp%253A%252F%252Ftieba.baidu.com%252Fp%252F1806398922%26rp_data%3Dtiebaname%253D%2525E6%25259D%2525A8%2525E4%2525B8%25259E%2525E7%252590%2525B3%2526tiebatitle%253D%2525E2%252594%252583%2525E6%252583%2525B3%2525E5%2525B9%2525B8%2525E7%2525A6%25258F%2525E7%25259A%252584%2525E4%2525BA%2525BA%2525E2%252594%252583%2525E5%25258F%252591%2525E7%25258E%2525B0%2525E4%2525B8%2525AA%2525E5%2525A5%2525BD%2525E7%25258E%2525A9%2525E7%25259A%252584%2525E3%252580%252582%25253D%2525EF%2525BF%2525A3%2525CF%252589%2525EF%2525BF%2525A3%25253D%26rp_refer%3Dhttp%253A%252F%252Ftieba.baidu.com%252Ff%253Fkw%253D%2525D1%2525EE%2525D8%2525A9%2525C%257CCUT";

        System.out.println(StringUtil.decode(value, "utf-8"));
    }

    public static String getDefaultDate(String input , int defaultOffsetValue){
        if(!TimeUtil.isAviDate(input)){
            if(defaultOffsetValue == 99070){
                //周-
                return TimeUtil.getWeekFirstDay(TimeUtil.getDayStand(), 0);

            }else if(defaultOffsetValue == 99076){
                //周日
                return TimeUtil.getSpedayStand(TimeUtil.getWeekFirstDay(TimeUtil.getDayStand(), 0) , 6);

            }
            else if(defaultOffsetValue == 99300){
                //月的第一天
                return TimeUtil.getMonthFirstDay();

            }else if(defaultOffsetValue == 993031){
                //月的最后一天
                return TimeUtil.getMonthLastDay();

            }else {
                return TimeUtil.getOffsetDay(defaultOffsetValue);
            }

        }else {
            return input;
        }
    }

//	public static String getDefaultValue(String input , String defaultValue){
//		if(!StringUtil.ifEmpty(input)){
//			return SqlUtil.formatSql(input);
//		}else {
//			return defaultValue;
//		}
//	}

//	public static int pinyingCompareTo(String s1 , String s2){
//		
//		int len1 = s1.length();
//		int len2 = s2.length();
//		int n = Math.min(len1, len2);
//		char v1[] = s1.toCharArray();
//		char v2[] = s2.toCharArray();
//
//		String r1 = null;
//		String r2 = null;
//
//		int offset = 0;
//		int k = 0;
//
//		while (k < n) {
//
//			r1 = getPinying(v1[k]);
//			r2 = getPinying(v2[k]);
//			offset = r1.compareTo(r2);
//			if (offset != 0) {
//				return offset;
//			}
//			k++;
//		}
//
//		return len1 - len2;
//		
//		
//	}

//	public static String getPinying(char c){
//		
//		
//		try {
//			String r[] = PinyinHelper.toHanyuPinyinStringArray(c, hanYuPinOutputFormat);
//			if(r != null && r.length >= 1 ){
//				return r[0];
//			}
//		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return NULL_STRING;
//		}
//		return NULL_STRING;
//
//	}


}
