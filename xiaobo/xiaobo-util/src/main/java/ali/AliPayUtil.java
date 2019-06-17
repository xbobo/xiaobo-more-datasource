package ali;

import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

@Component
public class AliPayUtil {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AliPayUtil.class);
	
	public String aliPay(AlipayTradeAppPayModel model) {
		String message="";
		try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigUtil.GATEWAYURL, AlipayConfigUtil.APP_ID
                    , AlipayConfigUtil.MERCHANT_PRIVATE_KEY, "json", AlipayConfigUtil.CHARSET
                    , AlipayConfigUtil.ALIPAY_PUBLIC_KEY, AlipayConfigUtil.SIGN_TYPE);

//            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//            // model.setBody(orderTest.getBody());                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
//            model.setSubject(orderDetailDOList.get(0).getSubjectName());                 //订单名称
//            model.setOutTradeNo(orderDO.getOrderId().toString());           //商户订单号
//            model.setTimeoutExpress(AlipayConfigUtil.TIMEOUT_EXPRESS);                 //交易超时时间
//            model.setTotalAmount(String.valueOf(orderDO.getOrderPrice()));         //支付金额
//            model.setProductCode(AlipayConfigUtil.PRODUCT_CODE);              //销售产品码（固定值）


            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfigUtil.NOTIFY_URL);        //异步回调地址（后台）
            ali_request.setReturnUrl(AlipayConfigUtil.RETURN_URL);      //同步回调地址（APP）

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            message = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
 

        } catch (AlipayApiException e) {
            e.printStackTrace();
            logger.info("与支付宝交互出错，未能生成订单，请检查代码！");
        }
		return message;
	}
}
