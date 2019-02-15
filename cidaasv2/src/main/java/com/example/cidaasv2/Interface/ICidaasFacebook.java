package com.example.cidaasv2.Interface;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

public interface ICidaasFacebook {

    public void login(Result<AccessTokenEntity> accessTokenEntityResult);
    public void logout();
}
