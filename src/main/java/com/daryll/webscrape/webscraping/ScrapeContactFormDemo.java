package com.daryll.webscrape.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScrapeContactFormDemo {
    public static List<String> URLS = Arrays.asList(
            "https://aboitiz.com/contact-us/", "https://www.baihotels.com/contact-us",
            "https://www.fullspeedtechnologies.com/contact", "https://www.hmtower.com/", "https://www.tesla.com/contact",
            "https://www.waterfronthotels.com.ph/wcch_contact/", "https://www.wellmade-motors.com/contact-us/",
            "https://www.bluewatermaribago.com.ph/contact-us", "https://www.crownregency.com/contact");
    public static Map<String, String> FILES = Map.ofEntries(
            Map.entry("aboitiz", "aboitiz.html"), Map.entry("baihotel", "baihotel.html"),
            Map.entry("fst", "fst.html"), Map.entry("hmtower", "hmtower.html"),
            Map.entry("tesla", "tesla.html"), Map.entry("waterfront", "waterfront.html"),
            Map.entry("bluewater", "bluewater.html"), Map.entry("wellmade", "wellmade.html"),
            Map.entry("crownregency", "crownregency.html")
    );
    static String company = "aboitiz";
    public static void determineContactForm(Document document, MyClient client) {
        Element textarea = document.selectFirst("form textarea");

        if (textarea != null) {
            Element contactForm = textarea.parent();

            while (!contactForm.nodeName().equals("form")) {
                contactForm = contactForm.parent();
            }

            scrapeForm(contactForm, client);
        } else {
            for (Element form : document.select("form")) {
                Elements labels = form.select("label");

                if (labels.text().toLowerCase().contains("email") || labels.text().toLowerCase().contains("e-mail")) {
                    scrapeForm(form, client);  //  form has label email. scrape form
                    break;
                } else {
                    if (form.selectFirst("input[type=email]") != null) {
                        scrapeForm(form, client);  //  form has no label email. scrape form
                        break;
                    } else {
                        Elements textInputs = form.select("input[type=text]");
                        boolean emailFound = false;

                        for (Element textInput : textInputs) {

                            String attributes = textInput.attr("name")+textInput.attr("id")
                                    +textInput.attr("placeholder").toLowerCase()
                                    .replaceAll("[^a-zA-Z]", "")
                                    .replaceAll("\\s+","");

                            if (attributes.contains("email")) {
                                emailFound = true;
                                break;
                            }
                        }

                        if (emailFound) {
                            scrapeForm(form, client);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void scrapeForm(Element form, MyClient client) {

        Elements inputsUnderForm = form.select("input");
        Elements labels = form.select("label");

        if (!labels.isEmpty()) {
            inputsUnderForm.forEach(eachInput -> {
                if (isUserInput(eachInput)) {
                    Element parent = eachInput.parent();
                    Element label = null;

                    while (label == null) {
                        label = parent.selectFirst("label");
                        if (label == null) { parent = parent.parent(); }
                    }

                    fillInputs(eachInput, label.text(), client);
                }
            });
        } else {
            inputsUnderForm.forEach(eachInput -> {
                if (isUserInput(eachInput)) {
                    fillInputs(eachInput, eachInput.attr("placeholder"), client);
                }
            });

        }

        Element txtarea = form.selectFirst("textarea");
        if (txtarea != null) {
            txtarea.text(client.getMessage());
        }

        Document document = Jsoup.parse(form.parents().select("html").first().html());
        System.out.println(document);
    }

    public static boolean isUserInput(Element in) {
        return !in.attr("type").equals("hidden")
                && !in.attr("type").equals("submit") && !in.attr("type").equals("button");
    }

    public static void fillInputs(Element eachInput, String lblPlaceholder, MyClient client) {
        lblPlaceholder = lblPlaceholder.toLowerCase()
                .replaceAll("[^a-zA-Z]", "").replaceAll("\\s+","");

        if ("firstname".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getFirstname());
        } else if ("lastname".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getLastname());
        } else if ("companyname".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getCompanyName());
        } else if ("subject".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getSubject());
        } else if ("address".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getAddress());
        } else if ("emailaddress".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getEmail());
        } else if ("phonenumbercontactnumbercontactno".contains(lblPlaceholder)) {
            eachInput.attr("value", client.getContactNumber());
        }
    }

    public static void main(String[] args) throws IOException {
        MyClient myClient = new MyClient.Builder()
                .companyName("Apple Inc")
                .address("California")
                .contactNumber("0987654321")
                .email("apple@gmail.com")
                .firstname("Steve")
                .lastname("Jobs")
                .subject("Inquiry")
                .message("Greetings From Apple Inc! I have some inquiry regarding your product.")
                .build();

        test(myClient);
    }


    public static void test(MyClient client) throws IOException {
        String url = "/Users/fst.user/Documents/springboot-apps/webscrape/src/main/java/com/daryll/webscrape/contactus/";
        //String url = "D:\\daryll\\SpringBoot\\springbootapps\\webscrape\\src\\main\\java\\com\\daryll\\webscrape\\contactus\\";
        determineContactForm(Jsoup.parse(new File(url+FILES.get(company))), client);
    }
}
