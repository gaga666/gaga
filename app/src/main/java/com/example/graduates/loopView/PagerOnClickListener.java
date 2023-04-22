package com.example.graduates.loopView;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.graduates.R;

public class PagerOnClickListener implements View.OnClickListener{

    Context mContext;

    public PagerOnClickListener(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pager_img1:
                Toast.makeText(mContext, "暂无赞助商1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img2:
                Toast.makeText(mContext, "暂无赞助商2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img3:
                Toast.makeText(mContext, "暂无赞助商3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img4:
                Toast.makeText(mContext, "暂无赞助商4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pager_img5:
                Toast.makeText(mContext, "暂无赞助商5", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
