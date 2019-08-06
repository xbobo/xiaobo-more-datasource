package price;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;


/**
 * 活动消费规则商品
 * Created by fu on 2019/4/26.
 */
@Data
public class ActivityConsumerProductInfoVO implements Comparable<ActivityConsumerProductInfoVO> {


    /**
     * 对应KEY ID
     */
    private Long ked_id;

    /**
     * 原价
     */
    @JsonSerialize(using = MoneyDoubleSerialize.class)
    private Double ori_price;

    /**
     * 折扣价格
     */
    @JsonSerialize(using = MoneyDoubleSerialize.class)
    private Double discount_price;
    /**
     * 平均折扣金额  联报专用
     */
    private Double avg_discount_price=0D;


    /***
     *          课程数量
     */
    private int course_count;
    
    /***
                   *          课程id
     */
    private Long class_id;
    
    //  剩余价格  均分价格用
    private Double surplus_price;


    @Override
    public int compareTo(ActivityConsumerProductInfoVO o) {
        return this.getOri_price().intValue()-o.getOri_price().intValue();
    }
}
