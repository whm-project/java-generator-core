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
package org.mybatis.generator.api;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getCamelCaseString;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.*;

import org.apache.tools.ant.taskdefs.Get;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.codegen.freemarker.JavaControllerGenerator;
import org.mybatis.generator.codegen.freemarker.JavaServiceGenerator;
import org.mybatis.generator.codegen.freemarker.JavaServiceImplGenerator;
import org.mybatis.generator.codegen.freemarker.PageGenerator;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.ControllerTemplateEntity;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.PageTemplateEntity;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.ServiceTemplateEntity;
import org.mybatis.generator.codegen.freemarker.TemplateEntity.ServiceImplTemplateEntity;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.*;

/**
 * This class is the main interface to MyBatis generator. A typical execution of the tool involves these steps:
 * 
 * <ol>
 * <li>Create a Configuration object. The Configuration can be the result of a parsing the XML configuration file, or it
 * can be created solely in Java.</li>
 * <li>Create a MyBatisGenerator object</li>
 * <li>Call one of the generate() methods</li>
 * </ol>
 *
 * @author Jeff Butler
 * @see org.mybatis.generator.config.xml.ConfigurationParser
 */
public class MyBatisGenerator {

    /** The configuration. */
    private Configuration configuration;

    /** The shell callback. */
    private ShellCallback shellCallback;

    /** The generated java files. */
    private List<GeneratedJavaFile> generatedJavaFiles;

    /** The generated xml files. */
    private List<GeneratedXmlFile> generatedXmlFiles;

    /** The warnings. */
    private List<String> warnings;

    /** The projects. */
    private Set<String> projects;

    /**
     * Constructs a MyBatisGenerator object.
     * 
     * @param configuration
     *            The configuration for this invocation
     * @param shellCallback
     *            an instance of a ShellCallback interface. You may specify
     *            <code>null</code> in which case the DefaultShellCallback will
     *            be used.
     * @param warnings
     *            Any warnings generated during execution will be added to this
     *            list. Warnings do not affect the running of the tool, but they
     *            may affect the results. A typical warning is an unsupported
     *            data type. In that case, the column will be ignored and
     *            generation will continue. You may specify <code>null</code> if
     *            you do not want warnings returned.
     * @throws InvalidConfigurationException
     *             if the specified configuration is invalid
     */
    public MyBatisGenerator(Configuration configuration, ShellCallback shellCallback,
            List<String> warnings) throws InvalidConfigurationException {
        super();
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }

        if (shellCallback == null) {
            this.shellCallback = new DefaultShellCallback(false);
        } else {
            this.shellCallback = shellCallback;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<String>();
        } else {
            this.warnings = warnings;
        }
        generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
        projects = new HashSet<String>();

        this.configuration.validate();
    }

    /**
     * This is the main method for generating code. This method is long running, but progress can be provided and the
     * method can be canceled through the ProgressCallback interface. This version of the method runs all configured
     * contexts.
     *
     * @param callback
     *            an instance of the ProgressCallback interface, or <code>null</code> if you do not require progress
     *            information
     * @throws SQLException
     *             the SQL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws InterruptedException
     *             if the method is canceled through the ProgressCallback
     */
    public void generate(ProgressCallback callback) throws SQLException,
            IOException, InterruptedException {
        generate(callback, null, null, true);
    }

    /**
     * This is the main method for generating code. This method is long running, but progress can be provided and the
     * method can be canceled through the ProgressCallback interface.
     *
     * @param callback
     *            an instance of the ProgressCallback interface, or <code>null</code> if you do not require progress
     *            information
     * @param contextIds
     *            a set of Strings containing context ids to run. Only the contexts with an id specified in this list
     *            will be run. If the list is null or empty, than all contexts are run.
     * @throws SQLException
     *             the SQL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws InterruptedException
     *             if the method is canceled through the ProgressCallback
     */
    public void generate(ProgressCallback callback, Set<String> contextIds)
            throws SQLException, IOException, InterruptedException {
        generate(callback, contextIds, null, true);
    }

    /**
     * This is the main method for generating code. This method is long running, but progress can be provided and the
     * method can be cancelled through the ProgressCallback interface.
     *
     * @param callback
     *            an instance of the ProgressCallback interface, or <code>null</code> if you do not require progress
     *            information
     * @param contextIds
     *            a set of Strings containing context ids to run. Only the contexts with an id specified in this list
     *            will be run. If the list is null or empty, than all contexts are run.
     * @param fullyQualifiedTableNames
     *            a set of table names to generate. The elements of the set must be Strings that exactly match what's
     *            specified in the configuration. For example, if table name = "foo" and schema = "bar", then the fully
     *            qualified table name is "foo.bar". If the Set is null or empty, then all tables in the configuration
     *            will be used for code generation.
     * @throws SQLException
     *             the SQL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws InterruptedException
     *             if the method is canceled through the ProgressCallback
     */
    public void generate(ProgressCallback callback, Set<String> contextIds,
            Set<String> fullyQualifiedTableNames) throws SQLException,
            IOException, InterruptedException {
        generate(callback, contextIds, fullyQualifiedTableNames, true);
    }

    /**
     * This is the main method for generating code. This method is long running, but progress can be provided and the
     * method can be cancelled through the ProgressCallback interface.
     *
     * @param callback
     *            an instance of the ProgressCallback interface, or <code>null</code> if you do not require progress
     *            information
     * @param contextIds
     *            a set of Strings containing context ids to run. Only the contexts with an id specified in this list
     *            will be run. If the list is null or empty, than all contexts are run.
     * @param fullyQualifiedTableNames
     *            a set of table names to generate. The elements of the set must be Strings that exactly match what's
     *            specified in the configuration. For example, if table name = "foo" and schema = "bar", then the fully
     *            qualified table name is "foo.bar". If the Set is null or empty, then all tables in the configuration
     *            will be used for code generation.
     * @param writeFiles
     *            if true, then the generated files will be written to disk.  If false,
     *            then the generator runs but nothing is written
     * @throws SQLException
     *             the SQL exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws InterruptedException
     *             if the method is canceled through the ProgressCallback
     */
    public void generate(ProgressCallback callback, Set<String> contextIds,
            Set<String> fullyQualifiedTableNames, boolean writeFiles) throws SQLException,
            IOException, InterruptedException {

        if (callback == null) {
            callback = new NullProgressCallback();
        }

        generatedJavaFiles.clear();
        generatedXmlFiles.clear();
        ObjectFactory.reset();
        RootClassInfo.reset();

        // calculate the contexts to run
        List<Context> contextsToRun;
        if (contextIds == null || contextIds.size() == 0) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun = new ArrayList<Context>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }

        // setup custom classloader if required
        if (configuration.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        // now run the introspections...
        int totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getIntrospectionSteps();
        }
        callback.introspectionStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.introspectTables(callback, warnings,
                    fullyQualifiedTableNames);
        }

        // now run the generates
        totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getGenerationSteps();
        }
        callback.generationStarted(totalSteps);

        for (Context context : contextsToRun) {
            context.generateFiles(callback, generatedJavaFiles,
                    generatedXmlFiles, warnings);
        }
        /**
         * 如果JavaServiceImplGeneratorConfiguration存在则调用相关方法
         * 如果JavaServiceGeneratorConfiguration存在则调用相关方法
         * 如果JavaControllerGeneratorConfiguration存在则调用相关方法
         */

        for (Context c:configuration.getContexts()) {


            if (c.getJavaServiceGeneratorConfiguration() != null) {
                JavaServiceGenerator.addJavaServiceGenerator(assignmentServiceTemplateEntity());
            }
            if (c.getJavaServiceImplGeneratorConfiguration() != null) {

                JavaServiceImplGenerator.addJavaServiceImplGenerator(assignmentServiceImplTemplateEntity());
            }
            if (c.getJavaControllerGeneratorConfiguration() != null) {

                JavaControllerGenerator.addJavaControllerGenerator(assignmentControllerTemplateEntity());
            }
            if (c.getPageGeneratorConfiguration() != null) {

                PageGenerator.addPageGenerator(assignmentPageTemplateEntity());
            }

        }

        // now save the files
        if (writeFiles) {
            callback.saveStarted(generatedXmlFiles.size()
                + generatedJavaFiles.size());

            for (GeneratedXmlFile gxf : generatedXmlFiles) {
                projects.add(gxf.getTargetProject());
                writeGeneratedXmlFile(gxf, callback);

            }

            for (GeneratedJavaFile gjf : generatedJavaFiles) {
                projects.add(gjf.getTargetProject());
                writeGeneratedJavaFile(gjf, callback);
            }

            for (String project : projects) {
                shellCallback.refreshProject(project);
            }
        }

        callback.done();
    }

    private void writeGeneratedJavaFile(GeneratedJavaFile gjf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gjf
                    .getTargetProject(), gjf.getTargetPackage());
            targetFile = new File(directory, gjf.getFileName());
            if (targetFile.exists()) {
                if (shellCallback.isMergeSupported()) {
//                    source = shellCallback.mergeJavaFile(gjf
//                            .getFormattedContent(), targetFile
//                            .getAbsolutePath(),
//                            MergeConstants.OLD_ELEMENT_TAGS,
//                            gjf.getFileEncoding());
                    source = shellCallback.mergeJavaFile(gjf
                            .getFormattedContent(), targetFile
                            .getAbsolutePath());

                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gjf.getFormattedContent();
                    warnings.add(getString("Warning.11", //$NON-NLS-1$
                            targetFile.getAbsolutePath()));
                } else {
                    source = gjf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gjf
                            .getFileName());
                    warnings.add(getString(
                            "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
                source = gjf.getFormattedContent();
            }

            callback.checkCancel();
            callback.startTask(getString(
                    "Progress.15", targetFile.getName())); //$NON-NLS-1$
            writeFile(targetFile, source, gjf.getFileEncoding());
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }

    private void writeGeneratedXmlFile(GeneratedXmlFile gxf, ProgressCallback callback)
            throws InterruptedException, IOException {
        File targetFile;
        String source;
        try {
            File directory = shellCallback.getDirectory(gxf
                    .getTargetProject(), gxf.getTargetPackage());
            targetFile = new File(directory, gxf.getFileName());
            if (targetFile.exists()) {
                if (gxf.isMergeable()) {
                    source = XmlFileMergerJaxp.getMergedSource(gxf,
                            targetFile);
                } else if (shellCallback.isOverwriteEnabled()) {
                    source = gxf.getFormattedContent();
                    warnings.add(getString("Warning.11", //$NON-NLS-1$
                            targetFile.getAbsolutePath()));
                } else {
                    source = gxf.getFormattedContent();
                    targetFile = getUniqueFileName(directory, gxf
                            .getFileName());
                    warnings.add(getString(
                            "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                }
            } else {
                source = gxf.getFormattedContent();
            }

            callback.checkCancel();
            callback.startTask(getString(
                    "Progress.15", targetFile.getName())); //$NON-NLS-1$
            writeFile(targetFile, source, "UTF-8"); //$NON-NLS-1$
        } catch (ShellException e) {
            warnings.add(e.getMessage());
        }
    }
    
    /**
     * Writes, or overwrites, the contents of the specified file.
     *
     * @param file
     *            the file
     * @param content
     *            the content
     * @param fileEncoding
     *            the file encoding
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeFile(File file, String content, String fileEncoding) throws IOException {

        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }
        
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }

    /**
     * Gets the unique file name.
     *
     * @param directory
     *            the directory
     * @param fileName
     *            the file name
     * @return the unique file name
     */
    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;

        // try up to 1000 times to generate a unique file name
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);

            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }

        if (answer == null) {
            throw new RuntimeException(getString(
                    "RuntimeError.3", directory.getAbsolutePath())); //$NON-NLS-1$
        }

        return answer;
    }

    /**
     * Returns the list of generated Java files after a call to one of the generate methods.
     * This is useful if you prefer to process the generated files yourself and do not want
     * the generator to write them to disk.
     *  
     * @return the list of generated Java files
     */
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        return generatedJavaFiles;
    }

    /**
     * Returns the list of generated XML files after a call to one of the generate methods.
     * This is useful if you prefer to process the generated files yourself and do not want
     * the generator to write them to disk.
     *  
     * @return the list of generated XML files
     */
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        return generatedXmlFiles;
    }



    public List<ControllerTemplateEntity> assignmentControllerTemplateEntity(){
        List<Context> contexts = configuration.getContexts();
        List<ControllerTemplateEntity> controllerTemplateEntityList = new ArrayList<>();
        for(Context c : contexts){
            JavaControllerGeneratorConfiguration jdc = c.getJavaControllerGeneratorConfiguration();
            Properties properties = jdc.getProperties();
            //controller配置
            String listViewModelPackage = properties.getProperty("listViewModelPackage");
            String resultViewModelPackage = properties.getProperty("resultViewModelPackage");
            String  searchTypeEnumPackage = properties.getProperty("searchTypeEnumPackage");

            List<TableConfiguration> tableConfigurations = c.getTableConfigurations();
            for(int i=0; i<tableConfigurations.size(); i++){
                TableConfiguration t = tableConfigurations.get(i);
                String controllerObjectName = this.getControllerObjectName(t);
                ControllerTemplateEntity controllerTemplateEntity = new ControllerTemplateEntity();

                if (listViewModelPackage!="" && listViewModelPackage != null) {
                    controllerTemplateEntity.setListViewModelPackage(listViewModelPackage);
                }
                if (resultViewModelPackage!="" && resultViewModelPackage != null) {
                    controllerTemplateEntity.setResultViewModelPackage(resultViewModelPackage);
                }
                if (searchTypeEnumPackage!="" && searchTypeEnumPackage != null) {
                    controllerTemplateEntity.setSearchTypeEnumPackage(searchTypeEnumPackage);
                }
                controllerTemplateEntity.setControllerPackage(jdc.getTargetPackage());
                controllerTemplateEntity.setModelPackage(c.getJavaModelGeneratorConfiguration().getTargetPackage());
                if(c.getJavaQueryModelGeneratorConfiguration() != null){
                    controllerTemplateEntity.setQueryModelPackage(c.getJavaQueryModelGeneratorConfiguration().getTargetPackage());
                }
                controllerTemplateEntity.setModelServicePackage(c.getJavaServiceGeneratorConfiguration().getTargetPackage());
                controllerTemplateEntity.setProjectTargetPackage(jdc.getTargetProject()+"/"+jdc.getTargetPackage().replaceAll("\\.","/")+"/");
                controllerTemplateEntity.setRequestUrl(Character.toLowerCase(controllerObjectName.charAt(0)) + controllerObjectName.substring(1)+"*");

                controllerTemplateEntity.setModelClassName(controllerObjectName);

                /**
                 * 这个表的所有主键（可能是组合主键，所以可能是多个）
                 */
                List<String> keyColumn_javaProperty = new ArrayList<>();
                List<String> keyColumn_javaProperty_firstUp = new ArrayList<>();
                IntrospectedTable introspectedTable = c.getIntrospectedTables().get(i);
                for (IntrospectedColumn column : introspectedTable.primaryKeyColumns) {
                    keyColumn_javaProperty.add(column.getJavaProperty());
                    keyColumn_javaProperty_firstUp.add(Character.toUpperCase(column.getJavaProperty().charAt(0)) + column.getJavaProperty().substring(1));
                }
                controllerTemplateEntity.setKeyColumn_javaProperty(keyColumn_javaProperty);

                //这个表的所有字段
                controllerTemplateEntity.setBaseColumns(introspectedTable.getBaseColumns());

                //树结构
                controllerTemplateEntity.setTreeStructure(t.getTreeStructure());
                //检查唯一
                controllerTemplateEntity.setCheckUniqueList(t.getCheckUniqueList());
                //状态删除修改的字段
                controllerTemplateEntity.setDeleteStateColumn(t.getDeleteStateColumn());
                //删除影响的其他表
                controllerTemplateEntity.setDeleteAffectedTableList(t.getDeleteAffectedTableList());

                //添加到集合中
                controllerTemplateEntityList.add(controllerTemplateEntity);
            }
        }
        return controllerTemplateEntityList;
    }

    public List<ServiceTemplateEntity> assignmentServiceTemplateEntity(){
        List<Context> contexts = configuration.getContexts();
        List<ServiceTemplateEntity> serviceTemplateEntityList = new ArrayList<ServiceTemplateEntity>();
        for (Context c: contexts){
            JavaServiceGeneratorConfiguration jdc = c.getJavaServiceGeneratorConfiguration();
            List<TableConfiguration> tableConfigurations = c.getTableConfigurations();
            Properties properties = jdc.getProperties();
            for (TableConfiguration t:tableConfigurations){
                String serviceObjectName = this.getServiceObjectName(t);
                ServiceTemplateEntity serviceTemplate = new ServiceTemplateEntity();
                serviceTemplate.setServicePackage(jdc.getTargetPackage());
                String  rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
                if (rootClass!="" && rootClass != null) {
                    serviceTemplate.setBaseServicePackage(rootClass);
                }else{
                    serviceTemplate.setBaseServicePackage("com.my.base.BaseService");
                }
                serviceTemplate.setModelPackage(c.getJavaModelGeneratorConfiguration().getTargetPackage()+"."+serviceObjectName);
                serviceTemplate.setServiceInterface(serviceObjectName+"Service");
                serviceTemplate.setProjectTargetPackage(jdc.getTargetProject()+"/"+jdc.getTargetPackage().replaceAll("\\.","/")+"/");
                serviceTemplate.setBaseServiceInterface("BaseService<"+serviceObjectName+">");
                //树结构
                serviceTemplate.setTreeStructure(t.getTreeStructure());
                serviceTemplateEntityList.add(serviceTemplate);
            }
        }
        return serviceTemplateEntityList;

    }

    public List<ServiceImplTemplateEntity> assignmentServiceImplTemplateEntity(){
        List<Context> contexts = configuration.getContexts();
        List<ServiceImplTemplateEntity> serviceTemplateEntities = new ArrayList<ServiceImplTemplateEntity>();
        boolean flag;
        for (Context c:contexts){
            JavaServiceImplGeneratorConfiguration jgc = c.getJavaServiceImplGeneratorConfiguration();
            List<TableConfiguration> tableConfigurations = c.getTableConfigurations();
            Properties properties = jgc.getProperties();
            for(int i=0; i<tableConfigurations.size(); i++){
                TableConfiguration t = tableConfigurations.get(i);
                flag = false;
                for (GeneratedJavaFile gjf : generatedJavaFiles) {
                    if (gjf.getFileName().contains("WithBLOBs")&& gjf.getFileName().contains(t.getServiceImplObjectName())) {
                        flag = true;
                        break;
                    }
                }
                String serviceObjectName = this.getServiceImplObjectName(t);
                ServiceImplTemplateEntity serviceImplTemplateEntity = new ServiceImplTemplateEntity();
                serviceImplTemplateEntity.setClassName(serviceObjectName+"ServiceImpl");
                String projectTargetPackage = jgc.getTargetProject()+"/"+jgc.getTargetPackage().replaceAll("\\.","/")+"/";
                serviceImplTemplateEntity.setProjectTargetPackage(projectTargetPackage);
                serviceImplTemplateEntity.setTemplatePackage(jgc.getTargetPackage());
                serviceImplTemplateEntity.setMapperType(serviceObjectName+"Dao");
                String  rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
                if (rootClass!="" && rootClass != null) {
                    serviceImplTemplateEntity.setBaseServiceImplPackage(rootClass);
                }else{
                    serviceImplTemplateEntity.setBaseServiceImplPackage("com.my.base.BaseServiceImpl");
                }
                serviceImplTemplateEntity.setMapperName(Character.toLowerCase(serviceObjectName.charAt(0)) + serviceObjectName.substring(1)+"Dao");
                serviceImplTemplateEntity.setModelClazz(serviceObjectName);
                serviceImplTemplateEntity.setMapperPackage(c.getJavaClientGeneratorConfiguration().getTargetPackage()+"."+serviceObjectName+"Dao");
                serviceImplTemplateEntity.setModelPackage(c.getJavaModelGeneratorConfiguration().getTargetPackage()+"."+serviceObjectName);
                serviceImplTemplateEntity.setServicePackage(c.getJavaServiceGeneratorConfiguration().getTargetPackage()+"."+serviceObjectName+"Service");
                serviceImplTemplateEntity.setServiceInterface(serviceObjectName+"Service");
                serviceImplTemplateEntity.setColumnsHasBLOB(flag);


                //searchType包
                String  searchTypeEnumPackage = properties.getProperty("searchTypeEnumPackage");
                if (searchTypeEnumPackage!="" && searchTypeEnumPackage != null) {
                    serviceImplTemplateEntity.setSearchTypeEnumPackage(searchTypeEnumPackage);
                }
                //树结构
                serviceImplTemplateEntity.setTreeStructure(t.getTreeStructure());


                IntrospectedTable introspectedTable = c.getIntrospectedTables().get(i);
                /**
                 * 这个表的所有主键（可能是组合主键，所以可能是多个）
                 */
                List<String> keyColumn_javaProperty = new ArrayList<>();
                List<String> keyColumn_javaProperty_firstUp = new ArrayList<>();
                for (IntrospectedColumn column : introspectedTable.primaryKeyColumns) {
                    keyColumn_javaProperty.add(column.getJavaProperty());
                    keyColumn_javaProperty_firstUp.add(Character.toUpperCase(column.getJavaProperty().charAt(0)) + column.getJavaProperty().substring(1));
                }
                serviceImplTemplateEntity.setKeyColumn_javaProperty(keyColumn_javaProperty);

                //添加到集合
                serviceTemplateEntities.add(serviceImplTemplateEntity);
            }
        }
        return serviceTemplateEntities;
    }

    /**
     * 生成页面
     * @return
     */
    public List<PageTemplateEntity> assignmentPageTemplateEntity(){
        List<Context> contexts = configuration.getContexts();
        List<PageTemplateEntity> pageTemplateEntities = new ArrayList<PageTemplateEntity>();

        boolean flag;
        for (Context c:contexts){
            PageGeneratorConfiguration jgc = c.getPageGeneratorConfiguration();
            List<TableConfiguration> tableConfigurations = c.getTableConfigurations();
            Properties properties = jgc.getProperties();
            for(int i=0; i<tableConfigurations.size(); i++){
                TableConfiguration t = tableConfigurations.get(i);

                flag = false;
                for (GeneratedJavaFile gjf : generatedJavaFiles) {
                    if (gjf.getFileName().contains("WithBLOBs")&& gjf.getFileName().contains(t.getServiceImplObjectName())) {
                        flag = true;
                        break;
                    }
                }

                PageTemplateEntity pageTemplateEntity = new PageTemplateEntity();
                pageTemplateEntity.setPageName("manager");
                String pageTargetPackage = jgc.getTargetProject()+"/"+jgc.getTargetPackage().replaceAll("\\.","/");
                pageTemplateEntity.setPageTargetPackage(pageTargetPackage);
                pageTemplateEntity.setColumnsHasBLOB(flag);
                //树结构
                pageTemplateEntity.setTreeStructure(t.getTreeStructure());

                IntrospectedTable introspectedTable = c.getIntrospectedTables().get(i);
                //这个表的所有主键（可能是组合主键，所以可能是多个）
                pageTemplateEntity.setKeyColumn(introspectedTable.primaryKeyColumns);
                //这个表的所有字段
                pageTemplateEntity.setBaseColumns(introspectedTable.getBaseColumns());
                //这个表的唯一键（或组合）
                pageTemplateEntity.setCheckUniqueList(t.getCheckUniqueList());

                //要访问的controller地址 和 类名
                String controllerObjectName = this.getControllerObjectName(t);
                pageTemplateEntity.setRequestUrl(Character.toLowerCase(controllerObjectName.charAt(0)) + controllerObjectName.substring(1));
                pageTemplateEntity.setModelClassName(controllerObjectName);

                //添加到集合
                pageTemplateEntities.add(pageTemplateEntity);
            }
        }
        return pageTemplateEntities;
    }




    public String getServiceObjectName(TableConfiguration t) {
        if (stringHasValue(t.getServiceObjectName())) {
            return t.getServiceObjectName();
        } else {
            return getCamelCaseString(t.getTableName(), true);
        }
    }
    public String getServiceImplObjectName(TableConfiguration t) {
        if (stringHasValue(t.getServiceImplObjectName())) {
            return t.getServiceImplObjectName();
        } else {
            return getCamelCaseString(t.getTableName(), true);
        }
    }
    public String getControllerObjectName(TableConfiguration t) {
        if (stringHasValue(t.getControllerObjectName())) {
            return t.getControllerObjectName();
        } else {
            return getCamelCaseString(t.getTableName(), true);
        }
    }
    public String getPageObjectName(TableConfiguration t) {
        if (stringHasValue(t.getPageObjectName())) {
            return t.getPageObjectName();
        } else {
            return getCamelCaseString(t.getTableName(), true);
        }
    }

}
