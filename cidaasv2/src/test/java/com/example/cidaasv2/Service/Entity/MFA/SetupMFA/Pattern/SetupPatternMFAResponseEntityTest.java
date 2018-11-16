package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SetupPatternMFAResponseEntityTest {
    @Mock
    SetupPatternMFAResponseDataEntity data;
    @InjectMocks
    SetupPatternMFAResponseEntity setupPatternMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setSuccess(){
        setupPatternMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupPatternMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        setupPatternMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,setupPatternMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new SetupPatternMFAResponseDataEntity();

        data.setStatusId("Test");
        setupPatternMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",setupPatternMFAResponseEntity.getData().getStatusId());

    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme