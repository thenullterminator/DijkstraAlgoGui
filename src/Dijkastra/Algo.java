/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dijkastra;

import Models.Edge;
import java.util.*;

/**
 *
 * @author thenullterminator
 */
public class Algo {
    TreeMap<String,ArrayList<Edge>> graph;
    HashMap<String,Integer> dist=new HashMap<String,Integer>();
    HashMap<String,Boolean> vis=new HashMap<String,Boolean>();
    HashMap<String,Edge> path=new HashMap<String,Edge>();
    String src;
    String dest;
    String stringPath="";
    int shortestDist;
    
    public Algo(TreeMap<String,ArrayList<Edge>> graph,String src,String dest){
//        System.out.println(graph.size());
        this.graph=new TreeMap<String,ArrayList<Edge>> (graph);
        this.src=src;
        this.dest=dest;
    }
    
    public  void printPath(HashMap<String,Edge> path,String vert){

        if(path.get(vert)==null){
            return;
        }

        printPath(path,path.get(vert).getFrom());
        stringPath+=path.get(vert).getFrom()+" -> ";
    }
    
    public String getPath(){
        return stringPath;
    }
    
    public int getDistance(){
        return shortestDist;
    }
    
    public void implement(){
        
        if(graph==null||graph.size()==0) return ;
        
       
        
        for(Map.Entry<String,ArrayList<Edge>> entry : graph.entrySet()) {
            String key="";
            key = entry.getKey();
            dist.put(key,Integer.MAX_VALUE);
            vis.put(key,false);
        }

        dist.put(src,0);
        path.put(src,null);
        int V=vis.size();// the number of vertices....
        
        
        for(int i=0;i<V-1;i++){

            int min=Integer.MAX_VALUE;
            String u=""; 
            for(Map.Entry<String,Integer> entry:dist.entrySet()){

                String k=entry.getKey();
                int d=entry.getValue();

                if(vis.get(k)==false && d<=min){
                    min=d;
                    u=k;
                }
            }

            if(u.length()>0){
                vis.put(u,true);

                for(Edge e:graph.get(u)){

                    if(vis.get(e.getTo())==false && dist.get(u)!=Integer.MAX_VALUE && (dist.get(u)+e.getWeight()<dist.get(e.getTo()))){
                        dist.put(e.getTo(),dist.get(u)+e.getWeight());
                        path.put(e.getTo(),e);
                    }
                }
            }
        }
        
        if(dist.get(dest)==Integer.MAX_VALUE){
            stringPath="No Path Exists";
            shortestDist=Integer.MAX_VALUE;
        }
        else{
            printPath(path,dest);
            stringPath+=dest;
            shortestDist=dist.get(dest);
        }
        
        
    }
    
}
