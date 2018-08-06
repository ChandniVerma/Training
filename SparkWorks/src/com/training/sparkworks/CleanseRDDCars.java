package com.training.sparkworks;

import java.util.Arrays;

import org.apache.spark.api.java.function.Function;

public class CleanseRDDCars implements Function<String,String>{

	@Override
	public String call(String v1) throws Exception {
		// TODO Auto-generated method stub
		String[] attributList=v1.split(",");
		
		//change characters to numbers
		
		attributList[3]=(attributList[3].equals("two"))?"2":"4";
		
		attributList[4]=attributList[4].toUpperCase();
		return Arrays.toString(attributList);
	}

	
}
