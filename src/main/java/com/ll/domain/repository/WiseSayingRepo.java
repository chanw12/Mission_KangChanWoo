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
        sql.append("select count(*) from Wisesaying");
        WiseSaying.idVal = sql.selectint()+1;
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
//    public static void setWiseSayingList(List<WiseSaying> wiseSayingList) {
//        WiseSayingRepo.wiseSayingList = wiseSayingList;
//    }
//
//    public static void saveData(List<WiseSaying> data) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
//            oos.writeObject(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static List<WiseSaying> loadData() {
//        List<WiseSaying> data = new ArrayList<>();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
//            data = (List<WiseSaying>) ois.readObject();
//        }catch (FileNotFoundException e) {
//            // 파일이 존재하지 않으면 새로운 파일 생성
//            try {
//                File file = new File("data.txt");
//                file.createNewFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//        OptionalInt maxId = wiseSayingList.stream().mapToInt(fs -> fs.getId()).max();
//        if (maxId.isPresent()) {
//            WiseSaying.idVal = maxId.getAsInt() + 1;
//        }
//        return data;
//    }



//    static ObjectMapper mapper = new ObjectMapper();
//    public static void FileLoad(){
//
//        try {
//            WiseSayingRepo.setWiseSayingList(new ArrayList<>(Arrays.asList(mapper.readValue(new File(filename), WiseSaying[].class))));
//
//            OptionalInt maxId = wiseSayingList.stream().mapToInt(fs -> fs.getId()).max();
//            if (maxId.isPresent()) {
//                WiseSaying.idVal = maxId.getAsInt() + 1;
//            }
//
//        } catch (IOException ex) {
//            System.out.println("파일 읽기 오류: " + ex.getMessage());
//        }
//    }

//    public static void FileSave(List<WiseSaying> wiseSayingList){
//        try {
//            mapper.writeValue(new File(filename), wiseSayingList);
//        } catch (IOException ex) {
//            System.out.println("파일 쓰기 오류" + ex.getMessage());
//        }
//    }
}