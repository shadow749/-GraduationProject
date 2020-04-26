package com.bypx.synthesis;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class FileUtil {



    public String readString(File file) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            StringBuilder content = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 写入文件
     */
    public void writeString(File file,String content){

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                //关闭流释放资源
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 读取json类型的文件
     * @param systemSetting
     * @return
     */
    public String  readJson(File systemSetting) {
        String content = readString(systemSetting);
        return content;
    }

    /**
     * 写入json类型的文件
     * @param systemSetting
     * @param o
     */
    public void writeJson(File systemSetting,Object o) {
        String jsonString = JSON.toJSONString(o);
        writeString(systemSetting,jsonString);
    }
}

