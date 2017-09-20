package com.ayokhedma.ayokhedma.models;

import java.util.List;

/**
 * Created by MK on 01/06/2017.
 */

public class ObjectModel {
    private String id;
    private String category;
    private String catId;
    private String name;
    private String region;
    private String street;
    private String beside;
    private String description;
    private String start1;
    private String end1;
    private String start2;
    private String end2;
    private String start3;
    private String end3;



    private String weekend;
    private String color;
    private String count;
    private float rate;
    private List<String> phones;
    private List<CommentModel> comments;


    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet() {
        return street;
    }

    public String getBeside() {
        return beside;
    }

    public float getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public String getStart1() {
        return start1;
    }

    public String getEnd1() {
        return end1;
    }

    public String getStart2() {
        return start2;
    }

    public String getEnd2() {
        return end2;
    }

    public String getStart3() {
        return start3;
    }

    public String getEnd3() {
        return end3;
    }

    public String getWeekend() {
        return weekend;
    }

    public String getColor() {
        return color;
    }

    public List<String> getPhones() {
        return phones;
    }

    public String getCount() {
        return count;
    }

    public List<CommentModel> getComments() {
        return comments;
    }



    public class CommentModel {
        private String  name,subject,commentBody;

        public String getName() {
            return name;
        }

        public String getSubject() {
            return subject;
        }

        public String getcommentBody() {
            return commentBody;
        }


    }
}
