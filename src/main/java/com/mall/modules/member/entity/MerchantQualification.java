package com.mall.modules.member.entity;

import com.mall.common.config.Global;
import com.mall.common.utils.StringUtils;

/**
 * 商户资质信息
 *
 * @author wankang
 * @date 2018-12-19
 */
public class MerchantQualification {
    private String companyName;
    private String businessLicenseImage;
    private String productLicense; // 营业执照注册号
    private String specialQualification;

    private MerchantQualification() {}
    public MerchantQualification(MemberInfo memberInfo) {
        this.companyName = memberInfo.getCompanyName();
        this.businessLicenseImage = memberInfo.getBusinessLicenseImage();
        this.productLicense = memberInfo.getProductLicense();
        this.specialQualification = memberInfo.getSpecialQualification();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage;
    }

    public String getProductLicense() {
        return productLicense;
    }

    public void setProductLicense(String productLicense) {
        this.productLicense = productLicense;
    }

    public String getSpecialQualification() {
        return specialQualification;
    }

    public void setSpecialQualification(String specialQualification) {
        this.specialQualification = specialQualification;
    }

    public String getBusinessLicenseImageFullUrl() {
        String businessLicenseImageFullUrl = "";
        if(StringUtils.isNotBlank(this.businessLicenseImage)) {
            businessLicenseImageFullUrl = Global.getConfig("userfiles.baseURL") + this.businessLicenseImage;
        }
        return businessLicenseImageFullUrl;
    }

    public String getSpecialQualificationFullUrl() {
        String specialQualificationFullUrl = "";
        if(StringUtils.isNotBlank(this.specialQualification)) {
            specialQualificationFullUrl = Global.getConfig("userfiles.baseURL") + this.specialQualification;
        }
        return specialQualificationFullUrl;
    }
}
