package com.webdm.common.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;




public class TimeUtil {
	public static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat hourFormat = new SimpleDateFormat("yyyyMMddHH");
	private static SimpleDateFormat dayFormatStand = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyyMMddHHmm");
	private static SimpleDateFormat fiveMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat apacheFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",Locale.US);
	
	private static SimpleDateFormat fullFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	private static final String NULL_STRING = "-";
	
	public static int getHourId(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

    public static Date formatTime(String time, String format){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
	public static String formatApacheTime(String time){
		
		
		try {
			return fullFormatter.format(apacheFormatter.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NULL_STRING;
	}
	
	public static long formatApacheTimeToSec(String time){
		
		
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(apacheFormatter.parse(time));
			return calendar.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static String getDayStr(){
		
		return dayFormat.format(new Date());
		
	}
	
	public static String getDayStr(Date date){
		
		return dayFormat.format(date);
		
	}
	public static String getDayStand(){
		
		return dayFormatStand.format(new Date());
		
	}
	
	//public int getDayOffset(string from, String String to)
	/**
	 * 2010-08-22
	 * @return
	 */
	public static String getYesterdayStand(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	public static Date getDate(String dayTime){
		
		if(dayTime != null){
			
			try {
				return dayFormat.parse(dayTime.replace("-", ""));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * ǰ���� 20101016
	 * @return
	 */
	public static String getPreTwo(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-2);
		return dayFormat.format(calendar.getTime());
		
	}
	
	/**
	 * ǰ���� 20101016
	 * @return
	 */
	public static String getCommonPreTwo(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-2);
		return dayFormatStand.format(calendar.getTime());
		
	}
	public static Date getYesterdayDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		return calendar.getTime();
		
	}
	public static boolean isFirstDayOfWeek(String dayTime){
		Calendar calendar = Calendar.getInstance();
		try {
			//calendar.setTime(new Date(dayTime.replace("-", "/")));
			calendar.setTime(dayFormatStand.parse(dayTime));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return calendar.get(Calendar.DAY_OF_WEEK)==2;
		
	}
	
	public static boolean isFirstDayOfMonth(String dayTime){
		if(dayTime != null && dayTime.length() >= 10){
			//System.out.println(dayTime.substring(8, 10));
			return NumberUtil.getIntValue(dayTime.substring(8, 10)) == 1;
		}else {
			return false;
		}
		
		
	}
	
	/**
	 * yyyy-MM-dd
	 * @param offset
	 * @return
	 */
	public static String getSpedayStand(int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		calendar.add(Calendar.DATE,offset);
		return dayFormatStand.format(calendar.getTime());
		
	}
		
	/**
	 * yyyy-MM-dd
	 * @param offset
	 * @return
	 */
	public static String getOffsetDay(int offset){
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DAY_OF_MONTH,offset);

		
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * yyyy-MM-dd
	 * @param offset < 0 ��ʾ֮���ʱ��
	 * @return
	 */
	public static String getSpedayStand(String from, int offset){
		if(from.indexOf("-") == -1){
			from = getDayTime(from);
		}
		
		Calendar calendar = Calendar.getInstance();
		try {
			//calendar.setTime(new Date(from.replace("-", "/")));
			calendar.setTime(dayFormatStand.parse(from));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		calendar.add(Calendar.DATE,offset);
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * yyyyMMdd
	 * @param offset < 0 ��ʾ֮���ʱ��
	 * @return
	 */
	public static String getSpedayNormal(String from , int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dayFormat.parse(from));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		calendar.add(Calendar.DATE,offset);
		
		return dayFormat.format(calendar.getTime());
		
	}
	
	public static String getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return dayFormatStand.format(calendar.getTime());
		
	}
	/**
	 * ��ȡ�µĵ����һ��
	 * @param flag �͸��µĲ��
	 * @return
	 */
	public static String getMonth(String from , int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dayFormatStand.parse(from));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, offset);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * ��ȡ�µĵ����һ��
	 * @param flag �͸��µĲ��,offset 0��ʾ���£�-1��ʾ�ϸ���
	 * @return
	 */
	public static String getMonthFirstDay(String from , int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dayFormatStand.parse(from));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, offset);		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		return dayFormatStand.format(calendar.getTime());
				
	}
	
	/**
	 * ��ȡ��ǰ�����·ݵĵ�һ��
	 * 
	 * @return
	 */
	public static String getMonthFirstDay(){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH	, 1);

		return dayFormatStand.format(calendar.getTime());
		
		
	}
		
	/**
	 * ��ȡ�µ����һ��
	 * 
	 * @return
	 */
	public static String getMonthLastDay(){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH	, 1);
		calendar.add(Calendar.MONTH	, 1);
		calendar.add(Calendar.DAY_OF_MONTH,   -1); 

		return dayFormatStand.format(calendar.getTime());
				
	}
	
	/**
	 * ������ڻ�ȡN����ǰ�����һ��,offset -1 ��ʾ���£�1Ϊ����
	 * 
	 * @return
	 */
	public static String getMonthLastDay(String from,int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dayFormatStand.parse(from));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_MONTH	, 1);  //����1��
		calendar.add(Calendar.MONTH,offset+1);     //����һ��
		calendar.add(Calendar.DAY_OF_MONTH,   -1); //�������һ��
		
		return dayFormatStand.format(calendar.getTime());
		
		
	}
	
	/**
	 * ��ȡ�ܵ����һ��
	 * @param flag �͸��µĲ��
	 * @return
	 */
	public static String getWeek(String from , int offset){
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date(from.replace("-", "/")));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
			
		int mark = calendar.get(Calendar.DAY_OF_WEEK);
		//System.out.println(mark);
		
		if(mark == 1){
			//offset = offset + 1;
			calendar.add(Calendar.WEEK_OF_MONTH, offset);
		}else {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			calendar.add(Calendar.WEEK_OF_MONTH, offset);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		//calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		
		//System.out.println(calendar.getFirstDayOfWeek());
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * �����������ܵĵ�һ��
	 * @param flag �͸��µĲ��,offset 0��ʾ���ܣ�-1��ʾ����
	 * @return
	 */
	public static String getWeekFirstDay(String from , int offset){
		Calendar calendar = Calendar.getInstance();
		try {
			//calendar.setTime(new Date(from.replace("-", "/")));
			calendar.setTime(dayFormatStand.parse(from));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
					
		int mark = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(mark == 1){
			calendar.add(Calendar.WEEK_OF_MONTH, offset-1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}else {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.WEEK_OF_MONTH, offset);
		}
				
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * �����������ܵĵ�һ��
	 * @param flag �͸��µĲ��,offset 0��ʾ���ܣ�-1��ʾ����
	 * @return
	 */
	public static String getWeekFirstDay(){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
					
		int mark = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(mark == 1){
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}else {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
				
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * �����������ܵ����һ��
	 * @param flag �͸��µĲ�� ,offset 0��ʾ���ܣ�-1��ʾ����
	 * @return
	 */
	public static String getWeekLastDay(String from , int offset){
		Calendar calendar = Calendar.getInstance();
		try {
			//calendar.setTime(new Date(from.replace("-", "/")));
			calendar.setTime(dayFormatStand.parse(from));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
					
		int mark = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(mark == 1){
			calendar.add(Calendar.WEEK_OF_MONTH, offset);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
		}else {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);			
			calendar.add(Calendar.WEEK_OF_MONTH, offset+1);
		}
				
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	/**
	 * �����������ܵ����һ��
	 * @param flag �͸��µĲ�� 
	 * @return
	 */
	public static String getWeekLastDay(){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new Date());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
					
		int mark = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(mark == 1){
			calendar.add(Calendar.WEEK_OF_MONTH, 0);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
		}else {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);			
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
		}
				
		return dayFormatStand.format(calendar.getTime());
		
	}
	
	public static int getCurrentHour(){
		Calendar calendar = Calendar.getInstance();
		
		return calendar.get(Calendar.HOUR_OF_DAY);
		
	}
	
	public static String getMinuteStrs(){
		
		return minuteFormat.format(new Date());
		
	}
	
	
	/**
	 * 20100809 ->>2010-08-09
	 * @param time
	 * @return
	 */
	public static String getDayTime(String time){
		
		if(time != null && time.length() >= 8){
			
			if(time.length() > 8){
				time = time.substring(0 , 8);
			}
			StringBuffer temp  = new StringBuffer(time);
			temp.insert(4, "-");
			temp.insert(7, "-");
			return temp.toString();
			
		}
		return null;
		
		
	}
	
	public static int getHourId(String time){
		
		if(time != null && time.length() >= 10){
			
			String idTemp = time.substring(8, 10);
			
			int id = 0;
			try {
				id = Integer.parseInt(idTemp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return id;
		}else {
			return -1;
		}
		
		
	}
	
	public static int getHour(String time){
		
		if(time != null && time.length() >= 8){
			
			String idTemp = time.substring(6, 8);
			
			int id = 0;
			try {
				id = Integer.parseInt(idTemp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return id;
		}else {
			return -1;
		}
		
		
	}
	
	public static int getMinuteId(String time){
		
		if(time != null && time.length() == 12){
			
			String idTemp = time.substring(10);
			int id = 0;
			try {
				id = Integer.parseInt(idTemp);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//System.out.println(idTemp);
			return id - (id % 5);
		}else {
			return 0;
		}
		
		
	}
	
	public static boolean isAviDate(String date){
		
		if(date == null){
			return false;
		}else {
			return date.matches("\\d{4}-\\d{1,2}-\\d{1,2}") || date.matches("\\d{8}");
		}
	}
	
	public static String isHistoryDay(String date){
		
		
		if(isAviDate(date)){
			String yes = getYesterdayStand();
			if(date.compareTo(yes) > 0){
				return yes;
			}else {
				return date;
			}
			
		}else {
			return getYesterdayStand();
		}
		
	}
	
	
	
	public static String getFivMinutes(String time , int offset){
		
		Calendar calendar = Calendar.getInstance();
		if(time != null){
			calendar.setTime(new Date(time.replace("-", "/")));
		}
		int m = calendar.get(Calendar.MINUTE);
		calendar.add(Calendar.MINUTE, -(m % 5) + 5 * offset);
		return fiveMinuteFormat.format(calendar.getTime()); 
		
	}
	
	public static String getCurrentFivMinutes(){
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new Date());
		
		int m = calendar.get(Calendar.MINUTE);
		//System.out.println(m);
		calendar.set(Calendar.MINUTE, m -(m % 5));
		return minuteFormat.format(calendar.getTime()); 
		
	}
	
	public static String getCurrentMinutes(){
		
		Calendar calendar = Calendar.getInstance();
		return minuteFormat.format(calendar.getTime()); 
		
	}
	
	public static String getCurrentFivMinutes(String time){
		
		Calendar calendar = Calendar.getInstance();
		if(time != null){
			calendar.setTime(new Date(time.replace("-", "/")));
		}
		int m = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, 5 -(m % 5));
		return minuteFormat.format(calendar.getTime()); 
		
	}
	public static String getFivMinutes(String time){
		
		if(time != null && time.length() == 12){
			String prefix = time.substring(0 , 10);
			int minute = NumberUtil.getIntValue(time.substring(10));
			
			minute = minute - (minute % 5);
			return prefix + (minute < 10 ? "0" + minute : String.valueOf(minute));
		}else {
			return time;
		}
		
		
	}
	public static boolean isCurrentHour(String time){
		
		int hourId = 0;
		if(time != null && time.length() > 13){
			hourId = NumberUtil.getIntValue(time.substring(11, 13));
			
		}
		if(hourId >= 0){
			int curId= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if(curId == hourId){
				return true;
			}
		}
		return false;
	}
	
	public static String getOffsetDayStr(int offset){
		
		if(offset > 1){
			Calendar calendar = Calendar.getInstance();
			StringBuilder result = new StringBuilder();
			int length = offset;
			calendar.add(Calendar.DAY_OF_MONTH, -length);
			for(int i = 1 ; i <= length ; i++){
				result.append("[ʱ��].[").append(dayFormatStand.format(calendar.getTime())).append("],");	
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				
			}
			return result.substring(0, result.length() - 1);
		}else {
			return null;
		}
		
	}
	
	/**
	 * ���ص�ǰʱ��֮ǰn��Сʱʱ�� �� 2011030810
	 * @param offset
	 * @return
	 */
	public static String getOffsetHourStr(int offset){
		

			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.HOUR_OF_DAY, offset);

			return hourFormat.format(calendar.getTime());

		
	}
	
	
	public static void main(String[] args) {
		String from="2011-08-29";
		/*String pre = TimeUtil.getWeekFistDay(from, 0);//getCurrentMonth();//getSpedayStand(from , -offset);
		String next = TimeUtil.getWeekFistDay(from, 0);*/
		//String c = TimeUtil.getWeek(from, 0);
		//System.out.println("*****:"+TimeUtil.getWeekFirstDay(from, 0));
		//System.out.println(c);
		//System.out.println(next);
		//getWeekLastDay("",-1);
		System.out.println(TimeUtil.dayFormat.format(TimeUtil.getYesterdayStand()));
	}
}
