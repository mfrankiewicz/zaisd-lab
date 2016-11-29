package lab02;

import java.util.LinkedList;

import lab01.Edge;
import lab01.GraphInterface;
import lab01.MatrixGraph;
import lab01.Vertex;

public class WarshalFloyd
{
	private int vertexCount;
	private int[][] distances;
	private int[][] predecessors;
	private LinkedList<Integer> path = new LinkedList<Integer>();
	
	public int[][] warshalFloyd(GraphInterface graph)
	{
		vertexCount = graph.getVertexCount()+1;

		this.distances = new int[vertexCount][vertexCount];
		this.predecessors = new int[vertexCount][vertexCount];
		
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				this.predecessors[i][j] = -1;
				Vertex iv = new Vertex().setVertexId(i);
				Vertex jv = new Vertex().setVertexId(j);
				
				Edge edge = graph.getEdge(iv, jv);
				
				if(i == j) {
					this.distances[i][j] = 0;
				} else if(edge == null) {
					this.distances[i][j] = 9999;
				} else {
					this.distances[i][j] = edge.getEdgeWeight();
					this.predecessors[i][j] = j;
				}
			}
		}	
		
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				for (int k = 0; k < vertexCount; k++) {
					if (this.distances[j][i] + this.distances[i][k] < this.distances[j][k]) {
						this.distances[j][k] = this.distances[j][i] + this.distances[i][k];
						this.predecessors[j][k] = this.predecessors[j][i];
					}
				}
			}
		}	
		
		return this.distances;
	}
	
	public LinkedList<Integer> getPath(int initialVertexId, int finalVertexId)
	{

		if (this.predecessors[initialVertexId][finalVertexId] != -1) {
			path.add(initialVertexId);
			
			while (initialVertexId != finalVertexId) {
				initialVertexId = this.predecessors[initialVertexId][finalVertexId];
				path.add(initialVertexId);
			}
		}
		
		return path;
	}
}
