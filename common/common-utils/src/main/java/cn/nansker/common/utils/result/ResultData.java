package cn.nansker.common.utils.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nansker
 * @date 2023/8/8 4:57
 * @description 统一数据响应类
 */
@ApiModel("统一数据响应类")
@Data
public class ResultData<T> implements Serializable {
	@ApiModelProperty("响应码")
	private Integer code;
	@ApiModelProperty("响应信息")
	private String message;
	@ApiModelProperty("响应数据")
	private T data;

	private ResultData() {}

	/**
	 * @author Nansker
	 * @date 2023/9/18 0:42
	 * @return com.nansker.utils.result.ResultData
	 * @description 成功响应
	*/
	public static ResultData ok() {
		ResultData resultData = new ResultData();
		resultData.setCode(StatusCode.SUCCESS.getCode());
		resultData.setMessage(StatusCode.SUCCESS.getMessage());
		return resultData;
	}
	/**
	 * @author Nansker
	 * @date 2023/9/18 0:42
	 * @return com.nansker.utils.result.ResultData
	 * @description 失败响应
	*/
	public static ResultData error() {
		ResultData resultData = new ResultData();
		resultData.setCode(StatusCode.ERROR.getCode());
		resultData.setMessage(StatusCode.ERROR.getMessage());
		return resultData;
	}

	public ResultData code(Integer code) {
		this.setCode(code);
		return this;
	}

	public ResultData message(String message) {
		this.setMessage(message);
		return this;
	}

	public ResultData data(T data) {
		this.setData(data);
		return this;
	}
	public ResultData data(StatusCode resultCode) {
		this.setCode(resultCode.getCode());
		this.setMessage(resultCode.getMessage());
		return this;
	}
	/**
	 * @author Nansker
	 * @date 2023/9/18 0:43
	 * @param page
	 * @return com.nansker.utils.result.ResultData
	 * @description mybatis-plus 分页数据
	*/
    public ResultData pageData(Page page) {
        Map<String, Object> map = new HashMap<>();
        map.put("rows", page.getRecords());
        map.put("total", page.getTotal());
        map.put("size", page.getSize());
        map.put("current", page.getCurrent());
        this.setData((T) map);
        return this;
    }
}
