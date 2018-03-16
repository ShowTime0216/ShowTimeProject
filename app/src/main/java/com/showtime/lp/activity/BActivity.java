package com.showtime.lp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
public class BActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text1)
    TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
        titleText.setText("Activity BBBB");
        text1.setText("跳转到C页面！！！");
    }

    @OnClick({R.id.left_layout, R.id.text1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.text1:
                Intent intent = new Intent(BActivity.this, CActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("BB-----", requestCode + "  " + resultCode + "  " + data);
//        if (resultCode == RESULT_OK) {
//            setResult(RESULT_OK);
//            finish();
//        }
    }
}
