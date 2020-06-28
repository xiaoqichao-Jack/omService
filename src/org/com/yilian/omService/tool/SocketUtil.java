package org.com.yilian.omService.tool;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class SocketUtil {
    private  String IP = getProp("ip");
    private String PORT = getProp("port");
    private static SocketUtil instance;
    private SocketUtil() {
    };

    public static SocketUtil getInstance() {
        if(null == instance){
            instance = new SocketUtil();
        }
        return instance;
    }

    public  Socket getSocket() {
        Socket socket = null;
        try {
            socket = new Socket(IP, Integer.valueOf(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!socket.isClosed() && socket.isConnected()) {

            return socket;
        } else {

            return null;
        }
    }




    public  String getProp(String key) {
        Properties properties = new Properties();
        String relativelyPath=System.getProperty("user.dir");
        String filePath = relativelyPath + File.separator + "config.properties";
        Reader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            properties.load(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
