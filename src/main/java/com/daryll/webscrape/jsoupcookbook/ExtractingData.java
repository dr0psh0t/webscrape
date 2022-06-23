package com.daryll.webscrape.jsoupcookbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ExtractingData {

    public static void main(String[] args) throws IOException {
        //extractAttributesTextAndHTMLFromElements();
        workingWithURLs();
    }

    public static void navigateTheDocument() throws IOException {

        File input = new File("/tmp/input.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        Element content = doc.getElementById("content");
        Elements links = content.getElementsByTag("a");

        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
        }

        //https://jsoup.org/cookbook/extracting-data/dom-navigation
    }

    public static void selectorSyntaxToFindElements() throws IOException {

        File input = new File("/tmp/input.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

        Elements links = doc.select("a[href]"); // a with href
        Elements pngs = doc.select("img[src$=.png]");
        // img with src ending .png

        Element masthead = doc.select("div.masthead").first();
        // div with class=masthead

        Elements resultLinks = doc.select("h3.r > a"); // direct a after h3

        //  https://jsoup.org/cookbook/extracting-data/selector-syntax
    }

    public static void extractAttributesTextAndHTMLFromElements() {

        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
        Document doc = Jsoup.parse(html);
        Element link = doc.select("a").first();

        String text = doc.body().text(); // "An example link"
        String linkHref = link.attr("href"); // "http://example.com/"
        String linkText = link.text(); // "example""

        String linkOuterH = link.outerHtml();
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"

        //https://jsoup.org/cookbook/extracting-data/attributes-text-html
    }

    public static void workingWithURLs() throws IOException {

        Document doc = Jsoup.connect("http://jsoup.org").get();

        Element link = doc.select("a").first();
        String relHref = link.attr("href"); // == "/"
        String absHref = link.attr("abs:href"); // "http://jsoup.org/"

        System.out.println(relHref);
        System.out.println(absHref);

        //  https://jsoup.org/cookbook/extracting-data/working-with-urls
    }
}
