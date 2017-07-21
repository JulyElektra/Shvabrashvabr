package com.example.elekt.shvabrashvabr.Presenter;

import com.example.elekt.shvabrashvabr.Model.Article;
import com.example.elekt.shvabrashvabr.Model.Model;

import java.util.Collection;

/**
 * Created by elekt on 21.07.2017.
 */

public class Manager implements IManager{
    private static Manager manager;

    static {
        manager = new Manager();
    }

    public static Manager getInstance(){
        return manager;
    }

    @Override
    public Collection<Article> getArticles() {
        return new Model().getArticles();
    }

}
