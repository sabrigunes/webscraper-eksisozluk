package com.sabrigunes.webscrapers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class Requester {

    private boolean isWorkWithDelay;
    private LocalDateTime lastRequestAt;
    private double requestDelay;
    private int minuteForRequestCounter;
    private int requestCountInAMinute;
    private int maxRequestAtAMinute;

    public Requester(long requestDelay) {
        setRequestDelay(requestDelay);
        isWorkWithDelay = true;
        lastRequestAt = LocalDateTime.now();
    }

    public Requester(int maxRequestAtAMinute) {
        setMaxRequestAtAMinute(maxRequestAtAMinute);
        lastRequestAt = LocalDateTime.now();
    }

    public double getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(double requestDelay) {
        if (requestDelay < -1)
            throw new IllegalArgumentException();
        this.requestDelay = requestDelay <= 0 ? -1 : requestDelay;
    }

    public int getMaxRequestAtAMinute() {
        return maxRequestAtAMinute;
    }

    public void setMaxRequestAtAMinute(int maxRequestAtAMinute) {
        if (maxRequestAtAMinute < -1)
            throw new IllegalArgumentException();
        this.maxRequestAtAMinute = maxRequestAtAMinute == 0 ? -1 : maxRequestAtAMinute;
    }

    public Document sendRequest(URL url){
        return sendRequest(url, RequestType.GET);
    }

    public Document sendRequest(URL url, RequestType requestType){
        return sendRequest(url, requestType, new String[] {}); //TODO: write the code for the parameterized version
    }

    //TODO: write the code for the parameterized version
    private Document sendRequest(URL url, RequestType requestType, String...params) {
        try{
            double delay = getRemainingDelayTime();
            if(delay != 0){
                System.out.printf("Yeni istek göndermeden önce %f saniye bekleniyor.", delay);
                Thread.sleep((long)(delay * 1000));
            }
            Document document;
            // TODO: check request type
            document = Jsoup.connect(url.toString()).get();
            return document;
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }


    }

    private double getRemainingDelayTime() {
        if (requestDelay == -1 || maxRequestAtAMinute == -1)
            return 0.;
        else if (isWorkWithDelay) {
            return ChronoUnit.SECONDS.between(LocalDateTime.now(), lastRequestAt);
        } else {
            if (LocalDateTime.now().getMinute() == minuteForRequestCounter) {
                if (minuteForRequestCounter > maxRequestAtAMinute)
                    return 60 - LocalDateTime.now().getMinute();
                else {
                    requestCountInAMinute += 1;
                    return 0.;
                }
            } else {
                minuteForRequestCounter = LocalDateTime.now().getMinute();
                requestCountInAMinute = 1;
                return 0.;
            }
        }
    }


}
