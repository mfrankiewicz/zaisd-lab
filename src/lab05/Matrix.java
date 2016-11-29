package lab05;

import java.math.BigDecimal;

public class Matrix {
	private BigDecimal[][] matrix;
	private int columns;
	private int rows;

//	public Matrix(int rows, int columns) {
//		this.columns = columns;
//		this.rows = rows;
//		this.matrix = new BigDecimal[rows][columns];
//	}

	public int getColumns() {
		return this.columns;
	}

	public int getRows() {
		return this.rows;
	}

	public BigDecimal getValue(int row, int column) {
		if (row >= this.getRows() || column >= this.getColumns()) {
			return BigDecimal.ZERO;
		}
		if (matrix[row][column] == null) {
			return BigDecimal.ZERO;
		}
		return matrix[row][column];
	}

	public void setValue(int row, int column, BigDecimal value) {
		
		this.matrix[row][column] = value;
	}

	public String print() {
		String s = "";
		
		for(int row = 0; row < this.rows; row++){
			for(int column = 0; column < this.columns; column++){

				s += this.matrix[row][column].setScale(2, BigDecimal.ROUND_FLOOR).toString() + " ";
			}
			s += "\n";
		}
		
		return s;
	}
	
	public void setRows(int rows)
	{
		this.rows = rows;
	}
	
	public void setColumns(int columns)
	{
		this.columns = columns;
	}
	
	public void initialize()
	{
		this.matrix = new BigDecimal[this.rows][this.columns];
	}
	
	public Matrix(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.initialize();
	}

	public Matrix() {
		// TODO Auto-generated constructor stub
	}

}