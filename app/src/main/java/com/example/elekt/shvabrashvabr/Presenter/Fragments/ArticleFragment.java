package com.example.elekt.shvabrashvabr.Presenter.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elekt.shvabrashvabr.Model.Article;
import com.example.elekt.shvabrashvabr.Presenter.Adaptors.ArticlesRecyclerAdaptor;
import com.example.elekt.shvabrashvabr.Presenter.Manager;
import com.example.elekt.shvabrashvabr.R;

import java.util.Collection;

/**
 * Created by elekt on 20.07.2017.
 */

public class ArticleFragment extends Fragment {
    RecyclerView recyclerViewArticles;
    Collection<Article> articles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.articles_recycler_layout, container, false);
        recyclerViewArticles = (RecyclerView) v.findViewById(R.id.articlesRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewArticles.setLayoutManager(manager);
        recyclerViewArticles.setHasFixedSize(true);
        articles = Manager.getInstance().getArticles();
        final ArticlesRecyclerAdaptor adaptor = new ArticlesRecyclerAdaptor(articles);
        recyclerViewArticles.setAdapter(adaptor);

        // Elements in recyclerView loading when scrolling in order to not download all articles in memory
        recyclerViewArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                int scrollExtent = recyclerView.computeVerticalScrollExtent();
                int scrollRange = recyclerView.computeVerticalScrollRange();
                if (scrollOffset + scrollExtent == scrollRange) {
                    articles.addAll(Manager.getInstance().getArticles());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                if (scrollOffset == 0) {
                    //TODO  pull to refresh
                }
            }
        });

        return v;
    }


}
