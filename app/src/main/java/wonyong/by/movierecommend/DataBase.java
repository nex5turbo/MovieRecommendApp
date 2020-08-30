package wonyong.by.movierecommend;

import android.provider.BaseColumns;

public final class DataBase {
/*
movieNm 영화제목
movieNmEn 영화 영어제목
openDt 개봉일
prdtStatNm 개봉상태
repNationNm 제작국가
repGenreNm 장르
 */
    public static final class CreateDB implements BaseColumns{
        public static final String ID = "id";
        public static final String TITLE = "title";
//        public static final String OPENDT = "opendt";
//        public static final String GENRE = "genre";
//        public static final String VOTEAVG = "voteavg";
        public static final String POSTER = "poster";
        public static final String _TABLENAME0 = "postertable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                + ID +" primary key , "
                + TITLE +" text not null , "
                + POSTER +" blob not null );";
    }
}
