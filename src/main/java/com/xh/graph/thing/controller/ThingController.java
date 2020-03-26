package com.xh.graph.thing.controller;
import com.alibaba.fastjson.JSONObject;
import com.xh.graph.thing.repository.ThingRepository;
import com.xh.graph.utils.JsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author xinghao
 * @descreption
 * @date 2019/1/11
 */

@Controller
@RequestMapping("/graph")
public class ThingController {
    private final ThingRepository thingRepository;

    @Autowired
    public ThingController(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @GetMapping("/graph")
    public String graphPage() {
        return "graph";
    }

    @ResponseBody
    @GetMapping("/node")
    public String getGraph() {
        List<Map<String,Object>> nodeList = thingRepository.getAllNode();
        List<Map<String, Object>> relationList = thingRepository.getAllRelation();
        JSONObject res = JsUtil.graph2Json(nodeList, relationList);
        return res.toString();
    }

    /*@GetMapping("/getSpecifiedNode")
    public List<String> getSpecifiedNode(){
        List res = thingRepository.getNodeByName("Dynamic_Programming");
        return res;
    }

    @GetMapping("/getSpecifiedRelation")
    public List<String> getSpecifiedRelation(){
        String r ="ns1__"+"subClassOf";
        List res = thingRepository.getSpecifiedRelation(r);
        return res;
    }
    @GetMapping("/getRelationViaName")
    public List<String> getRelationViaName(){
        String name1="Supervised_Learning";
        String name2="Machine_Learning";
        List res = thingRepository.getRelationViaName(name1,name2);
        return res;
    }

    @ResponseBody
    @GetMapping("/getPath")
    public String getPath(){
        String name1="Objective_Function";
        String name2="Machine_Learning";
        List res = thingRepository.getPath(name1,name2);
        JSONObject pathJson = JSONObject.parseObject(JSON.toJSONString(res.get(0)));
        JSONArray pathArray = pathJson.getJSONArray("r");
        return JsUtil.relations2Json(pathArray,thingRepository);

    }*/

    /*@GetMapping("/getLearningPath")
    public List<String> getLearningPath(){
        String name1="Object_Detection";
        String name2="ex__"+"ApplicationToTask";
        List res = thingRepository.getLearningPath(name1,name2);
        return res;
    }*/


}
