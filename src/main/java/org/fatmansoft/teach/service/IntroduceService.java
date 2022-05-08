package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
import org.fatmansoft.teach.dto.ChartInformationDTO;
import org.fatmansoft.teach.dto.CourseScoresDTO;
import org.fatmansoft.teach.dto.TotalRankDTO;
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

        ChartInformationDTO studyChartInformation = introduce.getStudyChartInformation(student, courseScoresDTOList);
        data.put("studyChartLabels", studyChartInformation.getLabels());
        data.put("studyChartData", studyChartInformation.getDatasets());

        ChartInformationDTO lifeChartInformation = introduce.getLifeChartInformation(student);
        data.put("lifeChartLabels", lifeChartInformation.getLabels());
        data.put("lifeChartData", lifeChartInformation.getDatasets());
        data.put("lifeChartColors", lifeChartInformation.getColors());

        Map m;

        m = new HashMap<>();
        m.put("title", "基本信息");
        m.put("content", "<div>Hello</br>fsdfsdfs</div>");
        // attachList.add(m);

        m = new HashMap<>();
        m.put("title", "平均成绩");   //
        m.put("content", averageScoreDTO.getAverageScoreForAll().toString());  // 学生成绩综述
        attachList.add(m);

        m = new HashMap();
        m.put("title", "总排名");   //
        m.put("content", totalRankDTO.getRank().toString());  // 学生成绩综述
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "自拍");
        m.put("content", "或许可以用字符画实现");  // 社会实践综述
        attachList.add(m);

        m = new HashMap<>();
        m.put("title", "家庭信息");
        StringBuilder familyInformation = new StringBuilder("<div>");
        for (Family value : student.getFamilies()) {
            familyInformation.append(value.toString());
            familyInformation.append("</br>");
        }
        familyInformation.append("</div>");
        m.put("content", familyInformation.toString());
        //attachList.add(m);

        data.put("attachList", attachList);
        return data;
    }
}
