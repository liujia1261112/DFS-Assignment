package DFS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * File Name: b.GraphBuilder.java
 *
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphBuilder {
    private Graph g;
    private Set<String> set=new HashSet<>();
    //You can have any number of private variables

    GraphBuilder(Graph g, String f) throws FileNotFoundException {
        this.g = g;
        buildGraph(f);
    }

    private void buildGraph(String f) throws FileNotFoundException {
        System.out.println("Building b.Graph from file " + f);
        int numlines = 0;
        GraphType.Type t = g.type;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            StringBuilder sb = new StringBuilder();
            String lastline = null;
            int notreadlines = 0;
            while (true) {
                String line1 = br.readLine();
                if (line1 == null) {
                    //System.out.println("lastline = " + lastline) ;
                    //System.out.println("notreadlines = " + notreadlines) ;
                    break;
                } else {
                    // 956468   9 0.00202841
                    //Eat both leading and trailing spaces using trim
                    String line = line1.trim();
                    //There may be more than one space
                    //956468   9 0.00202841
                    lastline = line;
                    String delims = "[ ]+";
                    String[] s = line.split(delims);
                    int l = s.length;
                    if (l == 3) {
                        ++numlines;
                        g.u.myassert((t == GraphType.Type.WEIGHTED_UNDIRECTED) || (t == GraphType.Type.WEIGHTED_DIRECTED));
                    } else if (l == 2) {
                        ++numlines;
                        g.u.myassert((t == GraphType.Type.UNDIRECTED) || (t == GraphType.Type.DIRECTED));
                    } else {
                        //System.out.println("l = " + l + " line = " + line) ;
                        ++notreadlines;
                    }

                    /******************************************************************
                     WRITE YOUR CODE BELOW
                     ******************************************************************/
                    if (s.length == 3 || s.length == 2) {
                        String s1=s[0];
                        String s2=s[1];
                        double w=0;
                        if (s.length==3) w=Double.valueOf(s[2]);

                        if (set.add(s1)){
                            g.buildNodeIfNotExistAndAppend(set.size()-1);
                            g.io.insertOrFind(s1,false);
                        }
                        if (set.add(s2)){
                            g.buildNodeIfNotExistAndAppend(set.size()-1);
                            g.io.insertOrFind(s2,false);
                        }
                        Node node1=g.getNode(g.io.insertOrFind(s1,true));
                        Node node2=g.getNode(g.io.insertOrFind(s2,true));

                        if (s1.equals(s2))  continue;

                        if (g.type== GraphType.Type.DIRECTED||g.type== GraphType.Type.WEIGHTED_DIRECTED){
                            g.createEdge(node1,node2,w,true);
                            g.createEdge(node2,node1,w,false);
                        }
                        if (g.type== GraphType.Type.UNDIRECTED||g.type== GraphType.Type.WEIGHTED_UNDIRECTED){
                            g.createEdge(node1,node2,w,true);
                            g.createEdge(node1,node2,w,false);
                            g.createEdge(node2,node1,w,true);
                            g.createEdge(node2,node1,w,false);
                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}