1��ע�⣺

	pom�ļ�����generator����ǲ�Ҫ����tk.mapper�������Ӱ�졣

	����ֶ�������������ͬ��Ĭ�ϲ�����@Columnע�⣬�����Ҫ���ɣ�������ǿ������ע�⣨<property name="forceAnnotation" value="true"/>���������commentGenerator����ǩ�͡�MapperPlugin�����ͬʱ���ڣ�ǿ������ע��д�ڡ�MapperPlugin���

	�����Ҫ������������table��ǩ�м���<generatedKey column="�����ֶ�" sqlStatement="JDBC">


2�������

	�����EqualsHashCodePlugin	���ɷ�����equals��hashCode

	�����ToStringPlugin		���ɷ�����toString

	�����LombokPlugin		ȡ������setter��getter����������lombok���ע��

	�����SerializablePlugin	�������к�


3������
	��ǩ��javaControllerGenerator
	���ԣ�property[listViewModelPackage]	���壺listViewModelPackage��������
	���ԣ�property[resultViewModelPackage]	���壺resultViewModelPackage��������
	���ԣ�property[searchTypeEnumPackage]	���壺searchTypeEnumPackage��������
	
	
	��ǩ��javaQueryModelGenerator
	���ԣ�property[queryRootClass]		���壺�̳е��������������Ǳ��룬�������ã��޼̳С�
		
	
	��ǩ��javaSerivceGenerator
	���ԣ�property[rootClass]		���壺�̳е��������������Ǳ��룬�������ã�Ĭ�ϼ̳У�com.my.base.BaseService��
	
	
	��ǩ��javaSerivceImplGenerator
	���ԣ�property[rootClass]		���壺�̳е��������������Ǳ��룬�������ã�Ĭ�ϼ̳У�com.my.base.BaseServiceImpl��
	���ԣ�property[searchTypeEnumPackage]	���壺searchTypeEnumPackage��������


	��ǩ��table
	�ӱ�ǩ��treeStructure ?	���壺���ṹ
		���ԣ�selfCdColumn	���壺����cd�����ֶ���
		���ԣ�superCdColumn	���壺�ϼ�cd�����ֶ���
		���ԣ�isParentColumn	���壺�Ƿ��Ǹ��ڵ��ֶ���
		���ԣ�treeIndexColumn	���壺�������ֶ���

	�ӱ�ǩ��checkUnique *	���壺Ψһ����
		���ԣ�uniqueColumnAry	���壺�����Ψһ���ֶ��������ֶ���ʱ�Կո����

	�ӱ�ǩ��deletedStateColumn *	���壺״̬ɾ����Ӱ����ֶ�
		���ԣ�column	���壺״̬�ֶ���
		���ԣ�value	���壺ɾ�����ֶ�ֵ��Ϊ��ǰvalue

	�ӱ�ǩ��deleteAffectedTable *	���壺ɾ��ʱ��Ӱ���������
		���ԣ�affectedTable	���壺��Ӱ��ı�
		���ԣ�sourceColumnAry	���壺
		���ԣ�affectedColumnAry	���壺��Ӱ����ֶ���


ps

��������������+����
* ����ñ�ǩ��ʹ�ö��
? ����ñ�ǩֻ��ʹ��һ��