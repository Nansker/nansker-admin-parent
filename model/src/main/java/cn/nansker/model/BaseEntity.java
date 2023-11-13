package cn.nansker.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nansker
 * @date 2023/11/5 20:41
 * @description 实体基类
 */
@ApiModel(value="BaseEntity对象", description="实体基类")
@Data
public class BaseEntity implements Serializable {
	@ApiModelProperty("当前页")
	@TableField(exist = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer pageNum;

	@ApiModelProperty("分页大小")
	@TableField(exist = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer pageSize;
}
