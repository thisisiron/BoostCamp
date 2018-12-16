package com.caicorp.boostcamp.Data;

public class MovieItem {

    String title;
    String userRating;
    String pubDate;
    String director;
    String actor;
    String image;

    public MovieItem(String title, String rate, String pubDate, String director, String actor, String image) {
        this.title = title;
        this.userRating = userRating;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.image = image;
    }

    public String getUrl() {
        return image;
    }

    public void setUrl(String image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return userRating;
    }

    public void setRate(String userRating) {
        this.userRating = userRating;
    }

    public String getYear() {
        return pubDate;
    }

    public void setYear(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
