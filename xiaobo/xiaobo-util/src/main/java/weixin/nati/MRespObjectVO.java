package weixin.nati;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MRespObjectVO {

    @JsonProperty("pre_pay")
    private MPrepayDataVO prePay;
    @JsonProperty("pay_status")
    private int payStatus;
    @JsonProperty("pay_status_desc")
    private String payStatusDesc;
    // 特价活动新增字段
    @JsonProperty("activity_id")
    private Long activityId; // 活动ID
    @JsonProperty("activity_type")
    private Integer activityType; // 活动类型
    @JsonProperty("activity_title")
    private String activityTitle; // 活动名称

    /***
     * 1 金币不足
     */
    @JsonProperty("coin_status")
    private int coinStatus;

    public MRespObjectVO() {

    }

    public MRespObjectVO(MPrepayDataVO prePay) {
        this.prePay = prePay;
    }
}
