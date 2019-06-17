package weixin.nati;


public class OrderEnum {
    /**
     * 订单状态
     * @author pan
     * **/
    public enum OrderStatus {
        /**
         * 待支付
         */
        NEW_ORDER(1,"待支付"),
        /**
         * 已支付且已分班
         * */
        PAID_ORDER_INTO_CLASS(2,"已支付"),
        /**
         * 取消支付
         * */
        CLOSE_ORDER(3,"取消支付"),

        /**
         * 4超时-超过30分钟未支付的订单
         * */
        PRE_ORDER(4,"支付超时"),


        /**
         * 退款
         * */
        REFUND_ORDER(8,"已退款"),

        /**
         * 9-已支付 未分班
         * */
        PAID_ORDER_NOT_INTO_CLASS(9,"未分班");

        private int status;
        private String name;

        OrderStatus(int status, String name) {
            this.status = status;
            this.name = name;
        }

        public int getStatus() {
            return status;
        }
        public String getName() {
            return name;
        }
        public static String getNameInfo(int status) {
            switch (status) {
                case 1: {
                    return NEW_ORDER.getName();
                }
                case 2: {
                    return PAID_ORDER_INTO_CLASS.getName();
                }
                case 3: {
                    return CLOSE_ORDER.getName();
                }
                case 4: {
                    return PRE_ORDER.getName();
                }
                case 8: {
                    return REFUND_ORDER.getName();
                }
                case 9: {
                    return PAID_ORDER_NOT_INTO_CLASS.getName();
                }
                default: {
                    throw new NotEnumTypeException("OrderStatusEnum不存在值为 " + status + " 的枚举");
                }
            }
        }
    }

    /**
     * 订单支付类别
     * @author pan
     * **/
    public enum  OrderPayType {
        /**
         * 0免费
         * */
        PAY_FREE(0,"免费"),

        /**
         * 1微信
         * */
        PAY_WECHAT(1,"微信"),

        /**
         * 2 支付宝
         * */
        PAY_ALIPAY(2,"支付宝"),

        /**
         *3有赞
         * */
        PAY_PRAISE(3,"有赞"),

        /**
         * 4 其他
         * */
        PAY_OTHER(4,"其他"),


        /**
         * 5 金币
         * */
        PAY_COIN(5,"学习币");
        private int value;
        private String name;

        private OrderPayType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
        public static String getNameInfo(int value) {
            switch (value) {
                case 0: {
                    return PAY_FREE.getName();
                }
                case 1: {
                    return PAY_WECHAT.getName();
                }
                case 2: {
                    return PAY_ALIPAY.getName();
                }
                case 3: {
                    return PAY_PRAISE.getName();
                }
                case 4: {
                    return PAY_OTHER.getName();
                }
                case 5: {
                    return PAY_COIN.getName();
                }
                default: {
                    return PAY_OTHER.getName();
                }
            }
        }
    }

    /**
     * 订单支付终端类型
     * @author pan 支付终端类型：1PC，2ios,3安卓,4h5页面,5管理员后台录入
     * **/
    public static enum OrderPayTagType {
        /**
         * PC
         * */
        PAY_TAG_PC(1,"PC"),

        /**
         * IOS
         * */
        PAY_TAG_IOS(2,"IOS"),

        /**
         * Android
         * */
        PAY_TAG_ANDROID(3,"Android"),

        /**
         *h5
         * */
        PAY_TAG_H5(4,"h5"),

        /**
         * 5管理员后台录入
         * */
        PAY_TAG_HAND_RECORD(5,"管理员后台录入");

        private int value;
        private String name;

        private OrderPayTagType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
        public static OrderPayTagType valueOf(int value) {
            switch (value) {

                case 1: {
                    return PAY_TAG_PC;
                }
                case 2: {
                    return PAY_TAG_IOS;
                }
                case 3: {
                    return PAY_TAG_ANDROID;
                }
                case 4: {
                    return PAY_TAG_H5;
                }
                case 5: {
                    return PAY_TAG_HAND_RECORD;
                }
                default: {
                    throw new NotEnumTypeException("OrderPayTypeEnum不存在值为 " + value + " 的枚举");
                }
            }
        }
    }

    /**
     * 订单导入方式
     * @author pan 1自动，2第三方平台自动，3手动
     * **/
    public enum  OrderModel {
        /**
         * 自动
         * */
        AUTO_IMPORT(1,"自动"),

        /**
         * 第三方平台自动
         * */
        THIRD_AUTO_IMPORT(2,"第三方平台"),

        /**
         * 手动
         * */
        HAND_IMPORT(3,"手动");

        private int value;
        private String name;

        private OrderModel(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
        public static String getNameInfo(int value) {
            switch (value) {

                case 1: {
                    return AUTO_IMPORT.getName();
                }
                case 2: {
                    return THIRD_AUTO_IMPORT.getName();
                }
                case 3: {
                    return HAND_IMPORT.getName();
                }
                default: {
                    throw new NotEnumTypeException("OrderPayTypeEnum不存在值为 " + value + " 的枚举");
                }
            }
        }
    }

    /**
     * 订单详情状态
     * @author pan 订单状态（1正常，2为退款）
     * **/
    public static enum  OrderDetailStatus {
        /**
         * 1正常
         * */
        NORMAL(1,"正常"),

        /**
         * 2为退款
         * */
        REFUND(2,"退款");

        private int status;
        private String name;

        private OrderDetailStatus(int status, String name) {
            this.status = status;
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public static OrderDetailStatus valueOf(int status) {
            switch (status) {
                case 1: {
                    return NORMAL;
                }
                case 2: {
                    return REFUND;
                }
                default: {
                    throw new NotEnumTypeException("OrderDetailStatusEnum不存在值为 " + status + " 的枚举");
                }
            }
        }
       }


    /***
     * app支付订单状态
     */
       public static  enum AppOrderStatus{
           WAIT_BUYER_PAY(1,"待支付"),
           TRADE_CLOSED(2,"超时关闭或支付完成后全额退款"),
           TRADE_SUCCESS(3,"支付成功"),
           TRADE_FINISHED(4,"支付超时"),
           EIGHT(8,"退款"),
           NINE(9,"未分班");
           private int value ;
           private String name ;

           private AppOrderStatus(int value,String name  ){
               this.value = value;
               this.name = name ;
           }

           public String getName(){
               return name;
           }

           public int getValue(){
               return value;
           }

           public static String getAppOrderStatus(int intValue){
               switch (intValue) {
                   case 1:
                       return OrderEnum.AppOrderStatus.WAIT_BUYER_PAY.getName();
                   case 2:
                       return OrderEnum.AppOrderStatus.TRADE_CLOSED.getName();
                   case 3:
                       return OrderEnum.AppOrderStatus.TRADE_SUCCESS.getName();
                   case 8:
                       return OrderEnum.AppOrderStatus.EIGHT.getName();
                   case 9:
                       return OrderEnum.AppOrderStatus.NINE.getName();
               }
               return null;
           }
       }
}
