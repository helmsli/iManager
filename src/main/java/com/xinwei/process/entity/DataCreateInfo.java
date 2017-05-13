package com.xinwei.process.entity;

import java.util.Date;
/**
 * 业务数据创建信息
 *
 */
public class DataCreateInfo extends DataInfo{
	
    private Long creatorId;//创建者ID

    private Date createTime;//创建时间

    private String extData1;//扩展信息

    private String extData2;

    private String extData3;

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExtData1() {
        return extData1;
    }

    public void setExtData1(String extData1) {
        this.extData1 = extData1;
    }

    public String getExtData2() {
        return extData2;
    }

    public void setExtData2(String extData2) {
        this.extData2 = extData2;
    }

    public String getExtData3() {
        return extData3;
    }

    public void setExtData3(String extData3) {
        this.extData3 = extData3;
    }
}