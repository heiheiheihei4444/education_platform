package com.xh.graph.thing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.xh.graph.bot.Bot;
import com.xh.graph.thing.repository.ThingRepository;

import com.xh.graph.utils.JsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bot")
public class BotController {

    private final ThingRepository thingRepository;

    @Autowired
    public BotController(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @ResponseBody
    @GetMapping("/bot")
    public String getMessage(@RequestParam String message) {

        String messageLike = message.replaceAll("\\+", "[+]");


        int messageFlag = Bot.MatchInput(message);


        String recommendRes = "";
        JSONObject res = new JSONObject();
        String botRes = "";

        //查询状态
        int status = 0;

        //如果节点为空
        List searchNode;
        searchNode = thingRepository.getNodeByName(message);
        if (searchNode.size() == 0) {
            String query = ".*(?i){0}.*";
            query = query.replace("{0}", messageLike);
            List<String> nameLike = thingRepository.getNameLike(query);
            if (nameLike.size() == 0) {
                botRes = JsUtil.createEmptyRes();
            } else {
                if (nameLike.size() > 30) nameLike = nameLike.subList(0, 30);
                botRes = JsUtil.createNameLike(nameLike);
            }
        } else {
            //搜索到相应节点，获取描述
            String type = thingRepository.getDataType(message);//math ML task
            status = 1;
            String description = JSON.parseObject(JSON.toJSONString(searchNode.get(0))).getJSONObject("properties").getString("ex__description");
            if (description == null || description.isEmpty()) description = "/";

            //获取用于展示的图谱
            List<Map<String, Object>> displayNodes = thingRepository.getDisplayNodes(message);
            if (displayNodes.size() == 0) {
                List node = new ArrayList();
                node.add(searchNode.get(0));
                res = JsUtil.graph2Json(node,new ArrayList());
            }else {
                JSONArray displayJson = JSONArray.parseArray(JSON.toJSONString(displayNodes));
                JSONArray resJson = new JSONArray();
                for (int i = 0; i < displayJson.size(); i++) {
                    JSONArray tem = displayJson.getJSONObject(i).getJSONArray("r");
                    for (int j = 0; j < tem.size(); j++) {
                        //获取所有的关系对
                        resJson.add(tem.getJSONObject(j));
                    }
                }
                res = JsUtil.relations2Json(resJson, thingRepository);
            }
            List<String> neighbors = thingRepository.getNeighbors(message);
            //用户搜索的是TASK
            if (type.equals("Task")) { //ent_Le
                /*List nodes = thingRepository.getLearningPath(message);
                JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
                JSONArray resArray = new JSONArray();
                for (int i = 0; i < nodesJson.size(); i++) {
                    JSONObject nodeJson = nodesJson.getJSONObject(i).getJSONObject("r");
                    resArray.add(nodeJson);
                }
                res = JsUtil.relations2Json(resArray, thingRepository);*/
                //--------------------------------------------------------------
                List mls = thingRepository.getMLByTask(message);
                String MLname = "/";
                if (mls.size() > 0) {
                    MLname = JSONObject.parseObject(JSON.toJSONString(mls.get(0))).getJSONObject("properties").getString("ex__name");
                }

                List maths = thingRepository.getMathByML(MLname);
                String mathName = "/";
                String mName = "";
                for (Object math : maths) {
                    mName += (JSONObject.parseObject(JSON.toJSONString(math)).getJSONObject("properties").getString("ex__name") + ",");
                }
                if (mName.length() > 0) {
                    mName = mName.substring(0, mName.length() - 1);
                    mathName = mName;
                }

                List fs = thingRepository.getFather(MLname);
                String fName = "/";
                if (fs.size() > 0) {
                    fName = JSONObject.parseObject(JSON.toJSONString(fs.get(0))).getJSONObject("properties").getString("ex__name");
                }

                String oriName = "/";
                List<String> oris = thingRepository.getOriAlgorithm(MLname);
                if (oris.size()==1) {
                    oriName = oris.get(0);
                }

                List tasks = thingRepository.getTaskByML(MLname);
                String otherTask = "/";
                for (Object task : tasks) {
                    String name = JSONObject.parseObject(JSON.toJSONString(task)).getJSONObject("properties").getString("ex__name");
                    if (!name.equals(message)) {
                        otherTask = name;
                        break;
                    }
                }


                botRes = JsUtil.createBotResTask(description, MLname, message,oriName, otherTask, mathName, neighbors);
            } else if (type.equals("ML")) {
                /*List nodes = thingRepository.getStructure(message);
                JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
                JSONArray resArray = new JSONArray();
                for (int i = 0; i < nodesJson.size(); i++) {
                    JSONObject nodeJson = nodesJson.getJSONObject(i).getJSONObject("r1");
                    resArray.add(nodeJson);
                }
                res = JsUtil.relations2Json(resArray, thingRepository);*/

                String fatherName = "/";
                List<Map<String, Object>> father = thingRepository.getFather(message);
                if (father.size() > 0) {
                    JSONObject.parseObject(JSON.toJSONString(father.get(0))).getJSONObject("properties").getString("ex__name");
                }

                String oriName = "/";
                List<String> oris = thingRepository.getOriAlgorithm(message);
                if (oris.size()==1) {
                    oriName = oris.get(0);
                }

                List maths = thingRepository.getMathByML(message);
                String mathName = "/";
                String mName = "";
                for (Object math : maths) {
                    mName += (JSONObject.parseObject(JSON.toJSONString(math)).getJSONObject("properties").getString("ex__name") + ",");
                }
                if (mName.length() > 0) {
                    mName = mName.substring(0, mName.length() - 1);
                    mathName = mName;
                }

                List<Map<String, Object>> taskByML = thingRepository.getTaskByML(message);
                String tasks = "";
                for (Object task : taskByML) {
                    String name = JSONObject.parseObject(JSON.toJSONString(task)).getJSONObject("properties").getString("ex__name");
                    tasks = tasks + name + ",";
                }
                if (tasks.length() > 0) {
                    tasks = tasks.substring(0, tasks.length() - 1);
                } else tasks = "/";
                botRes = JsUtil.createResML(message, description,oriName, mathName,tasks, neighbors);
            } else if (type.equals("Math")) {

                /*List nodes = new ArrayList();
                Object node = searchNode.get(0);
                JSONObject nodeJson = JSONObject.parseObject(JSON.toJSONString(node));

                nodes = thingRepository.getPreknowledges(message);

                JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
                JSONArray r = nodesJson.size() > 0 ? nodesJson.getJSONObject(nodesJson.size() - 1).getJSONArray("r") : new JSONArray();

                res = JsUtil.relations2Json(r, thingRepository);*/

                //--------------------------------

                List preMaths = thingRepository.getPreknowledge(message);

                String resMaths = "";
                for (Object math : preMaths) {
                    String name = JSONObject.parseObject(JSON.toJSONString(math)).getJSONObject("properties").getString("ex__name");
                    resMaths = resMaths + name + ",";
                }
                if (resMaths.length() > 0) {
                    resMaths = resMaths.substring(0, resMaths.length() - 1);
                } else resMaths = "/";

                List<String> MLs = thingRepository.getMLByMath(message);
                String resMLs = "";
                for (String ML : MLs) {
                    resMLs = resMLs + ML + ",";
                }
                if (resMLs.length() > 0) {
                    resMLs = resMLs.substring(0, resMLs.length() - 1);
                } else resMLs = "/";

                botRes = JsUtil.createResMath(message,description, resMaths, resMLs,neighbors);
            }
        }
        res.put("status", status);
        res.put("botRes", botRes);
        return res.toString();
    }

}


        /*if(messageFlag==1 && !result.equals("")){
            //How to learn specified knowledge, expected 1 variable, return 1 point.
            List nodes;
            nodes = thingRepository.getNodeByName(result);
            res = JsUtil.graph2Json(nodes, new ArrayList());
            botRes = JsUtil.createBotRes1( res);
        }else if(messageFlag==2 && !result.equals("")){
            //"How to achieve ... by algorithm"
            //How to achieve specified task, expected 1 variable, return learning path: ML-Task.
           *//* List nodes;
            nodes = thingRepository.getLearningPath(result);
            JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
            JSONArray resArray = new JSONArray();
            for (int i = 0; i < nodesJson.size(); i++) {
                JSONObject nodeJson = nodesJson.getJSONObject(i).getJSONObject("r");
                resArray.add(nodeJson);
            }
            res = JsUtil.relations2Json(resArray, thingRepository);
            //--------------------------------------------------------------
            List mls = thingRepository.getMLByTask(result);
            String MLname = "/";
            if (mls.size() > 0) {
                MLname =JSONObject.parseObject(JSON.toJSONString(mls.get(0))).getJSONObject("properties").getString("ex__name");
            }

            List fs = thingRepository.getFather(MLname);
            String fName = "/";
            if (fs.size() > 0) {
                fName = JSONObject.parseObject(JSON.toJSONString(fs.get(0))).getJSONObject("properties").getString("ex__name");
            }

            List vs = thingRepository.getVariantAlgorithm(MLname);
            String vName = "/";
            if (vs.size()>0) {
                vName = JSONObject.parseObject(JSON.toJSONString(vs.get(0))).getJSONObject("properties").getString("ex__name");
            }

            List tasks = thingRepository.getTaskByML(MLname);
            String otherTask="/";
            for (Object task : tasks) {
                String name = JSONObject.parseObject(JSON.toJSONString(task)).getJSONObject("properties").getString("ex__name");
                if (!name.equals(result)) {
                    otherTask = name;
                    break;
                }
            }*//*
            //botRes = JsUtil.createBotRes2(MLname, result, fName, vName, otherTask);
        }else if(messageFlag==3 && !result.equals("")){
            //the pre-knowledge/ori-algorithm of specified knowledge, expected 1 variable, return learning path.

            List nodes = new ArrayList();
            Object node = thingRepository.getNodeByName(result).get(0);
            JSONObject nodeJson = JSONObject.parseObject(JSON.toJSONString(node));
            String dataType = nodeJson.getJSONObject("properties").getString("ex__datatype");
            if ("ML".equals(dataType)) {
                nodes = thingRepository.getOriAlgorithms(result);
            } else if ("Math".equals(dataType)) {
                nodes = thingRepository.getPreknowledges(result);
            }
            JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
            JSONArray r = nodesJson.size() > 0 ? nodesJson.getJSONObject(nodesJson.size() - 1).getJSONArray("r") : new JSONArray();

            res = JsUtil.relations2Json(r, thingRepository);

            //--------------------------------

            String oriName = "/";
            if ("ML".equals(dataType)) {
                List ori = thingRepository.getOriAlgorithm(result);

                if (ori.size() > 0) {
                    oriName =JSONObject.parseObject(JSON.toJSONString(ori.get(0))).getJSONObject("properties").getString("ex__name");
                }
            } else if ("Math".equals(dataType)) {
                List math = thingRepository.getOriAlgorithm(result);

                if (math.size() > 0) {
                    oriName =JSONObject.parseObject(JSON.toJSONString(math.get(0))).getJSONObject("properties").getString("ex__name");
                }
            }

            List fs = thingRepository.getFather(oriName);
            String fName = "/";
            if (fs.size() > 0) {
                fName = JSONObject.parseObject(JSON.toJSONString(fs.get(0))).getJSONObject("properties").getString("ex__name");
            }

            List tasks = thingRepository.getTaskByML(oriName);
            String taskName = "/";
            if (tasks.size() > 0) {
                taskName = JSONObject.parseObject(JSON.toJSONString(tasks.get(0))).getJSONObject("properties").getString("ex__name");
            }
            botRes = JsUtil.createBotRes3(oriName,fName,taskName);

        }else if(messageFlag==4 && !result.equals("")){
            //the structure of specified knowledge, expected 1 variable, return fathercl-cl-subcl.

            List nodes;
            nodes = thingRepository.getStructure(result);

            JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
            JSONArray resArray = new JSONArray();
            for (int i = 0; i < nodesJson.size(); i++) {
                JSONObject nodeJson = nodesJson.getJSONObject(i).getJSONObject("r1");
                resArray.add(nodeJson);
            }
            res = JsUtil.relations2Json(resArray, thingRepository);
            List a = thingRepository.getNodeByName(result);

            botRes = JsUtil.createBotRes4(
                    JSONObject.parseObject(JSON.toJSONString(thingRepository.getNodeByName(result).get(0))),
                    JSONObject.parseObject(JSON.toJSONString(thingRepository.getFather(result).get(0)))
            );
        }else if(messageFlag==5 && !result.equals("")){
            //"What is the learning path of.."
            //the pre-knowledge of specified knowledge, expected 1 variable,return learning path: Math-ML-Task.
            System.out.println(123);
            List nodes;
            nodes = thingRepository.getCompeletePath(result);
            JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
            JSONArray resArray = new JSONArray();
            for (int i = 0; i < nodesJson.size(); i++) {
                JSONArray paths = nodesJson.getJSONObject(i).getJSONArray("r");
                resArray.addAll(paths);
            }
            res = JsUtil.relations2Json(resArray, thingRepository);
            //--------------------------------------------------------
            List MLs = thingRepository.getMLByTask(result);
            String MLName = "/";
            String mathName = "/";
            if (MLs.size() > 0) {
                MLName = JSONObject.parseObject(JSON.toJSONString(MLs.get(2))).getJSONObject("properties").getString("ex__name");

                List maths = thingRepository.getMathByML(MLName);
                String mName = "";
                for (Object math : maths) {
                    mName+=(JSONObject.parseObject(JSON.toJSONString(math)).getJSONObject("properties").getString("ex__name")+",");
                }
                if (mName.length() > 0) {
                    mName = mName.substring(0, mName.length() - 1);
                    mathName = mName;
                }
            }
            botRes = JsUtil.createBotRes5(result,mathName,MLName);

        }*/


    /*private JSONArray getPreOrOri(String result) {
        List nodes = new ArrayList();
        Object node = thingRepository.getNodeByName(result).get(0);
        JSONObject nodeJson = JSONObject.parseObject(JSON.toJSONString(node));
        String dataType = nodeJson.getJSONObject("properties").getString("ex__datatype");
        if ("ML".equals(dataType)) {
            nodes = thingRepository.getOriAlgorithm(result);
        } else if ("Math".equals(dataType)) {
            nodes = thingRepository.getPreknowledge(result);
        }
        JSONArray nodesJson = JSONArray.parseArray(JSON.toJSONString(nodes));
        return nodesJson.size()>0 ? nodesJson.getJSONObject(nodesJson.size() - 1).getJSONArray("r") : new JSONArray() ;
    }*/



