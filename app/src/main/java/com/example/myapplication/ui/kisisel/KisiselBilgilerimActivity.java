package com.example.myapplication.ui.kisisel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class KisiselBilgilerimActivity extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kisisel_b_fragment, container, false);

        //Hangi title'lar arası geçiş yapılacak
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("Kişisel Bilgileri");
        titleList.add("Kart Bilgileri");

        //Hangi fragmentler arası geçiş olacak
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new KisiselFragment());
        fragmentList.add(new KartBFragment());

        ViewPager viewPager = view.findViewById(R.id.viewpager_message);
        //Adapter'a değişkeblerini veriyoruz
        PageAdapter adapter = new PageAdapter(titleList, fragmentList, getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        //Tablayoutu fragment içine tanımlattık
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        //tablayout'ları swipe yap
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
}
