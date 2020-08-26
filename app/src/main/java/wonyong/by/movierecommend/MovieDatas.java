package wonyong.by.movierecommend;

public class MovieDatas {

    public String movienm;
    public String movienmen;
    public int opendt;
    public String prdtstatnm;
    public String repnationnm;
    public String repgenrenm;

    public MovieDatas(String movienm, String movienmen, int opendt, String prdtstatnm, String repnationnm, String repgenrenm){
        this.movienm = movienm;
        this.movienmen = movienmen;
        this.opendt = opendt;
        this.prdtstatnm = prdtstatnm;
        this.repnationnm = repnationnm;
        this.repgenrenm = repgenrenm;
    }

    public MovieDatas(){
        this.movienm = "";
        this.movienmen = "";
        this.opendt = 0;
        this.prdtstatnm = "";
        this.repnationnm = "";
        this.repgenrenm = "";
    }
}
