package org.xiaobo.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.util.DigestUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * 
 * @author xiaobo
 * @date 2019年4月25日
 */
public class JWTUtil {
	
	
//	# jwt配置
//	audience:
//	  clientId: 098f6bcd4621d373c2134e832627b4f6
//	  base64Secret: Fqtt1LcUz8bkDVKH0CO+b91EvtH60E7vweYGGN4PgxE=
//	  name: restapiuser
//	  expiresSecond: 2592000
//	  salt: 0oFt5SX4ONB6wPICwn
//	     标注中注册的声明（建议不强制使用）
//
//	iss：jwt签发者
//	sub：jwt所面向的用户
//	aud：接收jwt的一方
//	exp：jwt的过期时间，这个过期时间必须大于签发时间
//	nbf：定义在什么时间之前，该jwt都是不可用的
//	iat：jwt的签发时间
//	jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击 
    public static final String AUTH_HEADER_START = "bearer;";

    public static final String TOKEN_ONLY_KEY = "token_only_key";
    
    private static final String ADUDIENCE_AUDIENCE="3c2134e832627b4f6";
    
    private static final String ADUDIENCE_BASE64_SECRET="0b68570e90d854025cf3b8173caf8b93";
    
    private static final String ADUDIENCE_ISSUER="rest_api_issuer";
    
    private static final long ADUDIENCE_EXPIRES_SECOND=2592000*1000l;//30天
    
    private static final String ADUDIENCE_SALT="acc10ceed2dc853946c119dfeaf2d8da";
    
    private static final String HEADER_KEY="authorization";
	
	/**
	 *	 解析jwt
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 *  	构建jwt  40464266294501376
	 *   
	    *       无法直接设置 token 过期 ，外部控制  删除 token
	 *  
	 * @param map
	 * @param key
	 * @return
	 */
	public static Map<String, Object> createJWT(Map<String,Object> map,String key) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(ADUDIENCE_BASE64_SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
 
		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.claim(TOKEN_ONLY_KEY, key)
				.setSubject(key)		//		sub(Subject)：代表这个JWT的主体，即它的所有人；
				.setIssuedAt(new Date())//		iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
				.setId(key)				//		jti(JWT ID)：是JWT的唯一标识。
				.setAudience(ADUDIENCE_AUDIENCE)//		aud(Audience)：代表这个JWT的接收对象；
				.setIssuer(ADUDIENCE_ISSUER)//		iss(Issuser)：代表这个JWT的签发主体；
				.signWith(signatureAlgorithm, signingKey);
		if(map!=null&&map.size()>0) {
			for(Entry<String, Object> entry:map.entrySet()) {
				builder.claim(entry.getKey(), entry.getValue()); 
			}
		}
		// 添加Token过期时间
		if (ADUDIENCE_EXPIRES_SECOND >= 0) {
			long expMillis = nowMillis + ADUDIENCE_EXPIRES_SECOND;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp)//		exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
			.setNotBefore(now);//		nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
		}

		// 生成JWT
		 String compact = builder.compact();
		 
        Map<String, Object> result = new HashMap<>(3);
        result.put("g", "");
        result.put(HEADER_KEY, "bearer;" + compact);
        return result;
	}
	
	/**
	 * 	获取登录信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getLoginKey(HttpServletRequest request) throws Exception {
		 final String authHeader = request.getHeader(HEADER_KEY);
		  if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_START)) {
		      throw new Exception("无效token");
		  }
		  final String token = authHeader.substring(7);

		  final Claims claims = parseJWT(token,ADUDIENCE_BASE64_SECRET );
		  if (claims == null) {
		      throw new Exception("无效token");
		  }
		  if (claims.get(TOKEN_ONLY_KEY) == null) {
		      throw new Exception("用户已失效，重新登陆");
		  }
		  String id = claims.get(TOKEN_ONLY_KEY).toString();
		  return id;
	}
	/**
	 * 	密码加密 根据salt 加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String password) {
		String firstPassword = DigestUtils.md5DigestAsHex((ADUDIENCE_SALT + password).getBytes());
        return "###" + DigestUtils.md5DigestAsHex(firstPassword.getBytes());
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "陈");
		String key = "19027299799441408";
		Map<String, Object> createJWT = createJWT(map, key);
		System.out.println(createJWT.get(HEADER_KEY));
		String substring = createJWT.get(HEADER_KEY).toString().substring(7);
		Claims parseJWT = parseJWT(substring, ADUDIENCE_BASE64_SECRET);
		System.out.println(parseJWT.get(TOKEN_ONLY_KEY));
		//System.out.println(DigestUtils.md5DigestAsHex("BASE64_SECRET".getBytes()));
//		/acc10ceed2dc853946c119dfeaf2d8da
	}
}
