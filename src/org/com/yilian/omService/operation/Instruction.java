package org.com.yilian.omService.operation;

import org.com.yilian.omService.tool.SocketUtil;

import java.io.*;
import java.net.Socket;

/**
 * 操作集合
 */
public class Instruction {
    /**
     * 上传安装文件
     * @param socket
     */
    public void uploadingFiles(Socket socket,String file) {
        try {
            System.out.println("传输玩文件之前 socket状态 " + socket.isClosed());
            File fi = new File(file);
            System.out.println("文件长度:" + (int) fi.length());
            DataInputStream fis = new DataInputStream(new FileInputStream(fi));
            DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
            ps.writeUTF(fi.getName());
            ps.flush();
            ps.writeLong((long) fi.length());
            ps.flush();
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            while (true) {
                int read = 0;
                if (fis != null) {
                    read = fis.read(buf);
                }
                if (read == -1) {
                    break;
                }
                ps.write(buf, 0, read);
            }
            ps.flush();
            fis.close();
            //ps.close();//关闭流 关闭连接
            socket.shutdownOutput();
            System.out.println("文件传输完成");
            System.out.println("传输玩文件之后 socket状态 " + socket.isClosed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadConfigFile(Socket socket){
        try{
            DataInputStream inputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            long passedlen = 0;
            long len = 0;
            String fileDir = SocketUtil.getInstance().getProp("agentConfigFilePath");
            // 获取文件名
            String file = fileDir + File.separator + inputStream.readUTF();
            File filePath = new File(fileDir);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File updateFile = new File(file);
            if (updateFile.exists()) {
                updateFile.delete();
            }
            DataOutputStream fileOut = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file)));
            len = inputStream.readLong();
            System.out.println("文件的长度为:" + len + "\n");
            System.out.println("开始接收文件!" + "\n");
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read == -1) {
                    break;
                }
                // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
                System.out.println("文件接收了" + (passedlen * 100 / len)+ "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + file + "\n");
            fileOut.close();
        }catch(Exception e){
        }
    }
}
