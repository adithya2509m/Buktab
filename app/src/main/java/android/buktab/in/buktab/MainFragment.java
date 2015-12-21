package android.buktab.in.buktab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment {

    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public MainFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

       mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Fragment B"),
                InsertFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Fragment C"),
                ManageBooks.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("Fragment D"),
                SearchFragment.class, null);


        return rootView;
    }
}
