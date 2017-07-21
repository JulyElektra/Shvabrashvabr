package com.example.elekt.shvabrashvabr.Model;

import android.graphics.drawable.Drawable;

/**
 * The class stores the information about article
 * @title the title of the article
 * @link the link to the article
 * @description - description of the article
 * @pubDate - the date of publication
 * @categories - categories, tegs
 * @image - the image
 * @creator - the name of creator
 */

public class Article {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String categories;
    private Drawable image;
    private String creator;

    public Article(String title, String link, String description, String pubDate, String categories, Drawable image, String creator) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.categories = categories;
        this.image = image;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
