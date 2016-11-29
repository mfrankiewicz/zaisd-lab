package lab05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Scanner;

public class MatrixLoader {
	
	private Matrix[] matrices;

	public MatrixLoader(String filePath) throws FileNotFoundException {
		
		
		Scanner scanner = new Scanner(new File(filePath));
		String contents = scanner.useDelimiter("\\A").next();
		scanner.close();
		
		int index = 0, row = 0, column = 0;
		
		
		
		String[] matricesStr = contents.split("\n\n");
		
		
		Matrix[] matrices = new Matrix[matricesStr.length];
		
		for (String matrix : matricesStr) {
			
			
//			index = matrices.length;
//			matrices = java.util.Arrays.copyOf(matrices, matrices.length + 1);
			
			String[] rowsStr = matrix.split("\n");
			String[] columnsStrt = rowsStr[0].split(";");
			
			
			matrices[index] = new Matrix(rowsStr.length, columnsStrt.length);
			row = 0;
			
			for (String rowStr : rowsStr) {
				//matrices[index] = new Matrix();
				//matrices[index].setRows(rowsStr.length);
				
				String[] columnsStr = rowStr.split(";");
				column = 0;
				for (String columnStr : columnsStr) {
					//matrices[index].setColumns(columnsStr.length);
					//matrices[index].initialize();

					BigDecimal value = new BigDecimal(columnStr.trim()).setScale(2);
					matrices[index].setValue(row, column, value);
					column++;
				}
				row++;
			}

			
			
			index++;
		}

		this.matrices = matrices;
		
//		BufferedReader buffer = new BufferedReader(new FileReader(graphFile));
//		String line = "";
//		int i = 1;
//		for (line = buffer.readLine(); line != null; line = buffer.readLine()) {
//			String[] contents = line.split(";");
//			Integer.parseInt(t[0].trim())
//			
//		}
	}
	
	public Matrix[] getMatrices()
	{
		return this.matrices;
	}
}
