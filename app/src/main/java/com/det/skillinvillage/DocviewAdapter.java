package com.det.skillinvillage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Admin on 22-06-2018.
 */


public class DocviewAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public DocviewAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DocView_Download_Fragment tab1 = new DocView_Download_Fragment();
                return tab1;
            case 1:
                DocView_Download_Fragment tab2 = new DocView_Download_Fragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}