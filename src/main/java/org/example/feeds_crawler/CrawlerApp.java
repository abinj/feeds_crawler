package org.example.feeds_crawler;

import org.example.feeds_crawler.configuration.CrawlerConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
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

    }
}
