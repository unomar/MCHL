package com.sloppylinux.mchl.ui.common.views;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.sloppylinux.mchl.R;

/**
 * An MCHL themed Snackbar to display loading statuses.
 */
public class MchlSnackbar
{
    private final Snackbar snackbar;

    /**
     * Constructor.
     *
     * @param view     The view to place the snackbar on
     * @param text     The text to display
     * @param duration The duration the snackbar should last
     * @param context  The application context
     */
    public MchlSnackbar(View view, String text, int duration, Context context)
    {
        snackbar = Snackbar.make(view, text, duration);
        snackbar.setAction("No action", null).show();
        snackbar.setTextColor(context.getColor(R.color.black));
        snackbar.setBackgroundTint(context.getColor(R.color.colorAccent));
    }

    /**
     * Show the snackbar.
     */
    public void show()
    {
        snackbar.show();
    }

    /**
     * Hide the snackbar
     */
    public void dismiss()
    {
        snackbar.dismiss();
    }

    /**
     * Update the test to display for reuse.
     *
     * @param text The text to display
     * @return The snackbar instance
     */
    public MchlSnackbar setText(String text)
    {
        snackbar.setText(text);
        return this;
    }
}
