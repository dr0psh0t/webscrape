package com.daryll.webscrape.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    static String company = "tesla";
    public static void determineContactForm(Document document, MyClient client) {
        Element textarea = document.selectFirst("form textarea");

        if (textarea != null) {
            Element contactForm = textarea.parent();
            while (!contactForm.nodeName().equals("form")) { contactForm = contactForm.parent(); }
            scrapeForm(contactForm, client);
        }
    }

    public static void scrapeForm(Element form, MyClient client) {
        Elements inputsUnderForm = form.select("input");

        Elements labels = form.select("label");
        Element txtarea = form.selectFirst("textarea");
        txtarea.text(client.getMessage());

        if (!labels.isEmpty()) {
            inputsUnderForm.forEach(eachInput -> {
                /*
                <div>
                    <label>First Name </label>
                    <div>
                        <input type="text" id="edit-firstname" name="firstname" value="" size="60" maxlength="50">
                    </div>
                </div>
                <div>
                    <label>Last Name </label>
                    <div>
                        <input type="text" id="edit-lastname" name="lastname" value="" size="60" maxlength="50">
                    </div>
                </div>
                 */
                boolean isUserInput = !eachInput.attr("type").equals("hidden")
                        && !eachInput.attr("type").equals("submit")
                        && !eachInput.attr("type").equals("button");

                if (isUserInput) {

                    Element parent = eachInput.parent();
                    Element label = null;

                    while (label == null) {
                        label = parent.selectFirst("label");
                        if (label == null) { parent = parent.parent(); }
                    }

                    System.out.println("label="+label);
                }
            });
        }

        /*
        inputsUnderForm.forEach(eachInput -> {

            if (!labels.isEmpty()) {
                Elements siblingsOfInput = eachInput.siblingElements();

                siblingsOfInput.forEach(sibling -> {

                    if (sibling.nodeName().equalsIgnoreCase("label")) {
                        String lblTxt = sibling.text().toLowerCase()
                                .replaceAll("[^a-zA-Z]", "")
                                .replaceAll("\\s+","");

                        if ("firstname".contains(lblTxt)) {
                            eachInput.attr("value", client.getFirstname());
                        } else if ("lastname".contains(lblTxt)) {
                            eachInput.attr("value", client.getLastname());
                        } else if ("companyname".contains(lblTxt)) {
                            eachInput.attr("value", client.getCompanyName());
                        } else if ("subject".contains(lblTxt)) {
                            eachInput.attr("value", client.getSubject());
                        } else if ("address".contains(lblTxt)) {
                            eachInput.attr("value", client.getAddress());
                        } else if ("emailaddress".contains(lblTxt)) {
                            eachInput.attr("value", client.getEmail());
                        } else if ("phonenumbercontactnumber".contains(lblTxt)) {
                            eachInput.attr("value", client.getContactNumber());
                        }
                    }
                });
            } else {

                boolean stat = !eachInput.attr("type").equals("hidden")
                        && !eachInput.attr("type").equals("submit")
                        && !eachInput.attr("type").equals("button");

                if (stat) {

                    String placeholder = eachInput.attr("placeholder").toLowerCase()
                            .replaceAll("[^a-zA-Z]", "").replaceAll("\\s+","");

                    if ("firstname".contains(placeholder)) {
                        eachInput.attr("value", client.getFirstname());
                    } else if ("lastname".contains(placeholder)) {
                        eachInput.attr("value", client.getLastname());
                    } else if ("companyname".contains(placeholder)) {
                        eachInput.attr("value", client.getCompanyName());
                    } else if ("subject".contains(placeholder)) {
                        eachInput.attr("value", client.getSubject());
                    } else if ("address".contains(placeholder)) {
                        eachInput.attr("value", client.getAddress());
                    } else if ("emailaddress".contains(placeholder)) {
                        eachInput.attr("value", client.getEmail());
                    } else if ("phonenumbercontactnumbercontactno".contains(placeholder)) {
                        eachInput.attr("value", client.getContactNumber());
                    }
                }
            }
        });

        System.out.println(form);
         */
    }

    public static void test(MyClient client) throws IOException {
        //String url = "/Users/fst.user/Documents/springboot-apps/webscrape/src/main/java/com/daryll/webscrape/contactus/";
        String url = "D:\\daryll\\SpringBoot\\springbootapps\\webscrape\\src\\main\\java\\com\\daryll\\webscrape\\contactus\\";
        determineContactForm(Jsoup.parse(new File(url+FILES.get(company))), client);
    }

    public List<Document> scrapeContactForm(MyClient client, List<Company> companies) throws IOException {

        Document document = Jsoup.connect("https://www.hmtower.com/").get();

        System.out.println(document.html());

        return null;
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

    public static class Company {
        private final String companyName;
        private final String website;

        public Company(String companyName, String website) {
            this.companyName = companyName;
            this.website = website;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getWebsite() {
            return website;
        }

        @Override
        public String toString() {
            return "Company{" +
                    "companyName='" + companyName + '\'' +
                    ", website='" + website + '\'' +
                    '}';
        }
    }

    public static class MyClient {

        private final String companyName;
        private final String subject;
        private final String message;
        private final String address;
        private final String contactNumber;
        private final String firstname;
        private final String lastname;
        private final String email;

        private MyClient(Builder builder) {
            this.companyName = builder.companyName;
            this.subject = builder.subject;
            this.message = builder.message;
            this.address = builder.address;
            this.contactNumber = builder.contactNumber;
            this.firstname = builder.firstname;
            this.lastname = builder.lastname;
            this.email = builder.email;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getAddress() {
            return address;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getMessage() {
            return message;
        }

        public String getSubject() {
            return subject;
        }

        @Override
        public String toString() {
            return "MyClient{" +
                    "companyName='" + companyName + '\'' +
                    ", subject='" + subject + '\'' +
                    ", message='" + message + '\'' +
                    ", address='" + address + '\'' +
                    ", contactNumber='" + contactNumber + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }

        public static class Builder {

            private String companyName;
            private String subject;
            private String message;
            private String address;
            private String contactNumber;
            private String firstname;
            private String lastname;
            private String email;

            public Builder companyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            public Builder subject(String subject) {
                this.subject = subject;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public Builder address(String address) {
                this.address = address;
                return this;
            }

            public Builder contactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
                return this;
            }

            public Builder firstname(String firstname) {
                this.firstname = firstname;
                return this;
            }

            public Builder lastname(String lastname) {
                this.lastname = lastname;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public MyClient build() {
                return new MyClient(this);
            }
        }
    }
}
