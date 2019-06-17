package weixin.nati;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 将秒数转化为时间格式
     * @param time
     * @return
     */
    public static String getTime(long time) {
        Date date=new Date();
        date.setTime(time*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmDate=simpleDateFormat.format(date);
        return fmDate;
    }

    public static String getTime(long time, String format) {
        Date date=new Date();
        date.setTime(time*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String fmDate=simpleDateFormat.format(date);
        return fmDate;
    }

    public static String getStartDate(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        String times = sdr.format(new Date(time * 1000L));
        return times;

    }

    public static String getEndDate(long time, int min) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        String times = sdr.format(new Date(time * 1000L + min * 60 * 1000L));
        return times;

    }

    /**
     * 设置微信二维码失效时间，并返回具体失效的时间点
     * @param expire 二维码的有效时间，单位是分钟
     * @return
     */
    public static String getOrderExpireTime(int expire){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        Date afterDate = new Date(now .getTime() + expire*60*1000L);
        return sdf.format(afterDate );
    }
    /**
     * 日期转为月日   例子：1月2日-1月3日
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDayStr(Long startTime, Long endTime) {
	SimpleDateFormat dayFormat = new SimpleDateFormat("MM月dd日");
	String startDate = "";
	String endDate = "";
	if (startTime != null) {
	    startDate = dayFormat.format(startTime * 1000l);
	}
	if (endTime != null) {
	    endDate = dayFormat.format(endTime * 1000l);
	}
	if (org.apache.commons.lang3.StringUtils.isNotEmpty(startDate)
		&& org.apache.commons.lang3.StringUtils.isNotEmpty(endDate)) {
	    if (!startDate.equals(endDate)) {
		return startDate + "-" + endDate;
	    }
	}
	return startDate;

    }
    /**
     * 结果为 ： 一二三四五六日
     * @param startTime
     * @return
     */
    public static String getWeekStr(Long startTime) {
        if(startTime==null) {
            return "";
        }
        Calendar c=Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()+24*60*60*1000*7));
        int weekday=c.get(Calendar.DAY_OF_WEEK);
        String [] str= {"日","一","二","三","四","五","六"};
        return  str[weekday-1];
   }
    
    public static String getyyyyMMdd(Long startTime, Long endTime) {
	SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
	String startDate = "";
	String endDate = "";
	if (startTime != null) {
	    startDate = dayFormat.format(startTime * 1000l);
	}
	if (endTime != null) {
	    endDate = dayFormat.format(endTime * 1000l);
	}
	if (org.apache.commons.lang3.StringUtils.isNotEmpty(startDate)
		&& org.apache.commons.lang3.StringUtils.isNotEmpty(endDate)) {
	    if (!startDate.equals(endDate)) {
		return startDate + "-" + endDate;
	    }
	}
	return startDate;

    }
    
    public static String getMMdd(Long startTime, Long endTime) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM月dd日");
        String startDate = "";
        String endDate = "";
        if (startTime != null) {
            startDate = dayFormat.format(startTime * 1000l);
        }
        if (endTime != null) {
            endDate = dayFormat.format(endTime * 1000l);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(startDate)
            && org.apache.commons.lang3.StringUtils.isNotEmpty(endDate)) {
            if (!startDate.equals(endDate)) {
            return startDate + "-" + endDate;
            }
        }
        return startDate;

        }
    /**
     * 时间转换  例子 1：50-3：50
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getHourStr(Long startTime, Long  endTime) {
	SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
	String startHour = "";
	String endHour = "";
	if (startTime != null) {
	    startHour = DateUtil.getStartDate(startTime);
	}
	if (endTime != null) {
	    endHour = DateUtil.getStartDate(endTime);
	}

	if (org.apache.commons.lang3.StringUtils.isEmpty(startHour)
		|| org.apache.commons.lang3.StringUtils.isEmpty(endHour)) {
	    return startHour + "" + endHour;
	}

	return startHour + "-" + endHour;
    }
    /**
               * 如果没有时间间隔  则按开始时间结束时间 处理
     * @param startTime
     * @param endTime
     * @param min
     * @return
     */
    public static String getHourStr(Long startTime,Long endTime, Double min) {
	SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
	String startHour = "";
	String endHour = "";
	if (startTime != null) {
	    startHour = DateUtil.getStartDate(startTime);
	    if (min!=null) {
		startHour = DateUtil.getStartDate(startTime);
		int m=(int) (min*1000l*60*60);
		String times = sdr.format(new Date(startTime * 1000L + m));
		return startHour + "-" + times;
	    }
	}
	if (endTime != null) {
	    endHour = DateUtil.getStartDate(endTime);
	}

	if (org.apache.commons.lang3.StringUtils.isEmpty(startHour)
		|| org.apache.commons.lang3.StringUtils.isEmpty(endHour)) {
	    return startHour + "" + endHour;
	}

	return startHour + "-" + endHour;
    }
	public static String getDate(long time) {
		SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日");
		String times = sdr.format(new Date(time * 1000L));
		return times;

	}
	public static void main(String[] args) {
	    
        
    }
}
