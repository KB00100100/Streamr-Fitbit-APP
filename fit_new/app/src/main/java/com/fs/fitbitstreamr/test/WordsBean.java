package com.fs.fitbitstreamr.test;

public class WordsBean {
    private String words;
    private float alpha;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public WordsBean(String words, float alpha) {
        this.words = words;
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
