package weixin.nati;

import lombok.Data;


@Data
public class Result {
    public static final Integer OK = 1;
    public static final Integer ERROR = 0;
    public static final Integer NOAUTH = -1;
    public static final Integer INVALID = -3;
    public static final String ERROR_MESSAGE = "服务器错误";
    private Integer status;
    private String message;
    private Object data;

    public Result(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    public static Result get(int status, String message, Object data) {
        return new Result(status, message, data);
    }

    public static Result createServerError() {
        Result result = new Result(ERROR, ERROR_MESSAGE, null);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "status:" + status +
                ", message:\"" + message + "\"" +
                ", data:" + data +
                "}";
    }
}
