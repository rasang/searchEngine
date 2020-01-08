package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test{
	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(d);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -7);
		d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		System.out.println("格式化后的日期：" + dateNowStr);
	}
}