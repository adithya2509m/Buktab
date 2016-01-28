package android.buktab.in.buktab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Srideepika on 1/28/2016.
 */
public class AboutUs extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aboutus,container,false);
        System.out.println("About us");
        return view;
    }

}
