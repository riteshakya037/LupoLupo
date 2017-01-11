package com.lupolupo.android.views.fragments.bases;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Ritesh Shakya
 */
public abstract class BackBaseFragment extends BaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public abstract void onBackPressed();
}
