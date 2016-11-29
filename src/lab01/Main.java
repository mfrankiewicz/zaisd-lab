package lab01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lab02.WarshalFloyd;
import lab03.FordFulkerson;
import lab04.Huffman;
import lab05.Matrix;
import lab05.MatrixCompute;
import lab05.MatrixLoader;
import lab06.PiCompute;
import lab07.Graham;
import lab07.Point;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		
		GraphInterface 	matrixGraph = null, listGraph = null;
		
		matrixGraph = new MatrixGraph();
		
		File graphFile = new File("src/graph_data.txt");
		
		BufferedReader buffer = new BufferedReader(new FileReader(graphFile));
		String line = "";
		int i = 1;
		for (line = buffer.readLine(); line != null; line = buffer.readLine()) {
			String[] t = line.split(";");
			
			Vertex initialVertex = new Vertex().setVertexId(Integer.parseInt(t[0].trim()));
			Vertex finalVertex = new Vertex().setVertexId(Integer.parseInt(t[1].trim()));
			Edge edge = new Edge().setEdgeId(i)
					.setEdgeWeight(Integer.parseInt(t[2].trim()));
			
			matrixGraph.addVertex(initialVertex);
			matrixGraph.addVertex(finalVertex);
			matrixGraph.addEdge(initialVertex, finalVertex, edge);
			i++;
			
		}
		
		buffer.close();
// lab2
//		float R = (float) warshalFloydBenchmark(listGraph)/warshalFloydBenchmark(matrixGraph);
//
//		System.out.println("R = " + R);

// lab3
//		FordFulkerson fordFulkerson = new FordFulkerson(matrixGraph.getVertexCount());
//		int maxFlow = fordFulkerson.fordFulkerson(matrixGraph, 109, 609);
//		System.out.println("Max flow 109 -> 609: " + maxFlow);
//        
// lab4
//		Huffman huffman = new Huffman();
//		
//		huffman.huffman(1, "src/text_input.txt", "src/text_output.txt", false);
		
// lab5
//		multipleMatrices();
		
// lab6
//		computePi(true);
//		
//		computePi(false);
		
// lab07
		Graham graham = new Graham();
		LinkedList<Point> points = graham.getPoints("src/points.csv");
		graham.graham(points);
		
		System.out.println("Otoczka wypukła dla zadanego zbioru:\n");
		graham.print();
	}
	
	private static long warshalFloydBenchmark(GraphInterface graph)
	{
		long 							start = 0, stop = 0, diff = 0;
		int[][] 						result;
		LinkedList 						path;
		Class<? extends GraphInterface> graphClass = graph.getClass();
		WarshalFloyd 					warshalFloydAlgorithm = new WarshalFloyd();
		
		start 	= System.currentTimeMillis();
		result 	= warshalFloydAlgorithm.warshalFloyd(graph);
		path	= warshalFloydAlgorithm.getPath(1, 20);
		stop	= System.currentTimeMillis();
		diff 	= stop - start;
		
		System.out.println("# " + graphClass);
		System.out.println("Time: " + (diff) + "ms");
		System.out.println("Distance: " + result[1][20]);
		System.out.println("Path: " + path + "\n\n\n");
		
		return diff;
	}
	
	private static void multipleMatrices() throws InterruptedException, ExecutionException, FileNotFoundException
	{
		MatrixLoader matrixLoader = new MatrixLoader("src/matrices.txt");
		Matrix[] matrices = matrixLoader.getMatrices();
		

		
		List<Matrix> tab =  new Stack<Matrix>();

		for(int q = 0; q < 10; q++){
			tab.add(matrices[q]);
		}

		int matrixNum = tab.size();	
		int procNum = Runtime.getRuntime().availableProcessors();
		int x = (int) Math.floor(matrixNum/procNum);

		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		List<Future<Matrix>> f = new ArrayList<Future<Matrix>>();
		List<Matrix> l = new Stack<Matrix>();
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < matrixNum; i += x){
			if(i + x >= matrixNum - 1 && matrixNum % procNum != 0){
				x=matrixNum%procNum;
			}
			Future<Matrix> future = pool.submit(new MatrixCompute(tab, i, i + x)); 
			future.get();
	        f.add(future);
	        
		}
		
		for(Future<Matrix> s : f){ 

			l.add(s.get());
			
		}		
		while(l.size() != 1){
			Matrix R = MatrixCompute.multiply(l.remove(0), l.remove(0));
			
			l.add(R);
		}
		long end = System.currentTimeMillis();
		
		System.out.println("Czas wykonania mnozenia równoległego: " + (end - start) + "ms");
		
		pool.shutdown();
		
		start = System.currentTimeMillis();
		
		Matrix resMatrix = new Matrix();
		
		for(int q = 0; q < 10; q++){
			
			if (resMatrix.getRows() == 0) {
				resMatrix = MatrixCompute.multiply(matrices[q], matrices[q+1]);
			} else {
				resMatrix = MatrixCompute.multiply(resMatrix, matrices[q+1]);
			}
			
		}
		end = System.currentTimeMillis();
		
		System.out.println("Czas wykonania mnozenia: " + (end - start) + "ms");
	}
	
	private static void computePi(boolean parallel) throws InterruptedException, ExecutionException
	{
		double result = 0.0, n = Math.pow(10, 8);
		long startTime, finishTime;
		String computeType = parallel ? "równoległe":"sekwencyjne";
		
		startTime = System.currentTimeMillis();
		
		if (parallel) {
			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			List<Future<Double>> f = new ArrayList<Future<Double>>();
			int processorCount = Runtime.getRuntime().availableProcessors();
			double x = n / processorCount;
			
			for (int i = 0; i < n; i += x) {
				Future<Double> future = pool.submit(new PiCompute(i, i+x, n)); 
		        f.add(future);
			}
			
			for (Future<Double> future : f) { 
				result += future.get();
			}
			
		} else {
			for (double i = 0; i < n; i++) {
				result += 4 / (1 + Math.pow( ( (2*i) + 1) / (2*n), 2));
			}
		}
		finishTime = System.currentTimeMillis();
		
		System.out.println("Obliczanie " + computeType + ", wynik: " + result + ", czas: " + (finishTime - startTime) + "ms\n");
	}
}
