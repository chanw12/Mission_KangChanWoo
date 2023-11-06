package com.ll;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

public class TestMain {
    @Test
    @DisplayName("명령어 입력창 구현")
    void t1(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();


        System.out.print("명령)");
        String out = byteArrayOutputStream.toString().trim();
        Assertions.assertThat(out).isEqualTo("명령)");
    }
}