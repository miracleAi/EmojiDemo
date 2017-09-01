package com.example.zhulinping.emojidemo.emohiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhulinping.emojidemo.R;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiKeyboardLayout extends AutoHeightLayout implements View.OnClickListener{
    protected LayoutInflater mInflater;
    private Button mSendBtn;
    private EmojiEdittext mEdtxt;
    private ImageView mFaceBtn;

    public EmojiKeyboardLayout(Context context, AttributeSet arrs) {
        super(context,arrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateKeyboard();
        initView();
        initEmojiView();
    }

    private void initView() {
        mSendBtn = findViewById(R.id.btn_send);
        mEdtxt = findViewById(R.id.et_chat);
        mFaceBtn = findViewById(R.id.btn_face);

        mSendBtn.setOnClickListener(this);
        mFaceBtn.setOnClickListener(this);


    }

    private void initEmojiView() {
    }

    private void inflateKeyboard() {
        mInflater.inflate(R.layout.keyboart_layout, null);
    }

    private View inflateEmojiLayout() {
        return mInflater.inflate(R.layout.keyboart_layout, null);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {

    }

    @Override
    public void onClick(View view) {

    }
}
