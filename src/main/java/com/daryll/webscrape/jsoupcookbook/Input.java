package com.daryll.webscrape.jsoupcookbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class Input {

    public static void main(String[] args) throws IOException {

        //parseDocumentFromString();
        //parseBodyFragment();
        //loadDocumentFromURL();
        loadDocumentFromFile();
    }

    public static void parseDocumentFromString() {

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";

        Document doc = Jsoup.parse(html);

        System.out.println(doc.html());
    }

    public static void parseBodyFragment() {

        String html = "<div><p>Lorem ipsum.</p>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();

        System.out.println(body.html());
    }

    public static void loadDocumentFromURL() throws IOException {

        Document doc = Jsoup.connect("http://example.com/").get();
        String title = doc.title();
        System.out.println(title);

        Document doc2 = Jsoup.connect("http://example.com")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();

        System.out.println(doc2.title());
    }

    public static void loadDocumentFromFile() throws IOException {

        File input = new File("/Users/fst.user/Desktop/index.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        System.out.println(doc.title());
    }
}
