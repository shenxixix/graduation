package com.yolo.hw.opt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("列表分页信息")
public class ReqPageDto {
    @ApiModelProperty(notes = "页码")
    @NotNull(message = "页码不能为空")
    private Integer current = 1;
    @ApiModelProperty(notes = "每页条数")
    private Integer size = 10;
    @ApiModelProperty(notes = "总条数")
    private Integer totalSize = 0;
    @ApiModelProperty(notes = "0:翻页，1：不翻页，获取全部数据")
    private Integer allRecords = 0;

}
