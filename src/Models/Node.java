/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author thenullterminator
 */
public class Node {
    
    private String name;
    private int X;
    private int Y;
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setX(int X){
        this.X=X;
    }
    
    public int getX(){
        return this.X;
    }
    
    public void setY(int Y){
        this.Y=Y;
    }
    
    public int getY(){
        return this.Y;
    }
    
}
