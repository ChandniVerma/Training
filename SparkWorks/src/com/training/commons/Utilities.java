package com.training.commons;

import org.apache.spark.api.java.JavaRDD;

public class Utilities {
	
	public static void printStringRDD(
			JavaRDD<String> stringRDD, int limit) 
	
	{
	
		for(String temp:stringRDD.take(limit)) {
			System.out.println(temp);
		}
		System.out.println("**********************");
	}
	//this method to keep java running, so that we can 
	//explore spark instance()
	
	public static void printDataSource(JavaRDD stringRDD) {
		stringRDD.distinct().collect().forEach(System.out::println);
		
		
	}
	
	
	
	public static void hold() {
		while(true) {
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
