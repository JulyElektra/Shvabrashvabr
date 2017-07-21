package com.example.elekt.shvabrashvabr.Model;


import android.content.Context;

import com.example.elekt.shvabrashvabr.Model.Parser.XMLParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * Created by elekt on 21.07.2017.
 */

public class Model implements IModel {
    @Override
    public Collection<Article> getArticles() {
        try {
            return new XMLParser().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
