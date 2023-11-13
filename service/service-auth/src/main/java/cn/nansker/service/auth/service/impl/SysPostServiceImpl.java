package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysPostMapper;
import cn.nansker.service.auth.service.SysPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.nansker.model.auth.SysPost;
import org.springframework.stereotype.Service;

/**
* @author Nansker
* @description 针对表【sys_post(岗位信息表)】的数据库操作Service实现
* @createDate 2023-10-24 22:13:05
*/
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost>
    implements SysPostService {

}




