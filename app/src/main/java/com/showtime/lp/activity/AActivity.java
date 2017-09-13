package com.showtime.lp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;

/**
 * @author: Trouble Maker
 * @date: 2017/9/7 0007
 * @Description:
 */
public class AActivity extends BaseActivity {

    private static final int START_NUM = 0;
    private static final int END_NUM = 30;
    private MyAdapter mAdapter;

    private android.widget.TextView tvage;
    private android.support.v7.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        initViews();
        initDatas();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new MyAdapter(this, START_NUM, END_NUM);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.highlightItem(getMiddlePosition());
                    //将位置移动到中间位置
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(getScrollPosition(), 0);
                    System.out.println(getScrollPosition() + "");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                tvage.setText(String.valueOf(getMiddlePosition() + START_NUM));
            }
        });
        mAdapter.highlightItem(getMiddlePosition());
    }

    /**
     * 获取中间位置的position
     *
     * @return
     */
    private int getMiddlePosition() {
        return getScrollPosition() + (mAdapter.ITEM_NUM / 2);
    }

    /**
     * 获取滑动值, 滑动偏移 / 每个格子宽度
     *
     * @return 当前值
     */
    private int getScrollPosition() {
        return (int) (((double) recyclerView.computeHorizontalScrollOffset()
                / (double) mAdapter.getItemWidth()) + 0.5f);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.tvage = (TextView) findViewById(R.id.tv_age);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.AgeItemViewHolder> {


        private Context mContext;
        /**
         * 起始年龄
         */
        private int startNum;
        /**
         * 终止年龄
         */
        private int endNum;
        /**
         * 显示的数据个数
         */
        public int ITEM_NUM = 7;
        /**
         * 当前选中位置，高亮显示
         */
        private int mHighlight = -1;


        public MyAdapter(Context context, int startNum, int endNum) {
            mContext = context;
            this.startNum = startNum;
            this.endNum = endNum;
        }

        @Override
        public AgeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_example_age, parent, false);
            //设置宽度
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int) getItemWidth();
            return new AgeItemViewHolder(view);
        }


        @Override
        public void onBindViewHolder(AgeItemViewHolder holder, int position) {
            holder.mTextView.setText(String.valueOf(startNum + position));
            if (isSelect(position)) {
                holder.mTextView.setTextSize(30);
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.red));
            } else {
                holder.mTextView.setTextSize(20);
                holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.black_8a));
            }
        }

        /**
         * 高亮中心, 更新前后位置
         *
         * @param position 在list中的位置
         */
        public void highlightItem(int position) {
            mHighlight = position;
            int offset = ITEM_NUM / 2;
            for (int i = position - offset; i <= position + offset; ++i)
                notifyItemChanged(i);
        }

        /**
         * 判断某个条目是否是选中状态（居中状态）
         *
         * @param position
         * @return
         */
        private boolean isSelect(int position) {
            return mHighlight == position;
        }

        @Override
        public int getItemCount() {
            return endNum - startNum + 1;
        }

        /**
         * 获取每一个条目的宽度
         *
         * @return
         */
        public float getItemWidth() {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels / ITEM_NUM;
        }


        /**
         * ViewHolder类
         */
        public class AgeItemViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public AgeItemViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_item_age);
                mTextView.setTag(this);
            }

            public TextView getmTextView() {
                return mTextView;
            }
        }
    }
}
