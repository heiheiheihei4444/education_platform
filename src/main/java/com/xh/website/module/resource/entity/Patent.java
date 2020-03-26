package com.xh.website.module.resource.entity;


import com.xh.website.module.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xinghao
 * @descreption 文档实体类
 * @date 2018/12/19
 */

@Entity
public class Patent extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;


    private String patentURL;

    @Column(nullable = false)
    private String patentType;

    @Column(nullable = false)
    private String abstr;

    private String author;

    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPatentURL() {
        return patentURL;
    }

    public void setPatentURL(String patentURL) {
        this.patentURL = patentURL;
    }

    public String getPatentType() {
        return patentType;
    }

    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
