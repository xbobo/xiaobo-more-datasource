package weixin.nati;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 功能描述
 *
 * @author chenjian@pxjy.com
 * @date 2019/2/26 16:57
 */
@Data
public class OrderDetailDO {
    @JsonProperty(value = "order_detail_id")
    private Long orderDetailId;

    @JsonProperty(value = "order_id")
    private Long orderId;

    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "ori_price")
    private BigDecimal oriPrice;

    @JsonProperty(value = "course_price")
    private BigDecimal coursePrice;

    @JsonProperty(value = "stuff_price")
    private BigDecimal stuffPrice;

    @JsonProperty(value = "postage")
    private BigDecimal postage;

    @JsonProperty(value = "discount_price")
    private BigDecimal discountPrice;

    @JsonProperty(value = "payment")
    private BigDecimal payment;

    @JsonProperty(value = "status")
    private Integer status;

    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "product_img")
    private String productImg;

    @JsonProperty(value = "teacher_name")
    private String teacherName;

    @JsonProperty(value = "assistant_code")
    private String assistantCode;

    @JsonProperty(value = "validity_time")
    private Long validityTime;

    @JsonProperty(value = "quarter_code")
    private String quarterCode;

    @JsonProperty(value = "grade_name")
    private String gradeName;

    @JsonProperty(value = "subject_name")
    private String subjectName;

    @JsonProperty(value = "create_uid")
    private Long createUid;

    @JsonProperty(value = "modify_uid")
    private Long modifyUid;

    @JsonProperty(value = "create_time")
    private Long createTime;

    @JsonProperty(value = "modify_time")
    private Long modifyTime;

}
