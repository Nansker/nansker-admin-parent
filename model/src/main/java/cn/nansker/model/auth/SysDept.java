package cn.nansker.model.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author Nnasker
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
@ApiModel(value="SysDept对象", description="组织机构")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门id")
    private Long parentId;

    @ApiModelProperty(value = "树结构")
    private String treePath;

    @ApiModelProperty(value = "排序")
    private Integer sortValue;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态（1正常 0停用）")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记（0:可用 1:已删除）")
    private Integer isDeleted;

}
