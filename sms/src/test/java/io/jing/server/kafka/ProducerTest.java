package io.jing.server.kafka;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Map;

public class ProducerTest {
    public static void main(String[] args) throws Exception{
        Map<String,Object> props = Maps.newHashMap();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        File file = new File("/opt/kafka/latest/kafka.log");
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String s;
        while (true){
            s = br.readLine();
            s= "abc ab abc aaf ae abc";
            if(s != null){
                s = s.replace("."," ");
                producer.send(new ProducerRecord<>("log",  s));
            }else {
                reader.close();
                reader = new FileReader(file);
                br = new BufferedReader(reader);
                System.out.println("reset");
            }
            Thread.sleep(1000);

        }
//        int i = 0;
//        while (true){
//            producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
//            Thread.sleep(1000);
//            i++;
//        }

    }
}
