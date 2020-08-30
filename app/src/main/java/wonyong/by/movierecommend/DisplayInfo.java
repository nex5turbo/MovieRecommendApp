package wonyong.by.movierecommend;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayInfo {
    Context context;
    DisplayMetrics dm = new DisplayMetrics();

    public DisplayInfo(Context context){
        this.context = context;
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
    }

    public float getDeviceWidthMM(){
        return dm.widthPixels / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, dm);
    }

    public float getDeviceDPI(){
        return dm.xdpi;
    }

    public int getDeviceWidthPixel(){
        return dm.widthPixels;
    }

    public int getDeviceWidthDp(){
        return (int)(dm.widthPixels/(dm.xdpi/160));
    }
    //dp = pixel/(dpi/160)
    //pixel = dp*(dpi/160)
    public int getDpfromPixel(int pixel){
        return (int)((float)pixel/dm.xdpi);
    }
    public int getPixelfromDp(int dp){
        return (int)((float)dp*(dm.xdpi/160));
    }
}
