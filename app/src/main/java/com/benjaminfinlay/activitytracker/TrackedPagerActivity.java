package com.benjaminfinlay.activitytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.Stack;
import java.util.UUID;

/**
 * TrackedPagerActivity used to open the tracked activity's to view all information.
 * With the ability to swipe left and right to view other tracked activity's.
 */
public class TrackedPagerActivity extends FragmentActivity {

    private Stack<Tracked> mTracked;
    private TrackedFragment trackedFragmentRef;

    /**
     * Triggered when the Activity is opened.
     * @param savedInstanceState App's compiled code and resources.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets the view pager from the view.
        ViewPager mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        // Gets the amount of items in the list to be able to swipe between & get the tracked activity at a given position in list.
        mTracked = TrackedManager.get(this).getAllTracked();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mTracked.size();
            }
            @Override
            public Fragment getItem(int pos) {
                Tracked foundTracked = mTracked.get(pos);
                trackedFragmentRef = TrackedFragment.newInstance(foundTracked.getUUID());
                return trackedFragmentRef;
            }
        });

        // Listens for when the screen is swiped to change the viewed tracked activity.
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) { }
            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }
            public void onPageSelected(int pos) {
                Tracked tracked = mTracked.get(pos);
                if (tracked.getTitle() != null) {
                    setTitle(tracked.getTitle());
                }
            }
        });

        // Sets what the current mTracked is.
        UUID trackedId = (UUID)getIntent().getSerializableExtra(TrackedFragment.EXTRA_TRACKED_ID);
        for (int i = 0; i < mTracked.size(); i++) {
            if (mTracked.get(i).getUUID().equals(trackedId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
