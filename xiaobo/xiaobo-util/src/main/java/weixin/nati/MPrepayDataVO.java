package weixin.nati;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import lombok.Data;

@Data
public class MPrepayDataVO {
    // 通用字段
    @JsonProperty(value = "order_id")
    private String orderId;
    @JsonProperty(value = "message")
    private String message;

    // 微信
    // MWEB
    @JsonProperty(value = "mweb_url")
    private String mwebUrl;
    // NATIVE
    @JsonProperty(value = "code_url")
    private String codeUrl;
    @JsonProperty(value = "code_url_to_base64")
    private String codeUrlToBase64;
    
    // APP
    @JsonProperty(value = "sign")
    private String sign;
    @JsonProperty(value = "prepay_id")
    private String prepayId;
    @JsonProperty(value = "partner_id")
    private String partnerId;
    @JsonProperty(value = "open_id")
    private String openId;

    // APP && JSAPI
    @JsonProperty(value = "app_id")
    private String appId;
    @JsonProperty(value = "package_value")
    private String packageValue;
    @JsonProperty(value = "time_stamp")
    private String timeStamp;
    @JsonProperty(value = "nonce_str")
    private String nonceStr;

    // JSAPI
    @JsonProperty(value = "sign_type")
    private String signType;
    @JsonProperty(value = "pay_sign")
    private String paySign;

    // 支付宝
    /**
     * 商户网站唯一订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 收款支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字
     */
    @JsonProperty("seller_id")
    private String sellerId;

    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
    @JsonProperty("total_amount")
    private String totalAmount;

    /**
     * 该交易在支付宝系统中的交易流水号。
     */
    @JsonProperty("trade_no")
    private String tradeNo;

    public MPrepayDataVO(SortedMap<Object, Object> payMap) {

        this.orderId = String.valueOf(payMap.get("orderId"));

        this.mwebUrl = String.valueOf(payMap.get("mwebUrl"));

        this.codeUrl = String.valueOf(payMap.get("codeUrl"));
        //url 转base64
        if(payMap.get("codeUrl")!=null) {
            this.codeUrlToBase64=imageToBase64(this.codeUrl);
        }

        this.sign = String.valueOf(payMap.get("sign"));
        this.prepayId = String.valueOf(payMap.get("prepayId"));
        this.partnerId = String.valueOf(payMap.get("partnerId"));

        this.appId = String.valueOf(payMap.get("appId"));
        this.packageValue = String.valueOf(payMap.get("packageValue"));
        this.timeStamp = String.valueOf(payMap.get("timeStamp"));
        this.nonceStr = String.valueOf(payMap.get("nonceStr"));

        this.signType = String.valueOf(payMap.get("signType"));
        this.paySign = String.valueOf(payMap.get("paySign"));
        this.openId = String.valueOf(payMap.get("openId"));

        this.outTradeNo = String.valueOf(payMap.get("out_trade_no"));
        this.sellerId = String.valueOf(payMap.get("seller_id"));
        this.totalAmount = String.valueOf(payMap.get("total_amount"));
        this.tradeNo = String.valueOf(payMap.get("trade_no"));
    }

    public MPrepayDataVO(Map<String, Object> payMap) {
        this.orderId = String.valueOf(payMap.get("orderId"));

        this.mwebUrl = String.valueOf(payMap.get("mwebUrl"));

        this.codeUrl = String.valueOf(payMap.get("codeUrl"));
      //url 转base64
        if(payMap.get("codeUrl")!=null) {
            this.codeUrlToBase64=imageToBase64(this.codeUrl);
        }

        this.sign = String.valueOf(payMap.get("sign"));
        this.prepayId = String.valueOf(payMap.get("prepayId"));
        this.partnerId = String.valueOf(payMap.get("partnerId"));

        this.appId = String.valueOf(payMap.get("appId"));
        this.packageValue = String.valueOf(payMap.get("packageValue"));
        this.timeStamp = String.valueOf(payMap.get("timeStamp"));
        this.nonceStr = String.valueOf(payMap.get("nonceStr"));

        this.signType = String.valueOf(payMap.get("signType"));
        this.paySign = String.valueOf(payMap.get("paySign"));
        this.openId = String.valueOf(payMap.get("openId"));

        this.outTradeNo = String.valueOf(payMap.get("out_trade_no"));
        this.sellerId = String.valueOf(payMap.get("seller_id"));
        this.totalAmount = String.valueOf(payMap.get("total_amount"));
        this.tradeNo = String.valueOf(payMap.get("trade_no"));

    }

    public MPrepayDataVO() {

    }

    public MPrepayDataVO(String message, long orderId) {
        this.message = message;
        this.orderId = orderId + "";
    }
    
    public MPrepayDataVO(String message) {
        this.message = message;
        this.orderId = orderId + "";
    }
    
    
    
    /**
     * 类型转换
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferImage(BitMatrix matrix) {
        int width=matrix.getWidth();
        int height=matrix.getHeight();
        BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? 0xff000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
    /**
     * url 转为 base64
     * 在前端JS 接受的时候需要解一下url

    decodeURI(data.res_data.png_url)//利用js自带的api 进行解码

ok 这样就可以直接使用我们传过去的base64 进行图片展示了

记得要我们传回去的参数前面加上 

 data:image/png;base64,  
     * @param codeUrl
     * @return
     */
    public static String imageToBase64(String codeUrl) {
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        Map hints=new HashMap();
        BitMatrix bitMatrix=null;
        BufferedImage image = null;
            try {
                bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250,250, hints);
                image=toBufferImage(bitMatrix);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
                ImageIO.write(image, "png", baos);//写入流中
                byte[] bytes = baos.toByteArray();//转换成字节
                sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
                String png_base64 =  encoder.encodeBuffer(bytes).trim();//转换成base64串
                png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
                return "data:image/png;base64,"+png_base64; 
            } catch (Exception e) {
                e.printStackTrace();
            }
        return "";
    }

}
