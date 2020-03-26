package com.xh.graph.bot;

/**
 * @author xinghao
 * @descreption
 * @date 2019/1/15
 */

public class Bot {


    /*private static String regex = "\\S*\\_\\S*";*/



    /*public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }*/

    public static String getMatcher(String input) {
        String result = "";
        if(matchString(input,"What is the learning path of")){
            String[] split = input.split("What is the learning path of ");
            result=split[split.length-1];
        } else if(matchString(input,"How to achieve")){
            String[] split = input.split("How to achieve ");
            result=split[split.length-1];
        }else if(matchString(input,"How to learn")){
            String[] split = input.split("How to learn");
            result=split[split.length-1];
        }else if(matchString(input,"What's the family of")){
            String[] split = input.split("What's the family of");
            result=split[split.length-1];
        }else if(matchString(input,"What is")){
            String[] split = input.split("What is");
            result=split[split.length-1];
        }
        result = result.trim().replaceAll("[.¡£?£¿]", "");

        return result;
    }

    public static boolean matchString( String parent,String child )
    {
        Boolean a =false;
        String[] array = parent.split(child);
        if((array.length-1)==1){
            a=true;
        }
        return a;
    }

    public static int MatchInput(String input) {
        int result=0;
        if(matchString(input,"What is the learning path of")){
            //"How to achieve ... from math"
            //the pre-knowledge of specified knowledge, expected 1 variable,return learning path: Math-ML-Task.
            result=5;
        } else if(matchString(input,"How to achieve")){
            //"How to achieve ... by algorithm"
            //How to achieve specified task, expected 1 variable, return learning path: ML-Task.
            result=2;
        }else if(matchString(input,"How to learn")){
            //the pre-knowledge/ori-algorithm of specified knowledge, expected 1 variable, return learning path.
            result=3;
        }else if(matchString(input,"What's the family of")){
            //the structure of specified knowledge, expected 1 variable, return fathercl-cl-subcl.
            result=4;
        }else if(matchString(input,"What is")){
            //How to learn specified knowledge, expected 1 variable, return 1 point.

            result=1;
        }


        return result;
    }



/*
    public static String getRegex() {
        return regex;
    }
*/

}
