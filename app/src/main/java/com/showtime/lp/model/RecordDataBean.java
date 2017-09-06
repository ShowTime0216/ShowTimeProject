package com.showtime.lp.model;

/**
 * @author: Trouble Maker
 * @date: 2017/9/1 0001
 * @Description:
 */
public class RecordDataBean {

//    private int raceId;
//    private int placeId;

    private int id;
    private String time;
    private String recordUrl;
    private String number;
    private String numScore;
    private String valid;
    private String score;
    private String isRead;
    private String duration;

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumScore() {
        return numScore;
    }

    public void setNumScore(String numScore) {
        this.numScore = numScore;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

}
