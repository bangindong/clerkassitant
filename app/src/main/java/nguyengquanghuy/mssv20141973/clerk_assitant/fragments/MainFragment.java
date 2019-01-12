package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.MainPagerAdapter;

public class MainFragment extends BaseFragment implements OnTabSelectListener {

    @BindView(R.id.vpgMain)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private int currentTab = 1;

    private MainPagerAdapter mainPagerAdapter;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    void initView(View view) {
        setupPager();
        setHasToolBar(true);
        setTitle("Trang chủ");
    }

    private void setupPager() {
        if (mainPagerAdapter == null) {
            mainPagerAdapter = new MainPagerAdapter(getChildFragmentManager());
        }
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(currentTab);
                setupBottomBar();
            }
        }, 100);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position);
                setTitle(AppConstant.listMainTitle[currentTab]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupBottomBar() {
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.icon_button_employee:
                currentTab = 0;
                break;
            case R.id.icon_button_conversation:
                currentTab = 1;
                break;
            case R.id.icon_button_home:
                currentTab = 2;
                break;
        }
        if (viewPager.getCurrentItem() != currentTab) {
            viewPager.setCurrentItem(currentTab, true);
            setTitle(AppConstant.listMainTitle[currentTab]);
        }
    }
}
