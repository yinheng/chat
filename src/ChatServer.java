import Common.Component;
import com.yinheng.logger.Logger;
import net.Connection;
import net.Message;
import settings.Settingstorage;
import ui.FrameContaimer;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by 尹恒 on 2017/5/27.
 */
public class ChatServer implements Component, UI , Message{
    FrameContaimer frameContaimer;

    private boolean isrunning = true;

    private java.util.List<Connection> connections;

    public ChatServer() {
        frameContaimer = new FrameContaimer();
        frameContaimer.setTitle("server");
    }

    @Override
    public void start() {
        Logger.log("ChatServer start");
        show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServerSocket();
            }
        }).start();
    }

    @Override
    public void stop() {

        isrunning = false;
        for(Connection c : connections) {
            c.close();
        }
    }

    @Override
    public void show() {
        frameContaimer.show();
    }

    // 这个是阻塞的，不用新的县城 你上面的start会一直执行不完。
    public void startServerSocket() {
        try{
            ServerSocket serverSocket = new ServerSocket(8888);
            while (isrunning) {
                Socket socket = serverSocket.accept();
                Connection connection= new Connection();
                connection.setSocket(socket);
                connections.add(connection);
                Logger.log("server is start");
            }
        }catch(IOException e) {
            Logger.log("fail to start ServerSocket %s", e.getLocalizedMessage());
        }
    }

    @Override
    public void sendMessage(String message) {
        for(Connection connection: connections) {
            Socket socket = connection.getSocket();
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(message);
            }catch(Exception e) {
                Logger.log("fail to send message %s", e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void readMessage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(Connection connection: connections) {
                    Socket socket = connection.getSocket();
                    try {
                        InputStream is = socket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while (isrunning) {
                            while ((line = br.readLine()) != null) {
                                sendMessage(line);
                            }
                        }

                    }catch(Exception e) {
                        Logger.log("fail read message %s", e.getLocalizedMessage());
                    }
                }
            }
        });
        thread.start();

    }
}
