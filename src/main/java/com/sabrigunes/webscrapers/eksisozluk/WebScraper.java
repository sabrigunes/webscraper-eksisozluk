package com.sabrigunes.webscrapers.eksisozluk;

import com.sabrigunes.webscrapers.Requester;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WebScraper {
    public static String EKSI_URL = "https://eksisozluk1923.com/";
    private static final Requester requester;
    static URL subjectUrl;

    public static void main(String[] args) throws MalformedURLException {
        runForSitemap();
        System.exit(1);
        String url;
        if(args.length != 0)
            url = args[0];
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.print("URL giriniz:");
            url = scanner.nextLine();
        }
        runWithConsoleArgument(url);
    }

    static {
        requester = new Requester(-1); // TODO: 3L
    }

    private static void runWithConsoleArgument(String input) {
        try {
            URL url = new URL(input);
            runForSubject(url);
        } catch (MalformedURLException e) {
            System.err.println("Hatalı URL Girişi.");
        }
    }

    public static void runForSubject(String urlPostFix) throws MalformedURLException {
        subjectUrl = new URL(EKSI_URL + urlPostFix);
        runForSubject(subjectUrl);
    }

    public static void runForSubject(URL url) throws MalformedURLException {
        subjectUrl = url;
        Document document;
        ArrayList<Entry> entries = new ArrayList<>();

        do {
            document = requester.sendRequest(url);
            entries.addAll(Parser.parseEntriesForCurrentPage(document));
            url = Parser.getNextPageURL(url, document);
            if (entries.size() > 500){
                System.out.println("500 entry birikti. Veriler CSV dosyayına yazılıyor.");
                DataWriter.writeEntriesToCsv(entries);
                entries.clear();
            }

        }
        while (Parser.isThereNextPage(document));
        System.out.printf("%d entry toplandı. Veriler CSV dosyayına yazılıyor.%n", entries.size());
        DataWriter.writeEntriesToCsv(entries);
        System.out.printf("Veriler CSV dosyayına yazıldı.%n");
    }

    public static void runForSitemap() throws MalformedURLException {
        var document = requester.sendRequest(new URL(EKSI_URL + "/sitemap.xml"));
        DataWriter.writeSitemapToXml(document.toString());
        System.out.printf("Site haritası XML dosyayına yazıldı.%n");
    }
}
