package net.youlvke.yanyuke.activity;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.youlvke.yanyuke.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 放大图片
 */
public class EnlargePicActivity extends BaseActivity {

    private PhotoViewAttacher attacher;

    protected boolean isToolBarHiding = false;
    private ArrayList<String> imgPaths;
    private int position;
    private TextView tvIndicator;
    private ImageView ivPic;
    private AppBarLayout rlBgColor;
    private ImageView ivTabDelete;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_enlarge_pic;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imgPaths = bundle.getStringArrayList("imgPaths");
            position=bundle.getInt("position");
        }


        tvIndicator = (TextView) findViewById(R.id.tv_indicator);
        ivPic = (ImageView) findViewById(R.id.iv_enlarge_pic);
        rlBgColor = (AppBarLayout) findViewById(R.id.rl_bg_color);
        ivTabDelete = (ImageView) findViewById(R.id.iv_tab_delete);

        tvIndicator.setText((position+1)+"/"+imgPaths.size());

        attacher = new PhotoViewAttacher(ivPic);
        Glide.with(this).load(imgPaths.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivPic.setImageBitmap(resource);
                attacher.update();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ivPic.setImageDrawable(errorDrawable);
            }
        });

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowTab();
            }

            @Override
            public void onOutsidePhotoTap() {

                hideOrShowTab();
            }
        });
    }

    private void hideOrShowTab() {
        rlBgColor.animate()
                .translationY(isToolBarHiding ? 0 : -rlBgColor.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

    @Override
    protected void initListener() {
        ivTabDelete.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        attacher.cleanup();
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.iv_tab_delete:
                setResult(RESULT_OK);
                finish();

        }
    }
}