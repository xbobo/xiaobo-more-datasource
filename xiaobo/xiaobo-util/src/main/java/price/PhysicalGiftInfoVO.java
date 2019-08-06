 package price;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class PhysicalGiftInfoVO {


     /**
      *  礼物id
      */
     private String gift_id;

     /**
      * 礼物名称
      */
     private String gift_name;

     /**
      * 礼物图片
      */
     private String gift_image;

     /**
      * 礼物描述
      */
     private String gift_content;

     /**
      * 礼物价格
      */
     @JsonSerialize(using = MoneyDoubleSerialize.class)
     private Double gift_price;

     /**
      * 礼物原价
      */
     @JsonSerialize(using = MoneyDoubleSerialize.class)
     private Double gift_ori_price;

     /**
      * 销量
      */
     private Integer sale_count;

     /**
      * 创建时间
      */
     private Long create_time;

     /**
      * 库存
      */
     private Integer inventory;

}
