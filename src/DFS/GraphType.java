package DFS; /**
 * File Name: b.GraphType.java
 * 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

/*********************************************************************
NOTHING CAN BE CHANGED IN THIS FILE
**********************************************************************/
public class GraphType {
	public enum Type {
		NONE, UNDIRECTED , DIRECTED, WEIGHTED_UNDIRECTED, WEIGHTED_DIRECTED 
	}
	
	static String gtype(GraphType.Type t) {
		if (t == GraphType.Type.UNDIRECTED) {
			return "UNDIRECTED";
		}
		if (t == GraphType.Type.DIRECTED) {
			return "DIRECTED";
		}
		if (t == GraphType.Type.WEIGHTED_UNDIRECTED) {
			return "WEIGHTED_UNDIRECTED";
		}
		if (t == GraphType.Type.WEIGHTED_DIRECTED) {
			return "WEIGHTED_DIRECTED";
		}
		return "NONE";
	}
}