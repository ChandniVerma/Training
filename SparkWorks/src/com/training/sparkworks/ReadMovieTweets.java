package com.training.sparkworks;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ReadMovieTweets {
	public static void main(String[] args) {
		String appName="sampleApp";
		// as of now we have got two instances of spark or
		//this can be got from remote instance
		
		String sparkMaster="local[2]";
		JavaSparkContext spContext=null;
		SparkConf conf=new SparkConf().setAppName(appName).setMaster(sparkMaster);
		
		//creating spark context from configuration
		spContext=new JavaSparkContext(conf);
		
		//read the file into RDD
		JavaRDD<String> tweetsRDD=spContext.textFile("./data/movietweets.csv");
		
		tweetsRDD.take(5).forEach(System.out::println);
		
		
		
		//count number of tweets
		int count =(int) tweetsRDD.count();
		
		System.out.println("Number of tweets "+count);
		
		JavaRDD<String> upperCaseRDD=tweetsRDD.map(temp->temp.toUpperCase());
		
		upperCaseRDD.take(10).forEach(System.out::println);
		
		while(true) {
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
				
			}
			
		}
		
		
	}

}
