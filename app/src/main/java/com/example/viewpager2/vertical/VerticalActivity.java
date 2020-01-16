package com.example.viewpager2.vertical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.viewpager2.R;
import com.example.viewpager2.horizontal.HorizontalVpAdapter;

public class VerticalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        ViewPager2 viewPager2 = findViewById(R.id.vp_v);
        VerticalVpAdapter adapter = new VerticalVpAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
    }
}
