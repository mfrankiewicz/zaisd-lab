package lab01;

public class List {
	private int edgeCount = 0;
	private Edge firstEdge = null;
	private Edge lastEdge = null;
		
	public boolean isEmpty()
	{
		return this.firstEdge == null;
	}
	
	public int getEdgeCount()
	{
		return this.edgeCount;
	}
	
	public void addEdge(int edgeId, int edgeWeight)
	{
		Edge edge = new Edge();
		
		edge.setEdgeId(edgeId)
			.setEdgeWeight(edgeWeight);
		
		this.edgeCount++;
		
		if (this.isEmpty()) {
			edge.setPreviousEdge(null)
				.setNextEdge(null);

			this.firstEdge = edge;
			this.lastEdge = edge;
		} else {
			this.lastEdge.setNextEdge(edge);
			edge.setPreviousEdge(this.lastEdge);
			edge.setNextEdge(null);
			this.lastEdge = edge;
		}
	}
	
//	public void delete(int Edge){
//		Edge current = first;
//		while(current != null){
//			if(current.EdgeID == Edge){
//				N--;
//				if(current == first){
//					first = current.next;
//					current.next.prev = null;
//				}else if(current == last){
//					last = current.prev;
//					current.prev.next = null;
//				}else{
//					current.prev.next = current.next;
//					current.next.prev = current.prev;
//				}
//				current = null;
//			}else{
//				current = current.next;
//			}
//		}
//	}
//	
	public Edge findEdge(int edgeId)
	{
		Edge current = this.firstEdge;
		while(current != null){
			if(current.getEdgeId() == edgeId){
				return current;
			}else{
				current = current.getNextEdge();
			}					
		}
		return current;
	}

//	public Edge get(int i) {
//		Edge current = first;
//		for(int n = 0; n < i; n++){
//			current = current.next;
//		}
//		return current;
//	}
}
