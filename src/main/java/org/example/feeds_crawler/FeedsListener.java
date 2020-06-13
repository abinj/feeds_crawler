package org.example.feeds_crawler;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.feeds_crawler.configuration.CrawlerConfiguration;
import org.example.feeds_crawler.feeds.constants.KafkaConstants;
import org.example.feeds_crawler.feeds.listeners.FeedListener;
import org.example.feeds_crawler.feeds.models.Feed;

import java.util.Properties;

public class FeedsListener implements FeedListener {
    private CrawlerConfiguration configuration;
    private final Gson gson = new Gson();
    private KafkaProducer<String, String> kafkaProducer;

    public FeedsListener(CrawlerConfiguration configuration) {
        this.configuration = configuration;
    }

    public void initialize() {
        Properties properties = new Properties();
        if (configuration == null || configuration.getKafkaConfig() == null || configuration.getKafkaConfig().isEmpty()) {
            properties.put("bootstrap.servers", KafkaConstants.BOOTSTRAP_SERVERS);
            properties.put("metadata.broker.list", KafkaConstants.BROKERS_LIST);
            properties.put("acks", "all");
            properties.put("retries", 0);
            properties.put("linger.ms", 1);
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        } else {
            properties.putAll(configuration.getKafkaConfig());
        }
        kafkaProducer = new KafkaProducer<String, String>(properties);
    }

    @Override
    public void onNewEntry(Feed feed) {
        String feedJson = gson.toJson(feed);
        kafkaProducer.send(new ProducerRecord<String, String>(configuration.getKafkaTopic(), feed.getLink(), feedJson));
    }
}
