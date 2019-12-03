/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import java.util.*;
/**
 *
 * @author thenullterminator
 */

class CustomSortNode implements Comparator<Node>{
    
    public int compare(Node a,Node b){
        
        return a.getName().compareTo(b.getName());
    }
}

class CustomSortEdge implements Comparator<Edge>{
    
    public int compare(Edge a,Edge b){
        
        if(a.getFrom().compareTo(b.getFrom())==0){
            return a.getTo().compareTo(b.getTo());
        }
        return a.getFrom().compareTo(b.getFrom());
    }
}
public class Graph {
    
    private ArrayList<Node> nodes=new ArrayList<>();//list of nodes.
    private ArrayList<Edge> edges=new ArrayList<>();//list of undirected edges.
//    private ArrayList<Edge> dedges=new ArrayList<>();//list of edges.
    int count=0;//For giving name to each node.
    public void addNode(Node n){
        nodes.add(n);
        count++;
    }
    
    public void addEdge(Edge e){
//        dedges.add(e);
        edges.add(e);
//        Edge e2=new Edge();
//        e2.setFrom(e.getTo());
//        e2.setTo(e.getFrom());
//        e2.setWeight(e.getWeight());
//        edges.add(e2);//Considering an undirected edge without any self loops and multi-edges
    }
    
    public int generateID(){
        return count;
    }
    
    public Node searchNode(String n){
        
        for(Node i:nodes){
            if(n.equals(i.getName())) return i;
        }
        return null;
    }
    
    public Node overlapping(int x,int y,int d){
        for(Node i:nodes){
            
            if(Math.abs(x-i.getX())<=d && Math.abs(y-i.getY())<=d){
                return i;
            }
            
        }
        return null;
    }
    
    public int perpenDistance(int m,int n,Edge e){
        
        Node from=searchNode(e.getFrom());
        Node to=searchNode(e.getTo());
        
        double x1=from.getX(),y1=from.getY();
        double x2=to.getX(),y2=to.getY();
        
        double A=(y2-y1)/(x2-x1);
        double B=-1;
        double C=y1-x1*A;
        
        double num=Math.abs(A*m+B*n+C);
        double den=Math.sqrt(A*A+B*B);
        
        int ans=(int)(num/den);
        
        double sper=(x1-x2)/(y2-y1);
        
        double x=(B*sper*m-B*n-C)/(A+B*sper);
        double y=(A*n-C*sper*m-A*sper*m)/(A+B*sper);
       
        if(((m>=Math.min(x1, x2))&&(m<=Math.max(x1, x2))) && ((n>=Math.min(y1, y2))&&(n<=Math.max(y1, y2)))) return ans;
        return 1000;
    }
    
    public Edge selectedEdge(int x,int y,int d){
        
        for(Edge e:edges){
            
            if(perpenDistance(x,y,e)<d)return e;
        }
        return null;
    }
    
    public void changeCord(String name,int x,int y){
        
        for(Node i:nodes){
            if(i.getName().equals(name)){
                i.setX(x);
                i.setY(y);
            }
        }
    }
    
    public void removeAllEdges(Node n){
        
        ArrayList<Edge> r=new ArrayList<Edge>();
        for(Edge e:edges){
            if(e.getFrom().equals(n.getName()) || e.getTo().equals(n.getName())){
                r.add(e);
            }
        }
        
        for(Edge e:r){
            int i=edges.indexOf(e);
            if(i>=0) edges.remove(i);
        }
        r.clear();
        
        for(Edge e:edges){
            if(e.getFrom().equals(n.getName()) || e.getTo().equals(n.getName())){
                r.add(e);
            }
        }
        for(Edge e:r){
            int i=edges.indexOf(e);
            if(i>=0) edges.remove(i);
        }
        r.clear();
    }
    public void removeNode(Node n){
        
        if(n==null) return;
        int i=nodes.indexOf(n);
        if(i>=0) nodes.remove(i);
    }
    
    public void removeEdge(Edge e){
        if(e==null) return;
        int i=edges.indexOf(e);
        
        if(i>=0) edges.remove(i);
        
    }
    
    public Edge searchEdge(String from,String to){ 
        
        for(Edge e:edges){
            
            if(e.getFrom().equals(from) && e.getTo().equals(to)){
                return e;
            }
        }
        
        return null;
    }
    
    public TreeMap<String,ArrayList<Edge>> createAdjList(){
        TreeMap<String,ArrayList<Edge>> graph= new TreeMap<String ,ArrayList<Edge>>();
        for(Edge e:edges){
            
            if(!graph.containsKey(e.getFrom())){
                graph.put(e.getFrom(),new ArrayList<Edge>());
            }
            
            graph.get(e.getFrom()).add(e);
        }
        return graph;
    }
    
    public ArrayList<Node> sortedNodes(){
        ArrayList <Node> n=new ArrayList<Node>(nodes);
        Collections.sort(n,new CustomSortNode());
        return n;
    }
    
    public ArrayList<Edge> sortedEdges(){
        ArrayList <Edge> n=new ArrayList<Edge>(edges);
        Collections.sort(n,new CustomSortEdge());
        return n;
    }
    
    public void changeWeight(Edge e,int weight){
        int i=edges.indexOf(e);
        if(i>=0) edges.get(i).setWeight(weight);
    }
    public void printNodes(){
        for(Node n:nodes){
            System.out.println("Name: "+n.getName()+" X: "+n.getX()+" Y: "+n.getY());
        }
    }
    
    public void printEdges(){
        for(Edge e:edges){
            System.out.println("From: "+e.getFrom()+" To: "+e.getTo()+" Wt: "+e.getWeight());
        }
    }
    
}
