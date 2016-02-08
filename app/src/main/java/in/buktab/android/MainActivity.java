package in.buktab.android;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenuListView;

import io.saeid.fabloading.LoadingView;


public class MainActivity extends ActionBarActivity {
    LoadingView mLoadingView;
    private SwipeMenuListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        mLoadingView.addAnimation(Color.parseColor("#FFD200"),R.drawable.book1,
                LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#2F5DA9"),R.drawable.book2,
                LoadingView.FROM_TOP);
        mLoadingView.addAnimation(Color.parseColor("#FF4218"),R.drawable.book3,
                LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#C7E7FB"), R.drawable.book1,
                LoadingView.FROM_BOTTOM);

        //also you can add listener for getting callback (optional)
        mLoadingView.addListener(new LoadingView.LoadingListener() {
            @Override public void onAnimationStart(int currentItemPosition) {
            }

            @Override public void onAnimationRepeat(int nextItemPosition) {
            }

            @Override public void onAnimationEnd(int nextItemPosition) {
             //   Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_LONG);
            }
        });


        mLoadingView.startAnimation();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
