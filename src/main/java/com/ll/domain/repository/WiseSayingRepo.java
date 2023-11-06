package com.ll.domain.repository;

import com.ll.domain.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class WiseSayingRepo {
    private static List<WiseSaying> wiseSayingList;
    private static String filename;

    public static void setFilename(String filename) {
        WiseSayingRepo.filename = filename;
    }

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public WiseSayingRepo(){
        wiseSayingList = new ArrayList<>();
    }
    public static void setWiseSayingList(List<WiseSaying> wiseSayingList) {
        WiseSayingRepo.wiseSayingList = wiseSayingList;
    }

    public static void saveData(List<WiseSaying> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<WiseSaying> loadData() {
        List<WiseSaying> data = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            data = (List<WiseSaying>) ois.readObject();
        }catch (FileNotFoundException e) {
            // 파일이 존재하지 않으면 새로운 파일 생성
            try {
                File file = new File("data.txt");
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        OptionalInt maxId = wiseSayingList.stream().mapToInt(fs -> fs.getId()).max();
        if (maxId.isPresent()) {
            WiseSaying.idVal = maxId.getAsInt() + 1;
        }
        return data;
    }
}