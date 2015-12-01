package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;

/**
 * Created by zz on 15/11/27.
 */
@Entity
@Table(name = "crm_additional_info")
public class AdditionalInfo extends IdEntity{
    @Column
    private Long customerId;
    @Column
    private String isMarry;
    //工作单位
    @Column
    private String workUnit;
    @Column
    private String job;
    @Column
    private Long zipCode;
    //固定电话
    @Column
    private String  theSupplementaryPhone;
    @Column
    private String email;
    //客户来源 // TODO: 15/11/27 不一定用String
    @Column
    private String customerFrom;
    //直属主管
    @Column
    private String supervisor;

    @Column
    private String addressInfo;
    @Column
    protected String phoneNum;
    //客服姓名 不会更改
    @Column
    private String agentName;
    @Column
    private boolean deleteOrNot;

    public boolean isDeleteOrNot() {
        return deleteOrNot;
    }

    public void setDeleteOrNot(boolean deleteOrNot) {
        this.deleteOrNot = deleteOrNot;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIsMarry() {
        return isMarry;
    }

    public void setIsMarry(String isMarry) {
        this.isMarry = isMarry;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String wordUnit) {
        this.workUnit = wordUnit;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getTheSupplementaryPhone() {
        return theSupplementaryPhone;
    }

    public void setTheSupplementaryPhone(String theSupplementaryPhone) {
        this.theSupplementaryPhone = theSupplementaryPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerFrom() {
        return customerFrom;
    }

    public void setCustomerFrom(String customerFrom) {
        this.customerFrom = customerFrom;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
