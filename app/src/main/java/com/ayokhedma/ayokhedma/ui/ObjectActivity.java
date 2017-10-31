package com.ayokhedma.ayokhedma.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.FragmentPageAdapter;
import com.ayokhedma.ayokhedma.fragments.CommentFragment;
import com.ayokhedma.ayokhedma.fragments.DescriptionFragment;
import com.ayokhedma.ayokhedma.fragments.PhoneFragment;

public class ObjectActivity extends AppCompatActivity {
    SearchView searchView;
    TextView title;
    private String id,name,category;
    ViewPager pager;
    TabLayout tabLayout;
    FragmentPageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        //define toolbar and title
        Toolbar toolbar = (Toolbar) findViewById(R.id.obj_toolbar);
        title = (TextView) toolbar.findViewById(R.id.obj_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        category = intent.getStringExtra("category");
        title.setText(category + " " + name);
        getSupportActionBar().setTitle(category + " " + name);



        //define tabLayout and fragment adapter
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        pageAdapter.addFragments(new DescriptionFragment(), "معلومات");
       // pageAdapter.addFragments(new PhoneFragment(), "الهاتف");
        pageAdapter.addFragments(new CommentFragment(), "التعليقات");
        pager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        new DescriptionFragment();
                        break;
                 //   case 1 :
                   //     new PhoneFragment();
                   case 1 :
                     new CommentFragment();
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        final MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemsVisibility(menu,searchItem,false);
                title.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, searchItem, true);
                title.setVisibility(View.VISIBLE);
                return false;
            }
        });
        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconified(true);
        }
        return super.onCreateOptionsMenu(menu);
    }
    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }
}
