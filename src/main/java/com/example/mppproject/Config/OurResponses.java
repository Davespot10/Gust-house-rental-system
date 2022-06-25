package com.example.mppproject.Config;

import java.util.HashMap;

public class OurResponses {
    public static HashMap<Object, Object> okResponse(){
        HashMap<Object, Object> temp = new HashMap<>();
        temp.put("status", 200);
        temp.put("message", "Success");
        return temp;
    }
}
