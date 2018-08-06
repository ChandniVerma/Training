package com.training.sparkworks;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import com.training.commons.DataSource;
import com.training.commons.SparkConnection;
import com.training.commons.Utilities;

public class SparkOperationsClient {
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		JavaSparkContext sparkContext= SparkConnection.getContext();
		
		//start loading the data 
		
		// 1) load the collection and cache
		
		JavaRDD<Integer> collData=DataSource.getCollData();
		System.out.println("DISTINCT DATASOURCES");
	
		//(3,4,56,43,66,77,23,22,33,3,77,3)
		//sum =410
		//collData.distinct().collect().forEach(System.out::println);
		Utilities.printDataSource(collData);
		
		System.out.println("Total count "+collData.count());
		//2) load the file and cache it.
		
		JavaRDD<String> autoDataContent=sparkContext.textFile("./data/auto-data.csv");
		
		//to know the Records
		
		System.out.println("Number of Records "+autoDataContent.count());
		
		//printing 5 lines of data
		System.out.println("Loading data from file *******");
		
		Utilities.printStringRDD(autoDataContent, 5);
		
		//autoDataContent.take(5).forEach(System.out::println);
		
		//storing RDD's
		//autoDataContent.saveAsTextFile("./data/auto-data-modified.csv");
		
		
		//spark transformation
		//converting from CSV to TSV
		
		JavaRDD<String> tsvData=autoDataContent.map(str->str.replace(",", "\t"));
		
		Utilities.printStringRDD(tsvData, 5);
		System.out.println("####################");
		
		//////////Filter Example ///////////////
		// to remove header
		
		String header=autoDataContent.first();
		
		JavaRDD<String> autoDataWithoutHeader=autoDataContent.filter(s->!s.equals(header));
		
		Utilities.printStringRDD(autoDataWithoutHeader, 5);
		
		
		//has only totyato
		JavaRDD<String> autoToyota=autoDataContent.filter(s->s.contains("toyota"));
		System.out.println("toyota vehicles");
		Utilities.printStringRDD(autoToyota, 12);
		
		JavaRDD<String> autodistinct=autoDataContent.distinct();
		
		System.out.println("Distinct");
		Utilities.printStringRDD(autodistinct, 15);
		
		
		JavaRDD<String> words=autoToyota.flatMap(new FlatMapFunction<String, String>() {
			
			@Override
			public Iterator<String> call(String t)throws Exception{
				
				return Arrays.asList(t.split(",")).iterator();
			}
			
			
		});
		
		System.out.println("ToyotaRDD Word Count: "+words.count());
		
		//after cleansng the data from class #CleanseRDDCars
		
		System.out.println("******AFTER Cleasnising data*********");
		
		JavaRDD<String> cleanseRDD=autoDataContent.map(new CleanseRDDCars());
		
		Utilities.printStringRDD(cleanseRDD, 5);
		
		
		//Set Operations 
		
		JavaRDD<String> words1=sparkContext.parallelize(
				Arrays.asList("hello","How","are","you"));
		
		JavaRDD<String> words2=sparkContext.parallelize(
				Arrays.asList("hello","How","Were","yesterday"));
		
		System.out.println("Union Operations - Set ");
		Utilities.printStringRDD(words1.union(words2),9);
		
		System.out.println("Intersection -Set");
		Utilities.printStringRDD(words1.intersection(words2),9);
		
		//find sum of number in the given RDD
		
		Integer collDataCount=collData.reduce((x,y)->x+y);
		
		System.out.println("Sum of Given "+collDataCount);
		
		System.out.println("avg of city "+autoDataWithoutHeader.mapToDouble(a->Double.valueOf(a.split(",")[9])).stats().mean());
		System.out.println("avg of High way "+autoDataWithoutHeader.mapToDouble(a->Double.valueOf(a.split(",")[10])).stats().mean());
		
		
		
	
	}
	

}
