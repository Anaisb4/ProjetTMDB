package com.example.anais.projettmdb;

import java.util.LinkedList;

/**
 * Created by Victor and Anais on 05/06/2017.
 */

public class Movie {

    private String image;
    private String title;
    private String description;
    private String origine;
    private String date;
    private LinkedList<String> genre;
    private Double rating;


    //Constructeur de l'objet Movie
    public Movie(String image, String title, String description, String origine, String date, LinkedList<String> genre, Double rating)
    {
        this.image = "https://image.tmdb.org/t/p/w185/" +image;
        this.title = title;
        this.description = description;
        this.origine = origine;
        this.date = date;
        this.rating = rating;
        this.genre = new LinkedList<String>();
        this.genre.addAll(genre);
    }


    //Getters et Setters

    public String getImage() {
        return image;
    }

    public void setImage(int imageId) {
        this.image = image;
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

    public LinkedList<String> getGenre() {
        return genre;
    }

    public void setGenre(LinkedList<String> genre) {
        this.genre = genre;
    }

    public float getRating() {
        float f = new Float(rating);
        return f/2;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", origine='" + origine + '\'' +
                ", date='" + date + '\'' +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }

    public int compareRating(Movie m1){
        if(rating>m1.rating){
            return 1;
        } else if (rating<m1.rating){
            return  -1;
        } else {
            return 0;
        }
    }

    public int compareDate(Movie m1){
        return date.compareTo(m1.getDate());
    }
}
