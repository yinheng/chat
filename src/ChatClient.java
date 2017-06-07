import Common.Component;
import com.yinheng.logger.Logger;
import net.Connection;
import net.Message;
import settings.Settingstorage;
import ui.FrameContaimer;
import ui.UI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

/**
 * Created by 尹恒 on 2017/5/27.
 */
public class ChatClient  implements Component, UI, Message{
    private FrameContaimer frameContaimer;
    private Connection connection;


    public ChatClient() {
        frameContaimer = new FrameContaimer();
        frameContaimer.setTitle("client");
    }

    @Override
    public void start() {
        Logger.log("ChatClient start");
        show();
        startClientSocket();
    }

    @Override
    public void stop() {
        connection.close();

    }

    @Override
    public void show() {
        frameContaimer.show();
    }

    public void startClientSocket() {
        try {
            Socket s = new Socket("127.0.0.1", 8888);
            Logger.log("connection");
            connection = new Connection();
            connection.setSocket(s);
        } catch(IOException e) {
            Logger.log("fail to connect serverSocket %s", e.getLocalizedMessage());
        }
    }


    @Override
    public void sendMessage(String message) {
        Socket socket = connection.getSocket();
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message);
        }catch(Exception e) {
            Logger.log("fail to sendMessage %s" , e.getLocalizedMessage());
        }

    }

    @Override
    public void readMessage() {

    }
}
