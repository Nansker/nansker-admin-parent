package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysRoleMapper;
import cn.nansker.service.auth.service.SysRoleService;
import cn.nansker.model.auth.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Nansker
 * @description 针对表【sys_role(角色)】的数据库操作Service实现
 * @createDate 2023-10-24 22:13:05
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	@Override
	public Page listPage(SysRole role) {
		Page<SysRole> pageParam = new Page<>(role.getPageNum(), role.getPageSize());
		LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(StringUtils.isNotEmpty(role.getRoleName()), SysRole::getRoleName, role.getRoleName());
		queryWrapper.orderByAsc(SysRole::getId);
		Page<SysRole> page = page(pageParam, queryWrapper);
		return page;
	}

}




