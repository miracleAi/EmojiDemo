package com.example.zhulinping.emojidemo.display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhulinping.emojidemo.R;
import com.example.zhulinping.emojidemo.adapter.PageSetAdapter;
import com.example.zhulinping.emojidemo.data.EmojiModel;
import com.example.zhulinping.emojidemo.data.bean.EmojiBean;
import com.example.zhulinping.emojidemo.data.bean.EmoticonEntity;
import com.example.zhulinping.emojidemo.emohiview.EmojiKeyboardLayout;
import com.example.zhulinping.emojidemo.interfaces.EmoticonClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmojiActivity extends AppCompatActivity implements EmoticonClickListener {

    @BindView(R.id.lv_chat)
    ListView lvChat;
    @BindView(R.id.ek_bar)
    EmojiKeyboardLayout ekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EmojiModel.init(ekBar.getEtChat());
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        //EmojiModel.addEmotionSet(pageSetAdapter,this);
        EmojiModel.addEmojiSet(this, pageSetAdapter, this);
        EmojiModel.addXhsPageSetEntity(pageSetAdapter, this);
        ekBar.setAdapter(pageSetAdapter);
        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmojiActivity.this, "ADD", Toast.LENGTH_SHORT).show();
            }
        });
        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmojiActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
        if (isDelBtn) {
            EmojiModel.delClick(ekBar.getEtChat());
        } else {
            if (o == null) {
                return;
            }
            String content = null;
            if (o instanceof EmojiBean) {
                content = ((EmojiBean) o).arraySpec;
            } else if (o instanceof EmoticonEntity) {
                content = ((EmoticonEntity) o).getContent();
            }

            if (TextUtils.isEmpty(content)) {
                return;
            }
            int index = ekBar.getEtChat().getSelectionStart();
            Editable editable = ekBar.getEtChat().getText();
            editable.insert(index, content);
        }
    }
}
