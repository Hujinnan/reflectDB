package com.shou.hjn.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * springboot 启动时直接将sql文件加载到内存中
 */
@Component
public class ReadPropertiesFile  implements ServletContextAware,InitializingBean {
    private Map<String,String> sqlMap = new HashMap<>();

    public Map<String, String> getMap() {
        return sqlMap;
    }

    public void setMap(Map<String, String> map) {
        this.sqlMap = map;
    }

    //读取相应的sql文件
    public void getSqlByName(){
        try{
            Properties properties = new Properties();
            Resource resource = new ClassPathResource("sql.properties");
            File file = resource.getFile();
            properties.load(new InputStreamReader(new FileInputStream(file)));
//            properties.load(new InputStreamReader(ReadPropertiesFile.class.getClassLoader().getResourceAsStream("sql.properties"),"UTF-8"));
            Iterator<String> it = properties.stringPropertyNames().iterator();
            while (it.hasNext()){
                String key = it.next();
                System.out.println();
                if(key.trim() != "")
                    sqlMap.put(key,properties.getProperty(key));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        getSqlByName();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
