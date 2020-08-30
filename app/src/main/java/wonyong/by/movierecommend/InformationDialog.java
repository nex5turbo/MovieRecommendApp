package wonyong.by.movierecommend;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationDialog extends Dialog {
    TextView titleText, releaseText, overviewText, genreText, vote_averageText;
    ImageView posterImageView;
    Button similarButton, closeButton;
    Constants CONST = new Constants();
    int id;
    public InformationDialog(Context context, MovieRecyclerData data){
        super(context);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.information_dialog);

        titleText = findViewById(R.id.dialogTitleText);
        releaseText = findViewById(R.id.dialogReleaseText);
        genreText = findViewById(R.id.dialogGenreText);
        vote_averageText = findViewById(R.id.dialogVoteAverageText);
        overviewText = findViewById(R.id.dialogOverviewText);

        posterImageView = findViewById(R.id.dialogImageView);
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.image_view_rounding);
        posterImageView.setBackground(drawable);
        posterImageView.setClipToOutline(true);

        similarButton = findViewById(R.id.dialogSimilarButton);
        closeButton = findViewById(R.id.dialogCloseButton);

        id = data.id;
        Log.d("DIALOG ID", String.valueOf(id)+", "+String.valueOf(data.id));

        titleText.setText("제목 : "+data.title);
        releaseText.setText("출시일 : "+data.release_date);
        String genre = "";
        for(int temp : data.genre_ids){
            genre = genre+" "+CONST.getGenre(temp);
        }
        genreText.setText("장르 :"+genre);
        vote_averageText.setText("평점 : 10/"+String.valueOf(data.vote_average));
        String overviewTemp = data.overview.replace("\n", " ");
        overviewText.setText(overviewTemp);
        overviewText.setMovementMethod(new ScrollingMovementMethod());

        posterImageView.setImageBitmap(Bitmap.createScaledBitmap(data.poster, 480, 720, false));

        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        similarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SimilarActivity.class);
                Log.d("INTENT ID", String.valueOf(id));
                i.putExtra("id", id);
                getContext().startActivity(i);
                dismiss();
            }
        });

    }

}
