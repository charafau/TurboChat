package com.nullpointerbay.turbochat.model;

import java.util.List;

/**
 * Created by charafau on 2017/02/10.
 */

public class Message {

    private long id;
    private String text;
    private List<String> mentions;
    private List<String> emoticons;
    private List<Link> links;

    public Message(long id, String text, List<String> mentions, List<String> emoticons, List<Link> links) {
        this.id = id;
        this.text = text;
        this.mentions = mentions;
        this.emoticons = emoticons;
        this.links = links;
    }

    public Message(String text, List<String> mentions, List<String> emoticons, List<Link> links) {
        this.text = text;
        this.mentions = mentions;
        this.emoticons = emoticons;
        this.links = links;
    }

    public String getText() {
        return text;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public List<String> getEmoticons() {
        return emoticons;
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (mentions != null ? !mentions.equals(message.mentions) : message.mentions != null)
            return false;
        if (emoticons != null ? !emoticons.equals(message.emoticons) : message.emoticons != null)
            return false;
        return links != null ? links.equals(message.links) : message.links == null;

    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (mentions != null ? mentions.hashCode() : 0);
        result = 31 * result + (emoticons != null ? emoticons.hashCode() : 0);
        result = 31 * result + (links != null ? links.hashCode() : 0);
        return result;
    }
}
