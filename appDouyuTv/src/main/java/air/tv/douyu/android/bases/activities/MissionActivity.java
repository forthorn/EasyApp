package air.tv.douyu.android.bases.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.harreke.easyapp.frameworks.bases.IFramework;
import com.harreke.easyapp.frameworks.bases.activity.ActivityFramework;
import com.harreke.easyapp.frameworks.recyclerview.RecyclerFramework;
import com.harreke.easyapp.frameworks.recyclerview.RecyclerHolder;
import com.harreke.easyapp.widgets.transitions.SwipeToFinishLayout;

import air.tv.douyu.android.R;
import air.tv.douyu.android.bases.application.DouyuTv;
import air.tv.douyu.android.beans.Room;
import air.tv.douyu.android.holders.RoomHolder;

/**
 * 由 Harreke（harreke@live.cn） 创建于 2014/12/18
 */
public class MissionActivity extends ActivityFramework {
    private FollowRecyclerHelper mFollowRecyclerHelper;
    private String mToken;

    public static Intent create(Context context) {
        return new Intent(context, MissionActivity.class);
    }

    @Override
    protected void acquireArguments(Intent intent) {
        mToken = DouyuTv.getInstance().readUser().getToken();
    }

    @Override
    public void attachCallbacks() {
    }

    @Override
    protected void configActivity() {
        attachTransition(new SwipeToFinishLayout(this));
    }

    @Override
    protected void createMenu() {
        setToolbarTitle(R.string.app_mission);
        setToolbarNavigation();
    }

    @Override
    public void enquiryViews() {
        mFollowRecyclerHelper = new FollowRecyclerHelper(this);
        mFollowRecyclerHelper.setHasFixedSize(true);
        mFollowRecyclerHelper.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFollowRecyclerHelper.attachAdapter();
    }

    @Override
    public void establishCallbacks() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        start(SearchActivity.create(this));

        return false;
    }

    @Override
    public void startAction() {
        //        mFollowRecyclerHelper.from(API.getFollow(mToken, 20, mFollowRecyclerHelper.getCurrentPage()));
    }

    private class FollowRecyclerHelper extends RecyclerFramework<Room> {
        public FollowRecyclerHelper(IFramework framework) {
            super(framework);
        }

        @Override
        protected RecyclerHolder<Room> createHolder(View itemView, int viewType) {
            return new RoomHolder(itemView);
        }

        @Override
        protected View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return inflater.inflate(R.layout.item_room, parent, false);
        }

        @Override
        public void onItemClick(int position, Room room) {
            start(RoomActivity.create(getContext(), room.getRoom_id()));
        }
    }
}