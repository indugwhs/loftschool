  package com.indugwhs.loftschool.screens.main;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentManager;
 import androidx.fragment.app.FragmentPagerAdapter;
 import androidx.viewpager.widget.ViewPager;

 import com.google.android.material.floatingactionbutton.FloatingActionButton;
 import com.google.android.material.tabs.TabLayout;
 import com.indugwhs.loftschool.R;
 import com.indugwhs.loftschool.screens.AddMoneyActivity;

 import static com.indugwhs.loftschool.screens.AddMoneyActivity.ARG_TYPE_BUDGET;
 import static com.indugwhs.loftschool.screens.main.BudgetFragment.REQUEST_CODE;

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

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int activeFragmentIndex = viewPager.getCurrentItem();
//                Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);
                intent.putExtra(ARG_TYPE_BUDGET, activeFragmentIndex);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


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