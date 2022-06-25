package com.example.mppproject.Config;

import java.util.HashMap;

public class OurResponses {
    public static HashMap<Object, Object> okResponse(Object obj){
        HashMap<Object, Object> temp = new HashMap<>();
        temp.put("status", 200);
        temp.put("message", "Success");
        temp.put(obj.getClass().getSimpleName(), obj);
        return temp;
    }

    public static HashMap<Object, Object> errorResponse(){
        HashMap<Object, Object> temp = new HashMap<>();
        temp.put("status", 400);
        temp.put("message", "Not Success");
        return temp;
    }
}
