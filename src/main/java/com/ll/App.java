package com.ll;


import com.ll.domain.controller.CmdController;
import com.ll.domain.repository.WiseSayingRepo;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingRepo wiseSayingRepo;
    private final CmdController cmdController;

    public App(Scanner scanner, WiseSayingRepo wiseSayingRepo,CmdController cmdController) {
        this.scanner = scanner;
        this.wiseSayingRepo = wiseSayingRepo;
        this.cmdController = cmdController;

    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        WiseSayingRepo.setWiseSayingList(WiseSayingRepo.loadData());

        cmdController.cmdStart();
        WiseSayingRepo.saveData(wiseSayingRepo.getWiseSayingList());
    }
}