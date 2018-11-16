package com.example.cidaasv2.Models.DBModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserInfoModelTest {
    @Mock
    UserInfoModel sharedinstance;
    @InjectMocks
    UserInfoModel userInfoModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        UserInfoModel result = UserInfoModel.getShared();
        Assert.assertTrue(result instanceof UserInfoModel);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme