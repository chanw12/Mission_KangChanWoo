package com.ll.domain.controller;

import com.ll.domain.Context;
import com.ll.domain.WiseSaying;
import com.ll.domain.repository.WiseSayingRepo;
import com.ll.domain.utill.Rq;

import java.util.Scanner;

public class CmdController {
    private final WiseSayingRepo wiseSayingRepo;
    Context context = Context.getInstance();
    private final Scanner scanner;
    public CmdController(Scanner scanner,WiseSayingRepo wiseSayingRepo){
        this.wiseSayingRepo = wiseSayingRepo;
        this.scanner = scanner;
    }

    public void cmdStart(){
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            Rq rq = new Rq(cmd);
            if (rq.getAction().equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {

                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.print("작가 : ");
                String authorName = scanner.nextLine();
                WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++, content, authorName);
                System.out.println(wiseSaying.getId()+ "번 명언이 등록되었습니다.");
                wiseSayingRepo.getWiseSayingList().add(wiseSaying);
            }else if(rq.getAction().equals("목록")){
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                for (WiseSaying ws: wiseSayingRepo.getWiseSayingList()){
                    System.out.println(ws.getId()+ " / "+ ws.getAuthor()+ " / "+ ws.getBody());
                }
            }else if(rq.getAction().equals("삭제")){
                int id = rq.getParamAsInt("id",0);
                if(wiseSayingRepo.getWiseSayingList().stream().anyMatch(ws -> ws.getId() == id)){

                    WiseSaying wiseSaying = wiseSayingRepo.getWiseSayingList().stream().filter(ws -> ws.getId() == id).findFirst().get();
                    System.out.println(id+"번 명언이 삭제되었습니다.");
                    wiseSayingRepo.getWiseSayingList().remove(wiseSaying);
                }else{
                    System.out.println(id+"번 명언은 존재하지 않습니다.");
                }
            }


        }
    }
}
