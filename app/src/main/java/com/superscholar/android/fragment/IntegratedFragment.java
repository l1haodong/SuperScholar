package com.superscholar.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.superscholar.android.R;
import com.superscholar.android.entity.News;
import com.superscholar.android.holder.NetworkImageHolderView;
import com.superscholar.android.holder.NewsBannerHolderView;
import com.superscholar.android.tools.ServerConnection;
import com.superscholar.android.activity.ImageViewActivity;
import com.superscholar.android.activity.MemoActivity;
import com.superscholar.android.activity.WebActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhqy on 2017/6/14.
 */

public class IntegratedFragment extends Fragment{
    private List<String> networkImages;  //网络图片库url
    private String[] images = {
            ServerConnection.getBannerImageUrl(1),
            ServerConnection.getBannerImageUrl(2),
            ServerConnection.getBannerImageUrl(3),
            ServerConnection.getBannerImageUrl(4),
            ServerConnection.getBannerImageUrl(5),
            ServerConnection.getBannerImageUrl(6),
            ServerConnection.getBannerImageUrl(7)
    };
    private ConvenientBanner pictureBanner;  //综合界面图片Banner
    private ConvenientBanner newsBanner;   //综合界面新闻Banner
    private List<News>newses=new ArrayList<>();
    private List<String>newsTitles=new ArrayList<>();

    //按钮初始化
    private void initButton(View view){
        ImageButton memoButton=(ImageButton)view.findViewById(R.id.integrated_memo_button);
        memoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MemoActivity.class);
                startActivity(intent);
            }
        });
    }

    //图片横幅初始化
    private void initPictureBanner(View view){
        networkImages= Arrays.asList(images);
        pictureBanner=(ConvenientBanner)view.findViewById(R.id.integrated_topBanner);

        pictureBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageTransformer(new ZoomInTransformer())
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(getActivity(), ImageViewActivity.class);
                        intent.putExtra("imageUrl",networkImages.get(position));
                        startActivity(intent);
                    }
                });
        pictureBanner.setScrollDuration(1500);
    }

    //新闻横幅初始化
    private void initNewsBanner(View view){
        newsBanner=(ConvenientBanner)view.findViewById(R.id.integrated_newsBanner);

        //获取新闻数据
        ServerConnection.connectNewsAPI(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"网络异常，数据获取失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData =response.body().string();
                try{
                    JSONObject responseObject = new JSONObject(responseData);
                    String resultData=responseObject.getString("result");
                    JSONObject resultObject=new JSONObject(resultData);
                    String newsData=resultObject.getString("data");
                    JSONArray newsArray=new JSONArray(newsData);
                    for(int i=0;i<newsArray.length();i++){
                        JSONObject jsonObject=newsArray.getJSONObject(i);
                        News news=new News();
                        news.setUniquekey(jsonObject.getString("uniquekey"));
                        news.setTitle(jsonObject.getString("title"));
                        news.setUrl(jsonObject.getString("url"));
                        newses.add(news);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
                for(News news:newses){
                    newsTitles.add(news.getTitle());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsBanner.setPages(new CBViewHolderCreator<NewsBannerHolderView>() {
                            @Override
                            public NewsBannerHolderView createHolder() {
                                return new NewsBannerHolderView();
                            }
                        },newsTitles)
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent=new Intent(getActivity(),WebActivity.class);
                                        intent.putExtra("url",newses.get(position).getUrl());
                                        startActivity(intent);
                                    }
                                });
                    }
                });
            }
        });
    }

    //网络图片加载器初始化
    private void initImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.banner_image_null)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    @Override
    public void onResume() {
        super.onResume();
        pictureBanner.startTurning(4000);
        newsBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        pictureBanner.stopTurning();
        newsBanner.stopTurning();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.page_integrated,container,false);
        initImageLoader();
        initButton(view);
        initPictureBanner(view);
        initNewsBanner(view);
        return view;
    }
}
