package com.sabrigunes.webscrapers.eksisozluk;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Entry {
    private int entryId;
    private String subject;
    private LocalDateTime entryTime;
    private String entryOwner;
    private String entryContent;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    private ArrayList<String> links = new ArrayList<>();

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subjectUrl) throws MalformedURLException {
        setSubject(new URL(subject));
    }

    public void setSubject(URL url) {
        this.subject = url.getPath();
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public void setEntryTime(String text) {
        if (text.contains("~"))
            text = text.split("~")[0].trim();
        if (!text.contains(":"))
            text += " 00:00";
        setEntryTime(LocalDateTime.parse(text, DATE_TIME_FORMATTER));
    }

    public String getEntryOwner() {
        return entryOwner;
    }

    public void setEntryOwner(String entryOwner) {
        this.entryOwner = entryOwner;
    }

    public String getEntryContent() {
        return entryContent;
    }

    public void setEntryContent(String entryContent) {
        this.entryContent = entryContent;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void addLink(String link) {
        try{
            links.add(URLDecoder.decode(link, StandardCharsets.UTF_8));
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String toString() {
        return String.format("\"%s\",\"%d\",\"%s\",\"%s\",\"%s\"%n", subject, entryId, entryTime, entryOwner, entryContent);
    }



}
