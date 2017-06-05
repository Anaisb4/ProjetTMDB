package com.example.anais.projettmdb;

/**
 * Created by Pro on 05/06/2017.
 */

public class Movie {

    private int imageId;
    private String title;
    private String description;
    private String origine;
    private String date;
    private String genre;
    private Double rating;


    //Constructeur de l'objet Movie
    public Movie(int imageId, String title, String desc, String description, String origine, String date, String genre, Double rating)
    {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.origine = origine;
        this.date = date;
        this.genre = genre;
        this.rating = rating;
    }


    //Getters et Setters

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imageId=" + imageId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", origine='" + origine + '\'' +
                ", date='" + date + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }
}
