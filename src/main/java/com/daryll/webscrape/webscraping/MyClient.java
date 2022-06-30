package com.daryll.webscrape.webscraping;

public class MyClient {

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