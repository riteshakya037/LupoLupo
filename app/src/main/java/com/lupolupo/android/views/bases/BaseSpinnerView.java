package com.lupolupo.android.views.bases;

import android.widget.ArrayAdapter;

import com.lupolupo.android.preseneters.SpinnerInteractionListener;

/**
 * @author Ritesh Shakya
 */
public interface BaseSpinnerView {
    void setAdapter(ArrayAdapter<String> dataAdapter);

    void setListeners(SpinnerInteractionListener onItemSelectedListener);
}
