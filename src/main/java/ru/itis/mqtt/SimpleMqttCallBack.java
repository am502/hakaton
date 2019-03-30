package ru.itis.mqtt;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Msg;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class SimpleMqttCallBack implements MqttCallback {
    private static Map<Integer, Map<Integer, Double>> data = new HashMap<>();
    private static Gson gson = new Gson();
    private static DistanceDao distanceDao = new DistanceDao();

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) {
        Msg msg = gson.fromJson(new String(mqttMessage.getPayload()), Msg.class);
        String topicName = "iot-data";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>(topicName, UUID.randomUUID().toString(),
                new String(mqttMessage.getPayload())));
        System.out.println("Message sent successfully");
        System.out.println(msg);
        producer.close();
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
