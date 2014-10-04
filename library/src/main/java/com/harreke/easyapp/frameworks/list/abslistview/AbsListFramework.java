package com.harreke.easyapp.frameworks.list.abslistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.harreke.easyapp.adapters.abslistview.AbsListAdapter;
import com.harreke.easyapp.frameworks.bases.IFramework;
import com.harreke.easyapp.frameworks.list.ListFramework;
import com.harreke.easyapp.holders.abslistview.IAbsListHolder;

import java.util.Comparator;

/**
 * 由 Harreke（harreke@live.cn） 创建于 2014/07/31
 *
 * AbsListView框架
 *
 * @param <ITEM>
 *         条目类型
 * @param <HOLDER>
 *         条目容器类型
 */
public abstract class AbsListFramework<ITEM, HOLDER extends IAbsListHolder<ITEM>> extends ListFramework<ITEM>
        implements IAbsList<ITEM, HOLDER>, IAbsListItemClickListener<ITEM>, AdapterView.OnClickListener, AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {
    private AbsListView mAbsListView;
    private Adapter mAdapter;
    private int mHeaderCount = 0;
    private int mScrollState = SCROLL_STATE_IDLE;

    public AbsListFramework(IFramework framework, int listId) {
        super(framework, listId);
    }

    public final void addHeaderView(View view) {
        if (mAbsListView instanceof ListView) {
            ((ListView) mAbsListView).addHeaderView(view);
            mHeaderCount++;
        }
    }

    /**
     * 添加一个条目
     *
     * @param itemId
     *         条目Id，大于等于0，用于检测是否有重复条目
     *         若为-1，则不检测重复条目
     * @param item
     *         条目对象
     *
     * @return 如果添加成功，返回true，否则返回false
     */
    @Override
    public final boolean addItem(int itemId, ITEM item) {
        return mAdapter.addItem(itemId, item);
    }

    /**
     * 设置数据适配器
     */
    @Override
    public void bindAdapter() {
        mAdapter = new Adapter();
        mAbsListView.setAdapter(mAdapter);
    }

    /**
     * 清空列表
     */
    @Override
    public void clear() {
        super.clear();
        mAdapter.clear();
    }

    /**
     * 获得条目
     *
     * @param position
     *         条目位置
     *
     * @return 条目对象
     */
    public final ITEM getItem(int position) {
        return mAdapter.getItem(position);
    }

    /**
     * 获得条目总数
     *
     * @return 条目总数
     */
    @Override
    public int getItemCount() {
        return mAdapter.getCount();
    }

    /**
     * 判断列表是否为空
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < mHeaderCount) {
            onItemClick(position, null);
        } else {
            onItemClick(position, mAdapter.getItem(position - mHeaderCount));
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isLoadEnabled()) {
            if (mScrollState != SCROLL_STATE_IDLE && !isLastPage() && !isLoading()) {
                if (!isReverseScroll() && firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    onAction();
                } else if (isReverseScroll() && firstVisibleItem == 0) {
                    onAction();
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
    }

    /**
     * 刷新列表
     */
    @Override
    public void refresh() {
        mAdapter.refresh();
    }

    /**
     * 设置列表视图
     *
     * @param listView
     *         列表视图
     */
    @Override
    public void setListView(View listView) {
        mAbsListView = (AbsListView) listView;
        mAbsListView.setOnItemClickListener(this);
        mAbsListView.setOnScrollListener(this);
    }

    /**
     * 设置选中条目
     *
     * @param position
     *         条目位置
     */
    public final void setSelection(int position) {
        if (position >= 0 && position < mAdapter.getCount()) {
            mAbsListView.setSelection(position);
        }
    }

    /**
     * 排序列表条目
     *
     * @param comparator
     *         比较器
     */
    @Override
    public void sort(Comparator<ITEM> comparator) {
        mAdapter.sort(comparator);
    }

    private class Adapter extends AbsListAdapter<ITEM> {
        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HOLDER holder;
            ITEM item = getItem(position);

            if (convertView != null) {
                holder = (HOLDER) convertView.getTag();
            } else {
                convertView = createView(item);
                holder = createHolder(convertView);
                convertView.setTag(holder);
            }
            holder.setItem(position, item);

            return convertView;
        }
    }
}