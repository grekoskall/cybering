package com.wabnet.cybering.utilities;

import com.wabnet.cybering.model.advertisements.Advertisement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class AdComparator implements Comparator<Advertisement> {
    @Override
    public int compare(Advertisement advertisement1, Advertisement advertisement2) {
        String s1 = advertisement1.getStartDate();
        String s2 = advertisement2.getStartDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try {
            date1 = dateFormat.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
        try {
            date2 = dateFormat.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        return (-1)*date1.compareTo(date2);
    }
}
