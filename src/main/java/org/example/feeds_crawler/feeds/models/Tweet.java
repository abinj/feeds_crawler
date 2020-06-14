package org.example.feeds_crawler.feeds.models;

public class Tweet {
    private String createdAt;
    private String tweet_text;
    private String id;
    private String content_type;
    private String preview_img_url;
    private String video_url;
    private String expandedURL;
    private String post_type;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getPreview_img_url() {
        return preview_img_url;
    }

    public void setPreview_img_url(String preview_img_url) {
        this.preview_img_url = preview_img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getExpandedURL() {
        return expandedURL;
    }

    public void setExpandedURL(String expandedURL) {
        this.expandedURL = expandedURL;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        if (post_type == null) {
            this.post_type = "tweet";
        } else {
            this.post_type = post_type;
        }
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }
}
