package ru.itis.mqtt;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Msg;

import java.util.HashMap;
import java.util.Map;

public class SimpleMqttCallBack implements MqttCallback {
    private static Map<Integer, Map<Integer, Double>> data = new HashMap<>();
    private static Gson gson = new Gson();
    private static DistanceDao distanceDao = new DistanceDao();

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) {
        Msg msg = gson.fromJson(new String(mqttMessage.getPayload()), Msg.class);
        distanceDao.saveData(msg);
        System.out.println(msg);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
