package org.mybatis.generator.codegen.freemarker.TemplateEntity;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.config.CheckUnique;
import org.mybatis.generator.config.DeleteAffectedTable;
import org.mybatis.generator.config.DeleteStateColumn;
import org.mybatis.generator.config.TreeStructure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
　* @Description:
　* @author zdn
　* @date 2018/5/25 9:27
　*/
public class ControllerTemplateEntity implements TemplateEntity {
    private String listViewModelPackage;//listViewModel包名
    private String resultViewModelPackage;//resultViewModel包名
    private String searchTypeEnumPackage;//searchTypeEnum包名
    private String controllerPackage;//包名
    private String modelPackage;//所需要导入的model包
    private String queryModelPackage;//所需要导入的queryModel包
    private String modelServicePackage;//注入的service包
    private String requestUrl;//RequestMapping配置
    private String projectTargetPackage;//Service生成的目标工程包
    private String generatedDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//代码生成日期
    private String generatedTime =  new SimpleDateFormat("HH:mm").format(new Date());//代码生成时间

    private String modelClassName;//类名
    private List<String> keyColumn_javaProperty;//主键在实体类中的属性名
    private List<IntrospectedColumn> baseColumns;//字段在实体类中的属性名

    private TreeStructure treeStructure;  //树结构
    private List<CheckUnique> checkUniqueList;  //检查唯一
    private List<DeleteAffectedTable> deleteAffectedTableList;  //删除影响的其他表集合
    private DeleteStateColumn deleteStateColumn;  //状态删除影响的字段及字段值

    public List<String> getKeyColumn_javaProperty() {
        return keyColumn_javaProperty;
    }

    public void setKeyColumn_javaProperty(List<String> keyColumn_javaProperty) {
        this.keyColumn_javaProperty = keyColumn_javaProperty;
    }

    public List<IntrospectedColumn> getBaseColumns() {
        return baseColumns;
    }

    public void setBaseColumns(List<IntrospectedColumn> baseColumns) {
        this.baseColumns = baseColumns;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getQueryModelPackage() {
        return queryModelPackage;
    }

    public void setQueryModelPackage(String queryModelPackage) {
        this.queryModelPackage = queryModelPackage;
    }

    public String getModelServicePackage() {
        return modelServicePackage;
    }

    public void setModelServicePackage(String modelServicePackage) {
        this.modelServicePackage = modelServicePackage;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
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

    public List<DeleteAffectedTable> getDeleteAffectedTableList() {
        return deleteAffectedTableList;
    }

    public void setDeleteAffectedTableList(List<DeleteAffectedTable> deleteAffectedTableList) {
        this.deleteAffectedTableList = deleteAffectedTableList;
    }

    public DeleteStateColumn getDeleteStateColumn() {
        return deleteStateColumn;
    }

    public void setDeleteStateColumn(DeleteStateColumn deleteStateColumn) {
        this.deleteStateColumn = deleteStateColumn;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getListViewModelPackage() {
        return listViewModelPackage;
    }

    public void setListViewModelPackage(String listViewModelPackage) {
        this.listViewModelPackage = listViewModelPackage;
    }

    public String getResultViewModelPackage() {
        return resultViewModelPackage;
    }

    public void setResultViewModelPackage(String resultViewModelPackage) {
        this.resultViewModelPackage = resultViewModelPackage;
    }

    public String getSearchTypeEnumPackage() {
        return searchTypeEnumPackage;
    }

    public void setSearchTypeEnumPackage(String searchTypeEnumPackage) {
        this.searchTypeEnumPackage = searchTypeEnumPackage;
    }

    public List<CheckUnique> getCheckUniqueList() {
        return checkUniqueList;
    }

    public void setCheckUniqueList(List<CheckUnique> checkUniqueList) {
        this.checkUniqueList = checkUniqueList;
    }

    public TreeStructure getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(TreeStructure treeStructure) {
        this.treeStructure = treeStructure;
    }
}
