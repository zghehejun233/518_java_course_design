package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Achievement;
import org.fatmansoft.teach.models.CourseSelection;
import org.fatmansoft.teach.models.PracticeActivity;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.PracticeActivityRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

// origins： 允许可访问的域列表 *通配符表示全部
// maxAge:准备响应前的缓存持续的最大时间（以秒为单位）
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")

public class PracticeActivityController {
    @Autowired
    private PracticeActivityRepository practiceActivityRepository;

    public List getPracticeActivityMapList(String numName) {
        List dataList = new ArrayList();
        List<PracticeActivity> pList = practiceActivityRepository.findAll();
        if (pList == null || pList.size() == 0) {
            return dataList;
        }
        PracticeActivity p;
        Map m;
        for (int i = 0; i < pList.size(); i++) {
            p = pList.get(i);
            m = new HashMap();
            m.put("id", p.getId());
            m.put("studentNum", practiceActivityRepository.getStudentNum(p.getStudentName()));
            m.put("studentName", p.getStudentName());
            m.put("studentNameParas","model=studentEdit&id="+p.getId());
            String type = p.getPracticeType();
            switch (type) {
                case "1":
                    m.put("practiceType", "社会实践");
                    break;
                case "2":
                    m.put("practiceType", "学科竞赛");
                    break;
                case "3":
                    m.put("practiceType", "科技成果");
                    break;
                case "4":
                    m.put("practiceType", "培训讲座");
                    break;
                case "5":
                    m.put("practiceType", "创新项目");
                    break;
                case "6":
                    m.put("practiceType", "校外实习");
                    break;
                default:
                    m.put("practiceType", "未指定");
                    break;
            }
            m.put("practiceName", p.getPracticeName());
            m.put("teamMember",p.getTeamMember());
            m.put("location", p.getLocation());
            m.put("startTime", DateTimeTool.parseDateTime(p.getStartTime(),"yyyy-MM-dd"));
            m.put("endTime", DateTimeTool.parseDateTime(p.getEndTime(),"yyyy-MM-dd"));
            dataList.add(m);
        }
        return dataList;
    }

    @PostMapping("/practiceActivityInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceActivityInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = getPracticeActivityMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/practiceActivityEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceActivityEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        PracticeActivity p =null;
        Optional<PracticeActivity> op;
        if (id != null) {
            op = practiceActivityRepository.findById(id);
            if (op.isPresent()) {
                p = op.get();
            }
        }
        Map form = new HashMap();
        if (p != null) {
            form.put("id", p.getId());
            form.put("studentNum", practiceActivityRepository.getStudentNum(p.getStudentName()));
            form.put("studentName", p.getStudentName());
            form.put("practiceType",p.getPracticeType());
            form.put("practiceName",p.getPracticeName());
            form.put("teamMember",p.getTeamMember());
            form.put("location",p.getLocation());
            form.put("startTime",p.getStartTime());
            form.put("endTime",p.getEndTime());
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("practiceActivityDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceActivityDelete(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        PracticeActivity p = null;
        Optional<PracticeActivity> op;
        if (id != null) {
            op = practiceActivityRepository.findById(id);
            if (op.isPresent()) {
                p = op.get();
            }
        }
        if (p != null) {
            practiceActivityRepository.delete(p);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/practiceActivityEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse practiceActivityEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        // 根据提交的form信息生成Map
        Map form = dataRequest.getMap("form");
        // 读取数据
        Integer id = CommonMethod.getInteger(form, "id");
        String studentName = CommonMethod.getString(form, "studentName");
        String practiceType = CommonMethod.getString(form,"practiceType");
        String practiceName = CommonMethod.getString(form,"practiceName");
        String teamMember = CommonMethod.getString(form,"teamMember");
        String location = CommonMethod.getString(form,"location");
        Date startTime = CommonMethod.getDate(form,"startTime");
        Date endTime = CommonMethod.getDate(form,"endTime");

        // 声明空对象引用变量
        PracticeActivity p = null;
        Optional<PracticeActivity> op;

        // 判断是否在对某条记录操作
        // 如果是对某条记录操作，就会携带一个id信息，进入这条分支
        if (id != null) {
            // 根据id寻找这条记录
            op = practiceActivityRepository.findById(id);
            // 将对象引用变量通过ORM结构的性质a指向对应的记录
            if (op.isPresent()) {
                p = op.get();
            }
        }

        // 如果是添加一条新的记录，那么a就是null
        if (p == null) {
            // 实例化一个Achievement对象
            p = new PracticeActivity();
            // 获取最大id
            id = practiceActivityRepository.getMaxId();
            //  如果id为null,说明表中还没有记录,否则就递增一个
            if (id == null) {
                id = 1;
            } else {
                id += 1;
            }
            // 设置对象a的id
            p.setId(id);
        }
        p.setStudentName(studentName);
        p.setPracticeName(practiceName);
        p.setPracticeType(practiceType);
        p.setTeamMember(teamMember);
        p.setLocation(location);
        p.setStartTime(startTime);
        p.setEndTime(endTime);
        // 调用achievementRepository中的方法保存实例a
        practiceActivityRepository.save(p);
        // 返回id下的数据
        return CommonMethod.getReturnData(p.getId());
    }
}
