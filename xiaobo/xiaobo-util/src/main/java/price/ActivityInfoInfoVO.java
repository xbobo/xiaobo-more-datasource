package price;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * 活动基本信息实体
 * Created by fu on 2019/4/25.
 */
@Data
public class ActivityInfoInfoVO {

    private String aid;

    private List<Integer> bstp;
    /**
     * 活动标题
     */
    private String title;


    private Integer activity_consumer_type;


    private Integer activity_attend_type;
    /**
     * 现价
     */
    @JsonSerialize(using = MoneyDoubleSerialize.class)
    private Double price;

    /**
     * 原价
     */
    @JsonSerialize(using = MoneyDoubleSerialize.class)
    private Double ori_price;
    
    @JsonSerialize(using = MoneyDoubleSerialize.class)
    private Double sum_discount_price;

    /**
     * 年级
     */
    private String grade;

    /**
     * 活动开始时间
     */
    private Long enable_time;
    /**
     * 活动结束时间
     */
    private Long disable_time;


    /**
     * 课程开始时间
     */
    private Long course_enable_time;
    /**
     * 课程结束时间
     */
    private Long course_disable_time;


    /**
     * 课程数量
     */
    private Integer course_count;

    /**
     * 活动类型
     */
    private Integer activity_type;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 活动描述
     */
    private String description;

    /**
     * 商品服务(周边)
     */
    private List<String> product_service;

    /**
     * app图
     */
    private String app_image;

    /**
     * h5图
     */
    private String h5_image;

    /**
     * m站
     */
    private String m_image;

    /**
     * 活动内容
     */
    private String activity_content;

    /**
     * 备注
     */
    private String note;

    private Integer status;

    private Long create_uid;
    
    private String create_ume;

    private Long modify_uid;
    
    private String modify_ume;

    private Long create_time;

    private Long modify_time;
    
    
    /**
     * 活动商品列表
     */
    private List<ActivityConsumerProductInfoVO> activity_consumer_product_list;

    /**
     * 销售数量
     */
    private Long pay_count;
    
    /**
     * 基础销售数量
     */
    private Long basis_pay_count;

    /**
     * 前端展示销量
     */
    private Long show_pay_count;

    /**
     * 选择活动 0 空 1转介绍
     */
    private Integer activity_way;

    /**
     * 规则
     */
    Map<String,Double> consumer_rule=new HashMap<>();
    
    /**
     * 联报优惠规则
     */
    private List<ActivityProductDiscountInfoVO> discount_rule;
    
  //当前使用规则
    private List<ActivityProductDiscountInfoVO> current_discount_rules=new ArrayList<ActivityProductDiscountInfoVO>();
    
    /**
     * 标题备注
     */
    private String title_note;


    /**
     * 礼物规则
     */
    List<ActivityProductDiscountInfoVO> gift_rule;
    //当前礼物规则
    List<ActivityProductDiscountInfoVO> current_gift_rules=new ArrayList<ActivityProductDiscountInfoVO>();

    /**
     * 是否减价
     */
    private boolean is_discount;

    /**
     * 是否礼物
     */
    private boolean is_gift;
    
    /**
     * 学段
     */
    private Integer part_mode;
    
    private String day_str;
    
    private Long current_time;
}
