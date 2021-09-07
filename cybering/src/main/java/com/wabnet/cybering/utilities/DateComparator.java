package com.wabnet.cybering.utilities;

import com.wabnet.cybering.model.articles.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Article> {
    @Override
    public int compare(Article article1, Article article2) {
        String s1 = article1.getTimestamp();
        String s2 = article2.getTimestamp();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
