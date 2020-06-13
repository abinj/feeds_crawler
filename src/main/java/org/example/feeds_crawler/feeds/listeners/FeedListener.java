package org.example.feeds_crawler.feeds.listeners;

import org.example.feeds_crawler.feeds.models.Feed;

public interface FeedListener {
    void onNewEntry(Feed feed);
}
