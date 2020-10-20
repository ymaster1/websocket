package ym.chat.exception;

/**
 * @Author: ym
 * @Description: 自定义一个异常类
 * @Date: 2019/11/29 5:11 下午
 * @Version:
 */
public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private String Code;
    /**
     * 错误信息
     */
    private String Msg;
    public MyException() {
        super();
    }

    /**
     * 第一种构造方法，参数为MyError
     * @param errorInfoInterface
     */
    public MyException(ErrorInterFace errorInfoInterface) {
        super(errorInfoInterface.getCode());
        this.Code = errorInfoInterface.getCode();
        this.Msg = errorInfoInterface.getMsg();
    }

    /**
     * 第二种构造方法，参数只有错误信息，不推荐
     * @param errorMsg
     */
    public MyException(String errorMsg) {
        super(errorMsg);
        this.Msg = errorMsg;
    }

    /**
     * 第三种构造方法，参数为MyError和自定义的msg，推荐
     * @param error
     * @param errorMsg
     */
    public MyException(MyError error, String errorMsg) {
        super(errorMsg);
        this.Code = error.getCode();
        this.Msg = errorMsg;
    }

    /**
     * 第四种构造方法，参数为自定义code和自定义msg
     * @param code
     * @param errorMsg
     */
    public MyException(String code, String errorMsg) {
        super(errorMsg);
        this.Code = code;
        this.Msg = errorMsg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public String getErrorCode() {
        return Code;
    }

    public void setErrorCode(String errorCode) {
        this.Code = errorCode;
    }

    public String getErrorMsg() {
        return Msg;
    }

    public void setErrorMsg(String errorMsg) {
        this.Msg = errorMsg;
    }

}
