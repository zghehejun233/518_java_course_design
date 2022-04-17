package org.fatmansoft.teach.service;

import org.fatmansoft.teach.util.UimsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.yaml.snakeyaml.Yaml;

@Service
public class SystemService {
    @Autowired
    private ResourceLoader resourceLoader;

    public void loadUimsFile(){
        InputStream in = null;
        try {
            Yaml yaml = new Yaml();
            Resource resource = resourceLoader.getResource("classpath:uims.yml");
            in = resource.getInputStream();
            Map result =(Map)yaml.load(in);
            UimsUtil.getInstance().setRoot(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        return root;

    }

}

