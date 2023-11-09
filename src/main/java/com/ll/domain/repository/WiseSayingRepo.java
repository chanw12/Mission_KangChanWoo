package com.ll.domain.repository;

import com.ll.domain.SimpleDb;
import com.ll.domain.Sql;
import com.ll.domain.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepo {

    private static List<WiseSaying> wiseSayingList;
    private static String filename;

    public void makeWiseSaygingTable(){


        SimpleDb.run("""
                CREATE TABLE IF NOT EXISTS Wisesaying (
                    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                    PRIMARY KEY (id),
                    body TEXT NOT NULL,
                    author VARCHAR(255) NOT NULL
                );
                """);
        Sql sql = SimpleDb.genSql();
        sql.append("select max(id) from Wisesaying");

        WiseSaying.idVal = sql.selectLong()+1;
    }

    public long findId(long id){
        Sql sql = SimpleDb.genSql();
        return sql.append("select count(*) from Wisesaying")
                .append("where id = ?",id)
                .selectLong();

    }
    public WiseSaying findWiseSaying(long id){
        Sql sql = SimpleDb.genSql();
        return sql.append("select * from Wisesaying")
                .selectRow(WiseSaying.class);
    }

    public long deleteWiseSaying(long id){
        Sql sql = SimpleDb.genSql();
        return sql.append("delete from Wisesaying")
                .append("where id = ?",id)
                .update();
    }
    public long modiWiseSaying(long id,String newbody,String newauthor){
        Sql sql = SimpleDb.genSql();
        return sql.append("update Wisesaying")
                .append("set body = ?, author = ?",newbody,newauthor)
                .update();
    }

    public void listingWiseSaying(){
        Sql sql = SimpleDb.genSql();
        sql.append("select * from Wisesaying");
        List<WiseSaying> wiseSayingList = sql.selectRows(WiseSaying.class);
        wiseSayingList.stream().forEach(i->{
            System.out.println(i.getId() +" / "+i.getAuthor()+ " / "+i.getBody());
        });
    }
    public void registWiseSaying(WiseSaying wiseSaying){
        Sql sql = SimpleDb.genSql();
        sql.append("INSERT INTO Wisesaying (body, author)")
                .append( "VALUES (?, ?)",wiseSaying.getBody(),wiseSaying.getAuthor());
        sql.insert();
    }


    public static void setFilename(String filename) {
        WiseSayingRepo.filename = filename;
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public WiseSayingRepo(){
        wiseSayingList = new ArrayList<>();
    }

}