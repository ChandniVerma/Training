package com.training;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class StoreEmpRecord {
	
	public static void main(String[] args) {
		MongoClient mongoClient=new MongoClient("localhost", 27019);
		
		DB db=mongoClient.getDB("exdb");
		DBCollection dbc=db.getCollection("emps");
		
		
//		// first way to store data
//		DBObject object=new BasicDBObject();
//		object.put("empid", 301);
//		object.put("empname", "Kavita");
//		object.put("empemail", "kavita@gmail.com");
//		object.put("empsale", 334394);
//		
//		dbc.save(object);
//		System.out.println("Object Saved");
//		
//		
//		// Second way to store data
//		Map<String, Object> m=new HashMap<>();
//		m.put("empid", 302);
//		m.put("empname", "RUPA");
//		m.put("empemail", "RUPA@gmail.com");
//		m.put("empsale", 234394);
		
//		dbc.save(new BasicDBObject(m));
		
		// third way to store data
		
//		String jsonString ="{empid:303,empname:'siddharth',empemail:'siddhart@gmail.com',empsale:13000}";
//		
//		dbc.save((DBObject)JSON.parse(jsonString));
//		System.out.println("Saved");
		
		
		//fourth way ..... using bean of Employee Class
		
		Employee employee=new Employee();
		employee.setEmpId(304);
		employee.setEmpName("Anuj");
		employee.setEmpEmail("anuj@gmail.com");
		employee.setEmpSal(45454);
		
		dbc.save(employee);
	}

}
