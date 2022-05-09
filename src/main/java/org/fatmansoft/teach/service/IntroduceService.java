package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
import org.fatmansoft.teach.dto.ChartInformationDTO;
import org.fatmansoft.teach.dto.CourseScoresDTO;
import org.fatmansoft.teach.dto.TotalRankDTO;
import org.fatmansoft.teach.models.academic_activity.Competition;
import org.fatmansoft.teach.models.academic_activity.InnovationProject;
import org.fatmansoft.teach.models.academic_activity.Internship;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.daily.Achievement;
import org.fatmansoft.teach.models.daily.Activity;
import org.fatmansoft.teach.models.student_basic.EducationExperience;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.models.student_basic.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IntroduceService {
    @Resource
    private IntroduceImpl introduce;
    @Resource
    private GlobalScoreService globalScoreService;

    //个人简历信息数据准备方法  请同学修改这个方法，请根据自己的数据的希望展示的内容拼接成字符串，放在Map对象里， attachList 可以方多段内容，具体内容有个人决定
    public Map getIntroduceDataMap(Integer studentId) {
        // 获取学生的所有成绩，排名为空
        List<CourseScoresDTO> courseScoresDTOList = globalScoreService.getScoresForStudent(introduce.getStudent(studentId));
        // 获得包含排名的成绩，填充组合的对象
        courseScoresDTOList = globalScoreService.getRankForCoursesForStudent(courseScoresDTOList);
        // 基于所有成绩计算平均成绩
        AverageScoreDTO averageScoreDTO = globalScoreService.getAverageScoreForCoursesForStudent(courseScoresDTOList);
        SystemApplicationListener.logger.warn("average score： " + averageScoreDTO.getAverageScoreForAll());
        TotalRankDTO totalRankDTO = globalScoreService.getStudentForStudent(averageScoreDTO.getAverageScoreForAll());
        Student student = introduce.getStudent(studentId);
        Map data = new HashMap();
        data.put("myName", student.getStudentName());   // 学生信息
        data.put("overview", "Hello!");
        if (student.getSex().equals(1)) {
            data.put("sex", "男");
        } else if (student.getSex().equals(2)) {
            data.put("sex", "女");
        } else {
            data.put("sex", "第三性");
        }

        data.put("age", student.getAge());
        data.put("studentNum", student.getStudentNum());
        data.put("contact", student.getPhoneNumber() + "," + student.getEmail());
        List<Object> attachList = new ArrayList<>();
        List<Object> pdfInfo = new ArrayList<>();

        ChartInformationDTO studyChartInformation = introduce.getStudyChartInformation(student, courseScoresDTOList);
        data.put("studyChartLabels", studyChartInformation.getLabels());
        data.put("studyChartData", studyChartInformation.getDatasets());

        ChartInformationDTO lifeChartInformation = introduce.getLifeChartInformation(student);
        data.put("lifeChartLabels", lifeChartInformation.getLabels());
        data.put("lifeChartData", lifeChartInformation.getDatasets());
        data.put("lifeChartColors", lifeChartInformation.getColors());

        Map m;

        m = new HashMap<>();
        m.put("title", "学业情况");
        m.put("content", "");
        attachList.add(m);
        pdfInfo.add(m);

        m = new HashMap<>();
        m.put("title", "平均成绩");   //
        m.put("content", averageScoreDTO.getAverageScoreForAll().toString().substring(0,4));  // 学生成绩综述
        attachList.add(m);
        pdfInfo.add(m);


        m = new HashMap();
        m.put("title", "总排名");   //
        m.put("content", totalRankDTO.getRank().toString());  // 学生成绩综述
        attachList.add(m);
        pdfInfo.add(m);


        m = new HashMap<>();
        m.put("title", "--------");
        m.put("content", "");
        attachList.add(m);
        m = new HashMap<>();
        m.put("title", "基本情况");
        m.put("content", "");
        attachList.add(m);
        pdfInfo.add(m);


        m = new HashMap<>();
        m.put("title", "家庭信息");
        StringBuilder familyInformation = new StringBuilder("<div>");
        for (Family value : student.getFamilies()) {
            familyInformation.append(value.toString());
            familyInformation.append("</br>");
        }
        familyInformation.append("</div>");
        m.put("content", familyInformation.toString());
        attachList.add(m);
        StringBuilder familyPdfInformation = new StringBuilder("");
        for (Family value : student.getFamilies()) {
            familyPdfInformation.append(value.toString());
            familyPdfInformation.append("</br>");
        }
        m.put("content", familyPdfInformation.toString());
        pdfInfo.add(m);


        m = new HashMap<>();
        m.put("title", "教育信息");
        StringBuilder educationInformation = new StringBuilder("<div>");
        for (EducationExperience value : student.getEducationExperiences()) {
            educationInformation.append(value.toString());
            educationInformation.append("</br>");
        }
        educationInformation.append("</div>");
        m.put("content", educationInformation.toString());
        attachList.add(m);
        StringBuilder educationPdfInformation = new StringBuilder("");
        for (EducationExperience value : student.getEducationExperiences()) {
            educationPdfInformation.append(value.toString());
            educationPdfInformation.append("</br>");
        }
        m.put("content", educationPdfInformation.toString());
        pdfInfo.add(m);

        m = new HashMap<>();
        m.put("title", "--------");
        m.put("content", "");
        attachList.add(m);
        m = new HashMap<>();
        m.put("title", "教学活动");
        m.put("content", "");
        attachList.add(m);
        pdfInfo.add(m);

        m = new HashMap<>();
        m.put("title", "实践信息");
        StringBuilder practiceInformation = new StringBuilder("<div>");
        for (Practice value : student.getPractices()) {
            practiceInformation.append(value.toString());
            practiceInformation.append("</br>");
        }
        practiceInformation.append("</div>");
        m.put("content", practiceInformation.toString());
        attachList.add(m);
        StringBuilder practicePdfInformation = new StringBuilder("");
        for (Practice value : student.getPractices()) {
            practicePdfInformation.append(value.toString());
            practicePdfInformation.append("</br>");
        }
        m.put("content", practicePdfInformation.toString());
        pdfInfo.add(m);

        m = new HashMap<>();
        m.put("title", "竞赛信息");
        StringBuilder competitionInformation = new StringBuilder("<div>");
        for (Competition value : student.getCompetitions()) {
            competitionInformation.append(value.toString());
            competitionInformation.append("</br>");
        }
        competitionInformation.append("</div>");
        m.put("content", competitionInformation.toString());
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "创新创业");
        StringBuilder innovationInformation = new StringBuilder("<div>");
        for (InnovationProject value : student.getInnovationProjects()) {
            innovationInformation.append(value.toString());
            innovationInformation.append("</br>");
        }
        innovationInformation.append("</div>");
        m.put("content", innovationInformation.toString());
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "校外实习");
        StringBuilder internshipInformation = new StringBuilder("<div>");
        for (Internship value : student.getInternships()) {
            internshipInformation.append(value.toString());
            internshipInformation.append("</br>");
        }
        internshipInformation.append("</div>");
        m.put("content", internshipInformation.toString());
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "--------");
        m.put("content", "");
        attachList.add(m);
        m = new HashMap<>();
        m.put("title", "日常信息");
        m.put("content", "");
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "参与活动");
        StringBuilder activityInformation = new StringBuilder("<div>");
        for (Activity value : student.getActivities()) {
            activityInformation.append(value.toString());
            activityInformation.append("</br>");
        }
        activityInformation.append("</div>");
        m.put("content", activityInformation.toString());
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "所获成就");
        StringBuilder achievementInformation = new StringBuilder("<div>");
        for (Achievement value : student.getAchievements()) {
            achievementInformation.append(value.toString());
            achievementInformation.append("</br>");
        }
        achievementInformation.append("</div>");
        m.put("content", achievementInformation.toString());
        attachList.add(m);

        data.put("pdfInfo",pdfInfo);
        data.put("attachList", attachList);
        return data;
    }
}
