package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.Email;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Email.SetupEmailResponseDataEntity;

public class SetupEmailResponseDataEntityTest {

    SetupEmailResponseDataEntity setupEmailResponseDataEntity = new SetupEmailResponseDataEntity();

    @Test
    public void setStatusId() throws Exception {

        setupEmailResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId", setupEmailResponseDataEntity.getStatusId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme