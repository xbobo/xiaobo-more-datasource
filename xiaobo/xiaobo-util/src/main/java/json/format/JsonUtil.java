package json.format;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	/**
	下划线json 转驼峰json
	 */
	public static JSONObject lineToHumpJson(com.alibaba.fastjson.JSONObject mapObj){

		JSONObject jsonObject = new JSONObject();
		for (Object map: mapObj.entrySet()){
			//System.out.printlnln(((Map.Entry)map).getKey()+"  "+((Map.Entry)map).getValue());

			String key = JsonUtil.lineToHump(((Map.Entry) map).getKey().toString());
			jsonObject.put(key, ((Map.Entry) map).getValue());
		}

		return  jsonObject;
	}


	/** 下划线转驼峰 */
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	public static String humpToLine2(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static void main(String[] args) {
		UserTest user = new UserTest();
		user.setUserId("aaaBBBcccDDDD");
		user.setUserInfo("eeeFFFgggHHIi");
        String json = JSON.toJSONString(user);
        System.out.println(humpToLine2(json));
        String test="{\"User_id\":\"AAABBBCCC\",\"user_info\":\"eee_f_f_fggg_h_h_ii\"}";
        System.out.println(lineToHump(test));
		/**
		 * 道不尽红尘舍恋  诉不完人间恩怨 世世代代都是缘 留着同样的血  喝着同样的水
		 */
	}
}