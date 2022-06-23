package com.daryll.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupDemo2 {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://kensoftph.com").get();

        System.out.println("Contents from: "+doc.title());

        doc.getElementsByTag("h3").forEach(element -> {
            System.out.println(element.text());

        });
    }
}
