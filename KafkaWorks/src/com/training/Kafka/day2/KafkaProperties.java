package com.training.Kafka.day2;

public interface KafkaProperties {
	
	String TOPIC1="topicB";
	String TOPIC2="topicA";
	
	String  KAFKA_SERVER_URL="localhost";
	int KAFKA_SERVER_PORT=9092;
	
	int KAFKA_PRODUCER_BUFFER_SIZE=100*1024;
	int CONNECTION_TIME_OUT=1000*10*10;
	
	String CLIENT_ID="SimpleConsumerDemoClient";
	
//	//if you have java 9 n above you can have private methods here
//	private String getTopics() {
//		return "";
//	}

}
