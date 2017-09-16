package com.example.shru.googlemap.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by user on 1/18/2016.
 */
public class Calibri extends TextView{

    private static Typeface sCalibri;

    public Calibri(Context context) {
        super(context);
        if (isInEditMode()) return; //Won't work in Eclipse graphical layout
        setTypeface();
    }

    public Calibri(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        setTypeface();
    }

    public Calibri(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        setTypeface();
    }

    private void setTypeface() {
        if (sCalibri == null) {
            sCalibri = Typeface.createFromAsset(getContext().getAssets(), "Fonts/calibri.ttf");
        }
        setTypeface(sCalibri);
    }
}
