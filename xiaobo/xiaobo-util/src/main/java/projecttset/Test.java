package projecttset;

import java.util.HashMap;
import java.util.Map;


public class Test {
 
    static String clientId = "098f6bcd4621d373c2134e832627b4f6";
    static String base64Secret = "Fqtt1LcUz8bkDVKH0CO+b91EvtH60E7vweYGGN4PgxE=";
    static String name = "restapiuser";  
    static Integer expiresSecond = 2592000;
    static String salt = "0oFt5SX4ONB6wPICwn";

    public static void main(String[] args) {
	UserVO userVO = new UserVO();
	userVO.setUserNickname("1111");
	userVO.setUserId(1L);
	// 生成token
	String jwtToken = JwtUtil.createJWT(userVO.getUserNickname(), userVO.getUserId().toString(), clientId, name,
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

    }
}
