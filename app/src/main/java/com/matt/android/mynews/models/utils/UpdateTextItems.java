package com.matt.android.mynews.models.utils;

import com.matt.android.mynews.models.api.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class to manage Json Strings
 */
public class UpdateTextItems {

    private String text;

    //Constructor
    public UpdateTextItems() {
    }

    /**
     * set article section
     *
     * @param article Section name
     * @return String article
     */

    public String setSection(Result article) {
        if (!article.getSection().equals("")) {
            text = article.getSection();
        }
        return text;
    }

    /**
     * set article subSection (example : "Arts > Television")
     *
     * @param article
     * @return String article subsection
     */

    public String setSubSection(Result article) {
        String sectionTitle = article.getSection();
        text = "";
        if (article.getSubsection() != null) {
            String subsectionTitle = article.getSubsection();
            if (!article.getSubsection().equals("") && subsectionTitle.compareTo(sectionTitle) != 0) {
                text = " > " + article.getSubsection();
            }

        }
        return text;
    }

    /**
     * set Title
     *
     * @param article Set Title
     * @return title
     */

    public String setTitle(Result article) {
        if (!article.getTitle().equals("")) {
            text = article.getTitle();
        }
        return text;
    }

    /**
     * Set date
     *
     * @param article
     * @return article Date with right date format
     */

    public String setDate(Result article) {
        try {
            String dateStr = article.getPublishedDate();
            DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = srcDf.parse(dateStr);
            DateFormat destDF = new SimpleDateFormat("dd/MM/yy");
            dateStr = destDF.format(date);
            text = dateStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return text;
    }
}