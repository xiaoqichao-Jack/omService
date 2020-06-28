package org.com.yilian.omService;

import org.com.yilian.omService.operation.Functions;
import org.com.yilian.omService.operation.Instruction;
import org.com.yilian.omService.operation.SpecifyCollection;
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

public class Main {
    public static void main(String[] args) throws Exception{
         sendInstruction(SpecifyCollection.UPDATE_CONFIG.getValue());
    }

    private static void sendInstruction(int opration) throws Exception{
        Functions fun = Functions.getInstans();
        switch(opration){
            case 1 :
                fun.updateAndInstall();
                break; //可选
            case 2 :
                fun.viewAgentConfig();
                break; //可选
            case 3 :
                fun.updateAgentConfig();
                break; //可选
            case 4 :

                break; //可选
            case 5 :

                break; //可选
            case 6 :

                break; //可选
            case 7 :

                break; //可选
            default : //可选
                //语句
        }
    }

}
