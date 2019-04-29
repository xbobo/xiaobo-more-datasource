package org.xiaobo.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TEst {
	
	  public static void main(String[] args) {
//		  "end_time": "1554829200",
//          "start_time": "1554894000",
//          "status": 3,
//          "teacher_name": "陈剑",
//          "serve_type": 4,
//          "room_id": "19041037353854",
//          "group_id": "0",
//          "day_str": "2019.04.10",
//          "last_time": "19:00-01:00"
 
	//System.out.println(getDayStr(1550381400l, 1550388600l));
		  System.out.println(getStrDate(new Date(1554829200*1000l)));
		  System.out.println(getStrDate(new Date(1554894000*1000l)));
		 // System.out.println(getWeek(new Date(1554379200000l)));
	}
	  
	  public static String getWeek(Date date){ 
		  SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		  String week = sdf.format(date);
		  return week;
		}
	  
	  public static String getStrDate(Date date){ 
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  return sdf.format(date);
	   }
}
