package cn.nansker.service.auth.util;

import cn.nansker.model.auth.SysPermission;
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

	public static List<RouterVo> buildRouters(List<SysPermission> permissionList) {
		LinkedList<RouterVo> routerList = new LinkedList<>();
		for (SysPermission item : permissionList) {
			RouterVo router = new RouterVo();
			router.setHidden(false);
			router.setAlwaysShow(false);
			router.setPath(getRouterPath(item));
			router.setComponent(item.getComponent());
			router.setMeta(new RouterVo.MetaVo(item.getName(), item.getIcon()));
			List<SysPermission> children = item.getChildren();

			//如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
			if (item.getType().intValue() == 1) {
				List<SysPermission> hiddenPermissionList = Optional.ofNullable(children)
						.map(c -> c.stream()
								.filter(i -> StringUtils.isNotEmpty(i.getComponent()))
								.collect(Collectors.toList()))
						.orElse(Collections.emptyList());
				if (hiddenPermissionList != null && hiddenPermissionList.size() > 0) {
					for (SysPermission hiddenPermission : hiddenPermissionList) {
						RouterVo hiddenRouter = new RouterVo();
						hiddenRouter.setHidden(true);
						hiddenRouter.setAlwaysShow(false);
						hiddenRouter.setPath(getRouterPath(hiddenPermission));
						hiddenRouter.setComponent(hiddenPermission.getComponent());
						hiddenRouter.setMeta(new RouterVo.MetaVo(hiddenPermission.getName(), hiddenPermission.getIcon()));
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
	 * @param permission 菜单数据
	 * @return java.lang.String
	 * @author Nansker
	 * @date 2023/11/7 17:31
	 * @description 获取路由地址
	 */
	public static String getRouterPath(SysPermission permission) {
		String path = "/" + permission.getPath();
		if (permission.getParentId().intValue() != 0) {
			path = permission.getPath();
		}
		return path;
	}

}
