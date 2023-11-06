package com.ll.domain;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private static Context instance = null;
    private List<WiseSaying> wiseSayingList ;
    private Context() {
        wiseSayingList = new ArrayList<>();
    }

    public static Context getInstance(){
        if(instance==null){
            instance = new Context();
        }
        return instance;
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }
}