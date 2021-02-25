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

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringContainsSpace;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * The Class ColumnOverride.
 *
 * @author Jeff Butler
 */
public class DeleteStateColumn extends PropertyHolder {

    /** The column name. */
    private String columnName;

    /** The property name. */
    private String propertyName;

    /** The delete state. */
    private String deleteState;

    /**
     * Instantiates a new column override.
     *
     * @param columnName
     * @param deleteState
     */
    public DeleteStateColumn(String columnName, String deleteState) {
        super();

        this.columnName = columnName;
        this.deleteState = deleteState;
        this.propertyName = JavaBeansUtil.getCamelCaseString(columnName, false);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.propertyName = JavaBeansUtil.getCamelCaseString(columnName, false);
    }

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    public String getPropertyName() {
        return propertyName;
    }

    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("deletedStateColumn"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("column", columnName)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("propertyName", propertyName)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("value", deleteState)); //$NON-NLS-1$
        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }
}
