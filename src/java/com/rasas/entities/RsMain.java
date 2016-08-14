/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "RS_MAIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RsMain.findAll", query = "SELECT r FROM RsMain r"),
    @NamedQuery(name = "RsMain.findByRsNo", query = "SELECT r FROM RsMain r WHERE r.rsNo = :rsNo"),
    @NamedQuery(name = "RsMain.findByRsYear", query = "SELECT r FROM RsMain r WHERE r.rsYear = :rsYear"),
    @NamedQuery(name = "RsMain.findByRsCenter", query = "SELECT r FROM RsMain r WHERE r.rsCenter = :rsCenter"),
    @NamedQuery(name = "RsMain.findByRsSubCenter", query = "SELECT r FROM RsMain r WHERE r.rsSubCenter = :rsSubCenter"),
    @NamedQuery(name = "RsMain.findByEntryDate", query = "SELECT r FROM RsMain r WHERE r.entryDate = :entryDate"),
    @NamedQuery(name = "RsMain.findByUserId", query = "SELECT r FROM RsMain r WHERE r.userId = :userId")})
public class RsMain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RS_NO")
    private String rsNo;
    @Size(max = 4)
    @Column(name = "RS_YEAR")
    private String rsYear;
    @Size(max = 3)
    @Column(name = "RS_CENTER")
    private String rsCenter;
    @Size(max = 3)
    @Column(name = "RS_SUB_CENTER")
    private String rsSubCenter;
    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;
    @Size(max = 10)
    @Column(name = "USER_ID")
    private String userId;

    public RsMain() {
    }

    public RsMain(String rsNo) {
        this.rsNo = rsNo;
    }

    public String getRsNo() {
        return rsNo;
    }

    public void setRsNo(String rsNo) {
        this.rsNo = rsNo;
    }

    public String getRsYear() {
        return rsYear;
    }

    public void setRsYear(String rsYear) {
        this.rsYear = rsYear;
    }

    public String getRsCenter() {
        return rsCenter;
    }

    public void setRsCenter(String rsCenter) {
        this.rsCenter = rsCenter;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rsNo != null ? rsNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsMain)) {
            return false;
        }
        RsMain other = (RsMain) object;
        if ((this.rsNo == null && other.rsNo != null) || (this.rsNo != null && !this.rsNo.equals(other.rsNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.RsMain[ rsNo=" + rsNo + " ]";
    }
    
}
