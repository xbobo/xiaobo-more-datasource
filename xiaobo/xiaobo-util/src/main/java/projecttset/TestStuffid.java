package projecttset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;


public class TestStuffid {
 
    static String clientId = "098f6bcd4621d373c2134e832627b4f6";
    static String base64Secret = "Fqtt1LcUz8bkDVKH0CO+b91EvtH60E7vweYGGN4PgxE=";
    static String name = "restapiuser";  
    static Integer expiresSecond = 2592000;
    static String salt = "0oFt5SX4ONB6wPICwn";

    public static void main(String[] args) {
    	String [] arr= {"ERP","MMS"};
    	
	UserVO userVO = new UserVO();
	userVO.setUserNickname("陈剑");
	userVO.setUserId(19027299799441408L);
	userVO.setUserMobile("13381200177");
	userVO.setDnsNickName(arr);
	// 生成token
	String jwtToken = JwtUtil.createJWT(userVO.getUserNickname(), userVO.getUserId().toString(),userVO.getUserMobile(),userVO.getDnsNickName(), clientId, name,
		expiresSecond * 1000, base64Secret);
	System.out.println("bearer;" + jwtToken);
	Map<String, Object> resultG = new HashMap<>(2);
	resultG.put("user", userVO);
	Map<String, Object> result = new HashMap<>(3);
	result.put("g", resultG);
	result.put("authorization", "bearer;" + jwtToken);
	System.out.println(result.toString());
	//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi6ZmI5YmRIiwidXNlcmlkIjoiMTkwMjcyOTk3OTk0NDE0MDgiLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.yn0Mm3vIPdp3M7SmEGztlLbImc73AKbmcYtPRLZh7RU
	//bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi6ZmI5YmRIiwidXNlcmlkIjoiMTkwMjcyOTk3OTk0NDE0MDgiLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.yn0Mm3vIPdp3M7SmEGztlLbImc73AKbmcYtPRLZh7RU
	 final String authHeader = "bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi55u855u8IiwidXNlcmlkIjoiNDg3MzUyMTg5Mzc2Njc1ODQiLCJkbnNOaWNrTmFtZSI6WyJFUlAiLCJNTVMiXSwibW9iaWxlIjoiMTg1MTM1ODA2ODQiLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.NAnJiLJKbg-KtEE3Z4IwudEJnNjleQEOEzxBv52XBoo";
	 final String authHeader1 =  "bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5p2o5pmoIiwidXNlcmlkIjoiNTM5MjA0Njk5MjIxMjc4NzIiLCJkbnNOaWNrTmFtZSI6WyJFUlAiLCJNTVMiXSwibW9iaWxlIjoiMTUxNDAxMDQ1OTgiLCJsb2dpbl90aW1lIjoxNTU5MTI4MTEzODczLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.n0l0DvKYGhLoVAXCM2Pua_9AmvXabEZukNVErzgf5P0";
     final String token = authHeader1.substring(7);

     final Claims claims = JwtUtil.parseJWT(token, base64Secret);
     //dnsNickName=[ERP, MMS]
    //{name=盼盼, userid=48735218937667584, dnsNickName=[ERP, MMS], mobile=18513580684, iss=restapiuser, aud=098f6bcd4621d373c2134e832627b4f6}
	    Object userid = claims.get("userid");
	    Object name = claims.get("name");
	    Object mobile = claims.get("mobile");
	    List<String> list = (List<String>) claims.get("dnsNickName");
 
	    System.out.println("....");
	    
	   // 18611971665
    }
}
