package wonyong.by.movierecommend;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public final String NAVER_ID = "YUOz3v1oFnNd9meSfC6t"; //deprecated
    public final String NAVER_PWD = "j3mHCWXLxM"; //deprecated
    public final String MOVIE_BOXOFFICE_KEY = "927f426e5914b98a82fe8636620488b5"; //deprecated
    public final String MOVIE_BOXOFFICE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?"; //deprecated
    public final String TMDB_MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/search/movie?";
    public final String TMDB_MOVIE_SIMILAR_SEARCH_URL = "https://api.themoviedb.org/3/movie/"; //need movie_id
    public final String TMDB_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public final String NO_POSTER_URL = "https://folo.co.kr/img/gm_noimage.png";
    public final String TMBD_KEY = "c0e0fe5d77f2d08e7948ca71987e4caf";

    public static final Map<Integer, String> GENRES_MAP = new HashMap<Integer, String>(){
        {
            put(28, "액션");
            put(12, "어드벤쳐");
            put(16, "애니메이션");
            put(35, "코메디");
            put(80, "범죄");
            put(99, "다큐");
            put(18, "드라마");
            put(10751, "가족");
            put(14, "판타지");
            put(36, "역사");
            put(27, "호러");
            put(10402, "음악");
            put(9648, "미스테리");
            put(10749, "로맨스");
            put(878, "공상과학");
            put(10770, "TV영화");
            put(53, "스릴러");
            put(10752, "전쟁");
            put(37, "서부");

        }
    };

    public final int TYPE_MOVIE = 0; //deprecated
    public final int TYPE_NAVER = 1; //deprecated
    public final int TYPE_SERVER = 2;
    public final int TYPE_SEARCH_TMDB = 3;
    public final int TYPE_SEARCH_SIMILAR_TMDB = 4;
    public final int TYPE_NUMBER_PAGES = 5;
    public final int TYPE_RANK = 6;


    public String getGenre(int genre_ids){return GENRES_MAP.get(genre_ids);}

}
