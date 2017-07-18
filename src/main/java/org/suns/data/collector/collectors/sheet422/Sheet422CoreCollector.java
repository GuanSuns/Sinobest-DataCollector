package org.suns.data.collector.collectors.sheet422;

import org.suns.database.utils.config.DBConfig;
import org.suns.data.collector.config.sheet422.Sheet422CoreConfig;
import org.suns.data.collector.connector.OracleConnector;
import org.suns.database.utils.controller.Sheet422Controller;
import org.suns.database.utils.model.Sheet422CoreModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by guanl on 6/29/2017.
 */
public class Sheet422CoreCollector {

    public static void inspect() throws Exception{
        ArrayList<Sheet422CoreModel> totalResult = new ArrayList<>();
        ArrayList<Sheet422CoreModel> result2 = new ArrayList<>();
        ArrayList<Sheet422CoreModel> result3 = new ArrayList<>();
        ArrayList<Sheet422CoreModel> result4 = new ArrayList<>();

        inspect2(result2);
        inspect3(result3);
        inspect4(result4);

        mergeAllResults(totalResult, result2, result3, result4);

        for(Sheet422CoreModel sheet422Model : totalResult){
            if(!Sheet422Controller.addCore(sheet422Model)){
                throw new Exception("Fail to add Sheet 422 Core model to database");
            }
        }
    }

    private static void mergeAllResults(ArrayList<Sheet422CoreModel> totalResult
            , ArrayList<Sheet422CoreModel> result2
            , ArrayList<Sheet422CoreModel> result3
            , ArrayList<Sheet422CoreModel> result4){
        
        int size2 = result2.size();
        int size3 = result3.size();
        int size4 = result4.size();
        int maxIndex = Math.max(Math.max(size2, size3), size4);
        
        for(int i=0; i<maxIndex; i++){
            Sheet422CoreModel sheet422CoreModel = new Sheet422CoreModel();
            sheet422CoreModel.setDate(new Timestamp(new Date().getTime()));

            setModel2(size2, i, result2, sheet422CoreModel);
            setModel3(size3, i, result3, sheet422CoreModel);
            setModel4(size4, i, result4, sheet422CoreModel);

            totalResult.add(sheet422CoreModel);
        }
    }
    
    private static void setModel2(int size, int index
            , ArrayList<Sheet422CoreModel> from, Sheet422CoreModel to){
        if(index >= size){
            to.setTsName2("");
            to.setTsTotalSpace2((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsage2((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsedSpace2((float)DBConfig.getDefaultNumericNullValue());
        }else{
            Sheet422CoreModel fromModel = from.get(index);
            to.setTsName2(fromModel.getTsName2());
            to.setTsTotalSpace2(fromModel.getTsTotalSpace2());
            to.setTsUsage2(fromModel.getTsUsage2());
            to.setTsUsedSpace2(fromModel.getTsUsedSpace2());
        }
    }

    private static void setModel3(int size, int index
            , ArrayList<Sheet422CoreModel> from, Sheet422CoreModel to){
        if(index >= size){
            to.setTsName3("");
            to.setTsTotalSpace3((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsage3((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsedSpace3((float)DBConfig.getDefaultNumericNullValue());
        }else{
            Sheet422CoreModel fromModel = from.get(index);
            to.setTsName3(fromModel.getTsName3());
            to.setTsTotalSpace3(fromModel.getTsTotalSpace3());
            to.setTsUsage3(fromModel.getTsUsage3());
            to.setTsUsedSpace3(fromModel.getTsUsedSpace3());
        }
    }

    private static void setModel4(int size, int index
            , ArrayList<Sheet422CoreModel> from, Sheet422CoreModel to){
        if(index >= size){
            to.setTsName4("");
            to.setTsTotalSpace4((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsage4((float)DBConfig.getDefaultNumericNullValue());
            to.setTsUsedSpace4((float)DBConfig.getDefaultNumericNullValue());
        }else{
            Sheet422CoreModel fromModel = from.get(index);
            to.setTsName4(fromModel.getTsName4());
            to.setTsTotalSpace4(fromModel.getTsTotalSpace4());
            to.setTsUsage4(fromModel.getTsUsage4());
            to.setTsUsedSpace4(fromModel.getTsUsedSpace4());
        }
    }

    private static void inspect2(ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{
        final String[] inspectedHosts = Sheet422CoreConfig.getInspectedHosts2();
        final String[] users = Sheet422CoreConfig.getUsers2();
        final String[] passwords = Sheet422CoreConfig.getPasswords2();
        final int[] ports = Sheet422CoreConfig.getPorts2();
        final String[] sid = Sheet422CoreConfig.getSid2();

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost2(users[i], passwords[i], inspectedHosts[i]
                    , ports[i], sid[i], sheet422Models);
        }
    }

    private static void inspect3(ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{
        final String[] inspectedHosts = Sheet422CoreConfig.getInspectedHosts3();
        final String[] users = Sheet422CoreConfig.getUsers3();
        final String[] passwords = Sheet422CoreConfig.getPasswords3();
        final int[] ports = Sheet422CoreConfig.getPorts3();
        final String[] sid = Sheet422CoreConfig.getSid3();

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost3(users[i], passwords[i], inspectedHosts[i]
                    , ports[i], sid[i], sheet422Models);
        }
    }

    private static void inspect4(ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{
        final String[] inspectedHosts = Sheet422CoreConfig.getInspectedHosts4();
        final String[] users = Sheet422CoreConfig.getUsers4();
        final String[] passwords = Sheet422CoreConfig.getPasswords4();
        final int[] ports = Sheet422CoreConfig.getPorts4();
        final String[] sid = Sheet422CoreConfig.getSid4();

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost4(users[i], passwords[i], inspectedHosts[i]
                    , ports[i], sid[i], sheet422Models);
        }
    }

    private static void inspectHost2(String user
            , String password, String host
            , int port, String sid
            , ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{

        Connection connection = OracleConnector.getConnection(user
                , password, host, port, sid);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Sheet422CoreConfig.getInspectSQL());

        final String[] fieldNames = Sheet422CoreConfig.getFieldNames();
        while (resultSet.next()){
            String tsName = resultSet.getString(fieldNames[0]);
            Float totalSpace = resultSet.getFloat(fieldNames[1]);
            Float usedSpace = resultSet.getFloat(fieldNames[2]);
            Float usage = resultSet.getFloat(fieldNames[3]);

            Sheet422CoreModel sheet422CoreModel = new Sheet422CoreModel();
            sheet422CoreModel.setTsName2(tsName);
            sheet422CoreModel.setTsUsedSpace2(usedSpace);
            sheet422CoreModel.setTsUsage2(usage);
            sheet422CoreModel.setTsTotalSpace2(totalSpace);

            sheet422Models.add(sheet422CoreModel);
        }

        OracleConnector.closeConnection();
    }

    private static void inspectHost3(String user
            , String password, String host
            , int port, String sid
            , ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{

        Connection connection = OracleConnector.getConnection(user
                , password, host, port, sid);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Sheet422CoreConfig.getInspectSQL());

        final String[] fieldNames = Sheet422CoreConfig.getFieldNames();
        while (resultSet.next()){
            String tsName = resultSet.getString(fieldNames[0]);
            Float totalSpace = resultSet.getFloat(fieldNames[1]);
            Float usedSpace = resultSet.getFloat(fieldNames[2]);
            Float usage = resultSet.getFloat(fieldNames[3]);

            Sheet422CoreModel sheet422CoreModel = new Sheet422CoreModel();
            sheet422CoreModel.setTsName3(tsName);
            sheet422CoreModel.setTsUsedSpace3(usedSpace);
            sheet422CoreModel.setTsUsage3(usage);
            sheet422CoreModel.setTsTotalSpace3(totalSpace);

            sheet422Models.add(sheet422CoreModel);
        }

        OracleConnector.closeConnection();
    }

    private static void inspectHost4(String user
            , String password, String host
            , int port, String sid
            , ArrayList<Sheet422CoreModel> sheet422Models) throws Exception{

        Connection connection = OracleConnector.getConnection(user
                , password, host, port, sid);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Sheet422CoreConfig.getInspectSQL());

        final String[] fieldNames = Sheet422CoreConfig.getFieldNames();
        while (resultSet.next()){
            String tsName = resultSet.getString(fieldNames[0]);
            Float totalSpace = resultSet.getFloat(fieldNames[1]);
            Float usedSpace = resultSet.getFloat(fieldNames[2]);
            Float usage = resultSet.getFloat(fieldNames[3]);

            Sheet422CoreModel sheet422CoreModel = new Sheet422CoreModel();
            sheet422CoreModel.setTsName4(tsName);
            sheet422CoreModel.setTsUsedSpace4(usedSpace);
            sheet422CoreModel.setTsUsage4(usage);
            sheet422CoreModel.setTsTotalSpace4(totalSpace);

            sheet422Models.add(sheet422CoreModel);
        }

        OracleConnector.closeConnection();
    }
}