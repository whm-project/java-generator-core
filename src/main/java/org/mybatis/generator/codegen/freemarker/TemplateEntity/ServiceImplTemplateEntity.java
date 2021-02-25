package org.mybatis.generator.codegen.freemarker.TemplateEntity;

import org.mybatis.generator.config.TreeStructure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
　* @Description:
　* @author zdn
　* @date 2018/5/25 9:24
　*/
public class ServiceImplTemplateEntity implements TemplateEntity{
    private String templatePackage;//包名
    private String modelPackage;//所需要导入的model包
    private String mapperPackage;//所需要导入的mapper包
    private String servicePackage;//所需要导入的service包
    private String className;//生成的ServiceImpl类名
    private String serviceInterface;//实现的service接口
    private String mapperType;//对应的Mapper类
    private String mapperName;//Mapper类的实例对象名
    private String modelClazz;//对应的model类
    private String baseServiceImplPackage;//继承的基础实现类
    private String projectTargetPackage;//Service生成的目标工程包
    private String generatedDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//代码生成日期
    private String generatedTime =  new SimpleDateFormat("HH:mm").format(new Date());//代码生成时间
    private boolean columnsHasBLOB;//是否包含BLOB字段

    private TreeStructure treeStructure;  //树结构
    private List<String> keyColumn_javaProperty;//主键在实体类中的属性名
    private String searchTypeEnumPackage;//searchTypeEnum包名

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getTemplatePackage() {
        return templatePackage;
    }

    public void setTemplatePackage(String templatePackage) {
        this.templatePackage = templatePackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMapperType() {
        return mapperType;
    }

    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
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

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public String getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public void setGeneratedTime(String generatedTime) {
        this.generatedTime = generatedTime;
    }

    public boolean isColumnsHasBLOB() {
        return columnsHasBLOB;
    }

    public void setColumnsHasBLOB(boolean columnsHasBLOB) {
        this.columnsHasBLOB = columnsHasBLOB;
    }

    public String getBaseServiceImplPackage() {
        return baseServiceImplPackage;
    }

    public void setBaseServiceImplPackage(String baseServiceImplPackage) {
        this.baseServiceImplPackage = baseServiceImplPackage;
    }

    public TreeStructure getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(TreeStructure treeStructure) {
        this.treeStructure = treeStructure;
    }

    public List<String> getKeyColumn_javaProperty() {
        return keyColumn_javaProperty;
    }

    public void setKeyColumn_javaProperty(List<String> keyColumn_javaProperty) {
        this.keyColumn_javaProperty = keyColumn_javaProperty;
    }

    public String getSearchTypeEnumPackage() {
        return searchTypeEnumPackage;
    }

    public void setSearchTypeEnumPackage(String searchTypeEnumPackage) {
        this.searchTypeEnumPackage = searchTypeEnumPackage;
    }
}
