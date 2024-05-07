package com.softarena.checagocoffee.Acitivity.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.softarena.checagocoffee.Acitivity.Home.AllNews;
import com.softarena.checagocoffee.Acitivity.Home.NewsDetailsAdapter;
import com.softarena.checagocoffee.R;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recycler_news;
    private ArrayList<AllNews> newsModelList;
    private NewsDetailsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recycler_news = findViewById(R.id.recycler_news);
        newsModelList = new ArrayList<>();
        recycler_news.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        newsAdapter=new NewsDetailsAdapter(newsModelList,this);
        recycler_news.setAdapter(newsAdapter);
        List<AllNews> serializableExtraList = (List<AllNews>) getIntent().getSerializableExtra("model");
        newsModelList.addAll(serializableExtraList);
        newsAdapter.notifyDataSetChanged();

    }
}