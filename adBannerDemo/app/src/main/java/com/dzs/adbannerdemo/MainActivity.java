package com.dzs.adbannerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * 设置翻页时间
     */
    private static final long DELAY_TIME = 5000;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();
        loadTestData();
        initBanner();

    }

    private List<String> transformerList = new ArrayList<>();
    private ArrayAdapter adapter;
    private void initListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,transformerList);
        listView.setAdapter(adapter);
    }

    private void loadTestData(){
        //对应banner中需要填充的数据模型，可根据所需自定义属性 一般包括对应图片和你点击所需要跳转的链接或者标识你广告类型的Type等；
        bannerList = BannerBean.getBanners();
        //添加切换动作特效
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());
        adapter.notifyDataSetChanged();
    }
    List<BannerBean> bannerList;
    View banner;
    ConvenientBanner<BannerBean> convenientBanner;
    /**
     * 初始化广告栏
     */
    private void initBanner() {
        //在这里我使用banner作为listview的头部所以需要另外载入
        banner = LayoutInflater.from(this).inflate(R.layout.banner_layout, null);
        //从对应父布局加载你的banner
        convenientBanner = (ConvenientBanner<BannerBean>) banner.findViewById(R.id.banner);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerItemViewHolder();
            }
        }, bannerList);
        //这里设置底部小点的样式，若不设置则不显示
        convenientBanner.setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focused});
        //item点击事件
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //根据数据进行不同响应
                BannerBean bannerBean = bannerList.get(position);
            }
        });
        listView.addHeaderView(banner);
        listView.setOnItemClickListener(this);
    }

    //点击切换效果
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String transforemerName = transformerList.get(position);
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);
            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        if(convenientBanner != null){
            convenientBanner.startTurning(DELAY_TIME);
        }
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        if(convenientBanner != null){
            convenientBanner.stopTurning();
        }
    }

}
