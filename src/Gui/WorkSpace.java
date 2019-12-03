/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;
import static Gui.MoreInteractive.*;
import  Models.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
//import java.awt.event.*;
/**
 *
 * @author thenullterminator
 */



public class WorkSpace extends JPanel implements Runnable {
    
    ArrayList<String>pathList=new ArrayList<String>();
    ArrayList<Integer>x1=new ArrayList<>();
    ArrayList<Integer>y1=new ArrayList<>();
    ArrayList<Integer> shape=new ArrayList<>();//0,1,2,3,4
    int currind=0;
    Graphics G;
    Graph g;//Storing the passed by graph..
    Node s;
    Node d;
    String path="";
    Boolean sync=true;
    int flagAnimate=0;
    
    int ind=0,flag=1;
    int cir=0,sq=0,cross=0,plus=0,tri=0;
    JButton start,stop;
    Thread t;
    WorkSpace(Graph g,JButton stop){
        this.g=g;
        this.stop=stop;
    }
    
    public void setButton(JButton stop){
        this.stop=stop;
    }
    
    public void printPath(){
        for(String s:pathList){
            System.out.println(s);
        }
    }
    
    public void pushPath(String p){
        pathList.add(p);
        x1.add(-1);
        y1.add(-1);
    }
    
    public void pushShape(){
        if(cir==1) shape.add(0);
        else if(sq==1) shape.add(1);
        else if(cross==1) shape.add(2);
        else if(plus==1) shape.add(3);
        else if(tri==1) shape.add(4);
        currind++;
    }
    
    
    public void stop(){
        
        flagAnimate=0;
        repaint();
        sync=false;
        currind=0;
        stop.setEnabled(false);
        pathList.clear();
        x1.clear();y1.clear();
        shape.clear();
    }
    public void run(){
        flagAnimate=1;
        
        int localind=currind-1;
        String[] s=pathList.get(localind).split(" -> ");
        int x,y;
//        int x11=x,y11=y;
        for(int i=0;i<(s.length-1)&&sync;i++){
              
            
            Node from=g.searchNode(s[i]);
            Node to=g.searchNode(s[i+1]);
            
            this.x1.set(localind,from.getX());
            this.y1.set(localind,from.getY());
            x=x1.get(localind);y=y1.get(localind);
            int x2=to.getX();int y2=to.getY();
            double m,dx;
            m=(double)(y2-y)/(x2-x);
            dx = x2 - x;
//            System.out.println("F: "+from.getName()+" T: "+to.getName());
            int zz=1;
             while(sync){
                 
                try {
                    if(Math.abs(x1.get(localind)-x2)<=2 && Math.abs(y1.get(localind)-y2)<=2) {break;}
//                    System.out.println("X: "+x1+" Y: "+y1);
                    

                    if(x==x2){
                        x1.set(localind,x2);
                        y1.set(localind,y2);
                        break ;
                    }
                    
                    double moveX =  zz*(Math.abs(dx)/dx);
                    double moveY = m*moveX;
                    x1.set(localind,x+ (int)moveX);
                    y1.set(localind,y+ (int)moveY);
                    zz++;
                    repaint();
                    Thread.sleep(10);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
           }


            if(i==s.length-2) {i=-1;}
        }
//        System.out.println("Exited");
    }
    
    
    public void Animate(){
        
//        start.setEnabled(false);
        sync=true;
        stop.setEnabled(true);
        t=new Thread(this);
        t.start();
    }
    
    public void clear(){
        s=null;
        d=null;
        path="";
    }
    
    public void addSrc(Node src){
        this.s=src;
//        System.out.println("s: "+s.getName());
    }
    
    public void addDest(Node dest){
        this.d=dest;
//        System.out.println("d: "+d.getName());
    }
    
    public void addPath(String path){
        this.path=path;
    }
    
    public void tick(int zz) {
        
    }
 
    public void paint(Graphics G){
        
        this.G=G;
        super.paint(G);
        ArrayList<Node> nodes=g.sortedNodes();
        G.setColor(Color.red);
        for(Node n:nodes){ //Drawing all the nodes.
            if((s!=null && s==n)){
                G.setColor(Color.black);
                G.fillOval(n.getX()-8, n.getY()-8, 16, 16);
            }
            else if((d!=null && d==n)){
                G.setColor(Color.green);
                G.fillOval(n.getX()-8, n.getY()-8, 16, 16);
            }
            else{
                G.setColor(Color.red);
                G.fillOval(n.getX()-8, n.getY()-8, 16, 16);
            }
            
            G.setColor(Color.black);
            G.drawString(n.getName(),n.getX()-8, n.getY()-8);
            
        }
        
        ArrayList<Edge> edges=g.sortedEdges();
        
        for(Edge e:edges){ //Drawing all the edges.
            Node from=g.searchNode(e.getFrom());
            Node to=g.searchNode(e.getTo());
            int X=(from.getX()+to.getX())/2;
            int Y=(from.getY()+to.getY())/2;
            G.setColor(Color.black);
            G.setFont(new Font("default", Font.BOLD, 16));
            G.drawString(""+e.getWeight(),X, Y);
            G.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
        }
        
       
        if(!path.isEmpty()){
            String[] s=path.split(" -> ");
            G.setColor(Color.green);
            
            for(int i=0;i<s.length-1;i++){
                Node from=g.searchNode(s[i]);
                Node to=g.searchNode(s[i+1]);
                int X=(from.getX()+to.getX())/2;
                int Y=(from.getY()+to.getY())/2;
                Edge e=g.searchEdge(from.getName(), to.getName());
                G.setFont(new Font("default", Font.BOLD, 16));
                G.drawString(""+e.getWeight(),X, Y);
                G.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
            }
        }
        
        if(flagAnimate==1){
//            System.out.println("Enteres");
           
            
            for(int i=0;i<shape.size();i++){
                
                int x11=x1.get(i),y11=y1.get(i);
                if(shape.get(i)==0){
                    G.setColor(Color.blue);
                    G.fillOval(x11-5, y11-5, 10, 10);
                }
                else if (shape.get(i)==1){
                    G.setColor(Color.orange);
                    G.fillRect(x11-5, y11-5, 10, 10);
                }
                else if (shape.get(i)==2){
                     G.setColor(Color.black);
                    G.drawString("x",x11-4, y11-4);
                }
                else if (shape.get(i)==3){
                     G.setColor(Color.gray);
                    G.drawString("+",x11-4, y11-4);
                }
                else if(shape.get(i)==4){
                    G.setColor(Color.magenta);
                    G.drawLine(x11,y11,x11+10,y11);
                    G.drawLine(x11,y11,x11+5,y11-10);
                    G.drawLine(x11+10,y11,x11+5,y11-10);
                }
            }
        }
    }   
}
