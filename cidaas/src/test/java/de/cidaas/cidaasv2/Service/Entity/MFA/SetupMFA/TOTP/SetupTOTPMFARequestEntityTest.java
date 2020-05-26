package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class SetupTOTPMFARequestEntityTest {

    SetupTOTPMFARequestEntity setupTOTPRequestEntity;

    @Before
    public void setUp() {
        setupTOTPRequestEntity = new SetupTOTPMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupTOTPRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupTOTPRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupTOTPRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupTOTPRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupTOTPRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupTOTPRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupTOTPRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupTOTPRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupTOTPRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupTOTPRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme