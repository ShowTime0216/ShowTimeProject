package com.showtime.lp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.view.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Trouble Maker
 * @date: 2017/9/5 0005
 * @Description:
 */
public class ViewScrollActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.left_btn)
    TextView leftBtn;
    @BindView(R.id.right_btn)
    TextView rightBtn;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.index_text)
    TextView indexText;
    private ViewScrollAdapter viewScrollAdapter;
    private int pointIndex = 0;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_scroll);
        ButterKnife.bind(this);


        initView();

    }

    private void initView() {

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("k");
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewScrollAdapter = new ViewScrollAdapter(ViewScrollActivity.this, list);
        recyclerView.setAdapter(viewScrollAdapter);
    }

    @OnClick({R.id.left_btn, R.id.right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_btn:
                pointIndex++;
//                viewScrollAdapter.notifyDataSetChanged();
//                recyclerView.scrollToPosition(pointIndex);
                TextView textView = (TextView) recyclerView.getChildAt(pointIndex - linearLayoutManager.findFirstVisibleItemPosition()).findViewById(R.id.point_text);
                textView.setVisibility(View.VISIBLE);
                break;
            case R.id.right_btn:
                pointIndex++;
//                viewScrollAdapter.notifyDataSetChanged();
                break;
        }
    }

    class ViewScrollAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private LayoutInflater inflater;
        private List<String> list;

        public ViewScrollAdapter(Context context, List<String> list) {
            this.mContext = context;
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View view = inflater.inflate(R.layout.item_example_scroll, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            if (position == 0) {
                holder.line_view.setVisibility(View.VISIBLE);
            } else {
                holder.line_view.setVisibility(View.GONE);
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends MyViewHolder {
            private View line_view;
            private TextView number_text;
            private TextView name_text;
            private TextView score_text;
            private TextView is_text;
            private TextView point_text;

            public ViewHolder(View convertView) {
                super(convertView);
                line_view = convertView.findViewById(R.id.line_view);
                number_text = (TextView) convertView.findViewById(R.id.number_text);
                name_text = (TextView) convertView.findViewById(R.id.name_text);
                score_text = (TextView) convertView.findViewById(R.id.score_text);
                is_text = (TextView) convertView.findViewById(R.id.is_text);
                point_text = (TextView) convertView.findViewById(R.id.point_text);
            }
        }
    }
}
