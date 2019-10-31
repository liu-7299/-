package com.fh.admin.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

    private static final ThreadLocal<HttpServletRequest> thread = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletResponse> thread1 = new ThreadLocal<>();

    public static void setThread(HttpServletRequest request){
        thread.set(request);
    }

    public static HttpServletRequest getThread(){
        HttpServletRequest request = thread.get();
        return request;
    }

    public static void setThread1(HttpServletResponse response){
        thread1.set(response);
    }

    public static HttpServletResponse getThread1(){
        HttpServletResponse response = thread1.get();
        return response;
    }

    public static void remove(){
        thread.remove();
    }
    public static void remove1(){
        thread1.remove();
    }
}
