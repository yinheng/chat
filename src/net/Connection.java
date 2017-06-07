package net;

import com.yinheng.logger.Logger;

import java.net.Socket;

/**
 * Created by 尹恒 on 2017/5/27.
 */
public class Connection {
    Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {

        return socket;
    }

    public void close() {
        try {
            socket.close();

        }catch(Exception e) {
            Logger.log("fail to close socket %s", e.getLocalizedMessage());
        }
    }
}
