 package price;


import java.math.BigDecimal;

import lombok.Data;
/**
 * 
 * @author xiaobo
 * @date 2019/05/05
 */
@Data
 public class ValidateExtraProductVO {
    private Long product_id;
    private Long product_id_main;
   // '状态：0-删除；1-上架；2-下架；',
    private Integer status;
    private Integer product_num;
    private Integer buy_num;
    //'1为需要邮件，0为不需要',
    private Integer need_post;
    
    private BigDecimal old_price;
    private BigDecimal price;
}
