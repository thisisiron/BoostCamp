package com.caicorp.boostcamp;

public class SearchHelper {

    String url = "https://" + AppHelper.host +"?query=";
    int DISPLAY = 100;
    String title;

    public SearchHelper(String title) {
        this.title = title;
        url = url + title + "&display=" + DISPLAY;
    }


}
