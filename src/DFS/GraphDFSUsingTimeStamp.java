package DFS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * File Name: b.GraphDFSUsingTimeStamp.java
 *
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDFSUsingTimeStamp {
    private Graph g;
    private int[] work;
    private boolean[] cycle;
    private ArrayList<Integer> topologicalOrderArray;
    private String f;
    private Set<Integer> visited = new HashSet<>();
    private int step = 1;
    private Stack<Integer> stack = new Stack<>();
    private Map<Integer, List<Integer>> stepMap = new HashMap<>();
    private String tab = "\t";
    private int last = 0;
    //You can have any number of private classes, variables and functions

    GraphDFSUsingTimeStamp(Graph g, int[] work, boolean[] cycle, ArrayList<Integer> topologicalOrderArray, String f) {
        this.g = g;
        this.work = work;
        this.cycle = cycle;
        this.topologicalOrderArray = topologicalOrderArray;
        this.f = f;
        //You MUST WRITE 2 routines
        for (int i=0;i<g.nodes.size();i++)
        dfs(g.nodes.get(i));
        while (!stack.isEmpty()) topologicalOrderArray.add(stack.pop());
        writeDFSDot();
    }

    /*
     * WRITE CODE BELOW
     * //YOU CAN HAVE ANY NUMBER OF PRIVATE VARIABLES, DATA STRUCTURES AND FUNCTIONS
     */
    public void dfs(Node n) {
        if (visited.add(n.num)) {
            stepMap.computeIfAbsent(n.num, ArrayList::new);
            stepMap.get(n.num).add(step++);

            int curLast = last;
            n.fanout.forEach((k, v) -> {
                work[0] = work[0] + 1;

                if ((g.type == GraphType.Type.UNDIRECTED || g.type == GraphType.Type.WEIGHTED_UNDIRECTED)) {
                    if (k < n.num) {
                        if (k != curLast && stepMap.get(k).size() == 1) {
                            cycle[0] = true;
                        }
                    }
                } else if (visited.contains(k)) {
                    if (stepMap.get(k).size() == 1) {
                        cycle[0] = true;
                    }
                }

                last = n.num;
                dfs(g.nodes.get(k));
            });
            stack.push(n.num);
            stepMap.get(n.num).add(step++);
        }
    }

    public void writeDFSDot() {
        try {
            FileWriter myWriter = new FileWriter(f);
            myWriter.write(writeLine("digraph g {"));
            String s = tab + "label = \"[";
            for (int n : topologicalOrderArray) {
                s += g.io.getRealName(n) + " ";
            }
            s += "] " + (cycle[0] ? "LOOP" : "NOLOOP");
            myWriter.write(writeLine(s));

            for (int n = 0; n < g.nodes.size(); n++) {
                s = tab + g.io.getRealName(n) + "[label = <" + g.io.getRealName(n) + "<BR /><FONT POINT-SIZE=\"10\">" + stepMap.get(n).get(0) + "/" + stepMap.get(n).get(1) + "</FONT>>]";
                myWriter.write(writeLine(s));
            }

            s = "edge [dir=none, color=red]";
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
        } catch (Exception e) {

        }
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

    private String writeLine(String s) {
        return s + "\r\n";
    }
}
