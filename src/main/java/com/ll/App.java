package com.ll;


import com.ll.domain.SimpleDb;
import com.ll.domain.controller.CmdController;
import com.ll.domain.repository.WiseSayingRepo;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingRepo wiseSayingRepo;
    private final CmdController cmdController;
    private final SimpleDb simpleDb;
    public App(Scanner scanner, WiseSayingRepo wiseSayingRepo,CmdController cmdController,SimpleDb simpleDb) {
        this.scanner = scanner;
        this.wiseSayingRepo = wiseSayingRepo;
        this.cmdController = cmdController;
        this.simpleDb = simpleDb;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        simpleDb.setDevMode(true);
        wiseSayingRepo.makeWiseSaygingTable();
//        WiseSayingRepo.setWiseSayingList(WiseSayingRepo.loadData());
//        WiseSayingRepo.FileLoad();
        cmdController.cmdStart();
        simpleDb.stop();
//        WiseSayingRepo.saveData(wiseSayingRepo.getWiseSayingList());
//        WiseSayingRepo.FileSave(wiseSayingRepo.getWiseSayingList());
    }
}