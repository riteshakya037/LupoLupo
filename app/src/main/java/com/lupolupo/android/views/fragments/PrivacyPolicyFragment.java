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
import com.lupolupo.android.views.fragments.bases.BackBaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends BackBaseFragment {


    @OnClick(R.id.fragment_privacy_policy_contact_page)
    void onClick() {
        EventBus.getDefault().post(new FragmentEvent(ContactUsFragment.newInstance(this), false));
    }

    private static Fragment previousFragment;

    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Fragment previousFragment) {
        PrivacyPolicyFragment.previousFragment = previousFragment;
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("Privacy Policy"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new FragmentEvent(previousFragment, true));
    }
}
