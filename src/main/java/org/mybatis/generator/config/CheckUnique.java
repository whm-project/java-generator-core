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
public class CheckUnique extends PropertyHolder {

    /** The unique column name list. */
    private List<String> uniqueColumnNameList;

    /** The unique property name list. */
    private List<String> uniquePropertyNameList;

    /**
     *
     */
    public CheckUnique() {
        super();

        uniqueColumnNameList = new ArrayList<>();
        uniquePropertyNameList = new ArrayList<>();
    }

    public void addUniqueColumn(String uniqueColumnName){
        uniqueColumnNameList.add(uniqueColumnName);
        uniquePropertyNameList.add(JavaBeansUtil.getCamelCaseString(uniqueColumnName, false));
    }

    public List<String> getUniqueColumnNameList() {
        return uniqueColumnNameList;
    }

    public List<String> getUniquePropertyNameList() {
        return uniquePropertyNameList;
    }

    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("checkUnique"); //$NON-NLS-1$

        XmlElement uniqueColumnXmlElement = new XmlElement("uniqueColumnNameList"); //$NON-NLS-1$
        for(int i=0; i<uniqueColumnNameList.size(); i++){
            uniqueColumnXmlElement.addAttribute(new Attribute("column", uniqueColumnNameList.get(i)));
            uniqueColumnXmlElement.addAttribute(new Attribute("property", uniquePropertyNameList.get(i)));
        }

        xmlElement.addElement(uniqueColumnXmlElement);
        addPropertyXmlElements(xmlElement);
        return xmlElement;
    }
}
