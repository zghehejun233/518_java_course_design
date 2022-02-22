package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.UimsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class TestController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(HttpSession session) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return "User Content.";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess(HttpSession session) {

        LocalDateTime ts = (LocalDateTime)session.getAttribute("ts");

        Map<String, String> res = new HashMap<String, String>();

        if (ts == null) {
            res.put("ts", "");
        } else {
            res.put("ts", ts.toString());
        }
        session.setAttribute("ts",  LocalDateTime.now());
        return ResponseEntity.ok(new DataResponse(
                "ok",
                res
        ));
    }

    @PostMapping("/getMenuList")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getMenuList(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        List mList = new ArrayList();
        Map m = new HashMap();
        m.put("name","BaseTable");
        m.put("title","表格示例1");
        mList.add(m);
        m = new HashMap();
        m.put("name","BaseTable");
        m.put("title","表格示例2");
        mList.add(m);
        m = new HashMap();
        m.put("name","BaseForm");
        m.put("title","表单示例");
        mList.add(m);
        return CommonMethod.getReturnData(mList);
    }
    @PostMapping("/getMenuListHtml")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getMenuListHtml(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        List mList = new ArrayList();
        Map m = new HashMap();
        m.put("name","pages/changePassword.html");
        m.put("title","修改密码");
        mList.add(m);
        m = new HashMap();
        m.put("name","CourseTable");
        m.put("title","课程管理");
        mList.add(m);
        m = new HashMap();
        m.put("name","DashSample");
        m.put("title","测试");
        mList.add(m);
        Map data = new HashMap();
        data.put("mList", mList);
        data.put("uerName", user.getUserName());
        return CommonMethod.getReturnData(data);
    }
    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse changePassword(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String oldPassword = dataRequest.getString("oldPassword");
        String newPassword = dataRequest.getString("newPassword");
        User u = userRepository.findById(userId).get();
        if(!encoder.matches(oldPassword, u.getPassword())) {
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepository.save(u);
        return CommonMethod.getReturnMessageOK();
    }


    @PostMapping("/tableOneInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse tableOne(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");

        List tableList = new ArrayList();
        Map m = new HashMap();
        m.put("id","1");
        m.put("name","小白");
        m.put("sex","男");
        tableList.add(m);
        m = new HashMap();
        m.put("id","2");
        m.put("name","小红");
        m.put("sex","女");
        tableList.add(m);
        Map data = new HashMap();
        data.put("tableList", tableList);
        return CommonMethod.getReturnData(data);
    }


    @PostMapping("/tableTwoInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse tableTwo(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");

        List tableList = new ArrayList();
        Map m = new HashMap();
        m.put("id","1");
        m.put("courseName","软件1班");
        m.put("courseNum","0001001");
        m.put("name","小朱");
        m.put("score","90");
        tableList.add(m);
        m = new HashMap();
        m.put("id","2");
        m.put("courseName","软件1班");
        m.put("courseNum","0001003");
        m.put("name","小马");
        m.put("score","80");
        tableList.add(m);
        Map data = new HashMap();
        data.put("tableList", tableList);
        return CommonMethod.getReturnData(data);
    }

    @PostMapping("/tableTwoQuery")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse querytableOne(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String perName = dataRequest.getString("perName");
        String perType = dataRequest.getString("perType");
        System.out.println(perName+perType);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/formOneInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse formOne(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        Map form = new HashMap();
        form.put("name","郭文学");
        form.put("phoneNum","17854258214");
        form.put("sex",1);
        form.put("day","2019-01-01");
        Map data = new HashMap();
        data.put("form", form);
        return CommonMethod.getReturnData(data);
    }


    @PostMapping("/sumitFormformOne")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse sumitFormformOne(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/tableOneDetail")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse formOnedetail(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String id = dataRequest.getString("id");
        Map form = new HashMap();
        form.put("name","郭文学");
        form.put("sex",1);
        Map data = new HashMap();
        data.put("form", form);
        return CommonMethod.getReturnData(data);
    }

    @PostMapping("/tableOneEdit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse tableOneedit(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String id = dataRequest.getString("id");
        Map form = new HashMap();
        form.put("name","郭文学");
        form.put("sex",1);
        Map data = new HashMap();
        data.put("form", form);
        return CommonMethod.getReturnData(data);
    }

    @PostMapping("/sumitFormtableOneedit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse sumitFormtableOneedit(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        return CommonMethod.getReturnMessageOK();
    }


    @PostMapping("/tableOneDelete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse deleteItemtableOne(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        String id = dataRequest.getString("id");
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        return CommonMethod.getReturnMessageOK();
    }
}