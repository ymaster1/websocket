package ym.chat.exception;

/**
 * @Author: ym
 * @Description: 用枚举来实现描述错误信息的接口
 * @Date: 2019/11/29 5:29 下午
 * @Version:
 */
public enum MyError implements ErrorInterFace {
    /**
     * 枚举了各个可能出现的错误
     */
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!");
    /**
     * 错误码
     */
    private String Code;
    /**
     * 错误信息
     */
    private String msg;

    MyError(String code, String msg) {
        this.Code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return Code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
