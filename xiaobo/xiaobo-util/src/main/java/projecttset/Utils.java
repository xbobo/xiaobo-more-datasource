package projecttset;

import image.MD5Util;
import org.springframework.util.DigestUtils;
import org.xiaobo.util.SnowflakeIdWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 功能描述
 *
 * @author chenjian@pxjy.com
 * @date 2019/1/11 16:23
 */

public class Utils {
    /**
     * 验证码字符集
     */

    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /***
     * 默认区域id
     */
    public static Long NOMAL_AREA_ID = -1L;


    public static String SUCCESS = "SUCCESS" ;
    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @return 62进制字符串
     */
    public static String numToString(long num) {
        int scale = chars.length();
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }

        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return value;
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long stringToNum(String str) {
        int scale = chars.length();
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }

        return num;
    }

    public static long dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        long ts = date.getTime()/1000;
        return ts;
    }

    public static long dateToStamp(String s , String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        long ts = date.getTime()/1000;
        return ts;
    }

    public static boolean isUnixTime(long unixTime) {
        if (unixTime > 1000_000_000 && unixTime < 2000_000_000) {
            return true;
        }
        return false;
    }

    public static String getZbClassId() {
//        String vcode = "";
//        Random ran = new Random();
//        for (int i = 0; i < 7; i++) {
//            int n = ran.nextInt(chars.length());
//            vcode = vcode + chars.charAt(i);
//        }
//        return "WX" + vcode;
        return numToString(SnowflakeIdWorker.getId());
    }

    public static String getNickname() {
        String vcode = "";
        for (int i = 0; i < 4; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return "pxwx" + vcode;
    }
    public static String getEightNum() {
        String vcode = "";
        for (int i = 0; i < 8; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }

//    public static String getIpAddr(HttpServletRequest request) {
//        String ipAddress = null;
//        try {
//            ipAddress = request.getHeader("x-forwarded-for");
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("WL-Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getRemoteAddr();
//                if (ipAddress.equals("127.0.0.1")) {
//                    // 根据网卡取本机配置的IP
//                    InetAddress inet = null;
//                    try {
//                        inet = InetAddress.getLocalHost();
//                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
//                    }
//                    ipAddress = inet.getHostAddress();
//                }
//            }
//            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
//                // = 15
//                if (ipAddress.indexOf(",") > 0) {
//                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
//                }
//            }
//        } catch (Exception e) {
//            ipAddress = "";
//        }
//        // ipAddress = this.getRequest().getRemoteAddr();
//
//        return ipAddress;
//    }


    public static String getEncrypt(String salt, String password) {
        String firstPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        return "###" + DigestUtils.md5DigestAsHex(firstPassword.getBytes());
    }

    public static String timeStampToDate(Long lt, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


}
