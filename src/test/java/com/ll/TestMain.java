package com.ll;


import com.ll.domain.Context;
import com.ll.domain.controller.CmdController;
import com.ll.domain.repository.WiseSayingRepo;
import com.ll.domain.WiseSaying;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Scanner;

public class TestMain {
    @BeforeEach
    void setUp(){
        WiseSaying.idVal = 1;

    }


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
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();

        Scanner scanner = TestUtil.genScanner("""
                종료
                """.stripIndent());
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo);
        App app = new App(scanner,wiseSayingRepo,cmdController);

        app.run(); // 프로그램 실행


    }


    @Test
    @DisplayName("명언 클래스 생성 확인")
    void t4(){
        WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++,"안녕하세요","강찬우");

    }

    @Test
    @DisplayName("명언 명령어 입력시 명언 ,작가 입력창 구현")
    void t5(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                        """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo);
        new App(scanner,wiseSayingRepo,cmdController).run();

        scanner.close();

        String rs = byteArrayOutputStream.toString();

        Assertions.assertThat(rs).contains("1번 명언이 등록되었습니다.");

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);



    }
    @Test
    @DisplayName("명언 등록 확인")
    void t6(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();
        WiseSaying wiseSaying = new WiseSaying(1,"현재를 사랑하라.","작자미상");
        Context instance = Context.getInstance();

        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                        """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo);
        new App(scanner,wiseSayingRepo,cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();


        scanner.close();

        Assertions.assertThat(wiseSaying.getId()).isEqualTo(wiseSayingList.get(0).getId());

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
    }

    @Test
    @DisplayName("명언 등록시마다 번호 증가 확인")
    public void t7(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                2번째
                작자미상
                종료
                        """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo);

        new App(scanner,wiseSayingRepo,cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();
        scanner.close();

        Assertions.assertThat(wiseSayingList.get(1).getId()).isEqualTo(2);

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

    }

    @Test
    @DisplayName("목록 기능")
    void t8(){
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                목록
                종료
                        """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo);

        new App(scanner,wiseSayingRepo,cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();
        scanner.close();
        String rs = byteArrayOutputStream.toString();
        Assertions.assertThat(rs).contains("""
                번호 / 작가 / 명언
                ----------------------
                1 / 작자미상 / 현재를 사랑하라.
                """
        );

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
    }

    @Test
    @DisplayName("삭제 기능 확인")
    void t9() {
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                    등록
                    현재를 사랑하라.
                    작자미상
                    등록
                    1
                    2
                    등록
                    3
                    4
                    삭제?id=1
                    종료
                            """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner, wiseSayingRepo);

        new App(scanner, wiseSayingRepo, cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();
        scanner.close();
        Assertions.assertThat(wiseSayingList.size()).isEqualTo(2);
        String rs = byteArrayOutputStream.toString();
        Assertions.assertThat(rs).contains("""
                1번 명언이 삭제되었습니다.
                """
        );

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

    }
    @Test
    @DisplayName("삭제 실패 기능 확인")
    void t10() {
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                    등록
                    현재를 사랑하라.
                    작자미상
                    등록
                    1
                    2
                    등록
                    3
                    4
                    삭제?id=1
                    삭제?id=1
                    종료
                            """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner, wiseSayingRepo);

        new App(scanner, wiseSayingRepo, cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();
        scanner.close();
        Assertions.assertThat(wiseSayingList.size()).isEqualTo(2);
        String rs = byteArrayOutputStream.toString();
        Assertions.assertThat(rs).contains("""
                1번 명언은 존재하지 않습니다.
                """
        );

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

    }


    @Test
    @DisplayName("수정 기능 확인")
    void t11() {
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        Scanner scanner = TestUtil.genScanner("""
                    등록
                    현재를 사랑하라.
                    작자미상
                    수정?id=1
                    현재와 자신을 사랑하라.
                    홍길동
                    목록
                    종료
                            """.stripIndent());
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        CmdController cmdController = new CmdController(scanner, wiseSayingRepo);

        new App(scanner, wiseSayingRepo, cmdController).run();
        List<WiseSaying> wiseSayingList = wiseSayingRepo.getWiseSayingList();
        scanner.close();
        String rs = byteArrayOutputStream.toString();
        Assertions.assertThat(rs).contains("""
                명언(기존) : 현재를 사랑하라.
                작가(기존) : 작자미상
                2 / 홍길동 / 현재와 자신을 사랑하라.
                """
        );

        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

    }


}