package com.wabnet.cybering.model.discussions;

import java.util.Arrays;

public class DiscussionsInfo {

    Discussion[] discussionArray;
    int selectedIndex;

    public DiscussionsInfo() {
    }

    public DiscussionsInfo(Discussion[] discussionArray, int selectedIndex) {
        this.discussionArray = discussionArray;
        this.selectedIndex = selectedIndex;
    }

    public Discussion[] getDiscussionArray() {
        return discussionArray;
    }

    public void setDiscussionArray(Discussion[] discussionArray) {
        this.discussionArray = discussionArray;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public String toString() {
        return "DiscussionsInfo{" +
                "discussionArray=" + Arrays.toString(discussionArray) +
                ", selectedIndex=" + selectedIndex +
                '}';
    }
}
