package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
import org.fatmansoft.teach.dto.StudentScoresDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GuoSurui
 */
@Service
public class GlobalScoreService {

    /**
     * 获取学生的所有成绩的平均值
     *
     * @param studentScoresDTOList 学生所有成绩DTO列表
     * @return 返回平均成绩DTO
     */
    public AverageScoreDTO getAverage(List<StudentScoresDTO> studentScoresDTOList) {
        final int BASE_SCORE = 50;

        double averageScoreForAll = 0;
        double averageScoreForMajor = 0;
        double fullCreditsForAll = 0;
        double fullCreditsForMajor = 0;

        for (StudentScoresDTO value : studentScoresDTOList) {
            fullCreditsForAll += value.getCredit();
            try {
                if ("1".equals(value.getType())) {
                    fullCreditsForMajor += value.getCredit();
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }

        for (StudentScoresDTO value : studentScoresDTOList) {
            averageScoreForAll += value.getScore() * (value.getCredit() / fullCreditsForAll);

            try {
                if ("1".equals(value.getType())) {
                    averageScoreForMajor += value.getScore() * (value.getCredit() / fullCreditsForMajor);
                }
            } catch (NullPointerException nullPointerException) {
                SystemApplicationListener.logger.warn("存在未指定的记分方式");
                SystemApplicationListener.logger.warn(nullPointerException.toString());
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }
        return new AverageScoreDTO(
                averageScoreForAll,
                0.1 * (averageScoreForAll - BASE_SCORE),
                averageScoreForMajor,
                0.1 * (averageScoreForMajor - BASE_SCORE)
        );
    }
}
