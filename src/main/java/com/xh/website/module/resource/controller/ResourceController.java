package com.xh.website.module.resource.controller;

import com.xh.website.module.resource.entity.Patent;
import com.xh.website.module.resource.repository.PatentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.*;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/26
 */

@Controller
@RequestMapping("/resource")
public class ResourceController {

    private final
    PatentRepository patentRepository;

    @Autowired
    public ResourceController(PatentRepository patentRepository) {
        this.patentRepository = patentRepository;
    }

    @RequestMapping("resourcelist")
    public String getAll(@RequestParam(defaultValue = "all") String type, Model model) {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        Page<Patent> page;
        if (type.equals("all")){
            page = patentRepository.findAll(pageable);
        } else {
            page = patentRepository.findByPatentType(type, pageable);
        }
        model.addAttribute("type", type);
        model.addAttribute("page", page);
        return "resource";
    }

    @RequestMapping("/upload")
    public String getUploadPage() {
        return "patent_upload";
    }

    @PostMapping("/upload")
    public String upload(Patent patent, BindingResult bindingResult) {
        patentRepository.save(patent);
        return "patent_upload";
    }

    @ResponseBody
    @RequestMapping("/respage")
    public Page<Patent> getResPage(@RequestParam(required = false, defaultValue = "0") int resPageNow, @RequestParam(defaultValue = "all") String type, Model model) {
        Pageable pageable = PageRequest.of(resPageNow, 10, Sort.Direction.DESC, "id");
        Page<Patent> page;
        if (type == "all"){
            page = patentRepository.findAll(pageable);
        } else {
            page = patentRepository.findByPatentType(type, pageable);
        }
        return page;
    }



    @ResponseBody
    @PostMapping("/upload/classification")
    public String getCls(@RequestParam String name,@RequestParam String abstr) {
        String rootPath = "E:\\¹¤×÷ÎÄµµ\\bs\\textcnn-multi-class\\textcnn-multi-class\\web_textcnn\\";
        String pyPath = rootPath + "predict1.py";
        Process pr;
        String line = "";
        String reStr = "";
        try {
            String command = "python " + pyPath + " " + rootPath + " " + name + abstr;
            pr = Runtime.getRuntime().exec(command);

            InputStreamReader ir = new InputStreamReader(pr.getInputStream());
            BufferedReader in = new BufferedReader(ir);
            while ((line = in.readLine()) != null) {
                reStr = line;
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reStr;
    }
}
