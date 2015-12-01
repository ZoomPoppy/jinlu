package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zz on 15/11/28.
 */
@Entity
@Table(name = "crm_commitmentbook")
public class CommitBook extends IdEntity{
    @Column
    private Long customerId;
    @Column
    private String commitBookName;
    @Column
    private Customer.CertificateType certificateType;
    @Column
    private String certificateNumber;
    @Column
    private String  address;
    @Column
    private String contactPhone;
    @Column
    private boolean deleteOrNot;
    @Column
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDeleteOrNot() {
        return deleteOrNot;
    }

    public void setDeleteOrNot(boolean deleteOrNot) {
        this.deleteOrNot = deleteOrNot;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCommitBookName() {
        return commitBookName;
    }

    public void setCommitBookName(String commitBookName) {
        this.commitBookName = commitBookName;
    }

    public Customer.CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Customer.CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
