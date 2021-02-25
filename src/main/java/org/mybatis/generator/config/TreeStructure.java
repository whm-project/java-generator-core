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

/**
 * The Class ColumnOverride.
 *
 * @author Jeff Butler
 */
public class TreeStructure extends PropertyHolder {

    /** The selfCd column name. */
    private String selfCdColumnName;

    /** The selfCd property name. */
    private String selfCdPropertyName;

    /** The superCd column name. */
    private String superCdColumnName;

    /** The superCd property name. */
    private String superCdPropertyName;

    /** The treeIndex column name. */
    private String treeIndexColumnName;

    /** The treeIndex property name. */
    private String treeIndexPropertyName;

    /** The isParent column name. */
    private String isParentColumnName;

    /** The isParent property name. */
    private String isParentPropertyName;

    /**
     *
     */
    public TreeStructure(String selfCdColumnName, String superCdColumnName) {
        super();

        this.selfCdColumnName = selfCdColumnName;
        this.selfCdPropertyName = JavaBeansUtil.getCamelCaseString(selfCdColumnName, false);
        this.superCdColumnName = superCdColumnName;
        this.superCdPropertyName = JavaBeansUtil.getCamelCaseString(superCdColumnName, false);
    }

    /**
     *
     */
    public TreeStructure(String selfCdColumnName, String superCdColumnName, String treeIndexColumnName, String isParentColumnName) {
        super();

        this.selfCdColumnName = selfCdColumnName;
        this.selfCdPropertyName = JavaBeansUtil.getCamelCaseString(selfCdColumnName, false);
        this.superCdColumnName = superCdColumnName;
        this.superCdPropertyName = JavaBeansUtil.getCamelCaseString(superCdColumnName, false);
        this.treeIndexColumnName = treeIndexColumnName;
        this.treeIndexPropertyName = JavaBeansUtil.getCamelCaseString(treeIndexColumnName, false);
        this.isParentColumnName = isParentColumnName;
        this.isParentColumnName = JavaBeansUtil.getCamelCaseString(isParentColumnName, false);
    }

    public String getSelfCdColumnName() {
        return selfCdColumnName;
    }

    public void setSelfCdColumnName(String selfCdColumnName) {
        this.selfCdColumnName = selfCdColumnName;
        this.selfCdPropertyName = JavaBeansUtil.getCamelCaseString(selfCdColumnName, false);
    }

    public String getSelfCdPropertyName() {
        return selfCdPropertyName;
    }

    public String getSuperCdColumnName() {
        return superCdColumnName;
    }

    public void setSuperCdColumnName(String superCdColumnName) {
        this.superCdColumnName = superCdColumnName;
        this.superCdPropertyName = JavaBeansUtil.getCamelCaseString(superCdColumnName, false);
    }

    public String getSuperCdPropertyName() {
        return superCdPropertyName;
    }

    public String getTreeIndexColumnName() {
        return treeIndexColumnName;
    }

    public void setTreeIndexColumnName(String treeIndexColumnName) {
        this.treeIndexColumnName = treeIndexColumnName;
        this.treeIndexPropertyName = JavaBeansUtil.getCamelCaseString(treeIndexColumnName, false);
    }

    public String getTreeIndexPropertyName() {
        return treeIndexPropertyName;
    }

    public String getIsParentColumnName() {
        return isParentColumnName;
    }

    public void setIsParentColumnName(String isParentColumnName) {
        this.isParentColumnName = isParentColumnName;
        this.isParentPropertyName = JavaBeansUtil.getCamelCaseString(isParentColumnName, false);
    }

    public String getIsParentPropertyName() {
        return isParentPropertyName;
    }

    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("checkUnique"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("selfCdColumnName", selfCdColumnName));
        xmlElement.addAttribute(new Attribute("selfCdPropertyName", selfCdPropertyName));
        xmlElement.addAttribute(new Attribute("superCdColumnName", superCdColumnName));
        xmlElement.addAttribute(new Attribute("superCdPropertyName", superCdPropertyName));
        xmlElement.addAttribute(new Attribute("treeIndexColumnName", treeIndexColumnName));
        xmlElement.addAttribute(new Attribute("treeIndexPropertyName", treeIndexPropertyName));
        xmlElement.addAttribute(new Attribute("isParentColumnName", isParentColumnName));
        xmlElement.addAttribute(new Attribute("isParentPropertyName", isParentPropertyName));
        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }
}
