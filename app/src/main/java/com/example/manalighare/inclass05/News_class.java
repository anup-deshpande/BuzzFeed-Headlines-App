package com.example.manalighare.inclass05;

public class News_class {

    String Title;
    String Publish_date;
    String description;
    String image_url;

    @Override
    public String toString() {
        return "News_class{" +
                "Title='" + Title + '\'' +
                ", Publish_date='" + Publish_date + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }

    public News_class() {

    }
}
