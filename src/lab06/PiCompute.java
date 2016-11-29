package lab06;

import java.util.concurrent.Callable;

public class PiCompute implements Callable<Double>
{
	private double start = 0.0;
	private double end = 0.0;
	private double n = 0.0;
 
	public PiCompute(double s, double e, double n)
	{
		this.start = s;
		this.end = e;
		this.n = n;
	}
	
	public Double call() throws Exception
	{
		double result = 0.0;
		
		for(double i = start; i < end; i++){
			result += 4 / (1 + Math.pow( ( (2*i) + 1) / (2*n), 2));
		}
		
		return result;
	}
}

