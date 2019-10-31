package com.fh.shop.api.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {

    public static String send(String url){
        return  send(url,null,null);
    }

    public static String send(String url, Map<String, String> headers){
        return  send(url,headers,null);
    }

    public static String send(Map<String, String> params, String url){
        return  send(url,null,params);
    }

    public static String send(String url, Map<String, String> headers, Map<String, String> params){
        //打开浏览器
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //post请求 输入url地址
        HttpPost httpPost = new HttpPost(url);
        //循环添加头信息 没有不为空才添加
        if(null != headers && headers.size() > 0){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                //迭代器遍历
                Map.Entry<String, String> next = iterator.next();
                //添加头信息
                httpPost.addHeader(next.getKey(),next.getValue());
            }
        }
        CloseableHttpResponse response = null;
        String s = null;
        try {
            //参数信息 没有不为空才添加
            if(null != params && params.size() > 0) {
                //声明list集合放参数
                ArrayList<BasicNameValuePair> list = new ArrayList<>();
                //用key循环获取每对key value
                for (String key : params.keySet()) {
                    //添加参数
                    list.add(new BasicNameValuePair(key,params.get(key)));
                }
                //转码
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");
                //将转码后的东西放入post请求
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //执行请求
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            s = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            //抛出异常
            throw new RuntimeException(e);
        }finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != httpPost){
                httpPost.releaseConnection();
            }
            if(null != client){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

}
