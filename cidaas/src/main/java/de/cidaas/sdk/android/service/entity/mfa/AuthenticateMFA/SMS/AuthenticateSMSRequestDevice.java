package de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.SMS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateSMSRequestDevice implements Serializable {
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}