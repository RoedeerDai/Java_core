package com.roedeer.practice.enumeration;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 4/10/2019 3:17 PM
 **/
public class FeedStatus {
    public enum FileStatus {

        FEED_FILE_STATUS_PROCESSING ("PROCESSING"),
        FEED_FILE_STATUS_PROCESSED ("PROCESSED"),
        FEED_FILE_STATUS_ERROR ("ERROR");


        private final String status;
        FileStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum StagingStatus {
        STAGING_NEW ("NEW"),
        STAGING_PROCESSED ("PROCESSED"),
        STAGING_ERROR ("ERROR");

        private final String status;
        StagingStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }

    public enum CmeAction {
        REPORT_RE_GEN,
        REPORT_DONE,
        REPORT_NOT_START;
    }

    public enum CmeStatus {
        CME_REPORT_INIT ("INIT"),
        CME_REPORT_GENERATED ("GENERATED"),
        CME_BACKFILL_PROCESSING ("PROCESSING"),
        CME_BACKFILL_PROCESSED ("PROCESSED");

        private final String status;
        CmeStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }

}
