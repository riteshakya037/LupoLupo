package com.lupolupo.android.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends Fragment {


    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("Privacy Policy"));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }

}
