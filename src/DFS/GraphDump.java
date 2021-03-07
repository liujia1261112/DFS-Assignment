package DFS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * File Name: b.GraphDump.java
 *
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDump {
    private Graph g;
    private String name;
    //You can have any number of private variables

    GraphDump(Graph g, String n) {
        this.g = g;
        this.name = n;
        dump();
    }

    /******************************************************************
     WRITE YOUR CODE BELOW
     ******************************************************************/
    private void dump() {
        System.out.println("------------ " + name + " ------------");
        try {
            FileWriter myWriter = new FileWriter(name + ".txt");
            myWriter.write(writeLine("b.GraphType." + g.type));
            myWriter.write(writeLine("Num Vertices = " + g.numV()));
            myWriter.write(writeLine("Num Edges    = " + g.numE()));

            if (g.nodes.size() != 0) {
                for (Node node:g.nodes){
                    write(node,myWriter);
                }
            }
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
        String fanout = "NONE,";
        if (g.nodes.get(n.num).fanout.size() > 0) {
            fanout = "";
            for (Map.Entry<Integer, Edge> entrySet : g.nodes.get(n.num).fanout.entrySet()) {
                fanout += g.io.getRealName(entrySet.getKey()) + ",";
            }
        }
        String fanin = "NONE,";
        if (g.nodes.get(n.num).fanin.size() > 0) {
            fanin = "";
            for (Map.Entry<Integer, Edge> entrySet : g.nodes.get(n.num).fanin.entrySet()) {
                fanin += g.io.getRealName(entrySet.getKey()) + ",";
            }
        }
        try {
            myWriter.write(writeLine(g.io.getRealName(n.num) + " " + "Fanouts: " + fanout.substring(0, fanout.length() - 1)));
            myWriter.write(writeLine(g.io.getRealName(n.num) + " " + "FanIns: " + fanin.substring(0, fanin.length() - 1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}