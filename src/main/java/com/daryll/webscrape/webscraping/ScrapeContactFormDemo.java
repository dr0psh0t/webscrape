package com.daryll.webscrape.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class ScrapeContactFormDemo {

    public static void main(String[] args) throws IOException {

        MyClient myClient = new MyClient.MyClientBuilder()
                .companyName("Apple Inc")
                .address("California")
                .contactNumber("0987654321")
                .email("apple@gmail.com")
                .firstname("Steve")
                .lastname("Jobs")
                .subject("Inquiry")
                .message("Greetings From Apple Inc! I have some inquiry regarding your product.")
                .build();

        Company company = new Company("HM Tower", "https://www.hmtower.com/");

        //System.out.println(myClient);
        //System.out.println(company);

        test();
    }

    public static void test() throws IOException {
        Document document = Jsoup.connect("https://www.hmtower.com/").get();



        Element contactForm = document.selectFirst("form");

        System.out.println(contactForm.outerHtml());
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

        private MyClient(MyClientBuilder builder) {
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

        public static class MyClientBuilder {

            private String companyName;
            private String subject;
            private String message;
            private String address;
            private String contactNumber;
            private String firstname;
            private String lastname;
            private String email;

            public MyClientBuilder companyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            public MyClientBuilder subject(String subject) {
                this.subject = subject;
                return this;
            }

            public MyClientBuilder message(String message) {
                this.message = message;
                return this;
            }

            public MyClientBuilder address(String address) {
                this.address = address;
                return this;
            }

            public MyClientBuilder contactNumber(String contactNumber) {
                this.contactNumber = contactNumber;
                return this;
            }

            public MyClientBuilder firstname(String firstname) {
                this.firstname = firstname;
                return this;
            }

            public MyClientBuilder lastname(String lastname) {
                this.lastname = lastname;
                return this;
            }

            public MyClientBuilder email(String email) {
                this.email = email;
                return this;
            }

            public MyClient build() {
                return new MyClient(this);
            }
        }
    }
}
