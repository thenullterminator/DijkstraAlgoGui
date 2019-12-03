package Gui;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

class WS extends JPanel implements Runnable{
    private int x1, y1,X1,Y1;
    private int x2, y2;
    
    private int width, height;
    double m;
    private double dx, dy;
    
    public  void init() {
        System.out.println("hdiovu");
        x2 = 500;
        y2=500;
        x1 =450;
        y1 = 450;
        X1=x1;Y1=y1;
        width = height = 10;
        
        m=(double)(y2-y1)/(x2-x1);
        dx = x2 - x1;
        dy = y2 - y1;
//            this.repaint();
    Thread t=new Thread(this);
    t.start();
            
    }
    
    public void run(){
        while(true) {
                try {
                    tick();
                    this.repaint();
                    Thread.sleep(70);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            } 
    }
    
     public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        
        g.fillOval(x1, y1, width, height);
        g.fillOval(x2, y2, width, height);
    }

    private void tick() {
        double moveX =  1*(Math.abs(dx)/dx);
        double moveY = m*moveX;
        x1 += moveX;
        y1 += moveY;
    }
    
}

public class Distance extends JFrame {

    

    public Distance() {
//        setDefaultCloseOperation(JFrame.DISPOSE);

        WS w=new WS();
        add(w);
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        w.init();
    }
    public static void main(String[] args) {
        
        new Distance();
    }
}