package com.any.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IDEA
 * User: vector 
 * Data: 2018/7/19 0019
 * Time: 16:41
 * Description: 
 */
public class TestMain {
	public static void main(String[] args) throws ParseException {
		if ("2017-06-15T00:00:00".contains("T")) {
			System.out.println(dealDateFormat("1997-07-01T00:00:00"));
		}

	}

	public static String dealDateFormat(String oldDateStr) throws ParseException {
		//此格式只有  jdk 1.7才支持  yyyy-MM-dd‘T‘HH:mm:ss.SSSXXX
		//这个后面的.SSSXXX写了的话这一行就直接抛异常了，所以我去掉了，还有前面的T  一定要用英文的单引号包裹起来
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = df.parse(oldDateStr);
		SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		Date date1 =  df1.parse(date.toString());
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df2.format(date1);
	}
}
