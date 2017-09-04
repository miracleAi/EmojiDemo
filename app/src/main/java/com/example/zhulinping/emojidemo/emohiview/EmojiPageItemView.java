package com.example.zhulinping.emojidemo.emohiview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.zhulinping.emojidemo.R;

/**
 * Created by zhulinping on 2017/9/4.
 */

public class EmojiPageItemView extends RelativeLayout{
    private GridView mGvEmotion;

    public GridView getEmoticonsGridView() {
        return mGvEmotion;
    }

    public EmojiPageItemView(Context context) {
        this(context, null);
    }

    public EmojiPageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.emoji_page_layout, this);
        mGvEmotion = (GridView) view.findViewById(R.id.gv_emotion);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            mGvEmotion.setMotionEventSplittingEnabled(false);
        }
        mGvEmotion.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mGvEmotion.setCacheColorHint(0);
        mGvEmotion.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGvEmotion.setVerticalScrollBarEnabled(false);
    }

    public void setNumColumns(int row) {
        mGvEmotion.setNumColumns(row);
    }
}
