package org.fatmansoft.teach.service;

import org.fatmansoft.teach.dto.StudentScoresDTO;
import org.fatmansoft.teach.models.academic.Score;
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

    //个人简历信息数据准备方法  请同学修改这个方法，请根据自己的数据的希望展示的内容拼接成字符串，放在Map对象里， attachList 可以方多段内容，具体内容有个人决定
    public Map getIntroduceDataMap(Integer studentId) {
        System.out.println(studentId);
        List<StudentScoresDTO> scoresDTOList = introduce.getScoreData(studentId);
        Map data = new HashMap();
        data.put("myName", "张三");   // 学生信息
        data.put("overview", "本人.");  //学生基本信息综述
        List attachList = new ArrayList();
        Map m;
        m = new HashMap();
        m.put("title", "学习成绩");   //
        m.put("content", "成绩...");  // 学生成绩综述
        attachList.add(m);
        m = new HashMap();
        m.put("title", "社会实践");
        m.put("content", "社会实践...");  // 社会实践综述
        attachList.add(m);

        m = new HashMap();
        m.put("title", "成绩");
        System.out.println("hello");
        if (scoresDTOList != null && scoresDTOList.size() != 0) {

            for (StudentScoresDTO value : scoresDTOList) {
                System.out.println(value.toString());
                m.put("score", value.getScore());
            }
        }
        attachList.add(m);
        data.put("attachList", attachList);
        return data;
    }
}
