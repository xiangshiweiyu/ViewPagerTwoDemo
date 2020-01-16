# ViewPagerTwoDemo
一个关于ViewPager 2 的 demo

之前早有耳闻 Google 为我们提供新的控件来替换老旧的 ViewPager 进而解决一些不好解决的bug问题，巴拉巴拉一大堆，就是前因后果啥的......相信读者已经在“张鸿洋”大神、“郭霖”大神或者是其他Android 大佬的公众号那里看见了许许多多了，或许各位感觉很无聊了，笔者菜鸟，分析不了历史背景，也不是很懂源码，但是小菜鸟，可以带给位看官尝个鲜，教你怎么用，怎么上手哈，闲话不多说，我们步入正题。
##  一、效果
![全部效果](https://upload-images.jianshu.io/upload_images/6433394-398b118b017a5d85.gif?imageMogr2/auto-orient/strip)

## 二、介绍必要基础知识
#####1、ViewPager 与 ViewPager2 部分对比
ViewPager|ViewPager 2
--|:--:
PagerAdapter|RecyclerView.Adapter
FragmentStatePagerAdapter|FragmentStateAdapter 
addPageChangeListener|registerOnPageChangeCallback 
无|从右到左 (RTL) 的布局支持
无|垂直方向支持
无|停用用户输入的功能（setUserInputEnabled、isUserInputEnabled）

##### 2、部分基础知识
ViewPager 2 底层是用 RecycleView 实现

## 三、撰写功能
### 1、依赖引入
```
implementation 'androidx.viewpager2:viewpager2:1.0.0'
implementation 'androidx.recyclerview:recyclerview:1.1.0' // ViewPager 2 需要使用 RecycleView 的 adapter
```
### 2、实现横向滑动效果
#### 1）、实现效果
![横向滑动.gif](https://upload-images.jianshu.io/upload_images/6433394-0aa27c49ca978074.gif?imageMogr2/auto-orient/strip)
#### 2）、代码实现
###### a、 布局
Activity 布局内 就一个 ViewPager 2控件
item 界面就一个textView 
就不展示了
##### b、逻辑
```
package com.example.viewpager2.horizontal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateTime: 2020/1/15 15:06
 * Author: hxd
 * Content:
 * UpdateTime:
 * UpdateName;
 * UpdateContent:
 */
public class HorizontalVpAdapter extends RecyclerView.Adapter<HorizontalVpAdapter.HorizontalVpViewHolder> {

    private List<Integer> backgrounds;
    private Context mContext;

    HorizontalVpAdapter(Context context) {
        mContext = context;
        if (backgrounds == null) {
            backgrounds = new ArrayList<>();
            backgrounds.add(android.R.color.holo_blue_bright);
            backgrounds.add(android.R.color.holo_red_dark);
            backgrounds.add(android.R.color.holo_green_dark);
            backgrounds.add(android.R.color.holo_orange_light);
            backgrounds.add(android.R.color.holo_purple);
        }
    }

    @NonNull
    @Override
    public HorizontalVpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HorizontalVpViewHolder(LayoutInflater.from(mContext).inflate((R.layout.item_h_v), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HorizontalVpViewHolder holder, int position) {
        holder.mTextView.setText("第  " + (position + 1) + " 界面");
        holder.mLinearLayout.setBackgroundResource(backgrounds.get(position));
    }

    @Override
    public int getItemCount() {
        if (backgrounds == null) {
            return 0;
        }
        return backgrounds.size();
    }

    class HorizontalVpViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mTextView;

        HorizontalVpViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.ll_h_v);
            mTextView = itemView.findViewById(R.id.tv_hv);
        }
    }
}
```
适配器逻辑如上，因为 ViewPager 2 底层使用 RecycleView 实现的，所以这里不再使用 PagerAdapter 而是使用了 RecyclerView.Adapter 这也是 引入RecycleView 依赖的原因所在。
```
public class HorizontalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        ViewPager2 viewPager2 = findViewById(R.id.vp_h);
        HorizontalVpAdapter adapter = new HorizontalVpAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}
```
Activity 内的代码就没什么不同了和之前一样的两步走，初始化控件，设置适配器。
### 3、纵向滑动效果
#### 1）、实现效果
![纵向滑动](https://upload-images.jianshu.io/upload_images/6433394-a748f8f9e11a3f80.gif?imageMogr2/auto-orient/strip)
#### 2）、代码逻辑
###### a、 布局
Activity 的布局需要拿出
```
    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/vp_v"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical" />
```
布局内的 `   android:orientation="vertical"` 控制横向和纵向滑动 有点类似于 LinearLayout
###### b、 逻辑
```
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
```
Activity 内的`viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);`可以实现 xml 内的` android:orientation="vertical"`相同效果。
### 4、RadioGroup 与 ViewPager 2 连用
#### 1）、实现效果
![ViewPager 2 + RadioGroup 效果](https://upload-images.jianshu.io/upload_images/6433394-03cb4c2c1da22993.gif?imageMogr2/auto-orient/strip)
#### 2）、代码逻辑
###### a、 布局
Activity 布局
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".withRadioGroup.RgActivity">

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/vp_rg"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/rg_vp" />

    <RadioGroup
        android:id="@+id/rg_vp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_gravity="bottom"
        android:orientation="horizontal">

        <RadioButton
            android:id="@+id/rb_home"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:button="@null"
            android:checked="true"
            android:drawableTop="@drawable/selector_home"
            android:drawablePadding="5dp"
            android:gravity="center"
            android:paddingTop="5dp"
            android:paddingBottom="5dp"
            android:text="@string/home"
            android:textColor="@color/selector_rg"
            android:textSize="16sp" />

        <RadioButton
            android:id="@+id/rb_msg"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:button="@null"
            android:drawableTop="@drawable/selector_msg"
            android:drawablePadding="5dp"
            android:gravity="center"
            android:paddingTop="5dp"
            android:paddingBottom="5dp"
            android:text="@string/msg"
            android:textColor="@color/selector_rg"
            android:textSize="16sp" />

        <RadioButton
            android:id="@+id/rg_my"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:button="@null"
            android:drawableTop="@drawable/selector_my"
            android:drawablePadding="5dp"
            android:gravity="center"
            android:paddingTop="5dp"
            android:paddingBottom="5dp"
            android:text="@string/my"
            android:textColor="@color/selector_rg"
            android:textSize="16sp" />
    </RadioGroup>
</RelativeLayout>
```
这里没啥说的就类似于各位 HomeActivity 的实现方式了。
每个 Fragment 内的布局更加简洁这里不再陈述
###### b、 逻辑
Adapter 内逻辑
```
package com.example.viewpager2.withRadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateTime: 2020/1/15 16:32
 * Author: hxd
 * Content:
 * UpdateTime:
 * UpdateName;
 * UpdateContent:
 */
public class RgAdapter extends FragmentStateAdapter {

    private List<Class> fragments;

    public RgAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
    }

    public void addFragment(Fragment fragment) {
        if (fragments != null) {
            fragments.add(fragment.getClass());
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            return (Fragment) fragments.get(position).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
```
Activity 内代码
```
package com.example.viewpager2.withRadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.viewpager2.R;

public class RgActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager2 vpRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rg);
        RgAdapter adapter = new RgAdapter(this);

        RadioGroup rgVp = findViewById(R.id.rg_vp);
        vpRg = findViewById(R.id.vp_rg);
        rgVp.setOnCheckedChangeListener(this);

        vpRg.setAdapter(adapter);
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new MyFragment());
        vpRg.setCurrentItem(0);


        vpRg.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        ((RadioButton) findViewById(R.id.rb_home)).setChecked(true);
                        break;
                    case 1:
                        ((RadioButton) findViewById(R.id.rb_msg)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) findViewById(R.id.rg_my)).setChecked(true);
                        break;
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        switch (checkedId) {
            case R.id.rb_home:
                vpRg.setCurrentItem(0);
                break;
            case R.id.rb_msg:
                vpRg.setCurrentItem(1);
                break;
            case R.id.rg_my:
                vpRg.setCurrentItem(2);
                break;
        }
    }
}
```
这里需要说的是  `registerOnPageChangeCallback` 方法 ，这个方法可以可以监听到 ViewPager 2 的界面变化，进而去操作其他的控件。

### 4、TabLayout 与 ViewPager 2 连用
#### 1）、实现效果
![TabLayout+ViewPager 2 效果](https://upload-images.jianshu.io/upload_images/6433394-d18df846f9c45f5d.gif?imageMogr2/auto-orient/strip)
#### 2）、代码逻辑
###### a、 布局
Activity 布局
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tb_vp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabIndicatorFullWidth="false"
        app:tabMode="scrollable"
        app:tabIndicatorColor="#0371DD"
        app:tabRippleColor="@android:color/transparent"
        app:tabSelectedTextColor="#0371DD"
        app:tabTextColor="#111111" />

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/vp_tb"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```
Fragment 内布局很简单，不再列举了。
###### b、 逻辑
adapt 内部代码
```
package com.example.viewpager2.withTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateTime: 2020/1/15 17:47
 * Author: hxd
 * Content:
 * UpdateTime:
 * UpdateName;
 * UpdateContent:
 */
public class TabAdapter extends FragmentStateAdapter {

    private List<Integer> colors;

    TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (colors == null) {
            colors = new ArrayList<>();
        }
    }

    void addColor(int color) {
        if (colors != null) {
            colors.add(color);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ShowFragment.newInstance(colors, position);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}
```
这里提出一点，笔者 在 RadioGroup 、TabLayout 分别于ViewPager 2 的连用过程中分别使用了两种方法 将 Fragment 传给 adapter 这样的操作可以让各位的 Leaks 分析工具报出 内存泄漏。

Fragment 内的代码
```
package com.example.viewpager2.withTab;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.viewpager2.R;

import java.util.ArrayList;
import java.util.List;


public class ShowFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }
    
    static ShowFragment newInstance(List<Integer> colors, int item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("color", (ArrayList<Integer>) colors);
        bundle.putInt("item", item);
        ShowFragment fragment = new ShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.<FrameLayout>findViewById(R.id.fl_show)
                .setBackgroundResource(((ArrayList<Integer>) getArguments()
                        .getSerializable("color")).get(getArguments().getInt("item")));
        view.<TextView>findViewById(R.id.tv_show).setText("第 " + (getArguments().getInt("item") + 1) + " 个页面");
        super.onViewCreated(view, savedInstanceState);
    }
}
```
这里有一个静态方法 来接收 传入的数据。
这里 Activity 与 RadioGroup 与ViewPager 2 连用方式 很相似的没什么可说的，不再复述。

###### c、 google 官方 bug 
仔细观察这个案例 ，你会发现在ViewPager 2滑动的时候 TabLayout  的下划线切换了，但是 TabLayout 的字体颜色没有随之改变，但是在点击T abLayout  的 tab 标签的时候，下面的下划线和 ViewPager 2 是联动效果是有的，目前百度里各个大佬是没有解决方案的，所以笔者把这个 bug 提交给了 google 官方就是下面这个样子
![提交 bug](https://upload-images.jianshu.io/upload_images/6433394-d955bcc667d38d77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

现在陡然觉得，学英语真有用。
##  四、源码
闲话不多说了把源码贴上

