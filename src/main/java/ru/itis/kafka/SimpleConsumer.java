package ru.itis.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Msg;

import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) throws Exception {
        DistanceDao distanceDao = new DistanceDao();
        Gson gson = new Gson();

        String topicName = "iot-data";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("Subscribed to topic " + topicName);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                Msg msg = gson.fromJson(record.value(), Msg.class);
                int maxDataId;
                try {
                    maxDataId = distanceDao.getMaxDataId();
                } catch (Exception e) {
                    maxDataId = 0;
                }
                msg.setDistance(msg.getDistance() - 0.2);
                msg.setTagPackId(maxDataId / 255 * 255 + msg.getTagPackId());
                distanceDao.saveData(msg);
            }
        }
    }
}
