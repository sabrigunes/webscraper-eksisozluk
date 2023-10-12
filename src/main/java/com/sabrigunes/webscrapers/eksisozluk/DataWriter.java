package com.sabrigunes.webscrapers.eksisozluk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter {
    private static final String OUTPUT_CSV = "output.csv";
    private static final String OUTPUT_SITEMAP = "sitemap.xml";

    public static void main(String[] args) {


    }

    public static void writeEntriesToCsv(ArrayList<Entry> entries) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_CSV, true)) {
            for (Entry entry : entries)
                fileOutputStream.write(entry.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeEntriesToCsv(Entry entry) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_CSV, true)) {
            fileOutputStream.write(entry.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeSitemapToXml(String sitemap){
        try (FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_SITEMAP)) {
            fileOutputStream.write(sitemap.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}