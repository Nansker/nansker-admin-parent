package cn.nansker.service.auth.util;

import cn.nansker.model.auth.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nansker
 * @date 2023/11/5 20:52
 * @description 构建菜单工具类
 */
public class MenuHelper {

	/**
	 * @param menuList 菜单数据
	 * @return java.util.List<cn.nansker.model.auth.SysMenu>
	 * @author Nansker
	 * @date 2023/11/5 20:55
	 * @description 构建菜单树形结构
	*/
	public static List<SysMenu> buildTree(List<SysMenu> menuList){
	//查找根节点
		List<SysMenu> trees = new ArrayList<>();
		for(SysMenu menu : menuList){
			if (menu.getParentId().longValue() == 0){
				trees.add(findTreeNode(menu,menuList));
			}
		}
		return trees;
	}

	/**
	 * @param menu
	 * @param menuList
	 * @return void
	 * @author Nansker
	 * @date 2023/11/5 20:59
	 * @description 查找菜单子节点
	*/
	private static SysMenu findTreeNode(SysMenu menu, List<SysMenu> menuList) {
		//初始化子节点
		menu.setChildren(new ArrayList<SysMenu>());
		for (SysMenu item : menuList){
			if (item.getParentId().longValue() == menu.getId().longValue() ){
				menu.getChildren().add(findTreeNode(item,menuList));
			}
		}
		return menu;
	}

}
