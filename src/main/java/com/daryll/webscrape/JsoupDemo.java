package com.daryll.webscrape;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupDemo {

    public static final String blogUrl = "https://spring.io/blog";

    public static void main(String[] args) throws IOException {
        removingElements();
    }

    public static void loading() throws IOException {

        Document doc = Jsoup.connect("http://example.com").get();

        doc.select("p").forEach(System.out::println);
    }

    public static void detectAbnormalStatuses() throws IOException {

        try {
            Document doc404 = Jsoup.connect("https://spring.io/will-not-be-found").get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }
    }

    public static void customizeConnection() throws IOException {

        Connection connection = Jsoup.connect(blogUrl);

        connection.userAgent("Mozilla");
        connection.timeout(5000);
        connection.cookie("cookiename", "val234");
        connection.referrer("http://google.com");
        connection.header("headersecurity", "xyz123");

        Document docCustomConn = connection.get();

        /* or
        Document docCustomConn = Jsoup.connect(blogUrl)
            .userAgent("Mozilla")
            .timeout(5000)
            .cookie("cookiename", "val234")
            .cookie("anothercookie", "ilovejsoup")
            .referrer("http://google.com")
            .header("headersecurity", "xyz123")
            .get();
         */
    }

    public static void selecting() throws IOException {

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Elements links = doc.select("a");
        Elements sections = doc.select("section");
        Elements logo = doc.select(".spring-logo--container");
        Elements pagination = doc.select("#pagination_control");
        Elements divsDescendant = doc.select("header div");
        Elements divsDirect = doc.select("header > div");

        Element pag = doc.getElementById("pagination_control");
        Elements desktopOnly = doc.getElementsByClass("desktopOnly");
    }

    public static void traversing() throws IOException {

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Elements sections = doc.select("section");

        Element firstSection = sections.first();
        Element lastSection = sections.last();
        Element secondSection = sections.get(2);

        Elements allParents = firstSection.parents();
        Element parent = firstSection.parent();
        Elements children = firstSection.children();
        Elements siblings = firstSection.siblingElements();

        sections.forEach(el -> System.out.println("section: "+el));

        Elements sectionParagraphs = firstSection.select(".paragraph");
    }

    public static void extracting() throws IOException {

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Element firstArticle = doc.select("article").first();
        Element timeElement = firstArticle.select("time").first();
        String dateTimeOfFirstArticle = timeElement.attr("datetime");
        Element sectionDiv = firstArticle.select("section div").first();
        String sectionDivText = sectionDiv.text();
        String articleHtml = firstArticle.html();
        String outerHtml = firstArticle.outerHtml();
    }

    public static void settingAttributesAndInnerTextOrHtml() throws IOException {
        //  setting attributes and inner text/html

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Element firstArticle = doc.select("article").first();
        Element timeElement = firstArticle.select("time").first();
        Element sectionDiv = firstArticle.select("section div").first();

        System.out.println("before setting attribute and inner text/html:");
        System.out.println(timeElement.attr("datetime"));
        System.out.println(sectionDiv.html());
        System.out.println(firstArticle.select("h2").html());

        timeElement.attr("datetime", "2016-12-16 15:19:54.3");
        sectionDiv.text("foo bar");
        firstArticle.select("h2").html("<div><span></span></div>");

        System.out.println("\nafter setting attribute and inner text/html:");
        System.out.println(timeElement.attr("datetime"));
        System.out.println(sectionDiv.html());
        System.out.println(firstArticle.select("h2").html());
    }

    public static void creatingAndAppendingElements() throws IOException {
        //  creating and appending elements

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Element firstArticle = doc.select("article").first();

        Element link = new Element(Tag.valueOf("a"), "")
                .text("Checkout this amazing website!")
                .attr("href", "http://baeldung.com")
                .attr("target", "_blank");

        firstArticle.appendChild(link);

        System.out.println(firstArticle.html());
    }

    public static void removingElements() throws IOException {

        Document doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();

        Element firstArticle = doc.select("article").first();

        //System.out.println(doc.html());

        doc.select("li.blog-category").remove();
        firstArticle.select("img").remove();

        String docHtml = doc.html();

        System.out.println(docHtml);
    }
}

//  https://www.baeldung.com/java-with-jsoup