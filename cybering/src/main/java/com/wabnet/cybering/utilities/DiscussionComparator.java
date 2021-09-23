package com.wabnet.cybering.utilities;

import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.discussions.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DiscussionComparator implements Comparator<Discussion> {
    @Override
    public int compare(Discussion discussion1, Discussion discussion2) {
        Message[] messagesArray1 = discussion1.getMessagesArray();
        Message[] messagesArray2 = discussion2.getMessagesArray();
        if (messagesArray1 == null || messagesArray1.length <= 0) {
            return 1;
        } else if (messagesArray2 == null || messagesArray2.length <= 0) {
            return -1;
        }
        String lastMessageTimeStamp1 = messagesArray1[messagesArray1.length - 1].getTimeStamp();
        String lastMessageTimeStamp2 = messagesArray2[messagesArray2.length - 1].getTimeStamp();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1, date2;
        try {
            date1 = dateFormat.parse(lastMessageTimeStamp1);
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
        try {
            date2 = dateFormat.parse(lastMessageTimeStamp2);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        return (-1)*date1.compareTo(date2);
    }
}
