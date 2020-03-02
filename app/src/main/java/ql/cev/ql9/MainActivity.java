package ql.cev.ql9;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import ql.cev.ql9.Fragments.AboutFragment;
import ql.cev.ql9.Fragments.ContactFragment;
import ql.cev.ql9.Fragments.HomeFragment;
import ql.cev.ql9.Fragments.SponsorsFragment;

public class MainActivity extends AppCompatActivity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       HomeFragment homeFragment=new HomeFragment();
       FragmentManager manager=getFragmentManager();
       manager.beginTransaction().replace(R.id.frame_layout,homeFragment,homeFragment.getTag()).commit();
       BottomNavigationView navigation = findViewById(R.id.navigation);
       navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
   }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    HomeFragment homeFragment=new HomeFragment();
                    FragmentManager manager=getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout,homeFragment,homeFragment.getTag()).commit();
                    return true;
                case R.id.sponsor:
                    SponsorsFragment sponsorsFragment=new SponsorsFragment();
                    manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout,sponsorsFragment,sponsorsFragment.getTag()).commit();
                    return true;
                case R.id.contact:
                    ContactFragment fragmentContact=new ContactFragment();
                    manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout,fragmentContact,fragmentContact.getTag()).commit();
                    return true;
                case R.id.about:
                    AboutFragment fragmentAbout=new AboutFragment();
                    manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout,fragmentAbout,fragmentAbout.getTag()).commit();
                    return true;
            }
            return false;
        }
    };
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) { super.onBackPressed(); }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();
    }
}