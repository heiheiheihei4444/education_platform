package com.xh.graph.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xh.graph.thing.repository.ThingRepository;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xinghao
 * @descreption
 * @date 2019/1/11
 */

@Component
public class JsUtil {
    public static JSONObject graph2Json(List nodes, List relations) {
        JSONArray nodesArray = new JSONArray();
        for (Object node : nodes) {
            JSONObject nodeJson = JSONObject.parseObject(JSON.toJSONString(node));
            JSONObject propertiesJson = nodeJson.getJSONObject("properties");
            String nameJson = propertiesJson.getString("ex__name");
            nameJson = (nameJson != null) ? nameJson : propertiesJson.getString("uri");
            nodeJson.put("name", nameJson);

            String dataType = propertiesJson.getString("ex__datatype");
            if (dataType == null) {
                nodeJson.put("category", 2);
            } else {
                switch (dataType) {
                    case "Task":
                        nodeJson.put("category", 0);
                        break;
                    case "Math":
                        nodeJson.put("category", 1);
                        break;
                    case "ML":
                        nodeJson.put("category", 2);
                        break;
                }
            }

            nodesArray.add(nodeJson);
        }

        JSONArray relationsArray = JSONArray.parseArray(JSON.toJSONString(relations));

        JSONObject graphJson = new JSONObject();
        graphJson.put("nodes", nodesArray);
        graphJson.put("links", relationsArray);

        return graphJson;
    }

    //��·��ת��Ϊ������͹�ϵ����
    public static JSONObject relations2Json(JSONArray pathArray, ThingRepository thingRepository) {
        Set<Integer> nodesId = new HashSet<>();
        List<Object> links = new ArrayList<>();

        for (int i = 0; i < pathArray.size(); i++) {
            JSONObject pathObject = pathArray.getJSONObject(i);
            int sourceId = pathObject.getInteger("startNode");
            int targetId = pathObject.getInteger("endNode");

            nodesId.add(sourceId);
            nodesId.add(targetId);

            JSONObject link = new JSONObject();
            link.put("type", pathObject.getString("type"));
            link.put("source", sourceId);
            link.put("target", targetId);
            links.add(link);
        }

        List<Map<String, Object>> nodes = new ArrayList<>();
        for (int id : nodesId) {
            List<Map<String, Object>> node = thingRepository.getNodeById(id);
            nodes.add(node.get(0));
        }
        return JsUtil.graph2Json(nodes, links);
    }

    public static String createBotRes1(JSONObject res) {
        String template1 = "Name: %s,<br> Chinese Name:  %s,<br> Description:  %s,<br> Learning Link:  %s.<br>You can click to find some potentially relevant knowledge of  %s.";
        template1 = template1.replaceAll("%.*?s", "<span style=\"color:#104071\">$0</span>");
        JSONObject properties1 = res.getJSONArray("nodes").getJSONObject(0).getJSONObject("properties");
        return String.format(template1, properties1.getString("ex__name"), properties1.getString("ex__cn_name"), properties1.getString("ex__description"), properties1.getString("ex__link"), properties1.getString("ex__name"));

    }

    public static String createEmptyRes(){
        return "<b>֪ʶ����δ�ҵ������Ϣ��</b>";
    }

    public static String createNameLike(List<String> names){
        String recommends = "";
        for (String name : names){
            recommends+="<a onclick=\"searchName('"+name+"')\" style=\"margin-right:17px;word-wrap:break-word;\"  href=\"javascript:void(0);\">"+name+"</a>";
        }
        return "<b>��Ҫ�ҵ��ǲ��ǣ�</b>"+recommends;
    }

    public static String createBotResTask(String description,String MLName,String taskName,String oriName,String otherTaskName,String mathName,List<String> neighbors) {
        String template2 = "<b>�������������</b>%3$s��һ�ֻ���ѧϰ<b>����</b>��<br><br>"+
                "<b>����</b>��%1$s ��<br><br>"+
                "<b>֪ʶ�Ƽ���</b>%2$s ��ʵ�� %3$s ��һ���㷨, ���㷨�� %4$s �ݱ������%2$s �㷨�ĵײ����֪ʶ���� ��%6$s ��" +
                "ͬʱ�� %2$s ������ʵ������������ %5$s ��"+"<br><br>" +
                "<b>���ʵ���Ƽ���</b> %7$s";
        String sNeighbors="";
        for (String neighbor : neighbors){
            sNeighbors+="<a onclick=\"searchName('"+neighbor+"')\" style=\"margin-right:17px;word-wrap:break-word;\"  href=\"javascript:void(0);\">"+neighbor+"</a>";
        }
        template2 = template2.replaceAll("%.*?s", "<span style=\"color:#0186d5\">$0</span>");
        return String.format(template2,description, MLName,taskName,oriName,otherTaskName,mathName,sNeighbors);
    }

    public static String createResML(String nodeName,String description,String oriName,String mathName,String tasks,List<String> neighbors) {
        String template2 = "<b>�������������</b>%1$s��һ�ֻ���ѧϰ<b>�㷨</b>��<br><br>"+
                "<b>����</b>��%2$s <br><br>"+
                "<b>֪ʶ�Ƽ���</b>���㷨�� %3$s �ݱ������%1$s����ʵ�ֵĻ���ѧϰ���������%5$s���㷨�ĵײ����֪ʶ���� ��%4$s ��<br><br>" +
                "<b>���ʵ���Ƽ���</b> %6$s";
        String sNeighbors="";
        for (String neighbor : neighbors){
            sNeighbors+="<a onclick=\"searchName('"+neighbor+"')\" style=\"margin-right:17px;word-wrap:break-word;\"  href=\"javascript:void(0);\">"+neighbor+"</a>";
        }
        template2 = template2.replaceAll("%.*?s", "<span style=\"color:#104071\">$0</span>");
        return String.format(template2, nodeName,description,oriName,mathName,tasks,sNeighbors);
    }

    public static String createResMath(String nodeName,String description,String maths,String MLs,List<String> neighbors) {
        String template3 = "<b>�������������</b>%1$s��һ�ֻ���ѧϰ��ص�<b>��ѧ֪ʶ</b>��<br><br>"+
                "<b>����</b>��%2$s <br><br>"+
                "<b>֪ʶ�Ƽ���</b>����ѧ֪ʶ������֪ʶΪ�� %3$s ��ʹ�õ�%1$s�Ļ���ѧϰ�㷨�У�%4$s��<br><br>" +
                "<b>���ʵ���Ƽ���</b> %5$s";
        String sNeighbors="";
        for (String neighbor : neighbors){
            sNeighbors+="<a onclick=\"searchName('"+neighbor+"')\" style=\"margin-right:17px;word-wrap:break-word;\"  href=\"javascript:void(0);\">"+neighbor+"</a>";
        }
        template3 = template3.replaceAll("%.*?s", "<span style=\"color:#0186d5\">$0</span>");
        return String.format(template3,nodeName, description,maths,MLs,sNeighbors);
    }


    public static String createBotRes3(String oriName,String fName,String taskName) {
        String template4 = "The original algorithm in this field is %1$s, if you want to learn more about this technology, you should learn by this order as the graph shows.<br>" +
                "By the way, all of these algorithms are belong to %2$s, after learning the %1$s, you can achieve %3$s.";
        template4 = template4.replaceAll("%.*?s", "<span style=\"color:#0186d5\">$0</span>");
        return String.format(template4, oriName,fName,taskName);
    }

    public static String createBotRes4(String nodeName,String fatherName) {
        String template4 = "You can find the hierarchy about %1$s in the graph, %1$s belongs to %2$s. ";
        template4 = template4.replaceAll("%.*?s", "<span style=\"color:#104071\">$0</span>");
        return String.format(template4, nodeName,fatherName);
    }

    public static String createBotRes5(String taskName,String mName,String MLName) {
        String template5 = "The learning path to achieve %1$s can be found on your left. You should  use the %2$s to achieve the %1$s. <br>" +
                "It��s worth to mention, you can also achieve the  %2$s by using  %3$s ";
        template5 = template5.replaceAll("%.*?s", "<span style=\"color:#104071\">$0</span>");
        return String.format(template5,taskName,MLName,mName);
    }


/*    public static String path2Json(){
        return "";
    }*/
}



