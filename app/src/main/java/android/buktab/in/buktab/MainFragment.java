package android.buktab.in.buktab;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

        mTabHost.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View v) {
            }

            @Override
            public void onViewAttachedToWindow(View v) {
                mTabHost.getViewTreeObserver().removeOnTouchModeChangeListener(mTabHost);
            }
        });


        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        

       mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Name"),
               SearchFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("Department"),
                DeptSearchFragment.class, null);
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
            final TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);

            // Look for the title view to ensure this is an indicator and not a divider.(I didn't know, it would return divider too, so I was getting an NPE)
            if (tv == null)
                continue;
            else
                tv.setTextColor(0xFFFFFFFF);

        }
        //mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        return rootView;
    }
}
