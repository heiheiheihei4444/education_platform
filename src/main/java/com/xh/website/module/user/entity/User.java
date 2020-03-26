package com.xh.website.module.user.entity;

import com.xh.website.module.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author xinghao
 * @descreption 系统用户
 * @date 2018/12/18
 */

@Entity
public class User extends BaseEntity {
    @Column(unique = true,nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
