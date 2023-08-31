package com.springTest.FirstSpringBlog.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String title = "", tag = "", shortInfo = "", fullInfo = "";
    LocalDate date = LocalDate.now();
    int commentAmount = 0;

    public Post(String title, String tag, String shortInfo, String fullInfo) {
        this.title = title;
        this.tag = tag;
        this.shortInfo = shortInfo;
        this.fullInfo = fullInfo;
    }

    public Post(){}

    public Post(String title, String tag, String shortInfo, String fullInfo, LocalDate date, int commentAmount) {
        this.title = title;
        this.tag = tag;
        this.shortInfo = shortInfo;
        this.fullInfo = fullInfo;
        this.date = date;
        this.commentAmount = commentAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
