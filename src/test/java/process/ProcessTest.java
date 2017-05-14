package process;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xinwei.util.spring.SpringFactory;

public class ProcessTest {

	private static RepositoryService repositoryService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		repositoryService =(RepositoryService)SpringFactory.getBean("repositoryService");
	}

	@Test
	public void testRepositoryService(){
		//repositoryService.deleteDeployment("215001", true);
		List<Deployment> deployments =  repositoryService.createDeploymentQuery().list();
		for(Deployment deployment: deployments){
			repositoryService.deleteDeployment(deployment.getId(), true);
		}
	}
	public static void main(String[] args) {
		RepositoryService  repositoryService =(RepositoryService)SpringFactory.getBean("repositoryService");
		List<Deployment> deployments =  repositoryService.createDeploymentQuery().list();
		for(Deployment deployment: deployments){
			repositoryService.deleteDeployment(deployment.getId(), true);
		}
	}

}
