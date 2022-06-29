package com.daryll.webscrape.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

public class FindContactPage {

    public static void main(String[] args) throws IOException {

        Document document;
        boolean found = false;
        String url = "https://www.waterfronthotels.com.ph/";

        while (!found) {
            document = Jsoup.connect(url).get();
            Element a = document.selectFirst("a:contains(Contact)");    //  doc.select("div:contains(Pantry/Catering)").get(1)

            if (a != null) {
                url = a.attr("href");
                found = true;
            }
        }

        System.out.println(url);

        /*
        long s = System.currentTimeMillis();
        document = Jsoup.connect(url).get();
        Elements links = document.select("a");
        Optional<Element> a = links
                .stream()
                .filter(link -> "contactus".contains(link.text().toLowerCase()))
                .findFirst();
        System.out.println(a.get());
        System.out.println(System.currentTimeMillis()-s);
         */

        /*
        long s = System.currentTimeMillis();
        document = Jsoup.connect(url).get();
        Element a = document.selectFirst("a:contains(Contact)");
        System.out.println(a);
        System.out.println(System.currentTimeMillis()-s);
         */
    }

    public static boolean isContactFormFound(Document document) {
        Element textarea = document.selectFirst("form textarea");

        if (textarea != null) {
            Element contactForm = textarea.parent();

            while (!contactForm.nodeName().equals("form")) {
                contactForm = contactForm.parent();
            }

            return contactForm.nodeName().equals("form");
        } else {
            for (Element form : document.select("form")) {
                Elements labels = form.select("label");

                if (labels.text().toLowerCase().contains("email") || labels.text().toLowerCase().contains("e-mail")) {
                    return true;
                } else {
                    if (form.selectFirst("input[type=email]") != null) {
                        return true;
                    } else {

                        for (Element textInput : form.select("input[type=text]")) {
                            String attributes = textInput.attr("name")+textInput.attr("id")
                                    +textInput.attr("placeholder").toLowerCase()
                                    .replaceAll("[^a-zA-Z]", "")
                                    .replaceAll("\\s+","");

                            if (attributes.contains("email")) { return true; }
                        }
                    }
                }
            }
        }

        return false;
    }
}
