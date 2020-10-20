package ym.chat.exception;

/**
 * @Author: ym
 * @Description: 定义一个基础的接口类，自定义的错误描述枚举类需实现该接口。
 * @Date: 2019/11/29 5:27 下午
 * @Version:
 */
public interface ErrorInterFace {
    /**
     * 返回错误码
     * @return
     */
    String getCode();

    /**
     * 返回错误信息
     * @return
     */
    String getMsg();
}
