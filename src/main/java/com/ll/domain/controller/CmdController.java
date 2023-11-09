package com.ll.domain.controller;

import com.ll.domain.Context;
import com.ll.domain.repository.WiseSayingRepo;
import com.ll.domain.utill.Rq;

import java.util.Scanner;

public class CmdController {
    private final WiseSayingRepo wiseSayingRepo;
    Context context = Context.getInstance();
    private final Scanner scanner;
    private final BoardController boardController;
    public CmdController(Scanner scanner,WiseSayingRepo wiseSayingRepo, BoardController boardController){
        this.wiseSayingRepo = wiseSayingRepo;
        this.scanner = scanner;
        this.boardController = boardController;
    }

    public void cmdStart(){
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            Rq rq = new Rq(cmd);
            if (rq.getAction().equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                boardController.regist();
            }else if(rq.getAction().equals("목록")){
                boardController.list();
            }else if(rq.getAction().equals("삭제")){
                int id = rq.getParamAsInt("id",0);
                boardController.delete(id);
            }else if(rq.getAction().equals("수정")){
                int id = rq.getParamAsInt("id",0);
                boardController.modi(id);
            }


        }
    }
}
