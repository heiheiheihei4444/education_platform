package com.xh.website.module.person.repository;

import com.xh.website.module.paper.entity.Paper;
import com.xh.website.module.person.entiy.Degree;
import com.xh.website.module.person.entiy.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/19
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findByDegreeOrDegree(String degree1,String degree2,Pageable pageable);

    Page<Person> findByDegreeAndLeader(String degree,boolean leader,Pageable pageable);

    @Query(value = "select year(pa.date) as year ,count(*) as count from person as per inner join person_paper_list as ppl on per.id = ppl.person_id inner join  paper as pa on ppl.paper_list_id=pa.id where per.id=?1 group by year(pa.date)",nativeQuery = true)
    List<Map<String,Object>> findCountByDate(int person_id);

}
