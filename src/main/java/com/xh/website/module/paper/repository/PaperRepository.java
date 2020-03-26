package com.xh.website.module.paper.repository;

import com.xh.website.module.paper.entity.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PaperRepository extends JpaRepository<Paper, Integer> {

    @Query(value = "select paper.*  from person\n" +
            "  inner join person_paper_list on person_paper_list.person_id = person.id\n" +
            "  inner join paper on person_paper_list.paper_list_id = paper.id\n" +
            "where person.id = ?1", nativeQuery = true)
    List<Paper> findByPerson(int id);

    //paperlist页面所需

    @Query(value = "select * from paper where year(date)=?2 and  (keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%'))", nativeQuery = true)
    Page<Paper> findByYearAndNameLike(String queryName,String year, Pageable pageable);

    @Query(value = "select * from paper where type=?2 and  (keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%'))", nativeQuery = true)
    Page<Paper> findByTypeAndNameLike(String queryName,String type, Pageable pageable);

    @Query(value = "select * from paper where area=?2 and  (keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%'))", nativeQuery = true)
    Page<Paper> findByAreaAndNameLike(String queryName,String area, Pageable pageable);//paper_area也需要

    @Query(value = "select type,count(type) as counts from paper where keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%') group by type", nativeQuery = true)
    List<Map<String,Object>> groupByTypeAndNameLike(String queryName);

    @Query(value = "select year(date) as year,count(*) as counts from paper where keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%') group by year(date) order by year desc", nativeQuery = true)
    List<Map<String,Object>> groupByYearAndNameLike(String queryName);

    @Query(value = "select area,count(area) as counts from paper where keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%') group by area", nativeQuery = true)
    List<Map<String,Object>> groupByAreaAndNameLike(String queryName);



    //***************************************************************
    //paper_area页面所需
    // Page<Paper> findByArea(String area, Pageable pageable);
    @Query(value = "select * from paper where area=?1 and year(date)=?2", nativeQuery = true)
    Page<Paper> findByAreaAndYear(String area, String year, Pageable pageable);

    @Query(value = "select year(date) as year,count(*) as counts from paper where area=?1 group by year(date) DESC ", nativeQuery = true)
    List<Map<String,Object>> groupByYear1(String area);


    @Query(value = "select * from paper where keywords like CONCAT('%',?1,'%') or title like CONCAT('%',?1,'%')",nativeQuery = true)
    Page<Paper> findAllByNameLike(String queryName,Pageable pageable);
}

