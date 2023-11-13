package cn.nansker.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Nansker
 * @date 2023/11/7 16:31
 * @description 权限路由数据
 */
@Data
@ApiModel(value = "RouterVo", description = "权限路由")
public class RouterVo {
	@ApiModelProperty("路由地址")
	private String path;

	/**
	 * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
	 */
	@ApiModelProperty("是否隐藏")
	private boolean hidden;

	@ApiModelProperty("组件地址")
	private String component;

	/**
	 * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
	 */
	@ApiModelProperty("嵌套模式")
	private Boolean alwaysShow;

	@ApiModelProperty("其他元素")
	private MetaVo meta;

	@ApiModelProperty("子路由")
	private List<RouterVo> children;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MetaVo {
		/**
		 * 设置该路由在侧边栏和面包屑中展示的名字
		 */
		@ApiModelProperty("标题")
		private String title;

		/**
		 * 设置该路由的图标，对应路径src/assets/icons/svg
		 */
		@ApiModelProperty("图标")
		private String icon;
	}

}
