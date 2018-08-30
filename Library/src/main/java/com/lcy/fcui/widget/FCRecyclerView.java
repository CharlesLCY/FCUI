package com.lcy.fcui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author FanCoder.LCY
 * @date 2018/8/28 15:12
 * @email 15708478830@163.com
 * @desc 自定义RecyclerView
 **/
public class FCRecyclerView extends RecyclerView {
    private View emptyView;
    private static final String TAG = "CustomRecyclerView";

    public FCRecyclerView(Context context) {
        super(context);
    }

    public FCRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FCRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private final AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkDataEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkDataEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkDataEmpty();
        }
    };

    private void checkDataEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean showEmptyView = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(showEmptyView? VISIBLE : GONE);
            setVisibility(showEmptyView? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }

        checkDataEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkDataEmpty();
    }
}
