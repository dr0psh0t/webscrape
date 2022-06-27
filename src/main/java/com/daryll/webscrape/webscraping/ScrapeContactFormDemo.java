package com.daryll.webscrape.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScrapeContactFormDemo {

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

        /*Company company = new Company("HM Tower", "https://www.hmtower.com/");*/

        //System.out.println(myClient);
        //System.out.println(company);

        test(myClient);
    }

    public static void scrapeForm(Elements forms, MyClient client) {
        for (Element form : forms) {
            if (form.attr("method").equalsIgnoreCase("post")) {
                Elements inputsUnderForm = form.select("input");
                Elements labels = form.select("label");

                inputsUnderForm.forEach(eachInput -> {

                    if (!labels.isEmpty()) {
                        Elements siblingsOfInput = eachInput.siblingElements();

                        siblingsOfInput.forEach(sibling -> {
                            if (sibling.nodeName().equalsIgnoreCase("label")) {

                                String lblTxt = sibling.text()
                                        .toLowerCase()
                                        .replaceAll("[^a-zA-Z]", "")
                                        .replaceAll("\\s+","");
                                System.out.println(lblTxt);

                                /*
                                if (lblTxt.equalsIgnoreCase("first")
                                        || lblTxt.equalsIgnoreCase("firstname")) {
                                    System.out.println(eachInput.attr("value", client.getFirstname()));
                                } else if (lblTxt.equalsIgnoreCase("last")
                                        || lblTxt.equalsIgnoreCase("lastname")) {
                                    System.out.println(eachInput.attr("value", client.getLastname()));
                                } else if (lblTxt.equalsIgnoreCase("company")
                                        || lblTxt.equalsIgnoreCase("companyname")) {
                                    System.out.println(eachInput.attr("value", client.getCompanyName()));
                                } else if (lblTxt.equalsIgnoreCase("subject")) {
                                    System.out.println(eachInput.attr("value", client.getSubject()));
                                } else if (lblTxt.equalsIgnoreCase("message")
                                        || lblTxt.equalsIgnoreCase("comment")
                                        || lblTxt.equalsIgnoreCase("commentormessage")
                                        || lblTxt.equalsIgnoreCase("messageorcomment")) {
                                    System.out.println(eachInput.attr("value", client.getMessage()));
                                } else if (lblTxt.equalsIgnoreCase("address")) {
                                    System.out.println(eachInput.attr("value", client.getAddress()));
                                } else if (lblTxt.equalsIgnoreCase("email")
                                        || lblTxt.equalsIgnoreCase("emailaddress")) {
                                    System.out.println(eachInput.attr("value", client.getEmail()));
                                } else if (lblTxt.equalsIgnoreCase("phone")
                                        || lblTxt.equalsIgnoreCase("phonenumber")
                                        || lblTxt.equalsIgnoreCase("contact")
                                        || lblTxt.equalsIgnoreCase("contactnumber")) {
                                    System.out.println(eachInput.attr("value", client.getContactNumber()));
                                }*/
                            }
                        });
                    } else {
                        System.out.println(eachInput.attr("value", "TheValue_2!"));
                    }
                });

                break;
            }
        }
    }

    //public static void process

    public static void test(MyClient client) throws IOException {

        //String url = "https://aboitiz.com/contact-us/";
        //String url = "https://www.baihotels.com/contact-us";
        //String url = "https://www.fullspeedtechnologies.com/contact";
        //String url = "https://www.hmtower.com/";
        //String url = "https://www.tesla.com/contact";
        //String url = "https://www.waterfronthotels.com.ph/wcch_contact/";
        //String url = "https://www.wellmade-motors.com/contact-us/";
        //Document document = Jsoup.connect(url).get();

        //Element contactForm = document.selectFirst("form");
        //System.out.println(contactForm.outerHtml());

        //String file = "aboitiz.html";
        //String file = "baihotel.html";
        //String file = "fst.html";
        //String file = "hmtower.html";
        //String file = "tesla.html";
        String file = "waterfront.html";
        //String file = "wellmade.html";
        String url = "/Users/fst.user/Documents/springboot-apps/webscrape/src/main/java/com/daryll/webscrape/contactus/";
        Document document = Jsoup.parse(new File(url+file));
        Elements form = document.select("form");
        scrapeForm(form, client);
        //System.out.println(form);
        //System.out.println(form.size());

        /*
        Elements inputsUnderForm = form.select("input");
        //System.out.println(inputsUnderForm);
        //System.out.println(inputsUnderForm.size());
        inputsUnderForm.forEach(e -> {
            Elements siblingsOfInput = e.siblingElements();

            siblingsOfInput.forEach(sibling -> {
                System.out.println(sibling.nodeName());
                System.out.println(sibling);
            });

            //System.out.println(e.siblingElements());
        });

        Elements em = form.select("em");
        //System.out.println(em);
        //System.out.println(em.size());

        Elements buttonUnderForm = form.select("button");
        //System.out.println(buttonUnderForm);
        //System.out.println(buttonUnderForm.size());
         */
    }

    public List<Document> scrapeContactForm(MyClient client, List<Company> companies) throws IOException {

        Document document = Jsoup.connect("https://www.hmtower.com/").get();

        System.out.println(document.html());

        return null;
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
