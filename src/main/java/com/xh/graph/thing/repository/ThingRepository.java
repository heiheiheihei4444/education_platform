package com.xh.graph.thing.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Map;

/**
 * @author xinghao
 * @descreption
 * @date 2019/1/10
 */
public interface ThingRepository extends Neo4jRepository<Object, Long> {
    @Query("MATCH (n) return id(n) as id,properties(n) as properties")
    List<Map<String, Object>> getAllNode();

    @Query("MATCH (a)-[r]->(b) RETURN id(a) as source,type(r) as type,id(b) as target")
    List<Map<String, Object>> getAllRelation();

    @Query("MATCH (n) where n.ex__name = {0} return id(n) as id,properties(n) as properties")
    List<Map<String, Object>> getNodeByName(String name);

    @Query("MATCH (n) where id(n) = {0} return id(n) as id,properties(n) as properties")
    List<Map<String, Object>> getNodeById(int id);

    @Query("MATCH (n)-[r]->(b) where type(r) = {0} return n,type(r),b")
    List<Map<String, Object>> getSpecifiedRelation(String Relation);

    @Query("MATCH (n)-[r]->(b) where n.ex__name = {0} && b.ex__name = {1} return n,type(r),b")
    List<Map<String, Object>> getRelationViaName(String name1, String name2);

    @Query("match data=shortestpath((na{ex__name:{0}})-[r*1..3]->(nb{ex__name:{1}})) return r")
    List<Map<String, Object>> getPath(String name1, String name2);

    /*@Query("match p=(na{ex__name:{0}})<-[r:ex__ApplicationToTask]-(nb) return id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getMLByTask(String name);*/

    @Query("match p=(na{ex__name:{0}})-[r:ex__ApplicationToTask]->(nb) return id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getTaskByML(String name);

    @Query("match p=(na{ex__name:{0}})-[r:ex__ApplicationToML]->(nb) return nb.ex__name")
    List<String> getMLByMath(String name);

    @Query("match p=(na{ex__name:{0}})<-[r:ex__ApplicationToTask]-(nb) return r")
    List<Map<String, Object>> getLearningPath(String name);

    @Query("match p=((na{ex__name:{0}})<-[r:ex__Ori_algorithm]-(nb)) return id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getVariantAlgorithm(String name);

    @Query("match p=((na{ex__name:{0}})-[r:ex__Ori_algorithm*]->(nb)) return r")
    List<Map<String, Object>> getOriAlgorithms(String name);

    @Query("match p=((na{ex__name:{0}})-[r*..2]-(nb)) return na,r,nb")
    List<Map<String, Object>> getDisplayNodes(String name);

    @Query("match p=((na{ex__name:{0}})-[r:ex__Ori_algorithm]->(nb)) return nb.ex__name")
    List<String> getOriAlgorithm(String name);

    @Query("match p=((na{ex__name:{0}})-[r:ex__Preknowledge*]->(nb)) return r")
    List<Map<String, Object>> getPreknowledges(String name);

    @Query("match p=((na{ex__name:{0}})-[r:ex__Preknowledge]->(nb)) return id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getPreknowledge(String name);

    @Query("match (n{ex__name:{0}})-[r:ns1__subClassOf]->(m)  return id(m) as id,properties(m) as properties")
    List<Map<String, Object>> getFather(String name);

    @Query("match (n1{ex__name:{0}})<-[r1:ns1__subClassOf]-(m1) return r1 \n" +
            "union\n" +
            "match (n2{ex__name:{0}})-[r2:ns1__subClassOf]->(m2) with id(m2) as id  match (n1)-[r1:ns1__subClassOf]->(m1) where id(m1)=id return r1")
    List<Map<String, Object>> getStructure(String name);

    @Query("match p=(na{ex__name:{0}})<-[r:ex__ApplicationToML]-(nb) return id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getMathByML(String name);

    @Query("match p=( (a{ex__datatype:\"Math\"})-[r:ex__ApplicationToTask|:ex__ApplicationToML*]->(b{ex__datatype:\"Task\",ex__name:{0}})) return r")
    List<Map<String, Object>> getCompeletePath(String name);

    @Query("match p=( (na{ex__datatype:\"Math\"})-[r:ex__ApplicationToML*]->(nb{ex__datatype:\"ML\"})-[r1:ex__ApplicationToTask]->(nc{ex__datatype:\"Task\",ex__name:{0}})) return distinct id(nb) as id,properties(nb) as properties")
    List<Map<String, Object>> getMLByTask(String name);

    @Query("match (n) where n.ex__name={0} return n.ex__datatype")
    String getDataType(String name);

    @Query("match (a) where replace(a.ex__name,\"_\",\"\")=~{0} or a.ex__name=~{0} return a.ex__name")
    List<String> getNameLike(String name);

    @Query("match (a)-[]-(b) where b.ex__name={0} return a.ex__name")
    List<String> getNeighbors(String name);

}
