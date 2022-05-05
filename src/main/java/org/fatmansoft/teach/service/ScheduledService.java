package org.fatmansoft.teach.service;

import org.assertj.core.util.DateUtil;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.TotalScoreDTO;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
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
    private StudentRepository studentRepository;
    @Resource
    private ScoreImpl score;

    final int MicroSecondsPerMinute = 60000;

    /**
     * 系统启动一分钟后计算所有课程的成绩列表
     * 之后每三分钟刷新一次
     */
    @Scheduled(initialDelay = MicroSecondsPerMinute, fixedDelay = 3 * MicroSecondsPerMinute)
    public void refreshCourseRank() {
        SystemApplicationListener.logger.info("Begin refresh course rank>>>" + DateUtil.now());
        List<Course> courseList = courseRepository.findAll();
        for (Course value : courseList) {
            if (value.getScores() != null && value.getScores().size() != 0) {
                score.getCourseRankList(value);
            }
        }
        SystemApplicationListener.logger.info("Finish refresh course rank>>>" + DateUtil.now());
    }

    @Scheduled(initialDelay = 2 * MicroSecondsPerMinute, fixedDelay = 3 * MicroSecondsPerMinute)
    public void refreshTotalRank() {
        SystemApplicationListener.logger.info("Begin refresh total rank>>>" + DateUtil.now());
        List<Student> studentList = studentRepository.findAll();
        SystemApplicationListener.logger.info("打印所有学生");
        for (Student value : studentList) {
            SystemApplicationListener.logger.info(value.toString());
        }
        List<TotalScoreDTO > totalScoreDTOList = globalScoreService.getAllStudentsTotalScore();
        SystemApplicationListener.logger.info("打印没有排名的成绩");
        for (TotalScoreDTO value : totalScoreDTOList) {
            SystemApplicationListener.logger.info(value.toString());
        }
        totalScoreDTOList = globalScoreService.getTotalRank(totalScoreDTOList);
        SystemApplicationListener.logger.info("打印带有排名的成绩");
        for (TotalScoreDTO value : totalScoreDTOList) {
            SystemApplicationListener.logger.info(value.toString());
        }
        SystemApplicationListener.logger.info("Finish refresh total rank>>>" + DateUtil.now());
    }
}
