package com.daryll.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class IdentifyCorrectForm {

    public static final Set<String> INPUT_TYPES = Set.of(
            "button", "checkbox", "color", "date", "datetime-local", "email", "file", "hidden", "image",
            "month", "number", "password", "radio", "range", "reset", "search", "submit", "tel", "text",
            "time", "url", "week"
    );

    public static final Set<String> METHODS = Set.of("POST", "post", "GET", "get");

    public static final Set<String> ENCODING_TYPE = Set.of(
            "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"
    );

    public static final File INPUT = new File("/Users/fst.user/Desktop/correctform.html");

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(INPUT, "UTF-8");

        //checkIfInputHaveValidTypes(doc);
        //checkIfFormTagHasValidAttributes(doc);
        //checkIfInputHaveNameAttribute(doc);
        checkIfSubmitButtonIsValid(doc);
    }

    public static void checkIfFormTagHasValidAttributes(Document doc) {
        Element form = doc.select("form").first();

        String method = form.attr("method");
        String enctype = form.attr("enctype");
        String action = form.attr("action");

        System.out.println(method+" is valid method "+METHODS.contains(method));
        System.out.println(enctype+" is valid enctype "+ENCODING_TYPE.contains(enctype));
        System.out.println(action);
    }

    public static void checkIfInputHaveValidTypes(Document doc) throws IOException {
        Elements inputs = doc.select("input");

        inputs.forEach(in -> {
            String attr = in.attr("type");
            String attr2 = attr.isEmpty() ? "text" : attr;

            System.out.println(attr2+" is correct type = "+INPUT_TYPES.contains(attr2));
        });
    }

    public static void checkIfInputHaveNameAttribute(Document doc) {
        Elements inputs = doc.select("input");

        inputs.forEach(in -> {
            String name = in.attr("name");
            String type = in.attr("type");

            System.out.println("input "+type+" is named "+name);
        });
    }

    public static void checkIfSubmitButtonIsValid(Document doc) {
        Element form = doc.select("form").first();

        Element inputSubmitButton = form.selectFirst("input[type$=submit]");
        if (inputSubmitButton != null) {
            System.out.println(inputSubmitButton.attr("value"));
        }

        Element buttonSubmit = form.selectFirst("button");
        if (buttonSubmit != null) {
            System.out.println(buttonSubmit.text());
        }

        //System.out.println(inputSubmitButton.attr("value"));
    }
}
