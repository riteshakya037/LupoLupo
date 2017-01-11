package com.lupolupo.android.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.events.FragmentEvent;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.views.fragments.bases.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends BaseFragment {

    @OnClick(R.id.fragment_about_us_terms_and_condition)
    void onTermClick() {
        EventBus.getDefault().post(new FragmentEvent(TermOfUseFragment.newInstance(), false));
    }

    @OnClick(R.id.fragment_about_us_contact_us)
    void onContactClick() {
        EventBus.getDefault().post(new FragmentEvent(ContactUsFragment.newInstance(), false));
    }

    @OnClick(R.id.fragment_about_us_privacy_policy)
    void onPrivacyClick() {
        EventBus.getDefault().post(new FragmentEvent(PrivacyPolicyFragment.newInstance(), false));
    }

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new AboutUsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("About Us"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
