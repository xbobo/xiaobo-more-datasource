package price;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class ActivityProductDiscountInfoVO {
     /**
      * 满足商品数量
      */
     private Integer product_num;

     /**
      * 减金额
      */
     @JsonSerialize(using = MoneyDoubleSerialize.class)
     private Double account;
     
     
     /**
      * 赠送实物礼物
      */
     private List<PhysicalGiftInfoVO> gift_list;
}
