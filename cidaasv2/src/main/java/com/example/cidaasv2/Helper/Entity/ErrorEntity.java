package com.example.cidaasv2.Helper.Entity;

import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.FaceMetaData;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.VoiceMetaData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorEntity implements Serializable
{

    private int code;
    private String moreInfo;
    private String type;
    private int status;
    private String referenceNumber;
    private  String error;
    private FaceMetaData metadata;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public FaceMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(FaceMetaData metadata) {
        this.metadata = metadata;
    }
}
