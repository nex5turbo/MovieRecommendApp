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
                MainFragment mainFragment = new MainFragment();
                return mainFragment;
            case 1:
                SearchFragment searchFragment = new SearchFragment();
                return searchFragment;
            case 2:
                SearchPeopleFragment searchPeopleFragment = new SearchPeopleFragment();
                return searchPeopleFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }
}
