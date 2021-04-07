package org.mybatis.generator.codegen.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.PageTemplateEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mybatis.generator.codegen.freemarker.FreemarkerUtil.generateFreemarkerFile;


/**
 * 　* @Description: 生成接口实现类
 * 　* @author zdn
 * 　* @date 2018/5/25 9:38
 *
 */
public class PageGenerator {

    public static void addPageGenerator(List<PageTemplateEntity> pageTemplateEntityList) {
        try {
            for (PageTemplateEntity d : pageTemplateEntityList) {
                Configuration cfg = new Configuration();
                cfg.setClassForTemplateLoading(PageGenerator.class, "/template"); //指定模板所在的classpath目录

                //创建文件的路径
                String filePackage = "";
                //相对路径
                String pageTargetPackage = d.getPageTargetPackage();
                if(pageTargetPackage.indexOf(":") == -1){
                    filePackage = System.getProperty("user.dir") + "/" + pageTargetPackage;
                }
                //绝对路径
                else{
                    filePackage = pageTargetPackage;
                }

                /**
                 * 创建page
                 */
                //指定模板
                Template page_t = cfg.getTemplate("pageTemplate");
                //创建文件夹
                String page_filePackage = filePackage + "/ftl" + "/" + d.getModelClassName().toLowerCase();
                File page_f = new File(page_filePackage);
                page_f.mkdirs();
                //创建文件
                File page_file = new File(page_filePackage +"/" + d.getPageName() + ".ftl");
                if(!page_file.exists()){
                    generateFreemarkerFile(page_file, page_t, d);
                }

                /**
                 * 创建js
                 */
                //指定模板
                Template js_t = cfg.getTemplate("jsTemplate");
                //创建文件夹
                String js_filePackage = filePackage + "/static/js/" + d.getModelClassName().toLowerCase();
                File js_f = new File(js_filePackage);
                js_f.mkdirs();
                //创建文件
                File js_file = new File(js_filePackage +"/" + d.getPageName() + ".js");
                if(!js_file.exists()){
                    generateFreemarkerFile(js_file, js_t, d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
