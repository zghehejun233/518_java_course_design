package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Cost;
import org.fatmansoft.teach.models.PracticeActivity;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.CostRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/teach")
public class CostController {
    @Autowired
    private CostRepository costRepository;

    public List getCostMapList(String numname){
        List dataList = new ArrayList();
        List<Cost> cList = costRepository.findAll();
        if (cList==null||cList.size()==0){
            return dataList;
        }
        Cost c;
        Map m;
        for (int i = 0;i<cList.size();i++){
            c = cList.get(i);
            m = new HashMap();
            m.put("id",c.getId());
            m.put("costName",c.getCostName());
            m.put("costMoney",c.getCostMoney());
            m.put("time", DateTimeTool.parseDateTime(c.getTime(),"yyyy-MM-dd"));
            dataList.add(m);
        }
        return dataList;
    }

    @PostMapping("costInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costInit(@Valid @RequestBody DataRequest dataRequest){
        List dataList = getCostMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("costEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costEditInit(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        Cost c =null;
        Optional<Cost> op;
        if (id != null) {
            op = costRepository.findById(id);
            if (op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if (c!=null){
            form.put("id",c.getId());
            form.put("costName",c.getCostName());
            form.put("costMoney",c.getCostMoney());
            form.put("time",c.getTime());
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("costDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costDelete(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");
        Cost c = null;
        Optional<Cost> op;
        if (id != null) {
            op = costRepository.findById(id);
            if (op.isPresent()) {
                c = op.get();
            }
        }
        if (c != null) {
            costRepository.delete(c);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("costEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse costEditSubmit(@Valid @RequestBody DataRequest dataRequest){
        Map form = dataRequest.getMap("form");
        Integer id = CommonMethod.getInteger(form,"id");
        String costName = CommonMethod.getString(form,"costName");
        String costMoney = CommonMethod.getString(form,"costMoney");
        Date time = CommonMethod.getDate(form,"time");

        Cost c =null;
        Optional<Cost> op;

        if (id != null) {
            // 根据id寻找这条记录
            op = costRepository.findById(id);
            // 将对象引用变量通过ORM结构的性质a指向对应的记录
            if (op.isPresent()) {
                c = op.get();
            }
        }

        if (c == null) {
            // 实例化一个Achievement对象
            c = new Cost();
            // 获取最大id
            id = costRepository.getMaxId();
            //  如果id为null,说明表中还没有记录,否则就递增一个
            if (id == null) {
                id = 1;
            } else {
                id += 1;
            }
            // 设置对象a的id
            c.setId(id);
        }
        c.setCostName(costName);
        c.setCostMoney(costMoney);
        c.setTime(time);
        costRepository.save(c);
        return CommonMethod.getReturnData(c.getId());
    }
}
