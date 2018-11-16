package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupTOTPMFARequestEntityTest {
    
    SetupTOTPMFARequestEntity setupTOTPRequestEntity;

    @Before
    public void setUp() {
        setupTOTPRequestEntity=new SetupTOTPMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupTOTPRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID",setupTOTPRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupTOTPRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL",setupTOTPRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity()
    {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
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