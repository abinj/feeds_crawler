package org.example.feeds_crawler.feeds.crawlers;

import org.example.feeds_crawler.feeds.listeners.FeedListener;
import org.example.feeds_crawler.feeds.models.Tweet;
import twitter4j.*;

import java.util.List;

public class TwitterStreamHandler extends Thread{
    private static TwitterStream ts;
    private static Twitter twitter;
    private FeedListener feedListener;

    public static TwitterStream getTwitterFactory() {
        if (ts == null) {
            ts = new TwitterStreamFactory().getSingleton();
        }
        if (twitter == null) {
            twitter = new TwitterFactory().getInstance();
        }
        return ts;
    }

    public TwitterStreamHandler(FeedListener listener) {
        this.feedListener = listener;
    }

    public void startThread() {
        try {
            this.start();
            System.out.println("Twitter stream thread started !!!");
            getTwitterFactory();
            startStreamListen();
            storeTweetsHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeTweetsHistory() {
        final List<Status> statuses;
        try {
            statuses = twitter.getUserTimeline();
            for (Status status : statuses) {
                Tweet tweet = parseTweet(status);
                feedListener.onNewEntry(tweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private Tweet parseTweet(Status status) {
        Tweet tweet = new Tweet();
        tweet.setId(String.valueOf(status.getId()));
        tweet.setCreatedAt(status.getCreatedAt().toString());
        tweet.setTweet_text(status.getText());
        if (status.getMediaEntities() != null && status.getMediaEntities().length > 0) {
            tweet.setContent_type(status.getMediaEntities()[0].getType());
            tweet.setPreview_img_url(status.getMediaEntities()[0].getMediaURLHttps());
            if (status.getMediaEntities()[0].getVideoVariants() != null
                    && status.getMediaEntities()[0].getVideoVariants().length > 0) {
                tweet.setVideo_url(status.getMediaEntities()[0].getVideoVariants()[0].getUrl());
            }
        }
        if (status.getURLEntities() != null && status.getURLEntities().length > 0) {
            tweet.setExpandedURL(status.getURLEntities()[0].getExpandedURL());
        }
        return tweet;
    }

    private void startStreamListen() {
        StatusListener listener = new StatusListener(){

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
                System.out.println("Got a status deletion notice id:" + arg.getStatusId());
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onStatus(Status status) {
                Tweet tweet = parseTweet(status);
                System.out.println("----- MESSAGE FOUND ------");
                feedListener.onNewEntry(tweet);
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }
        };

        ts.addListener(listener);
        //Will listen only for own tweets, if want to listen for tweets based on user id's or keywords then needs to edit the filter
        try {
            long[] users = new long[]{ts.getId()};
            FilterQuery filter = new FilterQuery();
            //Listen for specific user
            filter.follow(users);

            //If want to listen for specific topic
            //String[] keywordsArray = { "obama" };
            //filtre.track(keywordsArray);
            ts.filter(filter);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void shutdownThread() {
        ts.cleanUp();
        ts.shutdown();
    }
}
