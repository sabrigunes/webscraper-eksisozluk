package com.sabrigunes.webscrapers.eksisozluk;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.image.AreaAveragingScaleFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Parser {

    public static URL getNextPageURL(URL url, Document document) {
        try{
            Element pager = document.select("div.pager").first();
            int current = Integer.parseInt(pager.attr("data-currentpage"));
            String result = url.toString();
            if(result.contains("p="))
                result = result.replace(String.format("p=%d", current), String.format("p=%d", ++current));
            else{
                result += url.toString().contains("?") ? "&p=" : "?p=";
                result += ++current;
            }
            return new URL(result.toString());
        }
        catch (MalformedURLException ex){
            throw new RuntimeException(ex.getMessage());
        }
        catch (Exception ex){
            return null;
        }
    }

    public static boolean isThereNextPage(Document document) {
        var element = document.select("div.pager");
        if (element.isEmpty())
            return false;
        int current = Integer.parseInt(element.first().attr("data-currentpage"));
        int count = Integer.parseInt(element.first().attr("data-pagecount"));
        return current < count;
    }

    public static String getTitle(Document document) {
        return document.getElementById("title").text();
    }

    public static ArrayList<Entry> parseEntriesForCurrentPage(Document document) {
        ArrayList<Entry> entries = new ArrayList<>();
        var list = document.getElementById("entry-item-list").getElementsByTag("li");
        for (var li : list) {
            var entry = parseEntry(li);
            entries.add(entry);
        }
        return entries;
    }

    private static Entry parseEntry(Element li) {
        Entry entry = new Entry();
        entry.setSubject(WebScraper.subjectUrl);
        entry.setEntryContent(li.getElementsByClass("content").text());
        entry.setEntryOwner(li.getElementById("entry-author").text());
        entry.setEntryTime(li.getElementsByClass("entry-date").text());
        entry.setEntryId(Integer.parseInt(li.attr("data-id")));
        parseLinks(entry,li.getElementsByClass("content").first());
        return entry;
    }

    private static void parseLinks(Entry entry,Element content){
        Elements elements = content.getElementsByTag("a");
        for (var element : elements){
            entry.addLink(element.attr("href"));
        }
    }

}
