package cn.nansker.service.auth.util;

import cn.nansker.model.auth.SysPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @date 2023/11/5 20:52
 * @description 构建菜单工具类
 */
public class MenuHelper {

	/**
	 * @param permissionList 菜单数据
	 * @return java.util.List<cn.nansker.model.auth.SysPermission>
	 * @author Nansker
	 * @date 2023/11/5 20:55
	 * @description 构建菜单树形结构
	*/
	public static List<SysPermission> buildTree(List<SysPermission> permissionList){
	//查找根节点
		List<SysPermission> trees = new ArrayList<>();
		for(SysPermission permission : permissionList){
			if (permission.getParentId().longValue() == 0){
				trees.add(findTreeNode(permission,permissionList));
			}
		}
		return trees;
	}

	/**
	 * @param permission
	 * @param permissionList
	 * @return void
	 * @author Nansker
	 * @date 2023/11/5 20:59
	 * @description 查找菜单子节点
	*/
	private static SysPermission findTreeNode(SysPermission permission, List<SysPermission> permissionList) {
		//初始化子节点
		permission.setChildren(new ArrayList<SysPermission>());
		for (SysPermission item : permissionList){
			if (item.getParentId().longValue() == permission.getId().longValue() ){
				permission.getChildren().add(findTreeNode(item,permissionList));
			}
		}
		return permission;
	}

}
