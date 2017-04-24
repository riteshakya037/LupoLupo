package com.lupolupo.android.views.custom.infinite_view_pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * @author Ritesh Shakya
 */

public class ViewPagerCustomDuration extends ViewPager {
    private final static boolean DEBUG = false;
    private static final long DEFAULT_AUTO_SCROLL_INTERVAL = 3000;//3s
    private static final int MSG_AUTO_SCROLL = 1;
    private static final int MSG_SET_PAGE = 2;
    private static final String TAG = ViewPagerCustomDuration.class.getSimpleName();
    private boolean mAutoScroll;
    private boolean mIsInfinitePagerAdapter;
    private boolean mTouchedWhenAutoScroll;
    private OnPageChangeListener mOnPageChangeListener;
    private long mDelay = DEFAULT_AUTO_SCROLL_INTERVAL;
    private MyHandler mHandler;

    public ViewPagerCustomDuration(Context context) {
        this(context, null);
    }

    public ViewPagerCustomDuration(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public static void log(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public void handlerMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTO_SCROLL:
                setItemToNext();
                sendDelayMessage();
                break;
            case MSG_SET_PAGE:
                setFakeCurrentItem(FakePositionHelper.getRealPosition(ViewPagerCustomDuration.this, msg.arg1), false);
                break;
        }
    }

    void init() {
        setOffscreenPageLimit(1);
        super.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(FakePositionHelper.getFakeFromReal(ViewPagerCustomDuration.this, position), positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position < FakePositionHelper.getStartPosition(ViewPagerCustomDuration.this) || position > FakePositionHelper.getEndPosition(ViewPagerCustomDuration.this)) {
                    log("position:" + position + "->" + FakePositionHelper.getRealPosition(ViewPagerCustomDuration.this, position) + "-return");
                    mHandler.removeMessages(MSG_SET_PAGE);
                    Message msg = mHandler.obtainMessage(MSG_SET_PAGE);
                    msg.arg1 = position;
                    mHandler.sendMessageDelayed(msg, 500);
                    return;
                } else {
                    log("position:" + position + "->" + FakePositionHelper.getRealPosition(ViewPagerCustomDuration.this, position));
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(FakePositionHelper.getFakeFromReal(ViewPagerCustomDuration.this, position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        mHandler = new MyHandler(this);
    }

    public void startAutoScroll() {
        startAutoScroll(this.mDelay);
    }

    public void startAutoScroll(long delayTime) {
        if (getAdapter() == null || getAdapter().getCount() == 0)
            return;
        this.mDelay = delayTime;
        this.mAutoScroll = true;
        sendDelayMessage();

    }

    private void sendDelayMessage() {
        mHandler.removeMessages(MSG_AUTO_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_AUTO_SCROLL, mDelay);
    }

    public void stopAutoScroll() {
        this.mAutoScroll = false;
        mHandler.removeMessages(MSG_AUTO_SCROLL);
    }

    public void setAutoScrollTime(long autoScrollTime) {
        this.mDelay = autoScrollTime;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    private void setItemToNext() {
        PagerAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            stopAutoScroll();
            return;
        }
        int totalCount = isInfinitePagerAdapter() ? FakePositionHelper.getRealAdapterSize(this) : adapter.getCount();
        if (totalCount <= 1)
            return;

        int nextItem = getFakeCurrentItem() + 1;
        if (isInfinitePagerAdapter()) {
            setFakeCurrentItem(nextItem);
        } else {
            if (nextItem == totalCount) {
                setFakeCurrentItem(0);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //to solve conflict with parent ViewGroup
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mAutoScroll || this.mTouchedWhenAutoScroll) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    this.mTouchedWhenAutoScroll = true;
                    stopAutoScroll();
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(FakePositionHelper.getRealFromFake(this, item), smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        return FakePositionHelper.getFakeFromReal(this, getFakeCurrentItem());
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(FakePositionHelper.getRealFromFake(this, item));
    }

    private int getFakeCurrentItem() {
        return super.getCurrentItem();
    }

    private void setFakeCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    private void setFakeCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    private int getAdapterSize() {
        return getAdapter() == null ? 0 : getAdapter().getCount();
    }

    private boolean isInfinitePagerAdapter() {
        return mIsInfinitePagerAdapter;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        mIsInfinitePagerAdapter = getAdapter() instanceof InfinitePagerAdapter;
        if (!mIsInfinitePagerAdapter) {
            throw new IllegalArgumentException("Currently, only InfinitePagerAdapter is supported");
        }
        setFakeCurrentItem(FakePositionHelper.getRealPosition(ViewPagerCustomDuration.this, 0), false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mAutoScroll || this.mTouchedWhenAutoScroll) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    this.mTouchedWhenAutoScroll = false;
                    startAutoScroll();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private static class MyHandler extends Handler {
        private WeakReference<ViewPagerCustomDuration> ref;

        MyHandler(ViewPagerCustomDuration ref) {
            this.ref = new WeakReference<>(ref);
        }

        @Override
        public void handleMessage(Message msg) {
            ViewPagerCustomDuration o = ref.get();
            if (o != null) {
                o.handlerMessage(msg);
            }
        }
    }

    public static class FakePositionHelper {
        /**
         * Can not be less than 3
         */
        public final static int MULTIPLIER = 5;

        static int getRealFromFake(ViewPagerCustomDuration viewPager, int fake) {
            int realAdapterSize = viewPager.getAdapterSize() / MULTIPLIER;
            if (realAdapterSize == 0)
                return 0;
            fake = fake % realAdapterSize;//ensure it
            int currentReal = viewPager.getFakeCurrentItem();
            return fake + (currentReal - currentReal % realAdapterSize);
        }

        static int getFakeFromReal(ViewPagerCustomDuration viewPager, int real) {
            int realAdapterSize = viewPager.getAdapterSize() / MULTIPLIER;
            if (realAdapterSize == 0)
                return 0;
            return real % realAdapterSize;
        }

        static int getStartPosition(ViewPagerCustomDuration viewPager) {
            return viewPager.getAdapterSize() / MULTIPLIER;
        }

        static int getEndPosition(ViewPagerCustomDuration viewPager) {
            int realAdapterSize = viewPager.getAdapterSize() / MULTIPLIER;
            return realAdapterSize * (MULTIPLIER - 1) - 1;
        }

        static int getRealAdapterSize(ViewPagerCustomDuration viewPager) {
            return viewPager.isInfinitePagerAdapter() ? viewPager.getAdapterSize() / MULTIPLIER : viewPager.getAdapterSize();
        }

        static int getRealPosition(ViewPagerCustomDuration viewPager, int position) {
            int realAdapterSize = getRealAdapterSize(viewPager);
            if (realAdapterSize == 0)
                return 0;
            int startPostion = getStartPosition(viewPager);
            int endPosition = getEndPosition(viewPager);
            if (position < startPostion) {
                return endPosition + 1 - realAdapterSize + position % realAdapterSize;
            }
            if (position > endPosition) {
                return startPostion + position % realAdapterSize;
            }
            return position;
        }

    }
}