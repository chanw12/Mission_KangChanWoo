package com.ll.domain.controller;

import com.ll.domain.SimpleDb;
import com.ll.domain.Sql;
import com.ll.domain.WiseSaying;
import com.ll.domain.repository.WiseSayingRepo;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class BoardController {
    private final Scanner scanner;
    private final WiseSayingRepo wiseSayingRepo;


    public void regist(){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String authorName = scanner.nextLine();
        WiseSaying wiseSaying = new WiseSaying(WiseSaying.idVal++, content, authorName);
        System.out.println(wiseSaying.getId()+ "번 명언이 등록되었습니다.");
        Sql sql = SimpleDb.genSql();
        sql.append("INSERT INTO Wisesaying (body, author)")
                .append( "VALUES (?, ?)",content,authorName);
        sql.insert();


//        wiseSayingRepo.getWiseSayingList().add(wiseSaying);
    }
    public void list(){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        Sql sql = SimpleDb.genSql();
        sql.append("select * from Wisesaying");
        List<WiseSaying> wiseSayingList = sql.selectRows(WiseSaying.class);
        wiseSayingList.stream().forEach(i->{
            System.out.println(i.getId() +" / "+i.getAuthor()+ " / "+i.getBody());
        });
    }
    public void delete(long id){

        long newId = wiseSayingRepo.findId(id);
        if(newId!=0){
            wiseSayingRepo.deleteWiseSaying(id);
            System.out.println(id+"번 명언이 삭제되었습니다.");
        }else{
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        }


    }
    public void modi(long id){

        long newId = wiseSayingRepo.findId(id);
        if(newId != 0){
            WiseSaying wiseSaying = wiseSayingRepo.findWiseSaying(id);
            System.out.println("명언(기존) : " + wiseSaying.getBody());
            System.out.print("명언 : ");
            String newbody = scanner.nextLine();

            System.out.println("작가(기존) : "+ wiseSaying.getAuthor());
            System.out.print("작가: ");
            String newAuthor = scanner.nextLine();
            wiseSayingRepo.modiWiseSaying(id,newbody,newAuthor);


        }else{
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        }


    }
}
