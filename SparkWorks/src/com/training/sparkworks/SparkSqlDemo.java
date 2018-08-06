package com.training.sparkworks;

import org.apache.log4j.Level;

import org.apache.log4j.Logger;
import static org.apache.spark.sql.functions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;


import com.training.commons.SparkConnection;

import scala.tools.nsc.interpreter.Javap.Showable;

public class SparkSqlDemo {
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);
		
		JavaSparkContext sparkContext= SparkConnection.getContext();
		
		SparkSession spSession=SparkConnection.getSession();
		
		Dataset<Row> empDatafields=spSession.read().json("./data/customerdata.json");
		
		
		empDatafields.show();
		
		//String[] col=empDatafields.columns();
		
		//gives data type of all row
		empDatafields.printSchema();
		
		//data queries 
		System.out.println("Select Demo");
		
		//System.out.println(empDatafields.selectExpr(col).agg());
		
		//empDatafields.select("name","salary").show();
		
		empDatafields.select(col("name"),col("salary")).show();
		
		System.out.println("Select with Conditions");
		
		empDatafields.filter(col("gender").equalTo("male")).show();
		
		System.out.println("GroupBy");
		empDatafields.groupBy(col("gender")).count().show();
		
		
		//group by deptid, average salary, max age
		
		Dataset<Row> summaryData=empDatafields.groupBy(col("deptid")).
				agg(avg(empDatafields.col("salary")),max(empDatafields.col("age")));
		
		summaryData.show();
		
		
		Department dept1=new Department(100,"Deveplopment");
		Department dept2=new Department(200,"Testing");
		List<Department> deplist=new ArrayList<Department>();
		deplist.add(dept1);
		deplist.add(dept2);
		
		Dataset<Row> deptDataField=spSession.createDataFrame(deplist, Department.class);
		
		System.out.println("Department Content are ");
		deptDataField.show();
		
		System.out.println("Join Employee with Dept");
		
//		Dataset<Row> emDeptJoin=empDatafields.join(deptDataField);
//		emDeptJoin.show();
		
		Dataset<Row> empDeptJoin=empDatafields.join(deptDataField,col("deptid")
				.equalTo(col("departmentId")));
		
		empDeptJoin.show();
		
		System.out.println("----------Join with aggregate------(Department and Employee )");
		
		empDatafields.filter(col("age").gt(30)).join(deptDataField,col("deptid").equalTo(col("departmentId")))
									.groupBy(col("deptid")).agg(avg(empDatafields.col("salary")),
											max(empDatafields.col("age"))).show();
				
		
		//to load data from CSV
		
		Dataset<Row> autoData=spSession.read().option("header", true).csv("data/auto-data.csv");
		
		autoData.show(5);
		
		/////////creating RDD with row objects
		Row row1=RowFactory.create(1,"India", "Bengaluru");
		Row row2=RowFactory.create(2,"USA","Reston");
		Row row3=RowFactory.create(3,"UK","Steevenscreek");
		
		
		List<Row> rList=new ArrayList<Row>();
		
		rList.add(row1);
		rList.add(row2);
		rList.add(row3);
		
		JavaRDD<Row> rowRDD=sparkContext.parallelize(rList);
		
		StructType schema=DataTypes.createStructType(new StructField[] {
				
				DataTypes.createStructField("id", DataTypes.IntegerType, false),
				DataTypes.createStructField("country", DataTypes.StringType, false),
				DataTypes.createStructField("city", DataTypes.StringType, false)}
				
				);
		
		Dataset<Row> tempDataFields=spSession.createDataFrame(rowRDD, schema);
		
		tempDataFields.show();
		
		//Dataset<Row> tryData=tempDataFields.join(empDatafields);
		//tryData.show();
		
		//working with csv data, with sql statements
		//provided the data is kept in table like format
		// the persistence will be only till the end of program
		// meaning temporary
//		
		autoData.createOrReplaceTempView("autos");
		System.out.println("Temp table Contents :");
//		
		System.out.println("Showing all files with hp greater than 200");
	    spSession.sql("select * from autos where hp > 200").show();
		
		
		//to find max 
		
		System.out.println("Find maximum  ");
		
		spSession.sql("select make, max(rpm) from autos" + " group by make order by 2").show();
		
		//convert DataFram to JavaRDD
		
		JavaRDD<Row> autoRDD=autoData.rdd().toJavaRDD();
		
		// reading the data from MYSQL DB
		
		Map<String, String> jdbcConnectParams=new HashMap<String,String>();
		
		jdbcConnectParams.put("url","jdbc:mysql://localhost:3306/exdb");
		jdbcConnectParams.put("driver","com.mysql.jdbc.Driver");
		jdbcConnectParams.put("dbtable", "employee");
		jdbcConnectParams.put("user", "root");
		jdbcConnectParams.put("password", "root@123");
		
		System.out.println("Create a dataframe from DB table Employee");
		
		Dataset<Row> sqlDataFields=spSession.read().format("jdbc").options(jdbcConnectParams).load();
		
		sqlDataFields.show();
		
		
						
	}

}
