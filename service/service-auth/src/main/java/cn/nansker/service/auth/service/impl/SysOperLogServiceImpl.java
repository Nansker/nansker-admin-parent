package cn.nansker.service.auth.service.impl;

import cn.nansker.service.auth.mapper.SysOperLogMapper;
import cn.nansker.service.auth.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.nansker.model.auth.SysOperLog;
import org.springframework.stereotype.Service;

/**
* @author Nansker
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
* @createDate 2023-10-24 22:13:05
*/
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
    implements SysOperLogService {

}




