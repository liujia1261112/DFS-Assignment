package DFS;

import java.util.ArrayList;

/**
 * File Name: b.GraphDFSUsingTimeStamp.java
 * 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDFSUsingTimeStamp{
	private Graph g ;
	private int [] work ;
	private boolean [] cycle;
	private ArrayList<Integer> topologicalOrderArray;
	private String f;
	//You can have any number of private classes, variables and functions
	
	GraphDFSUsingTimeStamp(Graph g, int [] work, boolean [] cycle,ArrayList<Integer> topologicalOrderArray,String f) {
		this.g = g ;
		this.work = work ;
		this.cycle = cycle ;
		this.topologicalOrderArray = topologicalOrderArray ;
		this.f = f ;
		//You MUST WRITE 2 routines 
		dfs() ;
		writeDFSDot() ;
	}
	
	/*
	 * WRITE CODE BELOW 
	 * //YOU CAN HAVE ANY NUMBER OF PRIVATE VARIABLES, DATA STRUCTURES AND FUNCTIONS
	 */

	 public void dfs(Node n){


	}
}
