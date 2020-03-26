package com.xh.website.module.project.entity;

import com.xh.website.module.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author xinghao
 * @descreption 项目实体类
 * @date 2018/12/19
 */

@Entity
public class Project extends BaseEntity {
    private String name;
    private String photo;
    private String brief;

    @Column(nullable = true)
    private String projectURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getProjectURL() { return projectURL; }

    public void setProjectURL(String projectURL) { this.projectURL = projectURL; }
}
