//package weixin.nati;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.xiaobo.util.SnowflakeIdWorker;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.domain.AlipayTradeAppPayModel;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.alipay.api.response.AlipayTradeAppPayResponse;
//import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
//import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
//import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
//import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
//import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
//import com.github.binarywang.wxpay.constant.WxPayConstants.TradeType;
//import com.github.binarywang.wxpay.exception.WxPayException;
//import com.github.binarywang.wxpay.service.WxPayService;
//import com.google.gson.Gson;
//
//import ali.AlipayConfigUtil;
//
//public class WxPayUtil {
//
//    @Autowired
//    private WxPayService payService;
//    
//	public Result tradeMsitePay(MPayDTO payInfo) {
//        //微信
//        Result objResult =null;
//        if (payInfo.getPay_type().equals(OrderEnum.OrderPayType.PAY_WECHAT.getValue())) {
//            String message = wxCreateOrderMsite(payInfo);
//            //logger.info("wx result message is :" + message);
//            //logger.info("11111111111111111111111111111:" + message);
//            JSONObject jsonObject = JSONObject.parseObject(message);
//            Map<String, Object> objectMap = jsonObject;
//            objectMap.put("orderId", payInfo.getOrder_id());
//            MRespObjectVO respObject = new MRespObjectVO(new MPrepayDataVO(objectMap));
//            //如果是活动订单返回值新增字段
//            //addActivityInfo(payInfo.getOrder_id(),respObject);
//            objResult = Result.get(Result.OK, "微信支付成功", respObject);
//        }
//        //支付宝
//        else if (payInfo.getPay_type().equals(OrderEnum.OrderPayType.PAY_ALIPAY.getValue())) {
//            String message = getAliPayOrderStr(payInfo.getOrder_id());
//            MPrepayDataVO prepayData = new MPrepayDataVO(message,payInfo.getOrder_id());
//            MRespObjectVO respObject = new MRespObjectVO(prepayData);
//            return Result.get(Result.OK, "支付宝支付成功", respObject);
//        }
//        //如果是金币支付就只返回订单号，然后等待用金币发起支付
//        else if (payInfo.getPay_type().equals(OrderEnum.OrderPayType.PAY_COIN.getValue())) {
//            Map<String, Object> objectMap = new HashMap<>();
//            objectMap.put("orderId", payInfo.getOrder_id());
//            MRespObjectVO respObject = new MRespObjectVO(new MPrepayDataVO(objectMap));
//            return Result.get(Result.OK, "", respObject);
//        }else {
//            objResult=new Result(Result.ERROR, "支付异常", new MRespObjectVO());
//        }
//
//        //根据订单id查询订单是否为活动订单
//        //logger.info("objResult:{}::"+objResult.toString());
//        return objResult;
//
//    }
//
//    private String wxCreateOrderMsite(MPayDTO payInfoDTO) {
//        OrderDO orderDO = new OrderDO();
//        		//orderRepository.queryOrderInfoById(payInfoDTO.getOrder_id());
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("order_id", orderDO.getOrderId());
//       List<OrderDetailDO> orderDetailDOList = new ArrayList<OrderDetailDO>();
//    		   //orderDetailService.queryOrderDetailList(queryMap);
//
//        WxPayUnifiedOrderRequest payInfo = new WxPayUnifiedOrderRequest();
//
//       // String strBody = StringUtils.WX_APP_NAME;
//        String strBody = "";
//        if(StringUtils.isNotBlank(payInfoDTO.getActivity_title())) {
//            strBody+="-"+payInfoDTO.getActivity_title();
//        }
////        if (orderDetailDOList != null && orderDetailDOList.size() > 0) {
////            strBody = strBody + "-" + orderDetailDOList.get(0).getProductName();
////        }
//        //APP名字-实际商品名称
//        payInfo.setBody(strBody);
//        payInfo.setOutTradeNo(String.valueOf(payInfoDTO.getOrder_id()));
//
//       // OrderPaymentVO oldOrderPaymentVO = paymentOrderRepository.getOrderPaymentVO(orderDO.getOrderId(), null);
//        //订单金额单位是元,因此要转换成分
//        BigDecimal totalFee = orderDO.getOrderPrice().multiply(new BigDecimal(100));
//        payInfo.setTotalFee(totalFee.intValue());
//        payInfo.setSpbillCreateIp(payInfoDTO.getIp_address());
//        String payTagType = MsitePayPlatformEnum.getPayTagType(payInfoDTO.getPlatform());
//        payInfo.setTradeType(payTagType);
//        
//        payInfo.setOutTradeNo(String.valueOf(payInfoDTO.getOrder_id())+payTagType);
//        
//        if (oldOrderPaymentVO != null && oldOrderPaymentVO.getGmtClose() > 0L) {
//            payInfo.setTimeExpire(DateUtil.getTime(oldOrderPaymentVO.getGmtClose(), "yyyyMMddHHmmss"));
//        }
//        logger.info("payInfo={}" + payInfo.toString());
//
//        String message = null;
//        try {
//          //NATIVE(1, "NATIVE"), APP_IOS(2, "APP"), APP_ANDROID(3, "APP"), MWEB(4, "MWEB"), BACK_SYSTEM(5, "BACK_SYSTEM"), JSAPI(6, "JSAPI");
//            if(TradeType.MWEB.equals(payTagType)) {
//                WxPayMwebOrderResult result = payService.createOrder(payInfo);
//                try{
//                    //转发url
//                    String redirectUrl = "&redirect_url="+URLEncoder.encode(wxPayConfiguration.getRedirectUrl()+"?order_id="+payInfoDTO.getOrder_id(),"utf-8");
//                    result.setMwebUrl(result.getMwebUrl() + redirectUrl);
//                }catch (UnsupportedEncodingException e) {
//                    throw new WxPayException(e.getMessage());
//                }
//                message = new Gson().toJson(result);
//            }else if(TradeType.APP.equals(payTagType)) {
//                WxPayAppOrderResult result = payService.createOrder(payInfo);
//                message = new Gson().toJson(result);
//            }else if(TradeType.JSAPI.equals(payTagType)) {
//                //JSAPI  展示
//                payInfo.setOpenid(payInfoDTO.getOpen_id());
//                WxPayMpOrderResult result = payService.createOrder(payInfo);
//                message = new Gson().toJson(result);
//            }else if(TradeType.NATIVE.equals(payTagType)) {
//                payInfo.setProductId(String.valueOf(payInfoDTO.getOrder_id()));
//                WxPayNativeOrderResult result = payService.createOrder(payInfo);
//                message = new Gson().toJson(result);
//            }
//
//
//            //如果支付单不为空，则直接返回支付信息，给接口支付
//            if (oldOrderPaymentVO == null) {
//                OrderPaymentVO paymentOrder = new OrderPaymentVO();
//                //下订单时设置超时时间
//                String endTime = DateUtil.getOrderExpireTime(30);
//                payInfo.setTimeExpire(endTime);
//                long timeEnd = Utils.dateToStamp(endTime, "yyyyMMddHHmmss");
//                paymentOrder.setGmtClose(timeEnd);
//                paymentOrder.setOrderPaymentId(SnowflakeIdWorker.getId());
//                paymentOrder.setOutTradeNo(orderDO.getOrderId());//商户订单号
//                paymentOrder.setTradeStatus(OrderEnum.AppOrderStatus.WAIT_BUYER_PAY.getValue());// 交易创建并等待买家付款
//                paymentOrder.setPayType(OrderEnum.OrderPayType.PAY_WECHAT.getValue());
//                paymentOrder.setTotalAmount(orderDO.getOrderPrice());//订单金额
//                paymentOrder.setPayType(OrderEnum.OrderPayType.PAY_WECHAT.getValue());
//                paymentOrderRepository.insertOrderPaymentVO(paymentOrder);
//            }
//        } catch (WxPayException e) {
//            logger.error("订单生成异常"+com.alibaba.fastjson.JSONObject.toJSONString(payInfo), e);
//            if(e.getErrCodeDes() == null && e.getReturnCode().equals("FAIL")) {
//                throw new WxPayException(e.getReturnMsg());
//            } else {
//                throw new WxPayException(e.getErrCodeDes());
//            }
//        }
//
//        logger.info("创建订单结果：" + message);
//        return message;
//    }
//    
//    /***
//     * 获取支付宝加签后台的订单信息字符串
//     * @param orderId
//     * @return
//     */
//    @Override
//    public String getAliPayOrderStr(long orderId) {
//
//        OrderDO orderDO = orderRepository.queryOrderInfoById(orderId);
//        //最终返回加签之后的，app需要传给支付宝app的订单信息字符串
//        String orderString = null;
//        logger.info("==================支付宝下单,商户订单：" + (orderDO));
//
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("order_id", orderDO.getOrderId());
//        List<OrderDetailDO> orderDetailDOList = orderDetailService.queryOrderDetailList(queryMap);
//        if (orderDetailDOList.size() == 0) {
//            logger.info("根据商户订单id查询数据异常");
//            return orderString;
//        }
//        try {
//            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
//            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigUtil.GATEWAYURL, AlipayConfigUtil.APP_ID
//                    , AlipayConfigUtil.MERCHANT_PRIVATE_KEY, "json", AlipayConfigUtil.CHARSET
//                    , AlipayConfigUtil.ALIPAY_PUBLIC_KEY, AlipayConfigUtil.SIGN_TYPE);
//
//            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//            // model.setBody(orderTest.getBody());                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
//            model.setSubject(orderDetailDOList.get(0).getSubjectName());                 //订单名称
//            model.setOutTradeNo(orderDO.getOrderId().toString());           //商户订单号
//            model.setTimeoutExpress(AlipayConfigUtil.TIMEOUT_EXPRESS);                 //交易超时时间
//            model.setTotalAmount(String.valueOf(orderDO.getOrderPrice()));         //支付金额
//            model.setProductCode(AlipayConfigUtil.PRODUCT_CODE);              //销售产品码（固定值）
//
//
//            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
//            ali_request.setBizModel(model);
//            ali_request.setNotifyUrl(AlipayConfigUtil.NOTIFY_URL);        //异步回调地址（后台）
//            // ali_request.setReturnUrl(AlipayConfigUtil.RETURN_URL);      //同步回调地址（APP）
//
//            // 这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
//            orderString = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
//            //创建商户支付宝订单,后面异步调用校验通知数据的正确性 TODO
//            OrderPaymentVO alipaymentOrder = new OrderPaymentVO();
//            alipaymentOrder.setOrderPaymentId(SnowflakeIdWorker.getId());
//            alipaymentOrder.setOutTradeNo(orderDO.getOrderId());//商户订单号
//            alipaymentOrder.setTradeStatus(OrderEnum.AppOrderStatus.WAIT_BUYER_PAY.getValue());// 交易创建并等待买家付款
//            alipaymentOrder.setTotalAmount(orderDO.getOrderPrice());//订单金额
//            alipaymentOrder.setPayType(OrderEnum.OrderPayType.PAY_ALIPAY.getValue());
//            paymentOrderRepository.insertOrderPaymentVO(alipaymentOrder);
//
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//            logger.info("与支付宝交互出错，未能生成订单，请检查代码！");
//        }
//
//        return orderString;
//    }
//}
