package weixin.all;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import lombok.Data;

@Data
@Component
//@ConfigurationProperties(prefix = "wxpay")
public class WxPayConfiguration {
    private String appId;

    private String mchId;

    private String mchKey;

    private String subAppId;

    private String subMchId;

    private String keyPath;

    private String notifyUrl ;

    private String tradeType ;
    
    private String redirectUrl ;

    @Bean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.appId);
        payConfig.setMchId(this.mchId);
        payConfig.setMchKey(this.mchKey);
        payConfig.setSubAppId(this.subAppId);
        payConfig.setSubMchId(this.subMchId);
        payConfig.setKeyPath(this.keyPath);
        payConfig.setNotifyUrl(this.notifyUrl);
        payConfig.setTradeType(this.tradeType);
        return payConfig;
    }

    @Bean
    public WxPayService payService() {
        WxPayService payService = new WxPayServiceImpl();
        payService.setConfig(payConfig());
        return payService;
    }
}
