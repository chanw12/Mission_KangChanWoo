package com.ll.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Sql {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private String query;
    private List<Object> parameters;


    public Sql(Connection connection) {
        this.connection = connection;
        this.query = new String();
        this.parameters = new ArrayList<>();
    }

    public Sql append(String clause) {

        this.query += " " + clause;
        return this;
    }

    public Sql append(String clause, Object... params) {
        this.query += " " + clause;
        for (Object param : params) {
            parameters.add(param);
        }
        return this;
    }
    public <T> Sql appendIn(String clause,List<T> list){
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            inClause.append("?");
            if (i < list.size() - 1) {
                inClause.append(",");
            }
        }
        this.query += " " + clause.replace("?", inClause);
//        System.out.println(this.query);
        this.parameters.addAll(list);
//        System.out.println(this.parameters);

        return this;
    }

    public String getQuery() {
        return query.toString();
    }

    public List<Object> getParameters() {
        return parameters;
    }


    public long insert(){
        long newId = -1;
        try {
            this.preparedStatement = this.connection.prepareStatement(this.query, Statement.RETURN_GENERATED_KEYS);
            setParameters();
            this.preparedStatement.executeUpdate();
            ResultSet generatedKeys = this.preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                newId = generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }
    public long update(){
        long affectedRowsCount = -1;
        try{
            this.preparedStatement = this.connection.prepareStatement(query);
            setParameters();
            this.preparedStatement.executeUpdate();
            affectedRowsCount = this.preparedStatement.getLargeUpdateCount();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return affectedRowsCount;
    }

    public long delete(){
        long affectedRowsCount = -1;
        try{
            this.preparedStatement = this.connection.prepareStatement(query);
            setParameters();
            this.preparedStatement.executeUpdate();
            affectedRowsCount = this.preparedStatement.getUpdateCount();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return affectedRowsCount;
    }
    public LocalDateTime selectDatetime(){
        LocalDateTime datetime = null;
        try{
            this.preparedStatement = this.connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            if(rs.next()){
                datetime = rs.getTimestamp(1).toLocalDateTime();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return datetime;

    }
    public long selectLong(){
        long id = -1;
        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            if(rs.next()) {
                id = rs.getLong(1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    public int selectint(){
        int id = -1;
        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            if(rs.next()) {
                id = rs.getInt(1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public List<Long> selectLongs(){
        List<Long> li = new ArrayList<>();

        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();

            while(rs.next()) {

                long id = rs.getLong(1);
                li.add(id);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return li;
    }

    public String selectString(){
        String title = "";
        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            if(rs.next()) {
                title = rs.getString("title");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return title;
    }

    public Map<String, Object> selectRow(){
        Map<String, Object> articleMap = new HashMap<>();
        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if(rs.next()){
                IntStream.rangeClosed(1,columnCount).forEach(i->{
                    try{
                        String columnName = resultSetMetaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        articleMap.put(columnName,value);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                });
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return articleMap;
    }

    public <T> T selectRow(Class<T> t){
        T resultInstance = null;
        try{
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if(rs.next()){
                resultInstance = t.getDeclaredConstructor().newInstance();
                for(int i=1;i<=columnCount;i++){
                    String columname = resultSetMetaData.getColumnName(i);
                    Object value = rs.getObject(i);

                    try{
                        Field field= t.getDeclaredField(columname);
                        field.setAccessible(true);
                        field.set(resultInstance,value);

                    }catch (NoSuchFieldException | SecurityException | IllegalAccessException e){
                        e.printStackTrace();
                    }

                }}

        }catch (SQLException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return resultInstance;

    }
    public <T> List<T> selectRows(Class<T> t) {
        T resultInstance = null;
        List<T> li= new ArrayList<>();
        try {
            this.preparedStatement = connection.prepareStatement(query);
            setParameters();
            ResultSet rs = this.preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while(rs.next()) {
                resultInstance = t.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columname = resultSetMetaData.getColumnName(i);
                    Object value = rs.getObject(i);

                    try {
                        Field field = t.getDeclaredField(columname);
                        field.setAccessible(true);
                        field.set(resultInstance, value);

                    } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                li.add(resultInstance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return li;
    }



    private void setParameters() throws SQLException {
        int index = 1;
        for (Object parameter : this.parameters) {
            this.preparedStatement.setObject(index, parameter);
            index++;
        }
    }


}

