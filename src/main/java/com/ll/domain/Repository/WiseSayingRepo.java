package com.ll.domain.Repository;

import com.ll.domain.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepo {
    private List<WiseSaying> wiseSayingList;

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public WiseSayingRepo(){
        wiseSayingList = new ArrayList<>();
    }
}