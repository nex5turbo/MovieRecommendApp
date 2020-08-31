package wonyong.by.movierecommend;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class MovieRecyclerData {

    public int id;
    public String title;
    public String original_title;
    public String original_language;
    public String release_date;
    public ArrayList<Integer> genre_ids;
    public Bitmap poster;
    public boolean adult;
    public String overview;
    public int vote_count;
    public float vote_average;
    public float popularity;
    String posterUrl;

    /*
                id : int
                title : String
                original_title : String
                original_language : String
                release_date : String
                genre_ids : Array<Integer>
                poster : Bitmap
                adult : boolean
                overview : String
                vote_count : int
                vote_average : float
                popularity : float

     */

    public MovieRecyclerData(int id, String title, String original_title, String original_language, String release_date, ArrayList<Integer> genre_ids, Bitmap poster, boolean adult, String overview, int vote_count, float vote_average, float popularity,
                             String posterUrl){
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.original_language = original_language;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.poster = poster;
        this.adult = adult;
        this.overview = overview;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.posterUrl = posterUrl;
    }
}
