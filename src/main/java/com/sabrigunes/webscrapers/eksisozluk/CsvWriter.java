package com.sabrigunes.webscrapers.eksisozluk;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CsvWriter {
    private static final String FILENAME = "output.csv";

    public static void main(String[] args) {


    }

    public static void writeToCsv(ArrayList<Entry> entries) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILENAME, true)) {
            for (Entry entry : entries)
                fileOutputStream.write(entry.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToCsv(Entry entry) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILENAME, true)) {
            fileOutputStream.write(entry.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}