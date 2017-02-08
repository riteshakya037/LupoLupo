package com.lupolupo.android.preseneters;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.views.activities.AllComicActivity;
import com.lupolupo.android.views.activities.GridActivity;
import com.lupolupo.android.views.activities.MainActivity;
import com.lupolupo.android.views.activities.SplashActivity;

/**
 * @author Ritesh Shakya
 */

public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private Activity activity;

    SpinnerInteractionListener(Activity activity) {
        this.activity = activity;
    }

    boolean userSelect = false;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        userSelect = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("selected");
        if (userSelect) {
            switch (i) {
                case 0:
                    if (!(activity instanceof MainActivity)) {
                        Intent intent = new Intent(activity, SplashActivity.class);
                        intent.putExtra(MainActivity.INTENT_MAIN, "");
                        activity.startActivity(intent);
                    }
                    break;
                case 1:
                    if (!(activity instanceof GridActivity)) {
                        Intent intent = new Intent(activity, SplashActivity.class);
                        intent.putExtra(GridActivity.INTENT_GRID, "");
                        activity.startActivity(intent);
                    }
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
        userSelect = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}