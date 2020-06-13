package org.example.feeds_crawler.configuration;

import java.util.HashMap;

public class CrawlerConfiguration {
    private HashMap<String, Object> kafkaConfig;
    private String kafkaTopic;

    public HashMap<String, Object> getKafkaConfig() {
        return kafkaConfig;
    }

    public void setKafkaConfig(HashMap<String, Object> kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }
}
