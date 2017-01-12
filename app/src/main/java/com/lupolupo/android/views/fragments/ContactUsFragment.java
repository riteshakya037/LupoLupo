package com.lupolupo.android.views.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.events.FragmentEvent;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.views.fragments.bases.BackBaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends BackBaseFragment {

    @OnClick(R.id.fragment_contact_us_info)
    void onInfo() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: info@lupolupo.com"));
        startActivity(Intent.createChooser(emailIntent, "Info"));
    }

    @OnClick({R.id.fragment_contact_us_feedback, R.id.fragment_contact_us_complaint_email})
    void onFeedback() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: feedback@lupolupo.com"));
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }

    private static Fragment previousFragment;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Fragment previousFragment) {
        ContactUsFragment.previousFragment = previousFragment;
        return new ContactUsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("Contact Us"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new FragmentEvent(previousFragment, true));
    }
}
