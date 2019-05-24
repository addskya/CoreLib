package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Orange on 18-12-27.
 * Email:addskya@163.com
 * 支持上拉加载更多,下拉刷新的RecyclerView
 */
public class RecyclerList extends XRecyclerView {

    private LoadingListenerWrapper mWrapper;

    public RecyclerList(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerList(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLoadingMoreEnabled(false);
        setPullRefreshEnabled(false);
    }

    @android.databinding.BindingAdapter(
            value = {"app:onRefresh", "app:onLoadMore"})
    public static void setListener(@NonNull RecyclerList view,
                                   @Nullable OnRefresh onRefresh,
                                   @Nullable OnLoadMore loadMore) {
        view.setOnRefresh(onRefresh);
        view.setOnLoadMore(loadMore);
    }

    public void setOnRefresh(@Nullable OnRefresh onRefresh) {
        if (mWrapper == null) {
            mWrapper = new LoadingListenerWrapper();
        }
        mWrapper.injectOnRefresh(onRefresh);
        setPullRefreshEnabled(true);
        setLoadingListener(mWrapper);
    }

    public void setOnLoadMore(@Nullable OnLoadMore onLoadMore) {
        if (mWrapper == null) {
            mWrapper = new LoadingListenerWrapper();
        }
        mWrapper.injectOnLoadMore(onLoadMore);
        setLoadingMoreEnabled(true);
        setLoadingListener(mWrapper);
    }

    private final class LoadingListenerWrapper implements LoadingListener {

        private OnRefresh mOnRefresh;
        private OnLoadMore mOnLoadMore;

        private void injectOnRefresh(@Nullable OnRefresh onRefresh) {
            mOnRefresh = onRefresh;
        }

        private void injectOnLoadMore(@Nullable OnLoadMore onLoadMore) {
            mOnLoadMore = onLoadMore;
        }

        @Override
        public void onRefresh() {
            if (mOnRefresh != null) {
                mOnRefresh.onRefresh();
            }
        }

        @Override
        public void onLoadMore() {
            if (mOnLoadMore != null) {
                mOnLoadMore.onLoadMore();
            }
        }
    }
}
