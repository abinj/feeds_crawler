package org.example.feeds_crawler.feeds.listeners;

import org.example.feeds_crawler.feeds.models.Tweet;

public interface FeedListener {
    void onNewEntry(Tweet tweet);
}
