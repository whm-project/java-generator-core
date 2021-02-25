package org.mybatis.generator.codegen.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.ServiceImplTemplateEntity;

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
public class JavaServiceImplGenerator {

    public static void addJavaServiceImplGenerator(List<ServiceImplTemplateEntity> serviceImplTemplateEntityList) {
        try {
            for (ServiceImplTemplateEntity d : serviceImplTemplateEntityList) {
                Configuration cfg = new Configuration();
                cfg.setClassForTemplateLoading(JavaServiceImplGenerator.class, "/template"); //指定模板所在的classpath目录
                Template t = cfg.getTemplate("ServiceImplTemplate"); //指定模板

                //创建文件的路径
                String filePackage = "";
                //相对路径
                String projectTargetPackage = d.getProjectTargetPackage();
                if(projectTargetPackage.indexOf(":") == -1){
                    filePackage = System.getProperty("user.dir") + "/" + projectTargetPackage;
                }
                //绝对路径
                else{
                    filePackage = projectTargetPackage;
                }
                File f = new File(filePackage);
                f.mkdirs();

                //创建文件
                File domainFile = new File(filePackage + d.getClassName() + ".java");
                generateFreemarkerFile(domainFile, t, d);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
