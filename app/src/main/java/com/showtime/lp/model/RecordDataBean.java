package com.showtime.lp.model;

import java.util.List;

/**
 * @author: Trouble Maker
 * @date: 2017/9/1 0001
 * @Description:
 */
public class RecordDataBean {

    private int raceId;
    private List<DataBean> placeList;

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public List<DataBean> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<DataBean> placeList) {
        this.placeList = placeList;
    }

    public class DataBean {
        private int placeId;
        private List<DataBean.RaceContent> contentList;

        public int getPlaceId() {
            return placeId;
        }

        public void setPlaceId(int placeId) {
            this.placeId = placeId;
        }

        public List<RaceContent> getContentList() {
            return contentList;
        }

        public void setContentList(List<RaceContent> contentList) {
            this.contentList = contentList;
        }

        public class RaceContent {

            private String time;
            private String recordUrl;
            private String number;
            private String numScore;
            private String valid;

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

    }
}
