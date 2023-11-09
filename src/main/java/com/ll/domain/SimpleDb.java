package com.ll.domain;

import java.sql.*;

public class SimpleDb {
    private String server;
    private String database;
    private String user_name;
    private String password;
    private Connection con = null;
    public SimpleDb(String server, String database, String user_name, String password){
        this.database = database;
        this.server = server;
        this.user_name = user_name;
        this.password = password;
    }

    public void setDevMode(boolean isDev){

        // 1.드라이버 로딩
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! <JDBC 오류> Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }

        // 2.연결
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false&devMode="+isDev, user_name, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(String sql){

        Statement st = null;
        try {
            st = con.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void run(String sql,Object... params){
        PreparedStatement st = null;
        try{
            st = con.prepareStatement(sql);
            int index = 1;
            for(Object param:params){
                st.setObject(index,param);
                index++;
            }
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Sql genSql(){
        return new Sql(con);
    }

    public void stop(){
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {}
    }

}
