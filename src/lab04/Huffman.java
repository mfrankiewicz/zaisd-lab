package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PriorityQueue; 
import java.util.Scanner;

public class Huffman {

    public int[] frequencyArray = new int[256];
    public String[] codes = new String[256];
    public HuffmanNode root;

    public HuffmanNode buildHuffmanTree() {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
        
        for(int i = 0 ; i < this.frequencyArray.length; i++) {
            if (this.frequencyArray[i] != 0) {
            	pq.add(new HuffmanNode((char) i, this.frequencyArray[i], null, null));
            }
        }
         
        while (pq.size() > 1) {
            HuffmanNode leftNode = pq.poll();
            HuffmanNode rightNode = pq.poll(); 
            HuffmanNode parent = new HuffmanNode('\0', leftNode.frequency + rightNode.frequency, leftNode, rightNode); 
            pq.add(parent);
        }
        return pq.poll();
    }
         
    public void constructCodeTable(HuffmanNode node, String s){
        if(node.isLeaf()){
        	this.codes[node.character] = s;
            return;
        }  
        
        this.constructCodeTable(node.leftNode, s+"0");
        this.constructCodeTable(node.rightNode, s+"1"); 
    }
    
    public String compress(String input){
        StringBuilder output = new StringBuilder();
        
        for(int i = 0; i < input.length(); i++){
        	output.append(this.codes[input.charAt(i)]);
        }
        return output.toString();
    }
    
    public String decompress(String input){
        StringBuilder output = new StringBuilder();
        HuffmanNode traveler = this.root;
        
        for(int i = 0; i < input.length(); i++){
            
            if (input.charAt(i) == '1') {
                traveler = traveler.rightNode;
            } else {
            	traveler = traveler.leftNode;
            }
            
            if (traveler.isLeaf()) {
            	output.append(traveler.character);
                traveler = this.root;
            }
        }
        return output.toString();
    }
    
    public void huffman(int codingLenght, String inputFile, String outputFile, boolean compress) throws FileNotFoundException
    {
    	String input = this.loadFile(inputFile);
    	String output = this.loadFile(outputFile);
        
        for(int i=0; i<input.length(); i++) {
        	this.frequencyArray[input.charAt(i)]++; 
        }

        this.root = this.buildHuffmanTree();
        this.constructCodeTable(this.root, "");

        if (compress) {
        	String compressed = this.compress(input);
        	this.saveFile(outputFile, compressed);
        	
        	System.out.println("Compression: " + (float)(input.length()*8 - compressed.length())/(input.length()*8)*100 + "%");
        } else {
        	String decompressed = this.decompress(output);
        	
        	System.out.println("Decompressed string:");
        	System.out.println(decompressed);
        }
        
        
       
        
        
    }
    
    public void saveFile(String filePath, String content) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(filePath);
        file.println(content);
        file.close();
    }
    
    public String loadFile(String filePath) throws FileNotFoundException {    	
		Scanner scanner = new Scanner(new File(filePath));
		String contents = scanner.useDelimiter("\\A").next();
		scanner.close();
		
		return contents; 
    }
}