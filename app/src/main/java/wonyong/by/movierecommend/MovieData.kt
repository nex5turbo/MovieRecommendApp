package wonyong.by.movierecommend

/*
movieNm 영화제목
movieNmEn 영화 영어제목
openDt 개봉일
prdtStatNm 개봉상태
repNationNm 제작국가
repGenreNm 장르
 */

data class MovieData(var movienm : String, var movienmen : String, var opendt : Int, var prdtstatnm : String, var repnationnm : String, var repgenrenm : String) {
}