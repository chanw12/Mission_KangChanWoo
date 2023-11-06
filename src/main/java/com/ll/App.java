package com.ll;

import com.ll.domain.Context;
import com.ll.domain.WiseSaying;
import com.ll.domain.repository.WiseSayingRepo;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingRepo wiseSayingRepo;

    public App(Scanner scanner, WiseSayingRepo wiseSayingRepo) {
        this.scanner = scanner;
        this.wiseSayingRepo = wiseSayingRepo;

    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {

                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.print("작가 : ");
                String authorName = scanner.nextLine();
                WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++, content, authorName);
                wiseSayingRepo.getWiseSayingList().add(wiseSaying);
                System.out.println(wiseSaying.getId()+"번 명언이 등록되었습니다.");

                break;
            }
        }
    }
}