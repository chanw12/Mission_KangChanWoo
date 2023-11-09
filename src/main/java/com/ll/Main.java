package com.ll;

import com.ll.domain.SimpleDb;
import com.ll.domain.controller.BoardController;
import com.ll.domain.controller.CmdController;
import com.ll.domain.repository.WiseSayingRepo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WiseSayingRepo wiseSayingRepo = new WiseSayingRepo();
        SimpleDb simpleDb = new SimpleDb("localhost", "simpleDb", "root", "lldj123414");

        BoardController boardController = new BoardController(scanner,wiseSayingRepo);
        WiseSayingRepo.setFilename("data.json");
        CmdController cmdController = new CmdController(scanner,wiseSayingRepo,boardController);

        App app = new App(scanner,wiseSayingRepo,cmdController,simpleDb);
        app.run();
    }

}