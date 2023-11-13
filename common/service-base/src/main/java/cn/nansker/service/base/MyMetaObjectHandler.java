package cn.nansker.service.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Nansker
 * @date 2023/9/18 23:55
 * @description 自定义源数据对象处理器，主要实现公共字段自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		//创建与更新时间
		metaObject.setValue("createTime", new Date());
		metaObject.setValue("updateTime", new Date());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		metaObject.setValue("updateTime",new Date());
	}

}
