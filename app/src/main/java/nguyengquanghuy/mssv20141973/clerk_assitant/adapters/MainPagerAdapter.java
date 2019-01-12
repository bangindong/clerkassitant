package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.BaseFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.ConversationFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.EmployeeFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.RoomFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(EmployeeFragment.newInstance());
        fragments.add(ConversationFragment.newInstance());
        fragments.add(RoomFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
