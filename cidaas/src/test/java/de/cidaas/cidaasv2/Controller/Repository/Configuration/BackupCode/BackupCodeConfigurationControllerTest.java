package de.cidaas.cidaasv2.Controller.Repository.Configuration.BackupCode;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class BackupCodeConfigurationControllerTest {

    Context context;

    BackupCodeConfigurationController shared;

    BackupCodeConfigurationController backupCodeConfigurationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        backupCodeConfigurationController = new BackupCodeConfigurationController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        BackupCodeConfigurationController result = BackupCodeConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof BackupCodeConfigurationController);
    }

    @Test
    public void testConfigureBackupCode() throws Exception {
        backupCodeConfigurationController.configureBackupCode("sub", null);
    }

    @Test
    public void testLoginWithBackupCode() throws Exception {
        backupCodeConfigurationController.loginWithBackupCode("code", null, null);
    }
}