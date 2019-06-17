package weixin.all;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants.TradeType;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.google.gson.Gson;

import weixin.nati.MPrepayDataVO;
import weixin.nati.MRespObjectVO;

@Component
public class WxPayUtils {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WxPayUtils.class);
	
    @Autowired
    private WxPayService payService;
    
    @Autowired
    private WxPayConfiguration wxPayConfiguration;
    
    @Autowired
    private WxPayConfig wxPayConfig;
    /**
     * 生成wx订单
     * @param payRequest
     * @param payType
     * @param orderIdField
     * @param orderId
     * @return
     */
	public MRespObjectVO wxToPay(WxPayUnifiedOrderRequest payRequest,String payType,String orderIdField,String orderId) {
		String message = null;
		MRespObjectVO respObject = null;
		try {
			//订单金额单位是元,因此要转换成分
	       // BigDecimal totalFee = orderDO.getOrderPrice().multiply(new BigDecimal(100));
			// NATIVE(1, "NATIVE"), APP_IOS(2, "APP"), APP_ANDROID(3, "APP"), MWEB(4,
			// "MWEB"), BACK_SYSTEM(5, "BACK_SYSTEM"), JSAPI(6, "JSAPI");
			if (TradeType.MWEB.equals(payType)) {
				WxPayMwebOrderResult result = payService.createOrder(payRequest);
				try {
					// 转发url
					String redirectUrl = "&redirect_url=" + URLEncoder.encode(
							wxPayConfiguration.getRedirectUrl() + "?"+orderIdField+"=" + orderId, "utf-8");
					result.setMwebUrl(result.getMwebUrl() + redirectUrl);
				} catch (UnsupportedEncodingException e) {
					throw new WxPayException(e.getMessage());
				}
				message =JSONObject.toJSONString(result);
			} else if (TradeType.APP.equals(payType)) {
				WxPayAppOrderResult result = payService.createOrder(payRequest);
				message = new Gson().toJson(result);
			} else if (TradeType.JSAPI.equals(payType)) {
				// JSAPI 展示
				//payRequest.setOpenid(payInfoDTO.getOpen_id());
				WxPayMpOrderResult result = payService.createOrder(payRequest);
				message =JSONObject.toJSONString(result);
			} else if (TradeType.NATIVE.equals(payType)) {
				WxPayNativeOrderResult result = payService.createOrder(payRequest);
				message =JSONObject.toJSONString(result);
			}
			JSONObject jsonObject = JSONObject.parseObject(message);
            Map<String, Object> objectMap = jsonObject;
            objectMap.put("orderId", orderId);
            respObject = new MRespObjectVO(new MPrepayDataVO(objectMap));
		} catch (WxPayException e) {
			logger.error("订单生成异常" + JSONObject.toJSONString(payRequest), e);
			if (e.getErrCodeDes() == null && e.getReturnCode().equals("FAIL")) {
				throw new MyWxPayException(e.getReturnMsg());
			} else {
				throw new MyWxPayException(e.getErrCodeDes());
			}
		}
		logger.info("创建订单结果：" + JSONObject.toJSONString(respObject));
		return respObject;
	}
	/**
	 * 	同步微信回调
	 * @param orderId
	 * @param payType
	 * @return
	 */
	public boolean returnCheck(String orderId, int payType) {
        try {
			WxPayOrderQueryResult result = payService.queryOrder(null, orderId);
			logger.info("微信回调查询订单result" + result.toString());
			String resultCode = result.getResultCode();
			String returnCode = result.getReturnCode();

			if ("SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode)) {
			    if ("SUCCESS".equals(result.getTradeState())) { //支付成功
			        //long timeEnd = Utils.dateToStamp(result.getTimeEnd(), "yyyyMMddHHmmss");
			         return true;
			    }
			}
		} catch (WxPayException e) {
			e.printStackTrace();
			logger.error("同步微信回调查询异常", e);
		}
        return false;
    }
	
	  /**
     * 微信异步通知支付结果的回调地址，notify_url
     *
     * @param request
     * @param response
     */
    //@RequestMapping(value = "wx_order_call_back")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> kvm = XMLUtil.parseRequestXmlToMap(request);
            String return_code = kvm.get("return_code");// 状态
            logger.info("kvm: " + JSONObject.toJSONString(kvm));
            String callBackXml = StringUtils.setXML("FAIL", "微信回调异常");
            if (SignUtils.checkSign(kvm, null, wxPayConfig.getMchKey())) {
                if ("SUCCESS".equals(return_code)) {
                	//订单金额 分转为元
                	//BigDecimal totalFee = new BigDecimal(kvm.get("total_fee")).divide(new BigDecimal(100));
                	//TODO 其它业务验证
                	 String outTradeNo = kvm.get("out_trade_no");//商户订单号
                     String tradeNo = kvm.get("transaction_id");//微信订单号
                	// TODO 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                	callBackXml = StringUtils.setXML("SUCCESS", "微信支付成功");
                }
            }
            response.getWriter().write(callBackXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
