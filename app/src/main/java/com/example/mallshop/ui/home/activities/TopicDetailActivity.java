package com.example.mallshop.ui.home.activities;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.bean.TopicDetailBean;
import com.example.mallshop.bean.TopicDetailCommentBean;
import com.example.mallshop.bean.TopicDetailRecommendListBean;
import com.example.mallshop.constract.detail.IDetail;
import com.example.mallshop.presenter.detail.TopicDetailPresenter;
import com.example.mallshop.ui.home.adapter.TopicCommentAdapter;
import com.example.mallshop.ui.home.adapter.TopicRecommendAdapter;

import butterknife.BindView;

public class TopicDetailActivity extends BaseActivity<IDetail.ITopicDetailPresenter> implements IDetail.ITopicDetailView, View.OnClickListener {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.rcl_comment)
    RecyclerView rclComment;
    @BindView(R.id.rcl_topic_list)
    RecyclerView rclTopicList;
    @BindView(R.id.img_back)
    ImageView imgBack;

    private String htmlStr = "<html>\n" +
            "            <head>" +
            "                        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
            "                        <style>*{margin:0;padding:0;}img{max-width: 100%; width:auto; height:auto;}</style>" +
            "            </head>" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private TopicCommentAdapter topicCommentAdapter;
    private TopicRecommendAdapter topicRecommendAdapter;

    @Override
    protected IDetail.ITopicDetailPresenter initPresenter() {
        return new TopicDetailPresenter();
    }

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        presenter.getTopic("topic/detail?id=" + id);
        presenter.getTopicRecommend("topic/related?id=" + id);
        presenter.getTopicComment("comment/list?valueId="+id+"&typeId=1&size=5");
    }

    @Override
    protected void initView() {
        imgBack.setOnClickListener(this);
        rclComment.setLayoutManager(new LinearLayoutManager(this));
        topicCommentAdapter = new TopicCommentAdapter(this);
        rclComment.setAdapter(topicCommentAdapter);
        topicRecommendAdapter = new TopicRecommendAdapter(this);
        rclTopicList.setLayoutManager(new LinearLayoutManager(this));
        rclTopicList.setAdapter(topicRecommendAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_topic_detail;
    }

    @Override
    public void setTopic(TopicDetailBean topic) {
        String content = topic.getData().getContent();
        updateWeb(content);
    }

    @Override
    public void setTopicRecommendList(TopicDetailRecommendListBean topicRecommendListBean) {
        topicRecommendAdapter.setData(topicRecommendListBean.getData());
    }

    @Override
    public void setTopicComment(TopicDetailCommentBean topicCommentBean) {
        topicCommentAdapter.setData(topicCommentBean.getData().getData());
    }

    private void updateWeb(String content) {
        int start = 0;
        int index = content.indexOf("//");
        String html = content.substring(index+2);
        StringBuilder sb = new StringBuilder();
        sb.append(content.substring(start,index));
        while(index != -1){
            sb.append("http://");
            index = html.indexOf("//");
            if (index == -1) {
                sb.append(html);
                break;
            }
            sb.append(html.substring(start,index));
            html = html.substring(index+2);
        }
        htmlStr = htmlStr.replace("$",sb.toString());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(htmlStr, "text/html; charset=utf-8", "utf-8");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
