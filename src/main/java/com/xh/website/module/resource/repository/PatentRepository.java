package com.xh.website.module.resource.repository;

import com.xh.website.module.resource.entity.Patent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/26
 */

public interface PatentRepository extends JpaRepository<Patent,Integer> {
    Page<Patent> findByPatentType(String patentType, Pageable pageable);
}
