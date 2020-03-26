package com.xh.website.module.paper.entity;

import com.xh.website.module.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author xinghao
 * @descreption 论文实体类
 * @date 2018/12/19
 */

@Entity
public class Paper extends BaseEntity {
    private String title;
    private String authors;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String level;

    private String keywords;

    @Column(nullable = false)
    private String paperURL;

    private String type;
    private String area;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPaperURL() {
        return paperURL;
    }

    public void setPaperURL(String paperURL) {
        this.paperURL = paperURL;
    }


}

