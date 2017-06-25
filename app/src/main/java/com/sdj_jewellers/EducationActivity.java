package com.sdj_jewellers;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.sdj_jewellers.adapter.EducationPagerAdapter;
import com.sdj_jewellers.fragment.CaratFragment;
import com.sdj_jewellers.fragment.ClarityFragment;
import com.sdj_jewellers.fragment.ColourFragment;
import com.sdj_jewellers.fragment.CutFragment;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private EducationPagerAdapter pagerAdapter;
    ArrayList<String> tabTitleList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        setupViewPager();
        setupToolbar();

    }

    private void setupViewPager() {
        mPagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.complaint_sliding_tab);
        mViewPager = (ViewPager)findViewById(R.id.complaint_view_pager);

        tabTitleList =new ArrayList<>();
        tabTitleList.add("CLARITY");
        tabTitleList.add("COLOUR");
        tabTitleList.add("CUT");
        tabTitleList.add("CARAT");
        tabStripConfiguration();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Education");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ClarityFragment(), "CLARITY");
        adapter.addFrag(new ColourFragment(), "COLOUR");
        adapter.addFrag(new CutFragment(), "CUT");
        adapter.addFrag(new CaratFragment(), "CARAT");

        viewPager.setAdapter(adapter);
    }
    public void tabStripConfiguration(){

        pagerAdapter=new EducationPagerAdapter(tabTitleList, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mPagerSlidingTabStrip.setTextColor(Color.parseColor("#FFFFFF"));
        mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.colorPrimary));  /*#47aabf*/
      /*  if(isTablet(ComplaintListActivity.this)==true){

            mPagerSlidingTabStrip.setTextSize(dpToPx(18));

        }else{
            mPagerSlidingTabStrip.setTextSize(dpToPx(14));
        }*/

        mPagerSlidingTabStrip.setViewPager(mViewPager);


        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int position = 0;

            @Override
            public void onPageSelected(int tabPosition) {
                // on changing the page
                // make respected tab selected
         /*       FragmentLifecycle fragmentToShow = (FragmentLifecycle) pagerAdapter.getItem(tabPosition);
                if (tabPosition == 0)
                    fragmentToShow.onResumeFragment(openIssueList, false);
                else
                    fragmentToShow.onResumeFragment(resolvedIssueList, false);*/

                System.out.println("tab position" + tabPosition);
//                FragmentLifecycle fragmentToPause = (FragmentLifecycle) pagerAdapter.getItem(position);
//                fragmentToPause.onPauseFragment();

                position = tabPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });

    }
    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            /*if (object instanceof ProfileFragment) {
                ((HistoryFragment)object).updateView();
            }*/
            return super.getItemPosition(object);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;

        }

        return true;
    }
}
