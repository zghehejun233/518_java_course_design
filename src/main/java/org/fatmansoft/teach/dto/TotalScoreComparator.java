package org.fatmansoft.teach.dto;

import java.util.Comparator;

public class TotalScoreComparator implements Comparator<TotalScoreDTO> {
    @Override
    public int compare(TotalScoreDTO o1, TotalScoreDTO o2) {
        return o2.getAverageScore().compareTo(o1.getAverageScore());
    }
}
