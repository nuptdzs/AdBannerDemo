# AdBannerDemo
安卓广告循环播放
---
安卓广告栏循环播放时很多app都会有的功能，实现方法也有很多，常见的有gallery和viewpager，今天使用github上一个广告栏框架实现这一功能。

+ Step 1：
在你的工程gradle下添加依赖

  > compile'com.bigkoo:convenientbanner:2.0.5'
+ Step 2：
 创建banner布局
  ``` xml
  <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:orientation="vertical"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

      <com.bigkoo.convenientbanner.ConvenientBanner
          android:id="@+id/banner"
          android:layout_width="match_parent"
          app:canLoop="true"
          android:layout_height="160dp">

      </com.bigkoo.convenientbanner.ConvenientBanner>
  </RelativeLayout>
  ```
+ Step 3 :
 创建banner中的item布局
   ``` xml
     <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout 
    	xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">

        <ImageView
            android:id="@id/bannerImage"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:scaleType="centerCrop"
             />

        <TextView
            android:id="@id/bannerFlag"
            android:layout_width="wrap_content"
            android:layout_height="22.0dip"
            android:layout_alignBottom="@id/bannerImage"
            android:layout_alignRight="@id/bannerImage"
            android:layout_marginBottom="3.0dip"
            android:background="@drawable/index_banner_tag_blue"
            android:gravity="center"
            android:text="推荐"
            android:paddingLeft="12.669983dip"
            android:paddingRight="10.0dip"
            android:shadowColor="#33000000"
            android:textColor="#ffffffff"
            android:textSize="10.669983sp" />
    </RelativeLayout>
   ```
+ Step 4：
 	构造BannerItemViewHolder处理Item布局的呈现效果
    ``` java
    public class BannerHolderView implements Holder<BannerBean> {
        @BindView(R.id.bannerImage)
        ImageView bannerImage;
        @BindView(R.id.bannerFlag)
        TextView bannerFlag;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            ButterKnife.bind(this,view);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            Glide.with(getContext()).load(data.getImgUrl()).into(bannerImage);
            bannerFlag.setText(data.getTag());

        }
    }
    ```
+ Step 5：
 	在activity或者fragment中对banner进行初始化并加载布局
    ``` java
     //对应banner中需要填充的数据模型，可根据所需自定义属性 一般包括对应图片和你点击所需要跳转的链接或者标识你广告类型的Type等；
    final List<BannerBean> bannerList = BannerBean.getBanners();
    //在这里我使用banner作为listview的头部所以需要另外载入
    banner = LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, null);
   //从对应父布局加载你的banner
    convenientBanner = (ConvenientBanner<BannerBean>) banner.findViewById(R.id.banner);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();//导入对应的banneritem进行数据呈现
            }
        }, bannerList);
    //这里设置底部小点的样式，若不设置则不显示
    convenientBanner.setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focused});
    ```
+ Step 6：
 设置自动切换
 需要在activity的onresume和onPause里加入以下代码。
   ``` java
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
   ```
+ PS
 如果需要添加切换动画效果可引入这个库，因为本项目是基于viewpager实现的，所以只需要对viewpager设置切换动画效果即可
 
    > compile 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar'
  
    ``` java 
      convenientBanner.getViewPager().setPageTransformer(true, transforemer);
    ``` 
    具体的动画效果实现请参考
    https://github.com/ToxicBakery/ViewPagerTransforms
    
>## 最后
   感谢该项目的[作者][1]   
   希望大家在使用中可以互相交流
   附上我写的demo [点击查看][2]


  [1]: https://github.com/saiwu-bigkoo/Android-ConvenientBanner
  [2]: https://github.com/nuptdzs/AdBannerDemo
