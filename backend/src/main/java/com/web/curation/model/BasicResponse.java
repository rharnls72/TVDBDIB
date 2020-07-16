package com.web.curation.model;

import io.swagger.annotations.ApiModelProperty;


public class BasicResponse {
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "msg", position = 2)
    public String msg;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
    @ApiModelProperty(value = "data", position = 4)
    public Object data;
}
