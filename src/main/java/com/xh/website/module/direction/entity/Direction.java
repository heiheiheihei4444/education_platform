package com.xh.website.module.direction.entity;

import com.xh.website.module.BaseEntity;
import com.xh.website.module.paper.entity.Paper;
import com.xh.website.module.person.entiy.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author xinghao
 * @descreption 研究方向实体类
 * @date 2018/12/19
 */

@Entity
public class Direction extends BaseEntity {

    @Column(unique = false,nullable = true)
    private String name;

    @Column(nullable = true,columnDefinition = "text")
    private String brief;


    private String photo;

    @OneToMany
    private List<Paper> paperList;

    @OneToMany
    private List<Person> personList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
