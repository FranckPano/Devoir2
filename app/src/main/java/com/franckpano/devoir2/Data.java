package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */
public class Data {
        private long id;
        private String comment;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
        public String getData() {
            return comment;
        }

        public void setData(String comment) {
            this.comment = comment;
        }

        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return comment;
        }
}