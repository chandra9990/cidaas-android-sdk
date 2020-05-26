package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSMSResponseDataEntityTest {
    AuthenticateSMSResponseDataEntity authenticateSMSResponseDataEntity = new AuthenticateSMSResponseDataEntity();

    @Test
    public void getSub() {
        authenticateSMSResponseDataEntity.setSub("test");
        assertTrue(authenticateSMSResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        authenticateSMSResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateSMSResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        authenticateSMSResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateSMSResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        authenticateSMSResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateSMSResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme