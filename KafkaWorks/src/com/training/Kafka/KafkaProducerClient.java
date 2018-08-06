package com.training.Kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

// http//kafka.apache.org

public class KafkaProducerClient {
	public static void main(String[] args) {
		//set all the properties
		//details can be kept in flat files or properties class
		
		
		Properties properties=new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());
		
		// for ack, retries etc etc
		// when producer sends we can ask for ack
		properties.setProperty("acks", "1");
		properties.setProperty("retries", "3");
		
		Producer<String, String> producer= new KafkaProducer<String, String>(properties);
		
		for(int i=0;i<20;i++) {
		
		ProducerRecord<String, String> producerRecord= new ProducerRecord<String, String>("topicA" ,"1", "Testing message from Java");
		
		//identify the kafka server details
		System.out.println("HELlo" +i);
		
		producer.send(producerRecord);
		}
		producer.flush();
		producer.close();
		
	}

}
