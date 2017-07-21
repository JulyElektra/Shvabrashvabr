package com.example.elekt.shvabrashvabr.Presenter.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elekt.shvabrashvabr.R;


/**
 * The class that contains getters to view of each article
 */

public class ArticleRecyclerHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView link;
    private TextView description;
    private TextView pubDate;
    private TextView category;
    private TextView creator;
    private ImageView image;

    public ArticleRecyclerHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.articleTitle);
        link = (TextView) itemView.findViewById(R.id.articleLink);
        description = (TextView) itemView.findViewById(R.id.articleDescription);
        pubDate = (TextView) itemView.findViewById(R.id.articlePublicDate);
        category = (TextView) itemView.findViewById(R.id.articleCategory);
        image = (ImageView) itemView.findViewById(R.id.articleImage);
        creator = (TextView) itemView.findViewById(R.id.articleCreator);

    }

    public TextView getCreator() {
        return creator;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getLink() {
        return link;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getPubDate() {
        return pubDate;
    }

    public TextView getCategory() {
        return category;
    }

    public ImageView getImage() {
        return image;
    }
}
