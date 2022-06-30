package com.daryll.webscrape.webscraping;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Final {

    static MyClient myClient = new MyClient.Builder()
            .companyName("Apple Inc")
            .address("California")
            .contactNumber("0987654321")
            .email("apple@gmail.com")
            .firstname("Steve")
            .lastname("Jobs")
            .subject("Inquiry")
            .message("Greetings From Apple Inc! I have some inquiry regarding your product.")
            .build();

    static Set<Company> companies = Set.of(
            new Company("aboitiz", "https://aboitiz.com/"),
                new Company("baihotel", "https://www.baihotels.com/"),
                new Company("fullspeed", "https://www.fullspeedtechnologies.com/"),
                new Company("hmtower", "https://www.hmtower.com/"),
                new Company("tesla", "https://www.tesla.com/"),
                //new Company("waterfront", "https://www.waterfronthotels.com.ph/"),
                new Company("wellmade", "https://www.wellmade-motors.com/"),
                new Company("bluewater", "https://www.bluewatermaribago.com.ph/"),
                new Company("crownregency", "https://www.crownregency.com/")
    );

    public static void main(String[] args) throws IOException {
        Set<Document> set = companies.stream().map(company -> {
            try {
                Document document = Jsoup.connect(company.getWebsite()).timeout(30000).get();
                Element a1 = document.selectFirst("a:contains(contact us)");    //  doc.select("div:contains(Pantry/Catering)").get(1)

                if (a1 != null) {
                    return scrapeLink(a1.attr("href"), company.getWebsite(), myClient);
                } else {
                    Element a2 = document.selectFirst("a:contains(contact)");

                    if (a2 != null) {
                        return scrapeLink(a2.attr("href"), company.getWebsite(), myClient);
                    } else {
                        Document theForm = determineContactForm(document, myClient);

                        if (!theForm.select("body").text().equals("IOException")) {
                            return theForm;
                        } else {
                            for (String eachUrl : List.of("contact-us", "contact", "Contact-Us", "Contact",
                                    "CONTACT-US", "CONTACT")) {
                                try {
                                    Document doc404 = Jsoup.connect(company.getWebsite()+eachUrl).get();
                                    return determineContactForm(doc404, myClient);

                                } catch (HttpStatusException ex) {}
                            }
                            return new Document("<html><head></head><body>IOException</body></html>");
                        }
                    }
                }

            } catch (IOException e) {
                return new Document("<html><head></head><body>IOException</body></html>");
            }
        }).collect(Collectors.toSet());

        set.forEach(document -> System.out.println(document.select("form")+"\n"));
    }

    public static Document scrapeLink(String href, String site, MyClient client) throws IOException {
        if (new UrlValidator().isValid(href)) {
            return determineContactForm(Jsoup.connect(href).timeout(30000).get(), client);
        } else {
            return determineContactForm(Jsoup.connect(site+href.replaceAll("[^a-zA-Z]", "")).timeout(30000).get(), client);
        }
    }

    public static Document determineContactForm(Document document, MyClient client) {
        Element textarea = document.selectFirst("form textarea");

        if (textarea != null) {
            Element contactForm = textarea.parent();

            while (!contactForm.nodeName().equals("form")) {
                contactForm = contactForm.parent();
            }

            return scrapeForm(contactForm, client);
        } else {
            for (Element form : document.select("form")) {
                Elements labels = form.select("label");

                if (labels.text().toLowerCase().contains("email") || labels.text().toLowerCase().contains("e-mail")) {
                    return scrapeForm(form, client);  //  form has label email. scrape form
                } else {
                    if (form.selectFirst("input[type=email]") != null) {
                        return scrapeForm(form, client);  //  form has no label email. scrape form
                    } else {
                        Elements textInputs = form.select("input[type=text]");

                        for (Element textInput : textInputs) {

                            String attributes = textInput.attr("name")+textInput.attr("id")
                                    +textInput.attr("placeholder").toLowerCase()
                                    .replaceAll("[^a-zA-Z]", "")
                                    .replaceAll("\\s+","");

                            if (attributes.contains("email")) {
                                return scrapeForm(form, client);
                            }
                        }
                    }
                }
            }
            return new Document("<html><head></head><body>IOException</body></html>");
        }
    }

    public static Document scrapeForm(Element form, MyClient client) {

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

        return Jsoup.parse(form.parents().select("html").first().html());
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
}
