package org.example.feeds_crawler;

import org.example.feeds_crawler.configuration.CrawlerConfiguration;
import org.example.feeds_crawler.feeds.crawlers.TwitterStreamHandler;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CrawlerApp {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: <file.yaml>");
            return;
        }

        Yaml yaml = new Yaml();
        InputStream inputStream = Files.newInputStream(Paths.get(args[0]));
        CrawlerConfiguration configuration = yaml.loadAs(inputStream, CrawlerConfiguration.class);
        final FeedsListener feedsListener = new FeedsListener(configuration);
        feedsListener.initialize();

        final TwitterStreamHandler streamHandler = new TwitterStreamHandler(feedsListener);
        streamHandler.startThread();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                streamHandler.shutdownThread();
                feedsListener.stop();
                super.run();
            }
        });
    }
}
