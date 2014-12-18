package tv.acfun.read.bases.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harreke.easyapp.frameworks.bases.IFramework;
import com.harreke.easyapp.frameworks.bases.fragment.FragmentFramework;
import com.harreke.easyapp.frameworks.lists.recyclerview.RecyclerFramework;
import com.harreke.easyapp.holders.recycerview.RecyclerHolder;

import java.util.ArrayList;

import tv.acfun.read.R;
import tv.acfun.read.api.API;
import tv.acfun.read.bases.activities.ContentActivity;
import tv.acfun.read.beans.Content;
import tv.acfun.read.holders.RecommendHolder;
import tv.acfun.read.parsers.ChannelListParser;

/**
 * 由 Harreke（harreke@live.cn） 创建于 2014/09/23
 */
public class RecommendFragment extends FragmentFramework {
    private RecommendRecyclerHelper mRecommendRecyclerHelper;

    @Override
    public void acquireArguments(Bundle bundle) {
    }

    @Override
    public void attachCallbacks() {
    }

    @Override
    public void enquiryViews() {
        mRecommendRecyclerHelper = new RecommendRecyclerHelper(this);
        mRecommendRecyclerHelper.setHasFixedSize(false);
        mRecommendRecyclerHelper.attachAdapter();
    }

    @Override
    public void establishCallbacks() {
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.fragment_recommend);
    }

    @Override
    public void startAction() {
        mRecommendRecyclerHelper.from(API.getChannelRecommend("110,73,74,75", 10));
    }

    private class RecommendRecyclerHelper extends RecyclerFramework<Content> {
        public RecommendRecyclerHelper(IFramework framework) {
            super(framework);
        }

        @Override
        public RecyclerHolder<Content> createHolder(View convertView, int viewType) {
            return new RecommendHolder(convertView);
        }

        @Override
        public View createView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(getActivity()).inflate(R.layout.item_recommend, parent, false);
        }

        @Override
        public void onRequestAction() {
            startAction();
        }

        @Override
        public void onItemClick(int position, Content content) {
            start(ContentActivity.create(getActivity(), content.getContentId()));
        }

        @Override
        public ArrayList<Content> onParse(String json) {
            ChannelListParser parser = ChannelListParser.parse(json);

            if (parser != null) {
                return parser.getItemList();
            } else {
                return null;
            }
        }
    }
}