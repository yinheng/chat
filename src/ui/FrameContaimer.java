package ui;

import com.yinheng.logger.Logger;
import net.Message;
import settings.Settingstorage;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by 尹恒 on 2017/5/27.
 */
public class FrameContaimer implements UI{

    private TextArea textArea = new TextArea();
    private TextField textField = new TextField();
    private String title;
    private EnterEventListener enterEventListener;



    public void setTitle(String title){
        this.title = title;
    }

    public void setEnterEventListener(EnterEventListener enterEventListener) {
        this.enterEventListener = enterEventListener;
    }

    @Override
    public void show() {
        Frame frame = new Frame();

        frame.setTitle(title);
        frame.setResizable(true); //是否可改变窗口大小
        frame.setSize(Settingstorage.windowsSize()[0], Settingstorage.windowsSize()[1]); //窗口大小，写死不好，所以写到配置类里

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Logger.log("windowClosing");
                System.exit(0);//设置监听，关闭窗口
            }
        });

        frame.add(textArea, BorderLayout.NORTH);//添加文本框组件到窗口上方
        frame.add(textField, BorderLayout.SOUTH);//添加输入框组件到窗口下方
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                Logger.log(e.getKeyChar());
                Logger.log(e.getKeyCode());
                if(e.getKeyCode() == 10) {
                    onEnter();
                }
            }
        });
        frame.pack();//根据窗口大小，把里面的组件调节到合适的大小

        frame.setVisible(true); //窗口可见
    }

    public void setTextFieldToTextArea(String text) {
        String originalText = textArea.getText();
        String newText = String.format("%s \n %s", originalText, text);
        textArea.setText(newText);
    } //把输入框的内容推上文本框的方法

    public void onEnter() {
        setTextFieldToTextArea(textField.getText());
        enterEventListener.onEnter(textField.getText());
        textField.setText(null);

    }

    public interface EnterEventListener {
        void onEnter(String txt);
    }
}
