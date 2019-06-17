package ali;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *说明：
 */

public class AlipayConfigUtil {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号 微信商户号：1521770711@1521770711
	public static String APP_ID = "2019021163236262" ;
			//"2016092700608795";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCK7+LW0OgvWWLv8ZJYNwhLWH40NQPfQCwj0DoRkTux8lf5vekiKVWnInQGLv8T9CNEWxZKDSQEiiQy7jlFam46JHoEsoKElUnPMJWHiOWiUEOjfOY5juQOY5qxM9vXkBX4IxIZDbnu3/86osyMOOrIrh26jpz1oCsc+zE0x6gUQXG0tArBZ44TXL6sSlpTxxGNMmFLPBzqPjrtTEHEhuqah+W46fJUwwkjikReVy3tEBFlxw94VogoRva+HeX3SKWkO/7P1XyM5/mAj49PSVt7hqVikOfaMaEOnTqkqEV8bh2lfH4Gnfcd5ojMnFxArC9Ou5IumUHwwezhUMz/oCs7AgMBAAECggEAdO6CNpNp5nfPeewRY+clNuyKgvvff2RuAdI9HQ3jdNdmzYzVCmhJaznm9lFVYwDFMcGLl9VJa/yrjKXMU6zIimyquQtYJ6fRj+vu3TwNMDdnRdBXnzewnp+b0Om5a8obg1RY5fcUdoPVNg9QIe/BQhawdJOUbHFZ97E9b5Nd1XUeGkCtKLJEDDmkQUzJd8cKbsjZMoJWWkswhEDScAH5iuwbPmYLQtyPQSMu9RT5/hZz0dvz5uJiE1rCdS+ZdvtDt7koKLBXTkCiGzBJ4iIed+5uIrt9V8bAJJz0A8Rh4mIdlLHDAiRL0FB4DWUkZnVMb73v1rblosM0/s9LSGt2eQKBgQDp69Bnq11A3aChYKdaCyUjyd0n582c9wxqX4xaopu0iQwc1oOEwOiTOGctQtq3gCCjoReCWh7QIZ62zgcAjWSqNUYNDrrbKO8xzGMXgIOFlsQa8QLTwv98OrZd8IsQv8zC1BEYMAhrmGQmfGg8Yn/OIE49kVzIpESBE+/JAZcRTwKBgQCYDP3yWAJ5EWBYMGuCxpqv9wfv+IDCYfAkJWN0TY8aJGAPvG7W8wiEAsl+hgSm3c895+Lq5p0Uj42TvJk/o0NMFwb83JUz1HfDqXzTuzR/81H04IVWrGUCGZE1brrAdaMTrChYq9rfTHnKkkWFYSgjUTwtLvER11qGOZ83wz7UVQKBgQDn2O5KkjJvZDsdN+sm9/zrp9wm/T1x4frQQ7uc6aZiHaN/wa3BGmJp6HF7DtQxJDE1fSkL7b60k1332eRey8WGXh78OilBpyTMusNMO/gfo/riqRYgRRlsS9UM7qknBLMyN3Zp/tGZswUwkAXQM+BwsNxbcYQA6a9sMBJtq8HLSQKBgDHxuoybGhzYd1Qge4AWaoM4WpQZhXnUH8RPv65ZN5M3tZIVrDbQ19A89iCoNuj03PkRrTjJjXlOegu4HIKJxzY8LiBq+FtxA/HyPz1xNZp9u0L7aPECaZ9Z9JefippjSbDRkOMneBp3DV5b05gvRLbtHR4eWDMrEOIcFU+Ew6ONAoGANeY/QsXNXKhCC20vyxdcn4AZJqbiBdBb/rpeCT22kyO5X6v4L+1n9UPqDtZ2n6Vy3AbpGU2PF5QjCEmKbuk2hHjtXbVzdipoN5qhDf3586C3KsvTE9p/2AJc9LIn1hDIRuCjy3tCHCw7by/+QdgLy99MXfr4vcqd17QkK0rKvjA=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkDldFQvne8iPgv1SN3ogCHts10QOoB0u+23XfavBQNIb9sCDt7DC0DwG7paBUecb5R61qW/bVQ6xhPo5yVQJ9Gq4lCoJB/lVGRCbYrzvQIrOKhhgcTfntsTZqFThTzC6tZnl1vMtW4UQt2FGKH5H0ebRMEm7SZxJMcLS92TOepVl7s3Tg8yu0YFSks0+6SL0aZ7CdICAt+9mCwfyDqfFIrfWO6Y0f6BRaKB67oqtL6qBm5l+a3Nzn2G/7jlsdm5Na9BcXGx1wrxbtdBhlb/7pFCnc/l8eUGzzn+0c661lRXhtKq2+Nlo2Kx4S0Lzu11aZqPlrYDZqCRUkdTCK9vJvQIDAQAB";
			//"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr1uYznl4OjVY0mj6usKSQOU4iyEHrAQ/+LebhFwnSBXS8bZMvGZN6cL98rMhDP4xyZrmUnaCBEZw1/gATri7+G29ShvfaYPhN3MtNqHpV+g0pEJ2GaoV4+n85sVOTBVHotGxJUjp5/RgqpCenDWyvcNI+nfXAAGyissRN2pdJAKPpbgwyBHn1ODqQUYa4/Pp1H0kMFj5SOhmfbJglKCGIy2t+1rywq/JQVWOVVl9uPBAMHzdga1vgvHoxu/844Vr0Qopxja59gWb+g7siXwyVZyjFKgFnHRa/gy3pCrJhiHBIm23l4mkx6xMxBfoG3PQd7kp5ZrX+G3UCn+iL07QrQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String NOTIFY_URL = "http://localhost:8080/api/payResult/order_pay_notify";

	// 页面跳转同步通知页面路径 可以将同步结果仅仅作为一个支付结束的通知
	public static String RETURN_URL = "";

	// 签名方式
	public static String SIGN_TYPE = "RSA2";
	
	// 字符编码格式
	public static String CHARSET = "utf-8";
	
	// 支付宝网关 //
	 public static String GATEWAYURL = "https://openapi.alipay.com/gateway.do";// 正式
	//public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";// 测试

	/**
	 * 订单有效日期  订单关闭时间1m～15d。m-分钟，h-小时，d-天，1c-当天
	 */
	public static String TIMEOUT_EXPRESS = "30m";

	public static String PRODUCT_CODE = "QUICK_MSECURITY_PAY";


	/**
	 *  获取订单号的 key
	 */
	public static String OUT_TRADE_NO = "out_trade_no";

	/**
	 * 获取交易状态码的 key
	 */
	public static String TRADE_STATUS = "trade_status";


	/**
	 * 商户签约的产品支持退款功能的前提下，买家付款成功
	 */
	public static String TRADE_SUCCESS = "TRADE_SUCCESS";

	/**
	 *  商户签约的产品不支持退款功能的前提下，买家付款成功；或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限
	 */
	public static String TRADE_FINISHED = "TRADE_FINISHED";


	/**
	 * 字符常量 “app_id”
	 */
	public static String APP_ID_NOTIFY = "app_id";


	/**
	 *  字符常量 “total_amount”
	 */
	public static String TOTAL_AMOUNT_NOTIFY = "total_amount";


	/**
	 * 字符常量 “total_amount”
			*/
	public static String SELLER_EMAIL_NOTIFY = "seller_email";


	/***
	 * 未付款交易超时关闭或支付完成后全额退款
	 */
	public static String  TRADE_CLOSED =  "TRADE_CLOSED" ;


	/***
	 * 交易创建并等待买家付款
	 */
	public static String  WAIT_BUYER_PAY =  "WAIT_BUYER_PAY" ;

}

