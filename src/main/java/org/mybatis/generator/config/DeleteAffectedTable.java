/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.config;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * The Class ColumnOverride.
 *
 * @author Jeff Butler
 */
public class DeleteAffectedTable extends PropertyHolder {


    /** The affected table name. */
    private String affectedTableName;

    /** The affected class name. */
    private String affectedClassName;

    /** The source column name. */
    private List<String> sourceColumnNameList;

    /** The source property name. */
    private List<String> sourcePropertyNameList;

    /** The affected column name. */
    private List<String> affectedColumnNameList;

    /** The affected property name. */
    private List<String> affectedPropertyNameList;

    /**
     *
     */
    public DeleteAffectedTable() {
        super();

        sourceColumnNameList = new ArrayList<>();
        sourcePropertyNameList = new ArrayList<>();
        affectedColumnNameList = new ArrayList<>();
        affectedPropertyNameList = new ArrayList<>();
    }

    public void setAffectedTableName(String affectedTableName) {
        this.affectedTableName = affectedTableName;
        this.affectedClassName = JavaBeansUtil.getCamelCaseString(affectedTableName, true);
    }

    public void addColumnMatch(String sourceColumnName, String affectedColumnName){
        sourceColumnNameList.add(sourceColumnName);
        sourcePropertyNameList.add(JavaBeansUtil.getCamelCaseString(sourceColumnName, false));
        affectedColumnNameList.add(affectedColumnName);
        affectedPropertyNameList.add(JavaBeansUtil.getCamelCaseString(affectedColumnName, false));
    }

    public String getAffectedTableName() {
        return affectedTableName;
    }

    public String getAffectedClassName() {
        return affectedClassName;
    }

    public List<String> getSourceColumnNameList() {
        return sourceColumnNameList;
    }

    public List<String> getSourcePropertyNameList() {
        return sourcePropertyNameList;
    }

    public List<String> getAffectedColumnNameList() {
        return affectedColumnNameList;
    }

    public List<String> getAffectedPropertyNameList() {
        return affectedPropertyNameList;
    }

    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("deleteAffectedTable"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("table", affectedTableName)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("className", affectedClassName)); //$NON-NLS-1$

        XmlElement sourceXmlElement = new XmlElement("sourceColumnList"); //$NON-NLS-1$
        XmlElement affectedXmlElement = new XmlElement("affectedColumnList"); //$NON-NLS-1$
        for(int i=0; i<sourceColumnNameList.size(); i++){
            if(affectedColumnNameList.size() >i){
                sourceXmlElement.addAttribute(new Attribute("column", sourceColumnNameList.get(i)));
                sourceXmlElement.addAttribute(new Attribute("property", sourcePropertyNameList.get(i)));
                affectedXmlElement.addAttribute(new Attribute("column", affectedColumnNameList.get(i)));
                affectedXmlElement.addAttribute(new Attribute("column", affectedPropertyNameList.get(i)));
            }
        }

        xmlElement.addElement(sourceXmlElement);
        xmlElement.addElement(affectedXmlElement);
        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }
}
