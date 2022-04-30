package org.fatmansoft.teach.dto;

import java.util.Comparator;

/**
 * @author 16645
 */
public class CourseRankComparator implements Comparator<CourseRankDTO> {

    @Override
    public int compare(CourseRankDTO o1, CourseRankDTO o2) {
        return o2.getScore().compareTo(o1.getScore());
    }
}
