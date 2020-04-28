package com.bypx.synthesis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bypx.synthesis.FileUtil;
import com.bypx.synthesis.bean.TaskInfo;
import com.bypx.synthesis.bean.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    FileUtil fileUtil;

    @Value("${app-setting}")
    private File systemSetting;

    @RequestMapping("getUser")
    @ResponseBody
    public Map getUser(){
        Map result = new HashMap();
        result.put("rows",JSONObject.parseArray(fileUtil.readString(systemSetting),  User.class));
        return  result;
    }


    @RequestMapping("user/login")
    @ResponseBody
    public Map login(HttpServletRequest request,String name, String pass){

        Map result = new HashMap();
        result.put("code","0");
        if(StringUtils.isEmpty(name ) || StringUtils.isEmpty(pass )){
            result.put("msg","参数错误");
            return result;
        }
        result.put("msg","账号或密码错误");
        List<User> list = JSONObject.parseArray(fileUtil.readString(systemSetting),  User.class);
        for (User user1 : list) {
            if(user1.getName().equals(name) && user1.getPass().equals(pass)){
                result.put("code","1");
                result.put("msg","");
                request.getSession().setAttribute("flag",true);

            }
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public Map add(String name,String pass){

        Map result = new HashMap();
        result.put("code","0");
        if(StringUtils.isEmpty(name ) || StringUtils.isEmpty(pass )){
            result.put("msg","参数错误");
            return result;
        }
        List<User> list = JSONObject.parseArray(fileUtil.readString(systemSetting),  User.class);
        boolean flag = true;
        for (User user1 : list) {
            if(user1.getName().equals(name)){
                result.put("code","0");
                result.put("msg","账号已存在");
                flag = false;
                break;
            }
        }
        if(flag){
            list.add(new User(name,pass));
            fileUtil.writeString(systemSetting,JSON.toJSONString(list));
            result.put("code","1");
            result.put("msg","2");
        }
        return result;
    }


    @RequestMapping("test")
    @ResponseBody
    public Map test(@RequestBody  List<TaskInfo> list){
        Map map = new HashMap();
        map.put("code",0);
         try {
             System.out.println(JSON.toJSONString(list));
             map.put("code",1);
          }catch (Exception e){
             e.printStackTrace();
             map.put("msg",e.getMessage());
          }
        return map;
    }
    @RequestMapping("teup")
    @ResponseBody
    public Map teup(@RequestBody TaskInfo taskInfo){
        Map map = new HashMap();
        map.put("code",0);
         try {
             System.out.println(JSON.toJSONString(taskInfo));
             map.put("code",1);
          }catch (Exception e){
             e.printStackTrace();
             map.put("msg",e.getMessage());
          }
        return map;
    }
}
