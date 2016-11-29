package lab05;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;


public class MatrixCompute implements Callable<Matrix> {
	int i, j;
	List<Matrix> t;
	
	public MatrixCompute(List<Matrix> t, int i , int j){
		this.t = t;
		this.i = i;
		this.j = j;
	}
		
	public Matrix call() throws Exception {
		
		List<Matrix> list =  new Stack<Matrix>();
		for(int x = i; x < j; x++){
			list.add(t.get(x));
			
		}

		while(list.size() > 1){
			Matrix A = multiply(list.remove(0), list.remove(0));
			list.add(A);
		}	

		return list.get(0);
	}
	
	public static Matrix multiply(Matrix mA, Matrix mB){

		Matrix result =  new Matrix(mA.getRows(), mB.getColumns());
		
       for (int i = 0; i < mA.getRows(); i++) { 
            for (int j = 0; j < mB.getColumns(); j++) { 
            	BigDecimal v = BigDecimal.ZERO;   
                for (int k = 0; k < mA.getColumns(); k++) { 
                	v = v.add(mA.getValue(i, k).multiply(mB.getValue(k, j)));
                }
                result.setValue(i, j, v);
            }
        }
		return result;
	}
	
}