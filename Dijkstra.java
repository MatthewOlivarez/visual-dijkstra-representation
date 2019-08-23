import java.awt.*;
import java.util.*;

public class Dijkstra {

   static DrawingPanel panel = new DrawingPanel(600, 500);
   static Graphics g = panel.getGraphics( );
   static int VERTEX_DIAMETER = 20;
   static final int  INFINITY = 99999;
   static final int  UNDEFINED = -1;
   static final boolean DEBUG = false;
   
   public static void main(String[] args) {
         
      Graph graph = new Graph();
      
      //add vertices      
//      Vertex v6 = new Vertex(60, 60, "Stop 6");
//      graph.addVertex(v6);
//      Vertex v3 = new Vertex(110, 120, "Stop 3");
//      graph.addVertex(v3);
//      Vertex v4 = new Vertex(220, 115, "Stop 4");
//      graph.addVertex(v4);
//      Vertex v1 = new Vertex(45, 200, "Stop 1");
//      graph.addVertex(v1);
//      Vertex v2 = new Vertex(110, 220, "Stop 2");
//      graph.addVertex(v2);
//      Vertex v5 = new Vertex(160, 40, "Stop 5");
//      graph.addVertex(v5);
      
      Vertex v1 = new Vertex(125, 375, "Stop 1");
      graph.addVertex(v1);
      Vertex v2 = new Vertex(350, 400, "Stop 2");
      graph.addVertex(v2);
      Vertex v3 = new Vertex(275, 225, "Stop 3");
      graph.addVertex(v3);
      Vertex v4 = new Vertex(500, 200, "Stop 4");
      graph.addVertex(v4);
      Vertex v5 = new Vertex(300, 50, "Stop 5");
      graph.addVertex(v5);
      Vertex v6 = new Vertex(100, 150, "Stop 6");
      graph.addVertex(v6);

      //add edges
      graph.addEdge(new Edge(v1, v2, 7));
      graph.addEdge(new Edge(v1, v6, 14));
      graph.addEdge(new Edge(v1, v3, 9));
      graph.addEdge(new Edge(v2, v3, 10));
      graph.addEdge(new Edge(v2, v4, 15));
      graph.addEdge(new Edge(v3, v4, 11));
      graph.addEdge(new Edge(v3, v6, 2));
      graph.addEdge(new Edge(v6, v5, 9));
      graph.addEdge(new Edge(v5, v4, 6));
      
      graph.drawGraph(g, VERTEX_DIAMETER);
      
      
      System.out.println("NO. of edges: " + graph.getEdges().size());
      
     
      
      
      Dijkstra(graph, v1); 
      String path = getOptimalPath(v1, v5);
      System.out.println("Optimal path from vertex " + v1.getName() + 
                         " to vertex " + v2.getName() + " : " + path);  
      
      
      while (true) {
    	  
    	  Vertex source = v1; // starting point
          
          Vertex target = v5; // ending point
    	  
          Vertex current = target;
          
          // iterate the path    
          while(!source.equals(current)) {
        	  
        	 target = current;
        	  
             current = current.getPrev();
            
             Edge e = graph.getEdge(current, target);
            
             panel.sleep(1000);
             
             e.drawEdgeLine(g, VERTEX_DIAMETER, Color.red);
             
          } // end inner while
          
          source = v1; // starting point
          
          target = v5; // ending point
    	 
          current = target;
          
          while(!source.equals(current)) {
        	  
         	 target = current;
         	  
             current = current.getPrev();
            
             Edge e = graph.getEdge(current, target);
             
             panel.sleep(1000);
              
             e.drawEdgeLine(g, VERTEX_DIAMETER, Color.blue);
              
           } // end inner while  
    	  
      } // end outer while
      
           
   } // main
   
  public static String getOptimalPath(Vertex source, Vertex target) {
                                        
      //System.out.println(" " + dist.length);                           
      String path = target.getName();
      Vertex current = target;
      System.out.println("getOptimalPath: from  " + source.getName()
                         + " to " + target.getName());
         
      while(!source.equals(current)) {
         current = current.getPrev();
         path = current.getName() + ", "  + path;
      } 
   
      return path + ", with distance " + target.getDist();
  } // method
  
  public static void Dijkstra(Graph graph, Vertex source) {
  
   //intialization 
   Set<Vertex> Q = new LinkedHashSet<Vertex>();
    
   Iterator<Vertex> iterator = graph.getVertices().iterator();
   while(iterator.hasNext()) {
      Q.add(iterator.next()); // stores each vertex into new set Q
   } //while
       
   source.setDist(0); // sets distance at source vertex to zero, which is also located in Q set 

   while(!Q.isEmpty()) { // runs through set Q until empty
      
      Vertex u = minDist(Q); // finds next vertex with min distance 
      
      panel.sleep(1000);
      u.markVertexOut(g, VERTEX_DIAMETER, Color.gray);
    	  
      
      
      Q.remove(u); // removes the min distance vertex out of queue
      
      
      iterator = getNeighbors(u, graph, Q).iterator(); // finds neighbors of 'u'
      while(iterator.hasNext()) { // iterates neighbors and sets appropriate distances
         int alt = 0;
         Vertex v = iterator.next();
         Edge e = graph.getEdge(u, v); // finds edge between vertices
         panel.sleep(1000);
         e.drawEdgeLine(g, VERTEX_DIAMETER, Color.yellow);
         
         
         //alt = dist[u] + length(u, v)
         alt = u.getDist() + e.getWeight(); // adds previous distance to new edge weight
         if(alt != 0 && alt < v.getDist()) { // alt not equal to zero and less than current vertex distance
        	panel.sleep(1000);
            e.drawEdgeLine(g, VERTEX_DIAMETER, Color.white);
        	v.setDist(alt);  // sets new distance for 'v'
            v.setPrev(u); // sets 'u' as previous vertex to 'v'
            panel.sleep(100);
            e.drawEdgeLine(g, VERTEX_DIAMETER, Color.black);
         } else {
        	 
        	panel.sleep(1000);
            e.drawEdgeLine(g, VERTEX_DIAMETER, Color.black);
        	 
         } // end if 
         
         
      } // inner while
      
      panel.sleep(1000);
      u.markVertexOut(g, VERTEX_DIAMETER, Color.red);
      
   } // outer while
   
  } // end method Dikjstra
  
  private static Set<Vertex> getNeighbors(Vertex u, Graph graph, Set<Vertex> Q) {
 
      Iterator<Edge> iterator = graph.getEdges().iterator();
      Set<Vertex> neighbors = new LinkedHashSet<Vertex>();
      
      while(iterator.hasNext()) {
         int alt = 0;
         Edge e = iterator.next();
         Vertex v1 = e.getV1();
         Vertex v2 = e.getV2();
         if(v1.equals(u)) {
            if(Q.contains(v2))
               neighbors.add(v2);
         } else if (v2.equals(u)) {
            if(Q.contains(v1)) {
               neighbors.add(v1);
            }
         }    
      } //while
      

      return neighbors; // returns set of vertices
  } // method
  
  private static Vertex minDist(Set<Vertex> Q) {
                                 
      //Vertex[] verticesQ = new Vertex[1];                       
      //verticesQ = Q.toArray(verticesQ);
      int min = 999999;
      Vertex u = new Vertex(-1,-1,"Foo"); // placeholder
      
      Iterator<Vertex> iterator = Q.iterator();
      while(iterator.hasNext()) {
         Vertex v = iterator.next();
         System.out.println(v.getDist());
         if(min > v.getDist()) {
             min = v.getDist();
             u = v;
         }// end if
         
         if (v.getDist() == Dijkstra.INFINITY) {
        	 
        	 g.drawString(String.valueOf(v.getDist()),
  	               v.getX(), v.getY());
        	 
         }
      
      } // end while
      
      System.out.println(u.toString());
      return u; // returns single vertex
  } //method
   
} //class 

class Vertex {

   private int x;  //vertex's x ordinate
   private int y;  //vertex's y ordinate
   //distance from source
   int dist = Dijkstra.INFINITY;
   Vertex prev = this;  
   private String name; //vertex name
   
   //constructor
   public Vertex(int X, int Y, String stopName) {
      x = X;
      y = Y;      
      name = stopName;
   }
   
   //getter and setters
   public int getX() {
       return x;
   }
   
   public int getY() {
       return y;
   }
   
   public int getDist() {
       return dist;
   }
   
   public String getName() {
       return name;
   }
   
   public Vertex getPrev() {
       return prev;
   }
   
   public void setX(int X) {
      x = x;
   }
   
   public void setDist(int d) {
      dist = d;
   }
   
   public void setY(int Y) {
      y = y;
   }

   public void setPrev(Vertex v) {
      prev = v;
   }


   public void setName(String stopName) {
      name = stopName;
   }
   
   public String toString(){
   
      return "Vertex: " + name + ", X: " + x 
                + ", Y: " + y;
   }
   
   public void drawVertex(Graphics g, int diameter) {
   
      g.setColor(Color.blue);
      g.drawOval(x, y, diameter, diameter);
      int radius = diameter / 2;
      int len = name.length();
      
      g.drawString(name.substring(len -1),
                   x + radius, y + radius); 
      
      
   }
   
   public void markVertexOut(Graphics g, int diameter, Color c) {
	   
	   g.setColor(c);
	   g.fillOval(x, y, diameter, diameter);
	   int radius = diameter / 2;
	   int len = name.length();
	   g.setColor(Color.black);  
	   g.drawString(name.substring(len -1),
	                   x + radius, y + radius);
	   
	   if(getDist() == 0 ) {
		   
		   g.drawString(String.valueOf(getDist()),
	               x, y);
	   }
	   
   }
   
   public boolean equals(Object o) {
  
      Vertex v = (Vertex)o;
      if(Dijkstra.DEBUG)
         System.out.println("CALL from Vertex equals() " + toString());
   
      boolean flag = false;
      
      if ( x == v.getX() && y == v.getY() && name.equals(v.getName()) ) {        
         flag = true;    
         if(Dijkstra.DEBUG) {
            System.out.println("Edge: " + toString() + "\n\t matches\n"
                             + v.toString());
         }
      } else {
         if(Dijkstra.DEBUG) {
            System.out.println("Edge: " + toString() + "\n\t NOT EQUAL\n"
                             + v.toString());
         }
      } // end if
      
      if(Dijkstra.DEBUG) {
         System.out.flush();
      }  
      return flag;
         
   } //method
   
} //class Vertex


class Edge {

   private Vertex v1;  //vertex 1
   private Vertex v2;  //vertex 2
   private int weight;  //edge weight
   
   //constructor aka "instantiator"
   public Edge(Vertex v_1, Vertex v_2, int wgt) {
   
      v1 = v_1;
      v2 = v_2;
      weight = wgt;
   } // constructor
   
   //getter and setters
   public int getWeight() {
       return weight;
   }
   
   public Vertex getV1() {
       return v1;
   }
   
   public Vertex getV2() {
       return v2;
   }
   
   public void setV1(Vertex v) {
       v1 = v;
   }
   
   public void setV2(Vertex v) {
       v2 = v;
   }
   
   public void setWeight(int w) {
       weight = w;
   }
   
   public String toString() {
   
      return "V1: " + v1 + ",\n\t\t\t V:2 " + v2 
             + ",\n\t\t\t Weight: " + weight;
   }
   
   public void drawEdgeLine(Graphics g, int VERTEX_DIAMETER, Color color) {
	   
	   int radius = VERTEX_DIAMETER / 2;
	   
	      
       v1.drawVertex(g, VERTEX_DIAMETER);
       v2.drawVertex(g, VERTEX_DIAMETER);
       int v1X = v1.getX();
       int v1Y = v1.getY();
       int v2X = v2.getX();
       int v2Y = v2.getY();
       g.setColor(color);
       g.drawLine(v1X + radius, v1Y + radius,
                 v2X + radius, v2Y + radius );
       
       g.drawString(String.valueOf(v2.getDist()),
               v2.getX(), v2.getY());
	   
   }
   
   public void drawEdge(Graphics g, int VERTEX_DIAMETER) {
	   
	  
      int radius = VERTEX_DIAMETER / 2;
      
      v1.drawVertex(g, VERTEX_DIAMETER);
      v2.drawVertex(g, VERTEX_DIAMETER);
      int v1X = v1.getX();
      int v1Y = v1.getY();
      int v2X = v2.getX();
      int v2Y = v2.getY();
      g.drawLine(v1X + radius, v1Y + radius,
                 v2X + radius, v2Y + radius );
                 
      int deltaX = Math.abs(v1X - v2X)/2;
      int deltaY = Math.abs(v1Y - v2Y)/2;
      int midX = 0;
      int midY = 0;
      
      if(v1X < v2X)
         midX = v1X + deltaX;
      else
         midX = v1X - deltaX;

      if(v1Y < v2Y)
         midY = v1Y + deltaY;
      else
         midY = v1Y - deltaY;   
      g.drawString(String.valueOf(weight), midX, midY);
                 
   } //method
   
   public boolean equals(Object o) {
   
      Edge e = (Edge)o;
   
      System.out.println("Edge equals() check\n" + e.toString()
                         + "\n" + toString());
      System.out.flush();
   
      if ( e.getV1().equals(v1) &&  e.getV2().equals(v2)
           && weight == e.getWeight())
         return true;
      else
         return false;
         
   } //method

   
} //class Edge

class Graph {

   private Set<Vertex> vertices = new LinkedHashSet<Vertex>();
   private Set<Edge> edges = new LinkedHashSet<Edge>();
   
   public Set<Vertex> getVertices() {
      return vertices;
   }
   
   public Set<Edge> getEdges() {
      return edges;
   }
   
   public Edge getEdge(Vertex v, Vertex u) {
   
      Edge e = new Edge(new Vertex(0, 0, "Foo"),
                        new Vertex(0, 0, "Poo"), 0);
                        
      Iterator<Edge> iterator = edges.iterator();
      while(iterator.hasNext()) {
         Edge e2 = iterator.next();
         if(e2.getV1().equals(v) || e2.getV1().equals(u))
            if(e2.getV2().equals(v) || e2.getV2().equals(u))
               e = e2;
      }
      
      return e;
   } // end method
   
   public void addVertex(Vertex v) {
      vertices.add(v);
   }
   
   public void addEdge(Edge e) {
   
      System.out.println("CALL from Graph addEdge() " + e.toString());
      System.out.flush();

   
      boolean flag = true;  //no duplicate edge 
      Edge e2 = new Edge(new Vertex(0, 0, "Foo"),
                        new Vertex(0, 0, "Poo"), 0);
                        
      Vertex v1 = e.getV1();
      Vertex v2 = e.getV2();                  

      Iterator<Edge> iterator = edges.iterator();
      while(iterator.hasNext()) {
         
         e2 = iterator.next();
         
         if(  (e2.getV1().equals(v1) || e2.getV1().equals(v2))
           && (e2.getV2().equals(v1) || e2.getV2().equals(v2))) 
            flag = false;
                  
      } // while
      
      if(flag)
         edges.add(e);
      else
         System.out.println("CALL from Graph addEdge() " + e.toString()
                              + "\n DUPLICATE EDGE!!");
  } // method addEdge

   public void drawGraph(Graphics g, int diameter) {
   
      Iterator<Edge> iterator = edges.iterator();
      while(iterator.hasNext()) {
         Edge e = iterator.next();
         e.drawEdge(g, diameter);      
      } // while

   } // method

   
} // class

