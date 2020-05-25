package club.jming.entity;

/**
 * 响应消息
 */
public class ResMessage {

    private Integer code;
    private String message;
    private Object data;


    public ResMessage(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResMessage(){}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

//    public Integer getCount() {
//        return count;
//    }
//
//    public void setCount(Integer count) {
//        this.count = count;
//    }

    @Override
    public String toString() {
        return "ResMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", content=" + data +
                '}';
    }
}
