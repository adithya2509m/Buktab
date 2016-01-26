package android.buktab.in.buktab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adithya on 25/1/16.
 */
public class Logout extends Fragment {

    View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.logout, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = pref.edit();
        editor.clear();
        editor.commit();
        startAnim();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Intent mainIntent = new Intent(getActivity(), Splash.class);
                stopAnim();
                getActivity().finish();
                getActivity().startActivity(mainIntent);


            }
        }, 2000);






        return rootView;

    }

    void startAnim(){
        rootView. findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        rootView.findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }
}
