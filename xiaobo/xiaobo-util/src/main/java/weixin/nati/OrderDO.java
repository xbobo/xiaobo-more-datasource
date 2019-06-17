package weixin.nati;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderDO {
    @JsonProperty(value = "order_id")
    private Long orderId;

    @JsonProperty(value = "student_id")
    private Long studentId;

    @JsonProperty(value = "order_ori_price")
    private BigDecimal orderOriPrice;

    @JsonProperty(value = "order_price")
    private BigDecimal orderPrice;

    @JsonProperty(value = "order_status")
    private Integer orderStatus;

    @JsonProperty(value = "receive_name")
    private String receiveName;

    @JsonProperty(value = "receive_phone")
    private String receivePhone;

    @JsonProperty(value = "receive_address")
    private String receiveAddress;

    @JsonProperty(value = "remarks")
    private String remarks;

    @JsonProperty(value = "is_address")
    private Integer isAddress;

    @JsonProperty(value = "pay_tag_type")
    private Integer payTagType;

    @JsonProperty(value = "pay_type")
    private Integer payType;

    @JsonProperty(value = "pay_over_time")
    private Long payOverTime;

    @JsonProperty(value = "pay_time")
    private Long payTime;

    @JsonProperty(value = "third_order_code")
    private String thirdOrderCode;

    @JsonProperty(value = "third_order_code_bak")
    private String thirdOrderCodeBak;

    @JsonProperty(value = "pay_money")
    private BigDecimal payMoney;

    @JsonProperty(value = "ori_money")
    private BigDecimal oriMoney;

    @JsonProperty(value = "province")
    private String province;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "district")
    private String district;

    @JsonProperty(value = "order_mode")
    private Integer orderMode;

    @JsonProperty(value = "campaign")
    private String campaign;

    @JsonProperty(value = "course_id")
    private String medium;

    @JsonProperty(value = "source")
    private String source;

    @JsonProperty(value = "create_uid")
    private Long createUid;

    @JsonProperty(value = "modify_uid")
    private Long modifyUid;

    @JsonProperty(value = "create_time")
    private Long createTime;

    @JsonProperty(value = "modify_time")
    private Long modifyTime;

    @JsonProperty(value = "admin_remarks")
    private String adminRemarks;

    //类型转换

    @JsonProperty(value = "order_id_format")
    private String orderIdFormat;

    @JsonProperty(value = "order_mode_format")
    private String orderModeFormat;

    @JsonProperty(value = "student_id_format")
    private String studentIdFormat;

    @JsonProperty(value = "pay_type_format")
    private String payTypeFormat;

    @JsonProperty(value = "order_status_format")
    private String orderStatusFormat;

    @JsonProperty(value = "pay_time_format")
    private String payTimeFormat;

    @JsonProperty(value = "activity_type")
    private Integer activityType;

    @JsonProperty(value = "activity_id")
    private Long activityId;
    @JsonProperty(value = "postage")
    private BigDecimal postage;
}