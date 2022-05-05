package org.fatmansoft.teach.dto;

/**
 * @author 16645
 */
public class TotalRankSerializator {
    public static String serializeTotalRank(TotalRankDTO totalRankDTO) {
        String result = totalRankDTO.getAverageScore() + ":";
        result = result + totalRankDTO.getRank() + ":";
        result = result + totalRankDTO.getPercent() + ":";
        result = result + totalRankDTO.getSameScoreNum();
        return result;
    }

    public static TotalRankDTO deserializeTotalRank(String str) {
        TotalRankDTO totalRankDTO = new TotalRankDTO();
        String stringDelimiter = ":";
        String[] temp = str.split(stringDelimiter);
        totalRankDTO.setAverageScore(Double.parseDouble(temp[0]));
        totalRankDTO.setRank(Integer.parseInt(temp[1]));
        totalRankDTO.setPercent(Double.parseDouble(temp[2]));
        totalRankDTO.setSameScoreNum(Integer.parseInt(temp[3]));
        return totalRankDTO;
    }
}
