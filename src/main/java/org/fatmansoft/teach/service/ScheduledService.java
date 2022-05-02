package org.fatmansoft.teach.service;

import org.assertj.core.util.DateUtil;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.service.academic.ScoreImpl;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledService {
    @Resource
    private GlobalScoreService globalScoreService;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private ScoreImpl score;

    /**
     * 系统启动一分钟后计算所有课程的成绩列表
     * 之后每三分钟刷新一次
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 180000)
    public void refreshCourseRank() {
        System.out.println("Begin refreshCourseRank>>>" + DateUtil.now());
        List<Course> courseList = courseRepository.findAll();
        for (Course value : courseList) {
            if (value.getScores() != null && value.getScores().size() != 0) {
                score.getCourseRankList(value);
            }
        }
        System.out.println("Finish refreshCourseRank>>>" + DateUtil.now());

    }
}
