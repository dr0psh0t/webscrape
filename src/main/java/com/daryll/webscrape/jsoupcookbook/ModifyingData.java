package com.daryll.webscrape.jsoupcookbook;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class ModifyingData {

    public static void main(String[] args) {
        sanitizeUntrustedHTML();
    }

    public static void setAttributeValues() {
        // https://jsoup.org/cookbook/modifying-data/set-attributes
    }

    public static void setHTMLofElement() {

        /*
        Element div = doc.select("div").first(); // <div></div>
        div.html("<p>lorem ipsum</p>"); // <div><p>lorem ipsum</p></div>
        div.prepend("<p>First</p>");
        div.append("<p>Last</p>");
        // now: <div><p>First</p><p>lorem ipsum</p><p>Last</p></div>

        Element span = doc.select("span").first(); // <span>One</span>
        span.wrap("<li><a href='http://example.com/'></a></li>");
        // now: <li><a href="http://example.com"><span>One</span></a></li>
         */

        // https://jsoup.org/cookbook/modifying-data/set-html
    }

    public static void setContentOfElements() {

        /*
        Element div = doc.select("div").first(); // <div></div>
        div.text("five > four"); // <div>five &gt; four</div>
        div.prepend("First ");
        div.append(" Last");
        // now: <div>First five &gt; four Last</div>
         */

        //  https://jsoup.org/cookbook/modifying-data/set-text
    }

    public static void sanitizeUntrustedHTML() {

        String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        String safe = Jsoup.clean(unsafe, Safelist.basic());
        // now: <p><a href="http://example.com/" rel="nofollow">Link</a></p>

        System.out.println(safe);

        // https://jsoup.org/cookbook/cleaning-html/safelist-sanitizer
    }
}
