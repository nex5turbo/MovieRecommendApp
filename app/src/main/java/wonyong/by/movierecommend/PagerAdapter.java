package wonyong.by.movierecommend;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int mNumOfTabs){
        super(fragmentManager);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                FragmentActivity tab1 = new FragmentActivity();
                return tab1;
            case 1:
                Fragment2Activity tab2 = new Fragment2Activity();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }
}
