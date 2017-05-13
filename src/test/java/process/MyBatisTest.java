package process;

import junit.framework.TestCase;

public class MyBatisTest extends TestCase {
	/*private AnnexTypeService annexTypeService;

	public void before() {
		// 使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
		// ApplicationContext ac = new ClassPathXmlApplicationContext(new
		// String[]{"spring.xml","spring-mybatis.xml"});
		// //从Spring容器中根据bean的id取出我们要使用的userService对象
		// annexTypeService = (AnnexTypeService) ac.getBean("userService");
	}

	@Test
	public void testAdd_annexType() {

		annexTypeService = (AnnexTypeService) SpringFactory
				.getBean("annexTypeServiceImpl");

		AnnexType type = new AnnexType();
		type.setTypeId(2000L);
		type.setTypeName("typeName2");
		type.setDescription("description2");
		annexTypeService.save(type);

	}

	@Test
	public void testUpdate_annexType() {

		annexTypeService = (AnnexTypeService) SpringFactory
				.getBean("annexTypeServiceImpl");

		AnnexType type = new AnnexType();
		type.setTypeId(1000L);
		type.setTypeName("typeName2");
		type.setDescription("description2");
		annexTypeService.update(type);

	}

	@Test
	public void testselectByPrimaryKey_annexType() {

		annexTypeService = (AnnexTypeService) SpringFactory
				.getBean("annexTypeServiceImpl");

		AnnexType selectByPrimaryKey = annexTypeService
				.selectByPrimaryKey(1000L);
		System.out.println(selectByPrimaryKey);

	}

	@Test
	public void testselectAll_annexType() {
		annexTypeService = (AnnexTypeService) SpringFactory
				.getBean("annexTypeServiceImpl");

		List<AnnexType> selectAll = annexTypeService.selectAll();

		for (AnnexType annexType : selectAll) {
			System.out.println(annexType);
		}
	}
	
	@Test
	public void testdelete_annexType() {
		annexTypeService = (AnnexTypeService) SpringFactory
				.getBean("annexTypeServiceImpl");

		annexTypeService.delete(2000L);
	}*/
}
