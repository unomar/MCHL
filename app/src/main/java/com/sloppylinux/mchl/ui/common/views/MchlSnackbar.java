package com.sloppylinux.mchl.ui.common.views;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.ui.R;

public class MchlSnackbar
{
    private final Snackbar snackbar;

    public MchlSnackbar(View view, String text, int duration, Context context)
    {
        snackbar = Snackbar.make(view, text, duration);
        snackbar.setAction("No action", null).show();
        snackbar.setTextColor(context.getColor(R.color.colorHighlight));
        snackbar.setBackgroundTint(context.getColor(R.color.orange));
    }

    public void show()
    {
        snackbar.show();
    }

    public void dismiss()
    {
        snackbar.dismiss();
    }

    public MchlSnackbar setText(String text)
    {
        snackbar.setText(text);
        return this;
    }
}
