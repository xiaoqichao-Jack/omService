package org.com.yilian.omService.operation;

import org.com.yilian.omService.Main;
import org.com.yilian.omService.tool.SocketUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Functions {
    private Functions(){}
    private static Functions instans;
    public static Functions getInstans(){
        if(null == instans){
            instans = new Functions();
        }
        return instans;
    }
    public void updateAndInstall() throws Exception{
        Socket socket = SocketUtil.getInstance().getSocket();
        //发送消息
        int instruction = SpecifyCollection.UPDATE_AND_INSTALL.getValue();
        writeMessage(getMessage(instruction),socket);
        //接收消息
        String verify = receiverMessage(socket);
        if(!verify.equals("verification success")){
            //指令校验失败 主动断开连接
            socket.close();
            System.out.println("断开连接");
        }else {
            //System.out.println("发送数据 执行指令");
            Instruction inst = new Instruction();
            String filePath = SocketUtil.getInstance().getProp("setupFile");
            inst.uploadingFiles(socket,filePath);
            //sendInstruction(instruction,socket);
            //读取指令执行结果
            String  executionResult = receiverMessage(socket);
            //System.out.println("接收消息之后 socket状态 " + socket.isClosed());
            System.out.println(executionResult);
        }
    }

    public void viewAgentConfig() throws Exception{
        Socket socket = SocketUtil.getInstance().getSocket();
        //发送消息
        int instruction = SpecifyCollection.VIEW_CONFIG.getValue();
        writeMessage(getMessage(instruction),socket);
//        //接收消息
//        String verify = receiverMessage(socket);
//        if(!verify.equals("verification success")){
//            //指令校验失败 主动断开连接
//            socket.close();
//            System.out.println("断开连接");
//        }else {
            //System.out.println("发送数据 执行指令");
            Instruction inst = new Instruction();
            inst.downloadConfigFile(socket);
 //       }
    }

    public void updateAgentConfig() throws Exception{
        Socket socket = SocketUtil.getInstance().getSocket();
        //发送消息
        int instruction = SpecifyCollection.UPDATE_CONFIG.getValue();
        writeMessage(getMessage(instruction),socket);
        //接收消息
        String verify = receiverMessage(socket);
        if(!verify.equals("verification success")){
            //指令校验失败 主动断开连接
            socket.close();
            System.out.println("断开连接");
        }else {
            //System.out.println("发送数据 执行指令");
            Instruction inst = new Instruction();
            String filePath = SocketUtil.getInstance().getProp("agentConfigFile");
            inst.uploadingFiles(socket,filePath);
            //sendInstruction(instruction,socket);
            //读取指令执行结果
            String  executionResult = receiverMessage(socket);
            //System.out.println("接收消息之后 socket状态 " + socket.isClosed());
            System.out.println(executionResult);
        }
    }


    private static void writeMessage(String message,Socket socket) throws Exception{
        OutputStream out = socket.getOutputStream();
        PrintStream printStream=new PrintStream(out);
        printStream.println(message);
        //printStream.println("1233");
        printStream.flush();
    }
    private static String receiverMessage(Socket socket)throws Exception{
        System.out.println("receiverMessage中 socket状态 " + socket.isClosed());
        InputStream in = socket.getInputStream();
        Scanner scanner = new Scanner(in);
        String result = scanner.nextLine();
        return result;
    }
    private static String getMessage(int specify) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date()).substring(0,"yyyy-MM-dd HH".length());
        StringBuffer sb = new StringBuffer( specify+date).reverse();
        //加密
        md5.update(sb.toString().getBytes());
        byte[] digest = md5.digest();
        BigInteger bi = new BigInteger(digest);
        String cipher = bi.toString(16).toLowerCase();
        System.out.println( "加密后："+cipher);
        return cipher;
    }
}
