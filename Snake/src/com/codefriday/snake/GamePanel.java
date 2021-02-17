package com.codefriday.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //存每一节蛇的坐标
    int DataX[] = new int[820];
    int DataY[] = new int[820];
    boolean IsStart;//游戏状态，默认false，未开始
    boolean IsOver;//是否失败
    int length;//蛇的长度
    int fx;//蛇的方向，↑0 →1 ↓2 ←3
    int FoodX,FoodY;//食物位置
    int score;//得分
    Random random = new Random();
    Timer timer = new Timer(200,this);
    public GamePanel(){
        init();
        this.setFocusable(true);//获得焦点事件
        this.addKeyListener(this);
        timer.start();
    }
    void init(){
        fx = 1;
        length = 3;
        IsStart = false;
        IsOver = false;
        score = 0;
        DataX[0] = 100; DataY[0] = 100;
        DataX[1] = 75; DataY[1] = 100;
        DataX[2] = 50; DataY[2] = 100;
        FoodX = 25 + 25*(random.nextInt(34));
        FoodY = 75 + 25*(random.nextInt(24));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        //画游戏静态面板
        this.setBackground(Color.white);
        Data.header.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,600);
        //画积分
        g.setColor(new Color(145, 19, 173, 255));
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度 "+length,750,30);
        g.drawString("分数 "+score,750,50);
        //画食物
        Data.food.paintIcon(this,g,FoodX,FoodY);
        //画蛇
        switch (fx){
            case 0:
                Data.up.paintIcon(this,g,DataX[0],DataY[0]);
                break;
            case 1:
                Data.right.paintIcon(this,g,DataX[0],DataY[0]);
                break;
            case 2:
                Data.down.paintIcon(this,g,DataX[0],DataY[0]);
                break;
            case 3:
                Data.left.paintIcon(this,g,DataX[0],DataY[0]);
                break;
        }
        for(int i = 1;i < length;i++){
            Data.body.paintIcon(this,g,DataX[i],DataY[i]);
        }
        if(!IsStart&&!IsOver){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏!",300,300);
        }
        if(IsOver){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("gg,按下空格重新开始!",250,300);
        }
    }

    //键盘监听事件接口中的方法
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_SPACE:
                if(IsOver){
                    init();
                }else {
                    IsStart = !IsStart;
                }
                repaint();
                break;
            case KeyEvent.VK_UP:
                if(IsStart){
                    fx = (fx==2?fx:0);
                }
                break;
            case KeyEvent.VK_DOWN:
                if(IsStart){
                    fx = (fx==0?fx:2);
                }
                break;
            case KeyEvent.VK_LEFT:
                if(IsStart){
                    fx = (fx==1?fx:3);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(IsStart){
                    fx = (fx==3?fx:1);
                }
                break;
        }

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //事件监听----通过固定时间刷新
    @Override
    public void actionPerformed(ActionEvent e) {
        if(IsStart){
            if(FoodX == DataX[0] && FoodY == DataY[0]){//吃食物
                length++;
                score += 10;
                //重新生成食物
                FoodX = 25 + 25*(random.nextInt(34));
                FoodY = 75 + 25*(random.nextInt(24));
            }
            for(int i = length-1;i>0;i--){
                DataX[i] = DataX[i-1];
                DataY[i] = DataY[i-1];
            }
            switch (fx){
                case 0:
                    DataY[0] = (DataY[0]==75?650:DataY[0]-25);
                    break;
                case 1:
                    DataX[0] = (DataX[0]==850?25:DataX[0]+25);
                    break;
                case 2:
                    DataY[0] = (DataY[0]==650?75:DataY[0]+25);
                    break;
                case 3:
                    DataX[0] = (DataX[0]==25?850:DataX[0]-25);
                    break;
            }
            for(int i = 1;i < length;i++){
                if(DataX[0] == DataX[i] && DataY[0] == DataY[i]){
                    IsOver = true;
                    IsStart = false;
                }
            }
            repaint();//重画
        }
        if(length>20){
            timer.setDelay(50);
        } else if(length>15){
            timer.setDelay(100);
        }
        else if(length>5){
            timer.setDelay(150);
        }
        timer.start();
    }
}
