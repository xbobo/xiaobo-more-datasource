package projecttset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;

import static java.lang.Long.parseLong;

public class TestStuffid {

    static String clientId = "098f6bcd4621d373c2134e832627b4f6";
    static String base64Secret = "Fqtt1LcUz8bkDVKH0CO+b91EvtH60E7vweYGGN4PgxE=";
    static String name = "restapiuser";
    static Integer expiresSecond = 2592000;
    static String salt = "0oFt5SX4ONB6wPICwn";

    public static void main(String[] args) {

        String[] arr = {"ERP", "MMS"};
        List<String> strList = new ArrayList<String>();
        for (int x = 0; x < 10; x++) {
            strList.add(x + "");
        }
        System.out.println(strList.subList(0, 6));

        UserVO userVO = new UserVO();
        userVO.setUserNickname("测试账号");
        userVO.setUserId(19027299799441408L);
        userVO.setUserId(79623599007506433L);
        userVO.setUserId(83684422264856576L);

        //userVO.setUserId(19027299879133185L);
        // front
        //userVO.setUserId(20963366932881408L);

        //userVO.setUserId(36820026381344768L);

        //userVO.setUserId(42979594073317376L);16169128680530105
        // mms
        userVO.setUserId(72388427321221120L);
        //userVO.setUserId(39087074100682752L);

        userVO.setUserId(41540177244758016L);
        //userVO.setUserId(19027299879133185L);

        userVO.setUserMobile("13381200177");
        userVO.setUserMobile("13261462623");
        userVO.setDnsNickName(arr);
        // 生成token
        String jwtToken = JwtUtil.createJWT(userVO.getUserNickname(), userVO.getUserId().toString(), userVO.getUserMobile(), userVO.getDnsNickName(), clientId, name,
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
        final String authHeader1 = "bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5p2o5pmoIiwidXNlcmlkIjoiNTM5MjA0Njk5MjIxMjc4NzIiLCJkbnNOaWNrTmFtZSI6WyJFUlAiLCJNTVMiXSwibW9iaWxlIjoiMTUxNDAxMDQ1OTgiLCJsb2dpbl90aW1lIjoxNTU5MTI4MTEzODczLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.n0l0DvKYGhLoVAXCM2Pua_9AmvXabEZukNVErzgf5P0";
        final String testHead1 = "bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5rWL6K-V6ams6I65IiwidXNlcmlkIjoiMzkwODcwNzQxMDA2ODI3NTIiLCJpc3MiOiJyZXN0YXBpdXNlciIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjMjEzNGU4MzI2MjdiNGY2In0.Bx3f_8LwhbK1bxGkWDsrwuZu5GizESgLDuFhBxmousU";
        final String testHead ="bearer;eyJ0eXAiOiJKV1QiLCJhbGcioiJIUzI1NiJ9.eyJuYW1lIjoicHh3eDAyODAiLCJ1c2VyaWQiOiI0MTU0MDE3NZI0NDC1ODAxNiIsImlzcyI6InJIc3RhcGI1c2VyIiwiYXVkIjoiMDk4ZjZiY2Q0NjIXZDM3M2MyMTM0ZTgzMjYyN2I0ZjYifQ.KbSzR13v8IOkEvvrpxnH-rMRG7CEVAFIxZemJ6466C4";
                             //"bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoicHh3eDAyODAiLCJ1c2VyaWQiOiI0MTU0MDE3NzI0NDc1ODAxNiIsImlzcyI6InJlc3RhcGl1c2VyIiwiYXVkIjoiMDk4ZjZiY2Q0NjIxZDM3M2MyMTM0ZTgzMjYyN2I0ZjYifQ.KbSzR13v8IOkEvvrpxnH-rMRG7CEVAFlxZemJ6466C4";
        final String token = testHead.substring(7);

        final Claims claims = JwtUtil.parseJWT(token, base64Secret);
        //dnsNickName=[ERP, MMS]
        //{name=盼盼, userid=48735218937667584, dnsNickName=[ERP, MMS], mobile=18513580684, iss=restapiuser, aud=098f6bcd4621d373c2134e832627b4f6}
        Object userid = claims.get("userid");
        long user_id = parseLong(userid.toString());
        System.out.println(user_id);
        Object name = claims.get("name");
        Object mobile = claims.get("mobile");
        List<String> list = (List<String>) claims.get("dnsNickName");

        System.out.println("....");

        // 18611971665  2019051416002001
        //     20190513002015
        // 21  2019062614001001      25189709790879744,25189902661754880,25189851092787200
        // 							 25189851092787200,25189709790879744,25189902661754880
        //							 40774564813512704,40774732887662592,40774788365721600

        //     2019062614001002     56076941909073920,51674052478410752,49465155827048448
        // 							56076942659854336,51674052520353792,49465155860602880
        //	   2019062614001003
        //    40774788365721600,25189709790879744


//	    "year" : 2019,
//        "grade_mode" : "09,08,07",
//        "quarter_mode" : "C",
//        "subject_mode" : "Y,S,E",
//        "course_mode" : "C",
//        "buy_start_time" : NumberLong(1551270038),
//        "buy_end_time" : NumberLong(1564416000)


        String encrypt = Utils.getEncrypt(salt, "185699");
        System.out.println(encrypt);

        System.out.println("ABCd".equalsIgnoreCase("abcD"));
        System.out.println("106345043779166208".length());
    }
}
