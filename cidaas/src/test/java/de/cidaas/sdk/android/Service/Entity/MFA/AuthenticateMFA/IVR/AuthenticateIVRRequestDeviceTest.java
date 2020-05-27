package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.IVR;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.IVR.AuthenticateIVRRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateIVRRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateIVRRequestDevice authenticateIVRRequestDevice = new AuthenticateIVRRequestDevice();
        authenticateIVRRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateIVRRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme