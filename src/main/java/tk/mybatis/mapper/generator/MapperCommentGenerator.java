package tk.mybatis.mapper.generator;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;

public class MapperCommentGenerator
        implements CommentGenerator
{
    private String beginningDelimiter = "";
    private String endingDelimiter = "";
    private boolean forceAnnotation;

    public void addJavaFileComment(CompilationUnit compilationUnit) {}

    public void addComment(XmlElement xmlElement)
    {
        xmlElement.addElement(new TextElement("<!--"));
        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - ");
        sb.append("@mbg.generated");
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement.addElement(new TextElement("-->"));
    }

    public void addRootComment(XmlElement rootElement) {}

    public void addConfigurationProperties(Properties properties)
    {
        String beginningDelimiter = properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }
        String endingDelimiter = properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }
        String forceAnnotation = properties.getProperty("forceAnnotation");
        if (StringUtility.stringHasValue(forceAnnotation)) {
            this.forceAnnotation = forceAnnotation.equalsIgnoreCase("TRUE");
        }
    }

    public String getDelimiterName(String name)
    {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(this.beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(this.endingDelimiter);
        return nameBuilder.toString();
    }

    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append("@mbg.generated");
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {}

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {}

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {
        StringBuilder sb;
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks()))
        {
            field.addJavaDocLine("/**");
            sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" * ");
            addJavadocTag(field, false);
            field.addJavaDocLine(" */");
        }
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        String update_Flag = "";
        Boolean isAuto = true;
        String column_name = introspectedColumn.getActualColumnName();
        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {

            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                update_Flag = ", updatable = false";
                if(introspectedTable.getTableConfiguration().getGeneratedKey()!=null
                        && introspectedColumn.getActualColumnName()
                        .equals(introspectedTable.getTableConfiguration().getGeneratedKey().getColumn())
                        &&(introspectedTable.getTableConfiguration().getGeneratedKey().isIdentity())){
                    update_Flag+=", insertable = false";
                }
                if (column_name.toLowerCase().equals(introspectedColumn.getJavaProperty())) {
                    field.addAnnotation("@Column(name = \"" + getDelimiterName(column_name) + "\""+update_Flag+")");
                    isAuto = false;
                }
                break;
            }
            if(introspectedTable.getTableConfiguration().getGeneratedKey()!=null
                    && introspectedColumn.getActualColumnName()
                    .equals(introspectedTable.getTableConfiguration().getGeneratedKey().getColumn())){
                update_Flag = ", updatable = false, insertable = false";
                if (column_name.toLowerCase().equals(introspectedColumn.getJavaProperty())) {
                    field.addAnnotation("@Column(name = \"" + getDelimiterName(column_name) + "\""+update_Flag+")");
                    isAuto = false;
                }
            }

        }

        if ((StringUtility.stringContainsSpace(column_name)) || (introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled())) {
            column_name = introspectedColumn.getContext().getBeginningDelimiter() + column_name + introspectedColumn.getContext().getEndingDelimiter();
        }
        if (!column_name.equals(introspectedColumn.getJavaProperty()) && isAuto) {
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column_name) + "\""+update_Flag+")");
        } else if ((StringUtility.stringHasValue(this.beginningDelimiter)) || (StringUtility.stringHasValue(this.endingDelimiter))) {
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column_name) + "\""+update_Flag+")");
        } else if (this.forceAnnotation) {
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column_name) + "\""+update_Flag+")");
        }
        if (introspectedColumn.isIdentity())
        {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        }
        else if (introspectedColumn.isSequenceColumn())
        {
            String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), new Object[] { tableName, tableName.toUpperCase() });
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
        }
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {}

    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {}

    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {}

    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks()))
        {
            sb.append(" * 获取");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks()))
        {
            sb.append(" - ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" *");
        method.addJavaDocLine(" * @mbg.generated");
        method.addJavaDocLine(" */");
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks()))
        {
            sb.append(" * 设置");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        Parameter parm = (Parameter)method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks()))
        {
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" *");
        method.addJavaDocLine(" * @mbg.generated");
        method.addJavaDocLine(" */");
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {}

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {}

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {}

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {}

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {}

    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {}
}
