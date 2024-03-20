package Graduation.donkas.responseResult;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseResult<T> {

    private ResponseResult() {
        this.head = new Head();
        this.head.resultCode = "0000";
        this.head.resultMsg = "Success";
    }
    private ResponseResult(T data) {
        this.head = new Head();
        this.head.resultCode = "0000";
        this.head.resultMsg = "Success";
        this.body = data;
    }
    private ResponseResult(String code, String msg, T data) {
        this.head = new Head();
        this.head.resultCode = code;
        this.head.resultMsg = msg;
        this.body = data;
    }
    private ResponseResult(String code, String msg) {
        this.head = new Head();
        this.head.setResultCode(code);
        this.head.setResultMsg(msg);

    }
    private Head head;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;
    @Data
    public static class Head {
        private String resultCode;
        private String resultMsg;
    }
    public static<T> ResponseResult<T> body(String code, String msg, T data) {
        return new ResponseResult<T>(code, msg, data);
    }
    public static<T> ResponseResult<T> body(String code, String msg) {
        return new ResponseResult<T>(code, msg);
    }
    public static<T> ResponseResult<T> body(T data) {
        return new ResponseResult<T>(data);
    }

    public static<T> ResponseResult<T> body() {
        return new ResponseResult<T>();
    }

}
