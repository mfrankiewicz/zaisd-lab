package lab04;

public class HuffmanNode implements Comparable<HuffmanNode>{
    public final char character;
    public final int frequency;
    public final HuffmanNode leftNode, rightNode;
            
    public HuffmanNode(char character, int frequency, HuffmanNode leftNode, HuffmanNode rightNode) {
        this.character = character;
        this.frequency = frequency;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
    
    public boolean isLeaf() {
        return leftNode==null && rightNode==null;
    }
    
	public int compareTo (HuffmanNode thisNode) {
		return this.frequency-thisNode.frequency;
	}
}

