package com.example.datalekage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.datalekage.Fragments.FilesFragment;
import com.example.datalekage.Fragments.ImagesFragment;
import com.example.datalekage.Fragments.VideosFragment;
import com.example.datalekage.adaperss.SectionPageAdapter;
import com.google.android.material.tabs.TabLayout;


public class NotesFragment extends Fragment  {

    View myFragment;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_notes, container, false);
        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tablayout);
        toolbar = myFragment.findViewById(R.id.toolbar_home);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {

        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());

        adapter.addFragment(new ImagesFragment(),"Images");
        adapter.addFragment(new VideosFragment(),"Videos");
        adapter.addFragment(new FilesFragment(),"Files");

        viewPager.setAdapter(adapter);
    }



}