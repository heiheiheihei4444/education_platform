package com.xh.website.module.news.entity;

import com.xh.website.module.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xinghao
 * @descreption 新闻实体类
 * @date 2018/12/19
 */

@Entity
public class News extends BaseEntity {
    private String title;

    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsType newsType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    private String photo;

    private String participator;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getParticipator() {
        return participator;
    }

    public void setParticipator(String participator) {
        this.participator = participator;
    }
}
