package org.mybatis.generator.codegen.freemarker.TemplateEntity;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.config.CheckUnique;
import org.mybatis.generator.config.TreeStructure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
　* @Description:
　* @author zdn
　* @date 2018/5/25 9:24
　*/
public class PageTemplateEntity implements TemplateEntity{

    private String pageName;//生成的page名
    private String pageTargetPackage;//page生成的目标工程包
    private String requestUrl;//RequestMapping配置
    private String modelClassName;//类名

    private boolean columnsHasBLOB;//是否包含BLOB字段
    private TreeStructure treeStructure;  //树结构
    private List<IntrospectedColumn> keyColumn;//主键在实体类中的属性名
    private List<IntrospectedColumn> baseColumns;//字段在实体类中的属性名
    private List<CheckUnique> checkUniqueList;  //检查唯一
    private List<String> includeFile;//依赖的基础页面

    public PageTemplateEntity(){
        keyColumn = new ArrayList<>();
        baseColumns = new ArrayList<>();
        checkUniqueList = new ArrayList<>();
        includeFile = new ArrayList<>();
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageTargetPackage() {
        return pageTargetPackage;
    }

    public void setPageTargetPackage(String pageTargetPackage) {
        this.pageTargetPackage = pageTargetPackage;
    }

    public boolean isColumnsHasBLOB() {
        return columnsHasBLOB;
    }

    public void setColumnsHasBLOB(boolean columnsHasBLOB) {
        this.columnsHasBLOB = columnsHasBLOB;
    }

    public TreeStructure getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(TreeStructure treeStructure) {
        this.treeStructure = treeStructure;
    }

    public List<IntrospectedColumn> getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(List<IntrospectedColumn> keyColumn) {
        this.keyColumn = keyColumn;
    }

    public List<IntrospectedColumn> getBaseColumns() {
        return baseColumns;
    }

    public void setBaseColumns(List<IntrospectedColumn> baseColumns) {
        this.baseColumns = baseColumns;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public List<String> getIncludeFile() {
        return includeFile;
    }

    public void setIncludeFile(List<String> includeFile) {
        this.includeFile = includeFile;
    }

    public List<CheckUnique> getCheckUniqueList() {
        return checkUniqueList;
    }

    public void setCheckUniqueList(List<CheckUnique> checkUniqueList) {
        this.checkUniqueList = checkUniqueList;
    }
}
