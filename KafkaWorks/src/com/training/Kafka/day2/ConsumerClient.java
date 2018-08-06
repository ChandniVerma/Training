package com.training.Kafka.day2;

public class ConsumerClient {
	public static void main(String[] args) {
		Consumer consumer= new Consumer(KafkaProperties.TOPIC2,false);
		consumer.start();
	}

}
