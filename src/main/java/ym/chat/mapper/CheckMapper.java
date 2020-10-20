package ym.chat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ym.chat.User;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/21 2:05 下午
 * @Version:
 */
@Mapper
public interface CheckMapper {
    /**
     * 通过名称查找
     * @param name
     * @return
     */
    User queryByName(@Param("name") String name);

    /**
     * 插入新用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

    /**
     * 校验用户
     * @param name
     * @param pwd
     * @return
     */
    User queryByNameAndpwd(@Param("name") String name,@Param("pwd") String pwd);
}
