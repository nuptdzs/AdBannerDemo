package com.dzs.adbannerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dzs on 2016/9/9.
 */
public class BannerItemViewHolder implements Holder<BannerBean> {
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
        Glide.with(context).load(data.getImgUrl()).into(bannerImage);
        bannerFlag.setText(data.getTag());

    }
}
