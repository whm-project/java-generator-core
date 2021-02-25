package org.mybatis.generator.codegen.freemarker.TemplateEntity;

import org.mybatis.generator.config.TreeStructure;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
　* @Description:
　* @author zdn
　* @date 2018/5/25 9:27
　*/
public class ServiceTemplateEntity implements TemplateEntity {
    private String servicePackage;//包名
    private String modelPackage;//所需要导入的model包
    private String serviceInterface;//生成的Service类名
    private String baseServiceInterface;//集成的基础service类
    private String baseServicePackage;//基础service类所在jar包
    private String modelClazz;//对应的model类
    private String projectTargetPackage;//Service生成的目标工程包
    private String generatedDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//代码生成日期
    private String generatedTime =  new SimpleDateFormat("HH:mm").format(new Date());//代码生成时间

    private TreeStructure treeStructure;  //树结构

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getBaseServiceInterface() {
        return baseServiceInterface;
    }

    public void setBaseServiceInterface(String baseServiceInterface) {
        this.baseServiceInterface = baseServiceInterface;
    }

    public String getModelClazz() {
        return modelClazz;
    }

    public void setModelClazz(String modelClazz) {
        this.modelClazz = modelClazz;
    }

    public String getProjectTargetPackage() {
        return projectTargetPackage;
    }

    public void setProjectTargetPackage(String projectTargetPackage) {
        this.projectTargetPackage = projectTargetPackage;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(String generatedTime) {
        this.generatedTime = generatedTime;
    }

    public String getBaseServicePackage() {
        return baseServicePackage;
    }

    public void setBaseServicePackage(String baseServicePackage) {
        this.baseServicePackage = baseServicePackage;
    }

    public TreeStructure getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(TreeStructure treeStructure) {
        this.treeStructure = treeStructure;
    }
}
