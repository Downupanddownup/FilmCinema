package com.example.hopjs.filmcinema.BitmapTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.hopjs.filmcinema.Test.Test;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class BitmapTool {
    public static Bitmap compressBitmap(Context context,int resId,double scale){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

    //    BitmapFactory.decodeFile(path, options);

        BitmapFactory.decodeResource(context.getResources(),
                resId,options);

        if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {

            Log.d("OomDemo", "alert!!!" + String.valueOf(options.mCancel) + " " + options.outWidth + options.outHeight);

            return null;

        }

        options.inSampleSize = computeSampleSize(options, 600, (int) (scale * 1024 * 1024));


        options.inJustDecodeBounds = false;

        options.inDither = false;

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

       // Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resId,options);
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
