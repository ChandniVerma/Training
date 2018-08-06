package com.training.Kafka;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaConsumerClient {
	
	public static void main(String[] args) {
		//set all the properties
		//details can be kept in flat files or properties class
		
		
		Properties properties=new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("key.deserializer", StringDeserializer.class.getName());
		properties.setProperty("value.deserializer", StringDeserializer.class.getName());
				
		properties.setProperty("group.id", "FirstGroup");
		properties.setProperty("session.timeout.ms", "30000");
		properties.setProperty("auto.offset.reset", "earliest");
		
		KafkaConsumer<String,String> kafkaConsumer=new KafkaConsumer<String, String>(properties);
		
		kafkaConsumer.subscribe(Arrays.asList("topicA"));
		while(true){
			 ConsumerRecords<String, String> consumerRecord=kafkaConsumer.poll(1000);

			 System.out.println("count no of msg got "+consumerRecord.count());

			 for(ConsumerRecord<String, String> temp:consumerRecord){
			 System.out.println("key:"+temp.key()+",Value: "+
			 temp.value()+",partition:"+ temp.partition()+" ....."+
			 temp.topic()+",Time:"+
			 new Date(temp.timestamp()));

			 }
			 try{
			 Thread.sleep(100);
			 }catch(Exception e){
			 e.printStackTrace();
			 }
			 }
		
		
		
	}


}
