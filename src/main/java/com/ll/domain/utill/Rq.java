package com.ll.domain.utill;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    String cmd;
    String action;
    String queryString;
    Map<String, String> paramsMap;

    public Rq(String cmd) {
        paramsMap = new HashMap<>();

        this.cmd = cmd;

        String[] cmdBits = cmd.split("\\?", 2);

        action = cmdBits[0].trim();
        if (cmdBits.length == 1) {
            return;
        }
        queryString = cmdBits[1].trim();
        String[] queryStringBits = queryString.split("&");
        for (int i = 0; i < queryStringBits.length; i++) {
            String queryParamStr = queryStringBits[i];
            String[] queryParamStrBits = queryParamStr.split("=", 2);
            String paramName = queryParamStrBits[0];
            String paramValue = queryParamStrBits[1];

            paramsMap.put(paramName, paramValue);
        }
    }

    public String getAction() {
        return action;
    }

    public int getParamAsInt(String paramName, int defaultValue) {
        String paramValue = paramsMap.get(paramName);
        if (paramValue != null) {
            try {
                return Integer.parseInt(paramValue);
            } catch (NumberFormatException e) {
            }
        }

        return defaultValue;
    }


}