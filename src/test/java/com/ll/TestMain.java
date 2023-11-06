package com.ll;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class TestMain {
    @Test
    @DisplayName("명령어 입력창 구현")
    void t1(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();


        System.out.print("명령)");
        String out = byteArrayOutputStream.toString().trim();
        Assertions.assertThat(out).isEqualTo("명령)");
    }

    @Test
    @DisplayName("명령어 입력 확인")
    void t2(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();
        System.out.print("명령)");
        String out = byteArrayOutputStream.toString().trim();
        Scanner scanner = TestUtil.genScanner("종료");
        String cmd = scanner.nextLine();

        Assertions.assertThat(cmd).isEqualTo("종료");
    }
    @Test
    @DisplayName("종료 기능 확인")
    void t3(){

        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                종료
                """.stripIndent());
        App app = new App(scanner);

        app.run(); // 프로그램 실행


    }


    @Test
    @DisplayName("명언 클래스 생성 확인")
    void t4(){
        WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++,"안녕하세요","강찬우");

    }
}