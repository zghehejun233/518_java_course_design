package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Achievement;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.AchievementRepository;
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

public class AchievementController {
    // 注入achievementRepository对象
    // 可以理解为通过注入外部对象实现对象的复用，避免了反复声明
    @Autowired
    private AchievementRepository achievementRepository;

    // 获取achievement列表
    public List getAchievementMapList(String numName) {
        // 实例化返回数据dataList对象
        // 这里有多态的思想，使用父类List声明对象引用变量
        // 实际指向的是ArrayList对象
        List dataList = new ArrayList();
        // 这里的<Achievement>是对泛型的限制
        // 可以理解为List<T>本身是可以对各种类的实例化对象使用的
        // 这里强制限定为Achievement类的对象
        // 此处调用了achievementRepository类的findAll()方法
        // findAll()等方法是achievementRepository继承自JpaRepository带来的
        List<Achievement> aList = achievementRepository.findAll();
        // 判断findAll()返回的是否为空，若是，则直接返回一个空的dataList
        if (aList == null || aList.size() == 0) {
            return dataList;
        }
        // 声明一个Achievement对象引用变量
        Achievement a;
        // 声明一个Map对象引用变量
        Map m;
        // 遍历aList
        for (int i = 0; i < aList.size(); i++) {
            // 实例化a
            a = aList.get(i);
            // 实例化m
            m = new HashMap();
            // 使用m.put()方法在返回数据dataList的单位成员m中添加信息
            m.put("id", a.getId());
            m.put("studentNum", achievementRepository.getStudentNum(a.getStudentName()));
            m.put("studentName", a.getStudentName());
            m.put("achievementName", a.getAchievementName());
            m.put("achievementType", a.getAchievementType());
            m.put("organization", a.getOrganization());
            m.put("level", a.getLevel());
            m.put("time", DateTimeTool.parseDateTime(a.getTime(),"yyyy-MM-dd"));
            // 向dataList中添加m
            dataList.add(m);
        }
        // 返回dataList
        return dataList;
    }

    // achievement页面初始化
    @PostMapping("/achievementInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementInit(@Valid @RequestBody DataRequest datarequest) {
        // 调用getAchievementMapList()方法
        List dataList = getAchievementMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    // achievement页面查询方法，还没有写
    @PostMapping("/achievementQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementQuery(@Valid @RequestBody DataRequest dataRequest) {
        return null;
    }

    //achievementEdit页面初始化
    @PostMapping("/achievementEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        Achievement a = null;
        Optional<Achievement> op;
        if (id != null) {
            op = achievementRepository.findById(id);
            if (op.isPresent()) {
                a = op.get();
            }
        }
        Map form = new HashMap();
        if (a != null) {
            form.put("id", a.getId());
            form.put("studentNum", achievementRepository.getStudentNum(a.getStudentName()));
            form.put("studentName", a.getStudentName());
            form.put("achievementName", a.getAchievementName());
            form.put("achievementType", a.getAchievementType());
            form.put("organization", a.getOrganization());
            form.put("level", a.getLevel());
            form.put("time", a.getTime());
        }
        return CommonMethod.getReturnData(form);
    }

    //  荣誉信息提交
    @PostMapping("/achievementEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse achievementEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        // 根据提交的form信息生成Map
        Map form = dataRequest.getMap("form");
        // 读取数据
        Integer id = CommonMethod.getInteger(form, "id");
        String studentName = CommonMethod.getString(form, "studentName");
        String achievementName = CommonMethod.getString(form, "achievementName");
        String achievementType = CommonMethod.getString(form, "achievementType");
        String organization = CommonMethod.getString(form, "organization");
        String level = CommonMethod.getString(form, "level");
        Date time = CommonMethod.getDate(form, "time");

        // 声明空对象引用变量
        Achievement a = null;
        Optional<Achievement> op;

        // 判断是否在对某条记录操作
        // 如果是对某条记录操作，就会携带一个id信息，进入这条分支
        if (id != null) {
            // 根据id寻找这条记录
            op = achievementRepository.findById(id);
            // 将对象引用变量通过ORM结构的性质a指向对应的记录
            if (op.isPresent()) {
                a = op.get();
            }
        }

        // 如果是添加一条新的记录，那么a就是null
        if (a == null) {
            // 实例化一个Achievement对象
            a = new Achievement();
            // 获取最大id
            id = achievementRepository.getMaxId();
            //  如果id为null,说明表中还没有记录,否则就递增一个
            if (id == null) {
                id = 1;
            } else {
                id += 1;
            }
            // 设置对象a的id
            a.setId(id);
        }
        a.setStudentName(studentName);
        a.setAchievementName(achievementName);
        a.setAchievementType(achievementType);
        a.setOrganization(organization);
        a.setLevel(level);
        a.setTime(time);
        // 调用achievementRepository中的方法保存实例a
        achievementRepository.save(a);
        // 返回id下的数据
        return CommonMethod.getReturnData(a.getId());
    }
}
