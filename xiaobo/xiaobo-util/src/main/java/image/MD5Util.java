package image;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    //使用MD5加密字符串
    public static String getSignCode(Map param ,String partnerKey){
        Map map = sortMapByKey(param);
        String str = getParamString(map,partnerKey);
        try {
            return DigestUtils.md5Hex(str.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }

    }
    //获得参数排序字符串
    public static String getParamString(Map map,String partnerKey){
        StringBuffer buffer = new StringBuffer();
        Map<String,String> sortMap = null;
            sortMap = sortMapByKey(map);
        for (Map.Entry entry : sortMap.entrySet()) {
            buffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        buffer.append("partner_key="+ partnerKey);
        return buffer.toString();
    }

    public static String getSignCode(Map param ,String partnerKey, String partnerValue){
        Map map = sortMapByKey(param);
        String str = getParamString(map,partnerKey,partnerValue);
        try {
            return DigestUtils.md5Hex(str.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }

    }

    public static String getParamString(Map map,String partnerKey, String partnerValue){
        StringBuffer buffer = new StringBuffer();
        Map<String,String> sortMap = null;
        sortMap = sortMapByKey(map);
        for (Map.Entry entry : sortMap.entrySet()) {
            buffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        buffer.append(partnerKey+"="+ partnerValue);
        return buffer.toString();
    }

    //获得参数无序字符串
    public static String getParamStringNoOrder(Map map){
        StringBuffer buffer = new StringBuffer();
        Map<String,String> mapNoOrder = map;
        for (Map.Entry entry : mapNoOrder.entrySet()) {
            buffer.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        buffer.delete(buffer.length()-1,buffer.length());
        return buffer.toString();
    }
    //获得排序好的map
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
    public static String filter(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，？\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String md5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            }
            else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}

class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
