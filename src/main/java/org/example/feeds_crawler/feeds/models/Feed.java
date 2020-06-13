package org.example.feeds_crawler.feeds.models;

import java.io.Serializable;

public class Feed implements Serializable {
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
