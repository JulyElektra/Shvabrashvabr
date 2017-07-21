package com.example.elekt.shvabrashvabr.Presenter.Adaptors;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.elekt.shvabrashvabr.Model.Article;
import com.example.elekt.shvabrashvabr.R;

import java.util.Collection;

/**
 * Created by elekt on 20.07.2017.
 */

public class ArticlesRecyclerAdaptor extends RecyclerView.Adapter<ArticleRecyclerHolder> {
    private Collection<Article> articles;



    public ArticlesRecyclerAdaptor(Collection<Article> articles) {
        this.articles = articles;
    }

    @Override
    public ArticleRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_layout,
                parent, false);
        ArticleRecyclerHolder recyclerHolderListNews = new ArticleRecyclerHolder(view);
        return recyclerHolderListNews;
    }

    @Override
    public void onBindViewHolder(final ArticleRecyclerHolder holder, int position) {

        // Set information about article in particular position in the View
        final Article currentArticle = (Article) articles.toArray()[position];
        holder.getTitle().setText(currentArticle.getTitle());
        holder.getLink().setText(currentArticle.getLink());
        holder.getDescription().setText(currentArticle.getDescription());
        holder.getCategory().setText(currentArticle.getCategories().toString());
        holder.getPubDate().setText(currentArticle.getPubDate());
        holder.getImage().setImageDrawable(currentArticle.getImage());
        holder.getCreator().setText(currentArticle.getCreator());

        // Open image in Dialog window if it clicked
        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                dialogBuilder.setTitle("Photo");
                Activity activity = (Activity) v.getContext();
                View view = LayoutInflater.from(activity.getApplication()).inflate(R.layout.image_layout, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageInDialog);

                //Get screen width and height
                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                //Show image on a half of the screen size
                imageView.setMinimumHeight(height/2);
                imageView.setImageDrawable(currentArticle.getImage());

                dialogBuilder.setView(view);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}
