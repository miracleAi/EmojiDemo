package com.example.zhulinping.emojidemo.emohiview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhulinping.emojidemo.R;
import com.example.zhulinping.emojidemo.adapter.PageSetAdapter;
import com.example.zhulinping.emojidemo.data.bean.PageSetBean;
import com.example.zhulinping.emojidemo.utils.EmojiKeyboardUtils;

import java.util.ArrayList;

/**
 * Created by zhulinping on 2017/9/1.
 */

public class EmojiKeyboardLayout extends AutoHeightLayout implements View.OnClickListener,FuncLayout.OnFuncChangeListener,
        EmojiEdittext.OnBackKeyClickListener,EmojiToolBarView.OnToolBarItemClickListener,EmojiShowPageView.OnEmoticonsPageViewListener{
    //弹出view类型为表情键盘
    public static final int FUNC_TYPE_EMOTION = -1;
    //弹出view为应用列表
    public static final int FUNC_TYPE_APPPS = -2;
    protected LayoutInflater mInflater;
    private Button mSendBtn;
    private EmojiEdittext mEdtxt;
    private ImageView mFaceBtn;
    private FuncLayout mEmojiLayout;
    protected boolean mDispatchKeyEventPreImeLock = false;
    //显示表情的view
    private EmojiShowPageView mPageView;
    //底部显示表情集图标view
    private EmojiToolBarView mToolBarView;
    //表情集多页引导indicator
    private EmojiIndicatorView mIndicatorView;


    public EmojiKeyboardLayout(Context context, AttributeSet arrs) {
        super(context, arrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateKeyboard();
        initView();
        initEmojiView();
    }

    private void initView() {
        mSendBtn = findViewById(R.id.btn_send);
        mEdtxt = findViewById(R.id.et_chat);
        mFaceBtn = findViewById(R.id.btn_face);
        mEmojiLayout = findViewById(R.id.emoji_page_fun_layout);

        mSendBtn.setOnClickListener(this);
        mFaceBtn.setOnClickListener(this);

        mEdtxt.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!mEdtxt.isFocused()) {
                    mEdtxt.setFocusable(true);
                    mEdtxt.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
    }

    private void initEmojiView() {
        View keybordView = inflateEmojiLayout();
        mEmojiLayout.addFuncView(FUNC_TYPE_EMOTION, keybordView);
        View view = mInflater.inflate(R.layout.test,null);
        //mEmojiLayout.addFuncView(FUNC_TYPE_APPPS, view);
        mPageView = keybordView.findViewById(R.id.emoji_page_view);
        mIndicatorView = keybordView.findViewById(R.id.emoji_indicator_view);
        mToolBarView = keybordView.findViewById(R.id.emoji_toolbar_view);

        mPageView.setOnIndicatorListener(this);
        mToolBarView.setOnToolBarItemClickListener(this);
        mEmojiLayout.setOnFuncChangeListener(this);
    }
    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetBean> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetBean pageSetEntity : pageSetEntities) {
                    mToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mPageView.setAdapter(pageSetAdapter);
    }
    //引入整个弹起布局
    private void inflateKeyboard() {
        mInflater.inflate(R.layout.keyboart_layout, this);
    }
    //返回表情显示布局，添加到弹起布局的功能layout里
    private View inflateEmojiLayout() {
        return mInflater.inflate(R.layout.emoji_layout, null);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        //调整表情显示布局的高度，与软键盘相等
        mEmojiLayout.updateHeight(height);
    }

    @Override
    public void onSoftPop(int height) {
        super.onSoftPop(height);
        //软键盘弹出，显示功能布局，表情符号显示为未选中
        mEmojiLayout.setVisibility(true);
        onFuncChange(mEmojiLayout.DEF_KEY);
    }

    @Override
    public void onSoftClose() {
        super.onSoftClose();
        //若只有软键盘弹出，功能布局非表情或应用，则恢复初始状态
        if(mEmojiLayout.isOnlyShowSoftKeyboard()){
            reset();
        }else{
            onFuncChange(mEmojiLayout.getCurrentFuncKey());
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_face:
                mEmojiLayout.toggleFuncView(FUNC_TYPE_EMOTION,isSoftKeyboardPop(),mEdtxt);
                break;
            case R.id.btn_send:
                break;
        }


    }
    private void reset() {
        EmojiKeyboardUtils.closeSoftKeyboard(mContext);
        mEmojiLayout.hideAllFuncView();
        mFaceBtn.setImageResource(R.drawable.icon_face_nomal);
    }
    //在表情键盘弹起或隐藏的时候控制页面其他控件相关操作
    public void addOnKeyBoardListener(FuncLayout.OnFuncKeyBoardListener listener){
        mEmojiLayout.addOnKeyBoardListener(listener);
    }

    @Override
    public void onFuncChange(int key) {
        if(key == FUNC_TYPE_EMOTION){
            mFaceBtn.setImageResource(R.drawable.icon_face_pop);
        }else{
            mFaceBtn.setImageResource(R.drawable.icon_face_nomal);
        }
    }

    @Override
    public void onBackKeyClick() {
        if (mEmojiLayout.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (mEmojiLayout.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onToolBarItemClick(PageSetBean pageSetEntity) {
        mPageView.setCurrentPageSet(pageSetEntity);
        mToolBarView.setToolBtnSelect(pageSetEntity.getEmojiSetId());
    }

    @Override
    public void emoticonSetChanged(PageSetBean pageSetEntity) {
        mToolBarView.setToolBtnSelect(pageSetEntity.getEmojiSetId());
    }

    @Override
    public void playTo(int position, PageSetBean pageSetEntity) {
        mIndicatorView.playTo(position,pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetBean pageSetEntity) {
        mIndicatorView.playBy(oldPosition,newPosition,pageSetEntity);
    }
    public EmojiEdittext getEtChat() { return mEdtxt; }
    public EmojiToolBarView getEmoticonsToolBarView() {
        return mToolBarView;
    }
}
