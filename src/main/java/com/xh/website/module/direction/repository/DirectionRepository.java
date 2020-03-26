package com.xh.website.module.direction.repository;

import com.xh.website.module.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author qixiaohui
 * @descreption
 * @date 2018/12/23
 */

public interface DirectionRepository extends JpaRepository<Direction,Integer> {
    /*Page<Paper> findByArea(String area, Pageable pageable);
    Page<Paper> findByAreaAndYear(String area, String year, Pageable pageable);
    @Query(value = "select year,count(year) as counts from paper where area=#{area} group by year DESC ", nativeQuery = true)
    List<Map<String,Object>> groupByYear(String area);*/
}
