package lab01;

public class ListGraph implements GraphInterface
{
	private Edge[][] graph = new Edge[0][0];
	private Vertex[] vertices = new Vertex[0];
	private int vertexCount;
	
	public void addEdge(Vertex initialVertex, Vertex finalVertex, Edge edge)
	{
		this.graph[initialVertex.getVertexId()][finalVertex.getVertexId()] = edge;
	}
	
	public Edge getEdge(Vertex initialVertex, Vertex finalVertex)
	{
		return this.graph[initialVertex.getVertexId()][finalVertex.getVertexId()];
	}
	
	public int getVertexCount()
	{
		return this.vertexCount;
	}

	public void addVertex(Vertex vertex) {
		if (!this.vertexExists(vertex)) {
			
			this.vertices = java.util.Arrays.copyOf(this.vertices, this.vertexCount + 1);
			this.vertices[this.vertexCount] = vertex;
			
			this.vertexCount++;
		}
		
	}

	public void removeVertex(Vertex vertex) {
		for(Vertex v : this.vertices) {
			if (v.getVertexId() == vertex.getVertexId()) {
				v = null;
				break;
			}
		}
		
	}

	public void removeEdge(Edge edge) {
		for(Edge[] e : this.graph) {
			for (Edge ee : e) {
				if (ee.getEdgeId() == edge.getEdgeId()) {
					e = null;
					break;
				}
			}
		}
	}

	public boolean vertexExists(Vertex vertex) {
		boolean exists = false;
		
		for(Vertex v : this.vertices) {
			if (v.getVertexId() == vertex.getVertexId()) {
				exists = true;
				break;
			}
		}
	            
		return exists;
	}

	public boolean edgeExists(Vertex initialVertex, Vertex finalVertex) {

		return this.graph[initialVertex.getVertexId()][finalVertex.getVertexId()] != null;

	}
}
