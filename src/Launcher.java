/**
 * Created by 尹恒 on 2017/5/27.
 */
public class Launcher {

    public static void main(String[] args) {

        ChatServer server = new ChatServer();
        ChatClient a = new ChatClient();
        ChatClient b = new ChatClient();

        server.start();// 如果那里不新建县城执行，这里就一直卡在这儿。
        //下面的没机会执行了。


        a.start();
        b.start();
    }
}
