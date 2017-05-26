
package com.xinwei.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.security.MessageCode;
import com.xinwei.security.dao.DepartmentDao;
import com.xinwei.security.dao.UserDao;
import com.xinwei.security.entity.Department;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.DepartmentService;
import com.xinwei.util.page.Page;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private UserDao userDao;

	public void save(Department department) throws ExistedException {
		// 验证部门的名称唯一
		validateParameter(department);
		departmentDao.save(department);
	}

	private void validateParameter(Department department) {
		if (departmentDao.findByName(department) != null) {
			throw new ExistedException(MessageCode.ORGNAME_EXISTS);
		}
	}

	public void update(Department department) {
		// 验证id是否为空
		if (null == department.getId() || 0 == department.getId()) {
			throw new ServiceException(MessageCode.ID_NULL);
		}
		// 验证部门的名称唯一
		validateParameter(department);
		departmentDao.update(department);
	}

	public void delete(Long id) throws ServiceException {
		//验证是否子级部门
		Long childCount = departmentDao.findCountByParentId(id);
		if(childCount > 0){
			throw new ServiceException(MessageCode.DEPARTMENT_CAN_NOT_DELETE_CHILD_EXISTS);
		}
		
		//验证部门下是否有用户
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("department_id", id);
		Long userCount = userDao.findByPageCount(map );
		if (userCount > 0) {
			throw new ServiceException(MessageCode.DEPARTMENT_CAN_NOT_DELETE_USER_EXISTS);
		}
		
		departmentDao.delete(id);
	}

	@Override
	public Department get(Long id) {
		return departmentDao.get(id);
	}

	@Override
	public List<Department> getAllDepartmentTree() {
		List<Department> departments = departmentDao.findAll();
		List<Department> temp = new ArrayList<Department>(departments) ;
		List<Department> result = makeTree(temp);
		return result;
	}
	
	
	private List<Department> makeTree(List<Department> list) {
		List<Department> top = new ArrayList<Department>();
		//获取第一级菜单
		for (Department department : list) {
			if (department.getParent_id() == null) {
				department.setChildren(new ArrayList<Department>(0));
				top.add(department);
			}
		}
		// 删除parentId = null;
		boolean isRemove = list.removeAll(top);
		
		//往一级菜单中 添加子菜单
		makeChildren(top, list);
		
		return top;
	}
	
	//循环  往上级部门中 添加子部门
	private void makeChildren(List<Department> tops, List<Department> departments) {
		if (departments.isEmpty()) {
			return ;
		}
		
		//保存parent中的下一级节点，用于后面删除
		List<Department> nextTops = new ArrayList<Department>();
		for (Department top : tops) 
		{
			for (Department department : departments) 
			{
				department.setChildren(new ArrayList<Department>(0));
				if (top.getId().equals(department.getParent_id())) 
				{
					top.getChildren().add(department);
					nextTops.add(department);
				}
			}
		}
		
		departments.removeAll(nextTops);
		makeChildren(nextTops, departments);
	}
	
	

	@Override
	public Page<Department> findByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
