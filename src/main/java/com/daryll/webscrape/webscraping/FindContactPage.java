package com.daryll.webscrape.webscraping;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FindContactPage {
    public static List<String> URLS = Arrays.asList(
            "https://aboitiz.com/",
            "https://www.baihotels.com/",
            "https://www.fullspeedtechnologies.com/",
            "https://www.hmtower.com/",
            "https://www.tesla.com/",
            "https://www.waterfronthotels.com.ph/",
            "https://www.wellmade-motors.com/",
            "https://www.bluewatermaribago.com.ph/",
            "https://www.crownregency.com/");

    public static void main(String[] args) throws IOException {

        URLS.forEach(site -> {
            try {
                System.out.println("SITE="+site);

                Document document = Jsoup.connect(site).timeout(30000).get();
                Element a1 = document.selectFirst("a:contains(contact us)");    //  doc.select("div:contains(Pantry/Catering)").get(1)

                if (a1 != null) {
                    String a1Href = a1.attr("href");

                    if (new UrlValidator().isValid(a1Href)) {
                        System.out.println("found contact page at "+a1Href);
                    } else {
                        System.out.println("found contact page at "+site+a1Href.replaceAll("[^a-zA-Z]", ""));
                    }

                } else {
                    Element a2 = document.selectFirst("a:contains(contact)");

                    if (a2 != null) {
                        String a2Href = a2.attr("href");

                        if (new UrlValidator().isValid(a2Href)) {
                            System.out.println("found contact page at "+a2Href);
                        } else {
                            System.out.println("found contact page at "+site+a2Href.replaceAll("[^a-zA-Z]", ""));
                        }

                    } else {

                        if (isContactFormFound(document)) {
                            //  scrape form
                            System.out.println("found contact page at "+site);
                        } else {
                            for (String eachUrl : List.of("contact-us", "contact", "Contact-Us", "Contact",
                                    "CONTACT-US", "CONTACT")) {
                                try {
                                    Document doc404 = Jsoup.connect(site+eachUrl).get();
                                    System.out.println("found contact page at "+site+eachUrl);
                                    break;
                                } catch (HttpStatusException ex) { }
                            }
                        }
                    }
                }

                System.out.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
                    if (form.selectFirst("input[type=email]") != null
                            && form.selectFirst("input[type=number]") != null) {
                        //System.out.println("fuck");
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
