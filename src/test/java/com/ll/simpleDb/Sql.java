package com.ll.simpleDb;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public String getQuery() {
        return query.toString();
    }

    public List<Object> getParameters() {
        return parameters;
    }

//    public void clear() {
//        query.setLength(0);
//        parameters.clear();
//    }

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

    private void setParameters() throws SQLException {
        int index = 1;
        for (Object parameter : this.parameters) {
            this.preparedStatement.setObject(index, parameter);
            index++;
        }
    }


}

