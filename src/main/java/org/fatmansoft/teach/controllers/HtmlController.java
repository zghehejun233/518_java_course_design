package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/html")
public class HtmlController {
    //  http://localhost:9090/test.html


    public String getHtmlString() {
        String contnet = "";
        contnet += "<h5 align='center'>段落p标记对齐方式</h5>";
        contnet += "<hr color='blue'>";
        contnet += "<p align='left'>网页的外观是否美观，很大程度上取决于其排版。</p>";
        contnet += "<p align='center'>网页的外观是否美观，很大程度上取决于其排版。</p>";
        contnet += "<p align='right'>网页的外观是否美观，很大程度上取决于其排版。</p>";
        return contnet;
    }
    //  http://localhost:9090/api/html/htmlGetBaseHtmlPage
    @GetMapping("/htmlGetBaseHtmlPage")
    public ResponseEntity<StreamingResponseBody> htmlGetBaseHtmlPage(HttpServletRequest request) {
        MediaType mType = new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8);
        try {
            byte data[] = getHtmlString().getBytes();
            StreamingResponseBody stream = outputStream -> {
                outputStream.write(data);
            };
            return ResponseEntity.ok()
                    .contentType(mType)
                    .body(stream);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //  http://localhost:9090/api/html/htmlGetHtmlPage?name=lxq
    @GetMapping("/htmlGetHtmlPage")
    public void htmlGetHtmlPage(HttpServletRequest request,  HttpServletResponse response) {
        String pa = request.getParameter("name");
        System.out.println(pa);
    }
    public ResponseEntity<StreamingResponseBody> getResponseObject(String content){
        MediaType mType = new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8);
        try {
            byte data[] = content.getBytes();
            StreamingResponseBody stream = outputStream -> {
                outputStream.write(data);
            };
            return ResponseEntity.ok()
                    .contentType(mType)
                    .body(stream);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
    public ResponseEntity<StreamingResponseBody> getResponseData(String content){
        MediaType mType = new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8);
        try {
            byte data[] = content.getBytes();
            StreamingResponseBody stream = outputStream -> {
                outputStream.write(data);
            };
            return ResponseEntity.ok()
                    .contentType(mType)
                    .header("Cache-Control", "no-cache")
                    .body(stream);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
    @GetMapping("/submitFormInfo")
    public ResponseEntity<StreamingResponseBody> submitFormInfo(HttpServletRequest request) {
        String name = request.getParameter("name");
        String maths = request.getParameter("maths");
        String physics =request.getParameter("physics");
        String c = "";
        c += "<h3 align='center'>输入信息</h3>";
        c += "<table border=‘1’ width=‘500px’ align=‘center’ bordercolor=‘#3366ff’>";
        c += "<tr align='center'>";
        c += "	<td colspan='1'>姓名</td>";
        c += "	<td colspan='1'>"+name + "</td>";
        c += "	</tr>";
        c += "<tr align='center'>";
        c += "	<td colspan='1'>数学</td>";
        c += "	<td colspan='1'>"+maths + "</td>";
        c += "	</tr>";
        c += "<tr align='center'>";
        c += "	<td colspan='1'>物理</td>";
        c += "	<td colspan='1'>"+physics + "</td>";
        c += "	</tr>";
        c += "</table>";
        return getResponseObject(c);
    }

    @PostMapping("/getAjaxData")
    public String getAjaxData(HttpServletRequest request) {
        String name = request.getParameter("name");
        String c = "87,90";
        return c;
    }
    @PostMapping("/submitAchievement")
    public String submitAchievement(@Valid @RequestBody Map request) {
        String name = (String)request.get("name");
        String maths = (String)request.get("maths");
        String physics = (String)request.get("physics");
        String c = "姓名:"+name+",数学:"+maths+"，物理:"+physics;
        return c;
    }

}
