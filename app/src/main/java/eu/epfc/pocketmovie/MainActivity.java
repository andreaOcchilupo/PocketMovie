package eu.epfc.pocketmovie;

/*
* GnomePremier
* andreajean
* key : ea2dcee690e0af8bb04f37aa35b75075
* https://trello.com/b/HnDSqnM5/pocketmovies
* images http://image.tmdb.org/t/p/w185/
* */

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = findViewById(R.id.view_pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        SavedFilmManager.getInstance().initWithContext(getApplicationContext());

    }

}