package ru.itis.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subcriber {
    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://192.168.0.54:1883", MqttClient.generateClientId());
        client.setCallback(new SimpleMqttCallBack());
        client.connect();

        client.subscribe("iot-data/1");
    }
}
