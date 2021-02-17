package com.codefriday.snake;
/**
 * 游戏主启动类
 * by：codefriday
 */
import javax.swing.*;

public class StartGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇小游戏-codefriday");
        //加入游戏面板
        frame.add(new GamePanel());

        //设置窗口参数
        frame.setBounds(10,10,915,720);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
