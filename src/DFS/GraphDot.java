package DFS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * File Name: b.GraphDot.java
 *
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDot {
    private Graph g;
    private String fname;
    private String tab = "\t";
    //You can have any number of private variables

    GraphDot(Graph g, String n) {
        this.g = g;
        this.fname = n;
        dump();
    }

    /******************************************************************
     WRITE YOUR CODE BELOW
     ******************************************************************/
    private void dump() {
        System.out.println("See dot file at " + fname);
        try {
            FileWriter myWriter = new FileWriter(fname);
            myWriter.write(writeLine("digraph g {"));
            String s = "edge [dir=none, color=red]";
            if (g.type == GraphType.Type.DIRECTED || g.type == GraphType.Type.WEIGHTED_DIRECTED)
                s = s.replace("dir=none, ", "");
            myWriter.write(writeLine(s));

            if (g.nodes.size() != 0) {
                for (Node node : g.nodes) {
                    write(node, myWriter);
                }
            }
            myWriter.write("}");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String writeLine(String s) {
        return s + "\r\n";
    }

    private void write(Node n, FileWriter myWriter) {
        for (Map.Entry<Integer, Edge> entrySet : g.nodes.get(n.num).fanout.entrySet()) {
            if ((g.type == GraphType.Type.UNDIRECTED || g.type == GraphType.Type.WEIGHTED_UNDIRECTED) && entrySet.getKey() < n.num) {
                continue;
            }
            try {
                String s = tab + g.io.getRealName(n.num) + " -> " + g.io.getRealName(entrySet.getKey());
                if (g.type == GraphType.Type.WEIGHTED_UNDIRECTED || g.type == GraphType.Type.WEIGHTED_DIRECTED)
                    s += " [label = " + entrySet.getValue().cost + "]";
                myWriter.write(writeLine(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}