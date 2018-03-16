package com.showtime.lp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:liupeng
 * 16/7/21 16:58
 * 注释:
 */
public class MineFragment extends BaseFragment implements EditText.OnEditorActionListener {
    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;
    @BindView(R.id.edit4)
    EditText edit4;
    @BindView(R.id.edit5)
    EditText edit5;
    @BindView(R.id.edit6)
    EditText edit6;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.text6)
    TextView text6;
    private View view;
    private TextView titleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (view == null) {
        view = inflater.inflate(R.layout.frag_mine, container, false);

//        }
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//        }
        ButterKnife.bind(this, view);
        initView();
        edit1.setOnEditorActionListener(this);
        edit2.setOnEditorActionListener(this);
        edit3.setOnEditorActionListener(this);
        edit4.setOnEditorActionListener(this);
        edit5.setOnEditorActionListener(this);
        edit6.setOnEditorActionListener(this);
        return view;
    }

    private void initView() {
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleText.setText("我");
        edit1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("11----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("11----", "-------");

                }
                return false;
            }
        });
        edit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("22----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("22----", "-------");
                }
                return false;
            }
        });
        edit3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("33----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("33----", "-------");
                }
                return false;
            }
        });
        edit4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("44----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("44----", "-------");
                }
                return false;
            }
        });
        edit5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("55----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("55----", "-------");
                }
                return false;
            }
        });
        edit6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("66----", keyCode + "   -------   " + event);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.e("66----", "-------");

                }
                return false;
            }
        });

    }

    @OnClick({R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text1:
                String[] str = new String[]{"1", "2", "3", "4", "5"};
                List<String> list = Arrays.asList(str);
                ToastUtils.showToast(getActivity(), list.get(0) + "     " + list.get(2));
                break;
            case R.id.text2:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("123123");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.text3:
//                try {
//                    TextView textView = null;
//                    textView.setText("----");
//                } catch (Exception e) {
////                    Log.e("e-------", e + "");
//                }
                break;
            case R.id.text4:
                break;
            case R.id.text5:
                break;
            case R.id.text6:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.e("-------", actionId + "    " + event);
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                Log.e("BALLACK", "IME_ACTION_DONE");
                break;
            case EditorInfo.IME_ACTION_GO:
                Log.e("BALLACK", "IME_ACTION_GO");
                break;
            case EditorInfo.IME_ACTION_NEXT:
                Log.e("BALLACK", "IME_ACTION_NEXT");
                break;
            case EditorInfo.IME_ACTION_NONE:
                Log.e("BALLACK", "IME_ACTION_NONE");
                break;
            case EditorInfo.IME_ACTION_PREVIOUS:
                Log.e("BALLACK", "IME_ACTION_PREVIOUS");
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                Log.e("BALLACK", "IME_ACTION_SEARCH");
                break;
            case EditorInfo.IME_ACTION_SEND:
                Log.e("BALLACK", "IME_ACTION_SEND");
                break;
            case EditorInfo.IME_ACTION_UNSPECIFIED:
                Log.e("BALLACK", "IME_ACTION_UNSPECIFIED");
                break;
            default:
                break;
        }
        return true;
    }

}
