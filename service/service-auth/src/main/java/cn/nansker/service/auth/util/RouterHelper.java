package cn.nansker.service.auth.util;

import cn.nansker.model.auth.SysMenu;
import cn.nansker.model.vo.RouterVo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Nansker
 * @date 2023/11/7 17:26
 * @description 权限路由工具类
 */
public class RouterHelper {

	public static List<RouterVo> buildRouters(List<SysMenu> permissionList) {
		LinkedList<RouterVo> routerList = new LinkedList<>();
		for (SysMenu menu : permissionList) {
			RouterVo router = new RouterVo();
			router.setHidden(false);
			router.setAlwaysShow(false);
			router.setPath(getRouterPath(menu));
			router.setComponent(menu.getComponent());
			router.setMeta(new RouterVo.MetaVo(menu.getName(), menu.getIcon()));
			List<SysMenu> children = menu.getChildren();

			//如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
			if (menu.getType().intValue() == 1) {
				List<SysMenu> hiddenMenuList = Optional.ofNullable(children)
						.map(c -> c.stream()
								.filter(item -> StringUtils.isNotEmpty(item.getComponent()))
								.collect(Collectors.toList()))
						.orElse(Collections.emptyList());
				if (hiddenMenuList != null && hiddenMenuList.size() > 0) {
					for (SysMenu hiddenMenu : hiddenMenuList) {
						RouterVo hiddenRouter = new RouterVo();
						hiddenRouter.setHidden(true);
						hiddenRouter.setAlwaysShow(false);
						hiddenRouter.setPath(getRouterPath(hiddenMenu));
						hiddenRouter.setComponent(hiddenMenu.getComponent());
						hiddenRouter.setMeta(new RouterVo.MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
						routerList.add(hiddenRouter);
					}
				}
			} else {
				if (!CollectionUtils.isEmpty(children)) {
					if (children.size() > 0) {
						router.setAlwaysShow(true);
					}
					router.setChildren(buildRouters(children));
				}
			}
			routerList.add(router);
		}
		return routerList;
	}

	/**
	 * @param menu 菜单数据
	 * @return java.lang.String
	 * @author Nansker
	 * @date 2023/11/7 17:31
	 * @description 获取路由地址
	 */
	public static String getRouterPath(SysMenu menu) {
		String path = "/" + menu.getPath();
		if (menu.getParentId().intValue() != 0) {
			path = menu.getPath();
		}
		return path;
	}

}
