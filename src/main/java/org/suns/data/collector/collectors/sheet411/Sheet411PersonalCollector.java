package org.suns.data.collector.collectors.sheet411;

import org.suns.database.utils.config.DBConfig;
import org.suns.data.collector.config.DFFormat;
import org.suns.data.collector.config.sheet411.Sheet411PersonalConfig;
import org.suns.data.collector.connector.HostConnector;
import org.suns.database.utils.controller.Sheet411Controller;
import org.suns.database.utils.model.Sheet411PersonalModel;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by guanl on 6/28/2017.
 */
public class Sheet411PersonalCollector {

    public static void inspect() throws Exception{
        Sheet411PersonalModel sheet411Model = new Sheet411PersonalModel();
        inspect2(sheet411Model);
        inspect3(sheet411Model);
        inspect45(sheet411Model);
        sheet411Model.setDate(new Timestamp(new Date().getTime()));

        if(!Sheet411Controller.addPersonal(sheet411Model)){
            throw new Exception("Fail to add Sheet 411 personal model to database");
        }
    }

    //个税大厅 2
    private static void inspect2(Sheet411PersonalModel sheet411Model) throws Exception{
        final String[] inspectedHosts = Sheet411PersonalConfig.getInspectedHosts2();
        final String[] users = Sheet411PersonalConfig.getUsers2();
        final String[] passwords = Sheet411PersonalConfig.getPasswords2();
        final int[] ports = Sheet411PersonalConfig.getPorts2();

        PriorityQueue<Float> rootUsage = new PriorityQueue<>(comparator);
        PriorityQueue<Float> webLogicUsage = new PriorityQueue<>(comparator);

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost(inspectedHosts[i], rootUsage, webLogicUsage
                    , users[i], passwords[i], ports[i]);
        }

        if(!rootUsage.isEmpty()){
            Float maxRootUsage = rootUsage.poll();
            sheet411Model.setUsage2(maxRootUsage);
        }else{
            sheet411Model.setUsage2((float) DBConfig.getDefaultNumericNullValue());
        }

        if(!webLogicUsage.isEmpty()){
            Float maxWebLogicUsage = webLogicUsage.poll();
            sheet411Model.setWeblogicUsage2(maxWebLogicUsage);
        }else{
            sheet411Model.setWeblogicUsage2((float)DBConfig.getDefaultNumericNullValue());
        }
    }

    //个税核心 3
    private static void inspect3(Sheet411PersonalModel sheet411Model) throws Exception{
        final String[] inspectedHosts = Sheet411PersonalConfig.getInspectedHosts3();
        final String[] users = Sheet411PersonalConfig.getUsers3();
        final String[] passwords = Sheet411PersonalConfig.getPasswords3();
        final int[] ports = Sheet411PersonalConfig.getPorts3();

        PriorityQueue<Float> rootUsage = new PriorityQueue<>(comparator);
        PriorityQueue<Float> webLogicUsage = new PriorityQueue<>(comparator);

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost(inspectedHosts[i], rootUsage, webLogicUsage
                    , users[i], passwords[i], ports[i]);
        }

        if(!rootUsage.isEmpty()){
            Float maxRootUsage = rootUsage.poll();
            sheet411Model.setUsage3(maxRootUsage);
        }else{
            sheet411Model.setUsage3((float)DBConfig.getDefaultNumericNullValue());
        }

        if(!webLogicUsage.isEmpty()){
            Float maxWebLogicUsage = webLogicUsage.poll();
            sheet411Model.setWeblogicUsage3(maxWebLogicUsage);
        }else{
            sheet411Model.setWeblogicUsage3((float)DBConfig.getDefaultNumericNullValue());
        }
    }

    //个税查询统计 4 以及 个税工作流 5
    private static void inspect45(Sheet411PersonalModel sheet411Model) throws Exception{
        final String[] inspectedHosts = Sheet411PersonalConfig.getInspectedHosts45();
        final String[] users = Sheet411PersonalConfig.getUsers45();
        final String[] passwords = Sheet411PersonalConfig.getPasswords45();
        final int[] ports = Sheet411PersonalConfig.getPorts45();

        PriorityQueue<Float> rootUsage = new PriorityQueue<>(comparator);
        PriorityQueue<Float> webLogicUsage = new PriorityQueue<>(comparator);

        for(int i=0; i<inspectedHosts.length; i++){
            inspectHost(inspectedHosts[i], rootUsage, webLogicUsage
                    , users[i], passwords[i], ports[i]);
        }

        if(!rootUsage.isEmpty()){
            Float maxRootUsage = rootUsage.poll();
            //Fill two cells with identical content
            sheet411Model.setUsage4(maxRootUsage);
            sheet411Model.setUsage5(maxRootUsage);
        }else{
            sheet411Model.setUsage4((float) DBConfig.getDefaultNumericNullValue());
            sheet411Model.setUsage5((float)DBConfig.getDefaultNumericNullValue());
        }

        if(!webLogicUsage.isEmpty()){
            Float maxWebLogicUsage = webLogicUsage.poll();
            //Fill two cells with identical content
            sheet411Model.setWeblogicUsage4(maxWebLogicUsage);
            sheet411Model.setWeblogicUsage5(maxWebLogicUsage);
        }else{
            sheet411Model.setWeblogicUsage4((float)DBConfig.getDefaultNumericNullValue());
            sheet411Model.setWeblogicUsage5((float)DBConfig.getDefaultNumericNullValue());
        }
    }

    private static void inspectHost(String host, PriorityQueue<Float> rootUsage
            , PriorityQueue<Float> webLogicUsage, String user
            , String password, int port) throws Exception{

        HostConnector.connect(user, password, host, port);

        String mountedSysCmd = DFFormat.getMountedSysCmd();
        String usageCmd = DFFormat.getUsageCmd();
        String strSysNames = HostConnector.executeCommand(mountedSysCmd);
        String strUsages = HostConnector.executeCommand(usageCmd);

        String[] sysNames = parseSysNames(strSysNames);
        Float[] usages = parseUsages(strUsages);
        if(sysNames.length != usages.length){
            HostConnector.disconnect();
            throw new Exception("Unexpected result of parsing df -h");
        }

        for(int i=0; i<sysNames.length; i++){
            if(sysNames[i].equals(Sheet411PersonalConfig.getRootDirectory())){
                rootUsage.add(usages[i]);
            }else if(sysNames[i].equals(Sheet411PersonalConfig.getSoftwareDirectory())){
                webLogicUsage.add(usages[i]);
            }
        }

        HostConnector.disconnect();
    }

    private static String[] parseSysNames(String strSysNames){
        String[] sysNames = strSysNames.split("\n");
        return Arrays.copyOfRange(sysNames, 1, sysNames.length);
    }

    private static Float[] parseUsages(String strUsage){
        String[] strUsages = strUsage.split("[%\n]+");
        if(strUsages.length <= 1){
            return new Float[0];
        }

        Float[] usages = new Float[strUsages.length-1];

        for(int i=0; i<strUsages.length - 1; i++){
            usages[i] = Float.valueOf(strUsages[i+1]);
        }
        return usages;
    }

    private static Comparator<Float> comparator = new Comparator<Float>() {
        @Override
        public int compare(Float o1, Float o2) {
            return (int)(o2-o1);
        }
    };
}
