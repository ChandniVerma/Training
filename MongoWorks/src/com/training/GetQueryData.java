package com.training;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;


public class GetQueryData {
	public static void main(String[] args) {
		MongoClient mongoClient=new MongoClient("localhost", 27019);
		
		DB db=mongoClient.getDB("exdb");
		DBCollection dbc=db.getCollection("emps");
		
		DBObject queryCondition=new BasicDBObject("empid", 103);
		DBObject sortCondition=new  BasicDBObject("empname", -1);
		
		Map<String,Integer> map=new HashMap<>();
		map.put("empid", 1);
		map.put("empname", 1);
		map.put("_id", 0);
		DBObject projecttionCondition=new BasicDBObject(map);
		
		
		
				
		//DBCursor cursor=dbc.find(queryCondition).sort(sortCondition);
		DBCursor cursor=dbc.find(queryCondition,projecttionCondition);
		
		
		while(cursor.hasNext()) {
			DBObject object=cursor.next();
			
			System.out.println("*****************");
			System.out.println(JSON.serialize(object));
//			System.out.println("Employee Id  " +object.get("empid"));
//			System.out.println("Employee Name " +object.get("empname"));
//			System.out.println("Employee email " +object.get("empemail"));
			
		}
		
		
	}

}
