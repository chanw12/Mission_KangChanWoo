package com.ll.domain.controller;

import com.ll.domain.WiseSaying;
import com.ll.domain.repository.WiseSayingRepo;

import java.util.Scanner;

public class BoardController {
    private final Scanner scanner;
    private final WiseSayingRepo wiseSayingRepo;
    public BoardController(Scanner scanner,WiseSayingRepo wiseSayingRepo){
        this.scanner = scanner;
        this.wiseSayingRepo = wiseSayingRepo;
    }
    public void regist(){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String authorName = scanner.nextLine();
        WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++, content, authorName);
        System.out.println(wiseSaying.getId()+ "번 명언이 등록되었습니다.");
        wiseSayingRepo.getWiseSayingList().add(wiseSaying);
    }
    public void list(){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying ws: wiseSayingRepo.getWiseSayingList()){
            System.out.println(ws.getId()+ " / "+ ws.getAuthor()+ " / "+ ws.getBody());
        }
    }
    public void delete(int id){
        if(wiseSayingRepo.getWiseSayingList().stream().anyMatch(ws -> ws.getId() == id)){

            WiseSaying wiseSaying = wiseSayingRepo.getWiseSayingList().stream().filter(ws -> ws.getId() == id).findFirst().get();
            System.out.println(id+"번 명언이 삭제되었습니다.");
            wiseSayingRepo.getWiseSayingList().remove(wiseSaying);
        }else{
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        }
    }
    public void modi(int id){
        if(wiseSayingRepo.getWiseSayingList().stream().anyMatch(ws -> ws.getId() == id)){
            WiseSaying wiseSaying = wiseSayingRepo.getWiseSayingList().stream().filter(ws -> ws.getId() == id).findFirst().get();
            System.out.println("명언(기존) : " + wiseSaying.getBody());
            System.out.print("명언 : ");
            wiseSaying.setBody(scanner.nextLine());
            System.out.println("작가(기존) : "+ wiseSaying.getAuthor());
            System.out.print("작가: ");
            wiseSaying.setAuthor(scanner.nextLine());

        }else{
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        }
    }
}
