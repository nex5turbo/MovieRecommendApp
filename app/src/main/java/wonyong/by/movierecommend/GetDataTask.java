package wonyong.by.movierecommend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
Naver Movie Search API
Client ID : YUOz3v1oFnNd9meSfC6t
Client Password : j3mHCWXLxM

영화진흥 위원회 API
KEY : 927f426e5914b98a82fe8636620488b5
&curPage=1&itemPerPage=100
 */

public class GetDataTask extends AsyncTask<URL, Integer, Object> {
    int TYPE;
    Context context;

    private DbOpenHelper mDbOpenHelper;

    Constants CONST = new Constants();

    public GetDataTask(int TYPE, Context context) {
        this.TYPE = TYPE;
        this.context = context;
    }

    public GetDataTask(int TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(URL... urls) {
        String result = "success";

        if(TYPE == CONST.TYPE_SEARCH_TMDB){
            result = getJson(urls);
            ArrayList<MovieRecyclerData> returnList = parsingTMDBJSON(result);
            return returnList;
        }else if(TYPE == CONST.TYPE_SERVER){
            result = getJson(urls);
        }

        return result;
    }


    private String getJson(URL... urls){
        String result = "";
        String str = "";
        for(int i = 0; i < urls.length; i++) {
            try {
                HttpURLConnection conn = (HttpURLConnection) urls[i].openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    result = buffer.toString();
                    reader.close();
                } else {
                    result = "Connection failed = " + conn.getResponseCode();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private ArrayList<MovieRecyclerData> parsingTMDBJSON(String json){
        ArrayList<MovieRecyclerData> returnList = new ArrayList<MovieRecyclerData>();
        try {
            Log.d("JSON STRING", json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray movieArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < movieArray.length(); i++){
                JSONObject movieObject = movieArray.getJSONObject(i);

                int id = Integer.parseInt(movieObject.getString("id"));
                String title = movieObject.getString("title");
                String original_title = movieObject.getString("original_title");
                String original_language = movieObject.getString("original_language");
                String release_date = movieObject.getString("release_date");

                ArrayList<Integer> genre_ids = new ArrayList<Integer>();
                String strGenre = movieObject.getString("genre_ids");
                StringTokenizer token = new StringTokenizer(strGenre, " ,\n[]");
                while(token.hasMoreTokens()) {
                    int temp = Integer.parseInt(token.nextToken());
                    genre_ids.add(temp);
                }

                String poster_path = movieObject.getString("poster_path");
                String strImageURL = CONST.TMDB_POSTER_URL+poster_path;
                URL imageurl = null;
                if(poster_path.equals("null")){
                    imageurl = new URL(CONST.NO_POSTER_URL);
                }else {
                    try {
                        imageurl = new URL(strImageURL);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                HttpURLConnection con = (HttpURLConnection) imageurl.openConnection();
                con.setDoInput(true);
                con.connect();
                InputStream input = con.getInputStream();
                Bitmap poster = BitmapFactory.decodeStream(input);
                con.disconnect();

                boolean adult = Boolean.getBoolean(movieObject.getString("adult"));
                String overview = movieObject.getString("overview");
                int vote_count = Integer.parseInt(movieObject.getString("vote_count"));
                float vote_average = Float.parseFloat(movieObject.getString("vote_average"));
                float popularity = Float.parseFloat(movieObject.getString("popularity"));

                MovieRecyclerData movieData = new MovieRecyclerData(id, title, original_title, original_language, release_date, genre_ids, poster, adult, overview, vote_count, vote_average, popularity);
                returnList.add(movieData);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
    }
}