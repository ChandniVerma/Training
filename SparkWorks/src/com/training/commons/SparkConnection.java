package com.training.commons;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkConnection {
	
	private static String appName="sampleApp";
	private static String sparkMaster="local[2]";
	
	private static JavaSparkContext spContext = null;
	private static SparkSession sparkSession=null;
	
	//create folder manuaaly on system
	private static String tempDir="file:///Users/chandni.v/Desktop/sparkWarehouse ";
	
	private static void getConnection() {
		if(spContext == null) {
			SparkConf conf=new SparkConf().setAppName(appName).setMaster(sparkMaster);
			
			spContext=new JavaSparkContext(conf);
			sparkSession =sparkSession.builder().appName(appName).master(sparkMaster)
					.config("spark.sql.warehouse.dir", tempDir).getOrCreate();
		}
	}
	
	public static JavaSparkContext getContext() {
		
		if(spContext==null) {
			getConnection();
		}
		return spContext;
		
	}
	
	public static SparkSession getSession() {
		if(sparkSession==null) {
			getConnection();
		}
		return sparkSession;
		
	}
	
}
