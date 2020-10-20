package ym.chat;


import java.io.Serializable;

import ym.chat.exception.MyError;

/**
 * @Author: ym
 * @Description:一个封装了code和msg的实体类，用于返回
 * @Date: 2019/11/29 5:58 下午
 * @Version:
 */
public class Response implements Serializable {
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误码
     */
    private String code;
    /**
     * 响应数据
     */
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 根据错误信息来创建返回值
     *
     * @param error
     */
    public Response(MyError error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public static Response success() {
        Response response = new Response(MyError.SUCCESS);
        return response;
    }

    /**
     * 成功有返回值时
     *
     * @param data
     * @return
     */
    public static Response success(Object data) {
        Response response = new Response(MyError.SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     * 失败
     *
     * @param
     * @return
     */
    public static Response fail(String code, String msg) {
        Response response = new Response(code, msg);
        //此data就是很多看不懂的java错误信息，所以可以直接设为null,因为code和msg自己已经定义了，已经知道错误是什么了
        response.setData(null);
        return response;
    }
    public static Response fail(MyError error) {
        Response response = new Response(error);
        response.setData(null);
        return response;
    }



}
