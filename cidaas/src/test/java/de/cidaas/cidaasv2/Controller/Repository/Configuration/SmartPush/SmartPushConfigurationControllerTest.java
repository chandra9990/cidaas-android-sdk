package de.cidaas.cidaasv2.Controller.Repository.Configuration.SmartPush;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.DBHelper;
import de.cidaas.sdk.android.cidaas.Models.DBModel.AccessTokenModel;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class SmartPushConfigurationControllerTest {
    Context context;
    SmartPushConfigurationController shared;

    SmartPushConfigurationController smartPushConfigurationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;

        DBHelper.setConfig(context);

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(false);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setSeconds(System.currentTimeMillis() + 1000000);

        DBHelper.getShared().setAccessToken(accessTokenModel);

        smartPushConfigurationController = new SmartPushConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        smartPushConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        SmartPushConfigurationController result = SmartPushConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof SmartPushConfigurationController);
    }

    @Test
    public void testConfigureSmartPush() throws Exception {

        SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity = new SetupSmartPushMFARequestEntity();
        setupSmartPushMFARequestEntity.setClient_id("ClientId");
        setupSmartPushMFARequestEntity.setLogoUrl("Logourl");
        setupSmartPushMFARequestEntity.setDeviceInfo(new DeviceInfoEntity());
        smartPushConfigurationController.configureSmartPush("userId", "baseurl", setupSmartPushMFARequestEntity, new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginWithSmartPush() throws Exception {
        smartPushConfigurationController.LoginWithSmartPush("baseurl", "clientId", null, null, null);
    }


    @Test
    public void testLoginWithSmart() throws Exception {
        smartPushConfigurationController.LoginWithSmartPush("baseurl", "clientId", null, null, null);
    }

    @Test
    public void testLoginWithSmartPushnull() throws Exception {
        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setSeconds(System.currentTimeMillis() + 1000000);

        smartPushConfigurationController.LoginWithSmartPush("baseurl", "clientId", null, null, null);
    }
}