package com.lupolupo.android.preseneters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.views.activities.AllComicActivity;
import com.lupolupo.android.views.activities.MainActivity;
import com.lupolupo.android.views.activities.SplashActivity;

/**
 * @author Ritesh Shakya
 */

public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener {

    private Activity activity;

    SpinnerInteractionListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                break;
            case 1:
                if (!(activity instanceof MainActivity))
                    activity.startActivity(new Intent(activity, MainActivity.class));
                break;
            case 2:
            case 3:
                AppLoader.getInstance().setAppMode(AppMode.match(i));
                Intent intent = new Intent(activity, SplashActivity.class);
                intent.putExtra(AllComicActivity.INTENT_ALL_EPISODE, "");
                activity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}