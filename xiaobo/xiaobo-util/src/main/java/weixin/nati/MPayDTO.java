 package weixin.nati;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
 public class MPayDTO {
    private Long order_id;
     // `pay_type` int(5) DEFAULT NULL COMMENT '0免费 1微信 2 支付宝 3有赞 4 其他,5学习币',
     private Integer pay_type;
     private String ip_address;
     //(1, "NATIVE"),(2, "APP"),(3, "APP"),(4, "MWEB"),(5, "BACK_SYSTEM"),(6, "JSAPI");
     private Integer platform;
     private String open_id;
     private List<Long> product_ids;
     private Long address_id;
     private Long activity_id;
     private Integer activity_type;
     private BigDecimal payment;
     
     private String mobile;
     private String code;
     private Integer client_type;
     
     private Long student_id;
     /**
      * 材料费
      */
     private BigDecimal stuff_price;
     /**
      * 邮费
      */
     private BigDecimal postage;
     // `pay_tag_type` int(5) DEFAULT NULL COMMENT '支付终端类型：1PC，2ios,3安卓,4h5页面,5管理员后台录入，6 JSAPI微信内浏览器调起h5, 7 有赞',
     private Integer pay_tag_type;
     /**
      * 0 不需要； 1 需要 邮寄
      */
     private Integer need_post;
     //活动名称
     private String activity_title;
     
     /**
      * 推荐人
      */
     private String recommender;
}
