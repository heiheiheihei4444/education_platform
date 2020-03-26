package com.xh.website.module.person.entiy;

import com.xh.website.module.BaseEntity;
import com.xh.website.module.paper.entity.Paper;

import javax.persistence.*;
import java.util.List;

/**
 * @author xinghao
 * @descreption 人员实体类
 * @date 2018/12/18
 */

@Entity
public class Person extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 20)
    private String telephone;

    @Column(length = 64)
    private String email;

    @Column(nullable = false)
    private String degree;

    private String unit;

    private String field;

    private Integer grade;

    private String photo;

    private String brief;


    private Boolean leader;

    private String title;

    @ManyToMany
    private List<Paper> paperList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
