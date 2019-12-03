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
public class Edge {
    
    private String from;
    private String to;
    private int weight;
    
    public void setFrom(String from){
        this.from=from;
    }
    
    public void setTo(String to){
        this.to=to;
    }
    
    public void setWeight(int weight){
        this.weight=weight;
    }
    
    public String getFrom(){
        return this.from;
    }
    
    public String getTo(){
        return this.to;
    }
    
    public int getWeight(){
        return this.weight;
    }
    
}
