package com.example.nasaearthimagerydatabase;

public interface NavInterface {

    /*
    This interface is used to generalize the toolbar and nav drawer to be universally usable for
    all of the activities. This interface allows Toolbar_Navigation to get activityId of all the activities.
     */
    int activityId = 0;

    public void setActivityId(int activityId);
    public int getActivityId();

}
