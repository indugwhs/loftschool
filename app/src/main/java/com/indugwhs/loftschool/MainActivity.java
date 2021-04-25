 package com.indugwhs.loftschool;

 import android.os.Bundle;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
 import androidx.fragment.app.FragmentPagerAdapter;
 import androidx.fragment.app.FragmentTransaction;
 import androidx.viewpager.widget.ViewPager;

 import com.google.android.material.tabs.TabLayout;

 public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expence);
        tabLayout.getTabAt(1).setText(R.string.income);


    }

    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position < 2) {
                return BudgetFragment.newInstance(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}