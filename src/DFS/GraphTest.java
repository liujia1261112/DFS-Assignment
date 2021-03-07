package DFS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * File Name: b.GraphTest.java
 *
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphTest {
    private IntUtil u = new IntUtil();

    GraphTest() throws FileNotFoundException {
        testBed();
    }

    private void dump(Graph g, String name) {
        System.out.println(GraphType.gtype(g.type));
        int numv = g.numV();
        int nume = g.numE();
        System.out.println("Num Vertices = " + numv);
        System.out.println("Num Edges    = " + nume);
    }

    private void build(String name, GraphType.Type graphType, int enodes, int eedges) throws FileNotFoundException {
        GraphIO io = new GraphIO();
        Graph g = new Graph(graphType, io);
        String f = GraphInputOutputDir.inputFileBase + name + ".txt";

        g.buildGraph(f);
        g.dumpGraph(name);
        f = GraphInputOutputDir.outputFileBase + name + ".dot";
        int v = g.numV();
        g.writeGraphAsDotFile(f);
        if (v < 25) {
            GraphInputOutputDir.dot2pdf(GraphInputOutputDir.outputFileBase + name);
        }
        if (v != enodes) {
            System.out.println("The graph has " + enodes + " Nodes. But you are telling " + v + " Nodes");
            u.myassert(v == enodes);
        }
        int e = g.numE();
        if (e != eedges) {
            System.out.println("The graph has " + eedges + " Edges. But you are telling " + e + " Edges");
            u.myassert(e == eedges);
        }
    }

    /*
     * Build graph, dump and print as a dotfile
     */
    private void testBuildGraph() throws FileNotFoundException {
        build("13", GraphType.Type.UNDIRECTED, 7, 24);
        build("14", GraphType.Type.WEIGHTED_UNDIRECTED, 6, 20);
        build("15", GraphType.Type.DIRECTED, 6, 6);
        build("16", GraphType.Type.WEIGHTED_DIRECTED, 5, 6);
        build("loopparallel", GraphType.Type.WEIGHTED_DIRECTED, 4, 3);
        build("cat", GraphType.Type.DIRECTED, 6, 7);
        build("hd2", GraphType.Type.WEIGHTED_DIRECTED, 78, 1095);
    }

    private void dfsUsingTimeStamp(String name, GraphType.Type graphType, boolean expectedHasloop) throws FileNotFoundException {
        GraphIO io = new GraphIO();
        Graph g = new Graph(graphType, io);
        String f = GraphInputOutputDir.inputFileBase + name + ".txt";

        System.out.println("---------------" + name + " ---------------------");
        g.buildGraph(f);
        //g.dumpGraph(name);
        f = GraphInputOutputDir.outputFileBase + name + ".dot";
        int v = g.numV();
        g.writeGraphAsDotFile(f);
        if (v < 25) {
            GraphInputOutputDir.dot2pdf(GraphInputOutputDir.outputFileBase + name);
        }
        int[] work = {0};
        boolean[] cycle = {false};
        ArrayList<Integer> topologicalOrderArray = new ArrayList<Integer>();
        f = GraphInputOutputDir.outputFileBase + name + "dfs.dot";
        System.out.println("-----Running dfsUsingTimeStamp on " + name + "-----");
        g.dfsUsingTimeStamp(work, cycle, topologicalOrderArray, f);
        int size = topologicalOrderArray.size();
        if (size != v) {
            System.out.println("The graph has " + v + " Nodes. But you visited " + size + " Nodes");
            u.myassert(false);
        }
        if (cycle[0] != expectedHasloop) {
            if (expectedHasloop == false) {
                System.out.println("The graph has NO loop, But you are telling graph has loop");
            } else {
                System.out.println("The graph has loop, But you are telling graph has NO loop");
            }
            u.myassert(cycle[0] == expectedHasloop);
        }
        boolean x = g.assertDFS(cycle[0], topologicalOrderArray);
        if (x == false) {
            System.out.println("Toplogical ordering is wrong");
            u.myassert(false);
        }
        dump(g, name);
        System.out.println("Work Done    = " + work[0]);
        System.out.print("Has Loop    = ");
        if (cycle[0]) {
            System.out.println(" YES");
        } else {
            System.out.println(" NO");
        }
        System.out.print("Topological order = ");
        for (int i : topologicalOrderArray) {
            System.out.print(g.io.getRealName(i) + " ");
        }
        System.out.println();
        System.out.println("You can see DFS traversal dot file at " + f);
        if (v < 25) {
            GraphInputOutputDir.dot2pdf(GraphInputOutputDir.outputFileBase + name + "dfs");
        } else {
            System.out.println("Too big graph to make pdf file from dot file using Graphviz");
        }
    }

    /*
     * Test dfsUsingTimeStamp
     */
    private void testDfsUsingTimeStamp() throws FileNotFoundException {
        dfsUsingTimeStamp("u1", GraphType.Type.UNDIRECTED, false);
        dfsUsingTimeStamp("1", GraphType.Type.UNDIRECTED, false);
        dfsUsingTimeStamp("udf1", GraphType.Type.UNDIRECTED, true);//loop
        dfsUsingTimeStamp("2", GraphType.Type.DIRECTED, false);
        dfsUsingTimeStamp("3", GraphType.Type.DIRECTED, true); //loop
        dfsUsingTimeStamp("cat", GraphType.Type.DIRECTED, false);
        dfsUsingTimeStamp("7", GraphType.Type.WEIGHTED_DIRECTED, false);
        dfsUsingTimeStamp("mediumEWD", GraphType.Type.WEIGHTED_DIRECTED, true); //loop
    }

    private void testBed() throws FileNotFoundException {
        //testBuildGraph() ;
        testDfsUsingTimeStamp();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //String s = outputFileBase + "output.txt";
        String s = "output.txt";
        if (false) { //Make it to true to write to a file
            System.out.println("Writing to " + s);
            try {
                System.setOut(new PrintStream(new FileOutputStream(s)));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String version = System.getProperty("java.version");
        System.out.println("Java version used for this program is " + version);
        System.out.println("b.GraphTest.java starts");
        GraphTest g = new GraphTest();
        System.out.println("b.GraphTest.java Ends");
    }
}
	