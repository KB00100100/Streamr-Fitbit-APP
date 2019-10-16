package com.fs.fitbitstreamr.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitysBean {


    /**
     * activities : []
     * goals : {"activeMinutes":120,"caloriesOut":4785,"distance":3,"floors":10,"steps":8000}
     * summary : {"activeScore":-1,"activityCalories":619,"caloriesBMR":1230,"caloriesOut":1745,"distances":[{"activity":"total","distance":4.98},{"activity":"tracker","distance":4.98},{"activity":"loggedActivities","distance":0},{"activity":"veryActive","distance":1.52},{"activity":"moderatelyActive","distance":0.44},{"activity":"lightlyActive","distance":3.02},{"activity":"sedentaryActive","distance":0}],"elevation":15.24,"fairlyActiveMinutes":7,"floors":5,"lightlyActiveMinutes":93,"marginalCalories":398,"sedentaryMinutes":936,"steps":6853,"veryActiveMinutes":18}
     */

    @SerializedName("goals")
    private GoalsBean goals;
    @SerializedName("summary")
    private SummaryBean summary;

    public GoalsBean getGoals() {
        return goals;
    }

    public void setGoals(GoalsBean goals) {
        this.goals = goals;
    }

    public SummaryBean getSummary() {
        return summary;
    }

    public void setSummary(SummaryBean summary) {
        this.summary = summary;
    }

    public static class GoalsBean {
        /**
         * activeMinutes : 120
         * caloriesOut : 4785
         * distance : 3
         * floors : 10
         * steps : 8000
         */

        @SerializedName("activeMinutes")
        private int activeMinutes;
        @SerializedName("caloriesOut")
        private int caloriesOut;
        @SerializedName("distance")
        private double distance;
        @SerializedName("floors")
        private int floors;
        @SerializedName("steps")
        private int steps;

        public int getActiveMinutes() {
            return activeMinutes;
        }

        public void setActiveMinutes(int activeMinutes) {
            this.activeMinutes = activeMinutes;
        }

        public int getCaloriesOut() {
            return caloriesOut;
        }

        public void setCaloriesOut(int caloriesOut) {
            this.caloriesOut = caloriesOut;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFloors() {
            return floors;
        }

        public void setFloors(int floors) {
            this.floors = floors;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }
    }

    public static class SummaryBean {
        /**
         * activeScore : -1
         * activityCalories : 619
         * caloriesBMR : 1230
         * caloriesOut : 1745
         * distances : [{"activity":"total","distance":4.98},{"activity":"tracker","distance":4.98},{"activity":"loggedActivities","distance":0},{"activity":"veryActive","distance":1.52},{"activity":"moderatelyActive","distance":0.44},{"activity":"lightlyActive","distance":3.02},{"activity":"sedentaryActive","distance":0}]
         * elevation : 15.24
         * fairlyActiveMinutes : 7
         * floors : 5
         * lightlyActiveMinutes : 93
         * marginalCalories : 398
         * sedentaryMinutes : 936
         * steps : 6853
         * veryActiveMinutes : 18
         */

        @SerializedName("activeScore")
        private int activeScore;
        @SerializedName("activityCalories")
        private int activityCalories;
        @SerializedName("caloriesBMR")
        private int caloriesBMR;
        @SerializedName("caloriesOut")
        private int caloriesOut;
        @SerializedName("elevation")
        private double elevation;
        @SerializedName("fairlyActiveMinutes")
        private int fairlyActiveMinutes;
        @SerializedName("floors")
        private int floors;
        @SerializedName("lightlyActiveMinutes")
        private int lightlyActiveMinutes;
        @SerializedName("marginalCalories")
        private int marginalCalories;
        @SerializedName("sedentaryMinutes")
        private int sedentaryMinutes;
        @SerializedName("steps")
        private int steps;
        @SerializedName("veryActiveMinutes")
        private int veryActiveMinutes;
        @SerializedName("distances")
        private List<DistancesBean> distances;

        public int getActiveScore() {
            return activeScore;
        }

        public void setActiveScore(int activeScore) {
            this.activeScore = activeScore;
        }

        public int getActivityCalories() {
            return activityCalories;
        }

        public void setActivityCalories(int activityCalories) {
            this.activityCalories = activityCalories;
        }

        public int getCaloriesBMR() {
            return caloriesBMR;
        }

        public void setCaloriesBMR(int caloriesBMR) {
            this.caloriesBMR = caloriesBMR;
        }

        public int getCaloriesOut() {
            return caloriesOut;
        }

        public void setCaloriesOut(int caloriesOut) {
            this.caloriesOut = caloriesOut;
        }

        public double getElevation() {
            return elevation;
        }

        public void setElevation(double elevation) {
            this.elevation = elevation;
        }

        public int getFairlyActiveMinutes() {
            return fairlyActiveMinutes;
        }

        public void setFairlyActiveMinutes(int fairlyActiveMinutes) {
            this.fairlyActiveMinutes = fairlyActiveMinutes;
        }

        public int getFloors() {
            return floors;
        }

        public void setFloors(int floors) {
            this.floors = floors;
        }

        public int getLightlyActiveMinutes() {
            return lightlyActiveMinutes;
        }

        public void setLightlyActiveMinutes(int lightlyActiveMinutes) {
            this.lightlyActiveMinutes = lightlyActiveMinutes;
        }

        public int getMarginalCalories() {
            return marginalCalories;
        }

        public void setMarginalCalories(int marginalCalories) {
            this.marginalCalories = marginalCalories;
        }

        public int getSedentaryMinutes() {
            return sedentaryMinutes;
        }

        public void setSedentaryMinutes(int sedentaryMinutes) {
            this.sedentaryMinutes = sedentaryMinutes;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public int getVeryActiveMinutes() {
            return veryActiveMinutes;
        }

        public void setVeryActiveMinutes(int veryActiveMinutes) {
            this.veryActiveMinutes = veryActiveMinutes;
        }

        public List<DistancesBean> getDistances() {
            return distances;
        }

        public void setDistances(List<DistancesBean> distances) {
            this.distances = distances;
        }

        public static class DistancesBean {
            /**
             * activity : total
             * distance : 4.98
             */

            @SerializedName("activity")
            private String activity;
            @SerializedName("distance")
            private double distance;

            public String getActivity() {
                return activity;
            }

            public void setActivity(String activity) {
                this.activity = activity;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }
        }
    }
}
