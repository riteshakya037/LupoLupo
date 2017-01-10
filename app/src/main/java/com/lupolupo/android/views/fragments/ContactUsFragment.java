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
public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("Contact Us"));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

}
