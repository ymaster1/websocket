package ym.chat;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/21 1:12 下午
 * @Version:
 */
@Data
public class User {
    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer age;

    /**
     * 地址
     */
    private String addr;
}
