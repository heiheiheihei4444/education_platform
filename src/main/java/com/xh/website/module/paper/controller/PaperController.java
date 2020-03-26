package com.xh.website.module.paper.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xh.website.module.paper.entity.Paper;
import com.xh.website.module.paper.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/paper")
public class PaperController {
    private final PaperRepository paperRepository;
    @Autowired
    public PaperController(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    //paperlist“≥√Ê
    @GetMapping("/paperlist")
    public String getAll() {
        /*System.out.println("searchWay£∫"+searchWay+",specificWay£∫"+specificWay);
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        Page<Paper> paperPage =checkPage("",searchWay,specificWay,pageable);
        List<Map<String,Object>> year_count=paperRepository.groupByYearAndNameLike("");
        List<Map<String,Object>> type_count=paperRepository.groupByTypeAndNameLike("");
        List<Map<String,Object>> area_count=paperRepository.groupByAreaAndNameLike("");
        List<Map<String,Object>> firstThreeYearCount=new ArrayList<>();
        List<Map<String,Object>> restYearCount=new ArrayList<>();
        if (year_count.size()>3){
            for (int i=0;i<3;i++){
                firstThreeYearCount.add(year_count.get(i));
            }
            for(int j=3;j<year_count.size();j++){
                restYearCount.add(year_count.get(j));
            }
        }else{
            firstThreeYearCount=year_count;
        }
        model.addAttribute("paperPage", paperPage);
        model.addAttribute("searchWay", searchWay);
        model.addAttribute("specificWay", specificWay);
        //model.addAttribute("year_count", year_count);
        model.addAttribute("type_count", type_count);
        model.addAttribute("area_count", area_count);
        //model.addAttribute("testYearCount", year_count.size());
        model.addAttribute("firstThreeYearCount", firstThreeYearCount);
        model.addAttribute("restYearCount", restYearCount);
*/
        return "paperlist";
    }

    @ResponseBody
    @GetMapping("/paperpage")
    public JSONObject getPaperPage(@RequestParam(defaultValue = "") String queryName, @RequestParam(defaultValue = "all") String searchWay, @RequestParam(defaultValue = "0") String specificWay,
                                    @RequestParam(defaultValue = "0") int paperPageNow) {
        queryName = queryName.replace("_"," ");
        Page<Paper> paperPage = null;
        Pageable pageable = PageRequest.of(paperPageNow, 3, Sort.Direction.DESC, "id");
        paperPage = checkPage(queryName,searchWay, specificWay, pageable);
        List<Map<String,Object>> year_count=paperRepository.groupByYearAndNameLike(queryName);
        List<Map<String,Object>> type_count=paperRepository.groupByTypeAndNameLike(queryName);
        List<Map<String,Object>> area_count=paperRepository.groupByAreaAndNameLike(queryName);
        List<Map<String,Object>> firstThreeYearCount=new ArrayList<>();
        List<Map<String,Object>> restYearCount=new ArrayList<>();
        if (year_count.size()>3){
            for (int i=0;i<3;i++){
                firstThreeYearCount.add(year_count.get(i));
            }
            for(int j=3;j<year_count.size();j++){
                restYearCount.add(year_count.get(j));
            }
        }else{
            firstThreeYearCount=year_count;
        }

        JSONObject pagerJson = JSON.parseObject(JSONArray.toJSONString(paperPage));
        pagerJson.put("searchWay", searchWay);
        pagerJson.put("specificWay", specificWay);
        pagerJson.put("type_count", type_count);
        pagerJson.put("area_count", area_count);
        pagerJson.put("firstThreeYearCount", firstThreeYearCount);
        pagerJson.put("restYearCount", restYearCount);
        return pagerJson;
    }

    private Page<Paper> checkPage(String queryName,String searchWay, String specificWay, Pageable pageable) {
        Page<Paper> paperPage = null;
        if ("all".equals(searchWay) || "".equals(searchWay)) {
            paperPage = paperRepository.findAllByNameLike(queryName,pageable);
            System.out.println(123);
        } else if ("year".equals(searchWay)) {
            paperPage = paperRepository.findByYearAndNameLike(queryName,specificWay, pageable);
        } else if ("type".equals(searchWay)) {
            paperPage = paperRepository.findByTypeAndNameLike(queryName,specificWay, pageable);
        } else if ("area".equals(searchWay)) {
            paperPage = paperRepository.findByAreaAndNameLike(queryName,specificWay, pageable);
        }
        return paperPage;
    }

}
