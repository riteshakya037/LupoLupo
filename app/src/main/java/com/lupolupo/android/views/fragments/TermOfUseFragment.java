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
public class TermOfUseFragment extends BackBaseFragment {

    private static Fragment previousFragment;

    @OnClick({R.id.fragment_term_of_use_contact_page, R.id.fragment_term_of_use_contact_page_second})
    void onClick() {
        EventBus.getDefault().post(new FragmentEvent(ContactUsFragment.newInstance(this), false));
    }

    public TermOfUseFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Fragment previousFragment) {
        TermOfUseFragment.previousFragment = previousFragment;
        return new TermOfUseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().post(new TitleEvent("Term of Use"));
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_term_of_use, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new FragmentEvent(previousFragment, true));
    }
}
