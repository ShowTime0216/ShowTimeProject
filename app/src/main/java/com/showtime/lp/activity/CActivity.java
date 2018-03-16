package com.showtime.lp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Trouble Maker
 * @date: 2017/9/20 0020
 * @Description:
 */
public class CActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        ButterKnife.bind(this);
        titleText.setText("Activity CCCC");
        text1.setText("跳转到D页面！！！");
        text2.setText("直接返回到A页面！！！");
    }

    @OnClick({R.id.left_layout, R.id.text1, R.id.text2, R.id.text3, R.id.text4})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.text1:
//                setResult(RESULT_OK);
//                finish();
                intent = new Intent(CActivity.this, DActivity.class);
                startActivity(intent);
                break;
            case R.id.text2:
                intent = new Intent(CActivity.this, AActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.text3:
                break;
            case R.id.text4:
                break;
        }
    }

}
