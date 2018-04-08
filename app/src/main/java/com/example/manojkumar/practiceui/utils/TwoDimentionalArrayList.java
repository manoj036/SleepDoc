package com.example.manojkumar.practiceui.utils;

import java.util.ArrayList;

/**
 * Created by mbreath on 07/04/18.
 */

public class TwoDimentionalArrayList<T> extends ArrayList<ArrayList<T>> {
    public void addToInnerArray(int index, T element) {
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }
        this.get(index).add(element);
    }

    public void addToInnerArray(int index, int index2, T element) {
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }

        ArrayList<T> inner = this.get(index);
        while (index2 >= inner.size()) {
            inner.add(null);
        }

        inner.set(index2, element);
    }
}