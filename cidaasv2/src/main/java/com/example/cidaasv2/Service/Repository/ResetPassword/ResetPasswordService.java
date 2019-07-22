package com.example.cidaasv2.Service.Repository.ResetPassword;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ResetPasswordService {
    //Reset Password
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static  ResetPasswordService shared;

    public  ResetPasswordService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType
        if(service==null) {
            service=new CidaassdkService(context);
        }

    }


    public static  ResetPasswordService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ResetPasswordService(contextFromCidaas);
            }

        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void initiateresetPassword(ResetPasswordRequestEntity resetPasswordRequestEntity, String baseurl,DeviceInfoEntity deviceInfoEntityFromParam,
                                      final Result<ResetPasswordResponseEntity> callback)
    {
        //Local Variables
        String resetpasswordUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                resetpasswordUrl=baseurl+ URLHelper.getShared().getInitiateResetPassword();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :ResetPasswordService :initiateresetPassword()"));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromParam;
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.initiateresetPassword(resetpasswordUrl,headers,resetPasswordRequestEntity).enqueue(new Callback<ResetPasswordResponseEntity>() {
                @Override
                public void onResponse(Call<ResetPasswordResponseEntity> call, Response<ResetPasswordResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,
                                    "Service failure but successful response" , "Error :ResetPasswordService :initiateresetPassword()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,
                                response,"Error :ResetPasswordService :initiateresetPassword()"));
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,
                            t.getMessage(), "Error :ResetPasswordService :initiateresetPassword()"));

                }
            });

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :ResetPasswordService :initiateresetPassword()",
                    WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,e.getMessage()));
        }
    }


    //Reset Password Validate Code
    public void resetPasswordValidateCode(ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity,
                                          String baseurl, final Result<ResetPasswordValidateCodeResponseEntity> callback)
    {
        //Local Variables
        String resetpasswordValidateCodeUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                resetpasswordValidateCodeUrl=baseurl+URLHelper.getShared().getResetPasswordValidateCode();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Exception :ResetPasswordService :resetNewPassword()"));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            serviceForResetPasswordValidateCode(resetpasswordValidateCodeUrl, resetPasswordValidateCodeRequestEntity, headers, callback);

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :ResetPasswordService :resetPasswordValidateCode()",
                    WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,e.getMessage()));
        }
    }

    public void serviceForResetPasswordValidateCode(String resetpasswordValidateCodeUrl, ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity, Map<String, String> headers, final Result<ResetPasswordValidateCodeResponseEntity> callback) {
        //Call Service-getRequestId
        ICidaasSDKService cidaasSDKService = service.getInstance();
        cidaasSDKService.resetPasswordValidateCode(resetpasswordValidateCodeUrl,headers,resetPasswordValidateCodeRequestEntity)
                .enqueue(new Callback<ResetPasswordValidateCodeResponseEntity>() {
                    @Override
                    public void onResponse(Call<ResetPasswordValidateCodeResponseEntity> call, Response<ResetPasswordValidateCodeResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,
                                        "Service failure but successful response" , "Exception :ResetPasswordService :resetNewPassword()"));
                            }
                        }
                        else {
                            assert response.errorBody() != null;
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,
                                    response,"Exception :ResetPasswordService :resetNewPassword()"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetPasswordValidateCodeResponseEntity> call, Throwable t) {
                        callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,
                                t.getMessage(), "Exception :ResetPasswordService :resetNewPassword()"));

                    }
                });
    }


    //Reset Password Validate Code
    public void resetNewPassword(ResetPasswordEntity resetPasswordEntity,
                                 String baseurl, final Result<ResetNewPasswordResponseEntity> callback)
    {
        //Local Variables
        String ResetNewPasswordUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For Change Password
                ResetNewPasswordUrl=baseurl+URLHelper.getShared().getChangePasswordURl();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), "Exception :ResetPasswordService :resetNewPassword()"));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            serviceCallForResetNewPassword(ResetNewPasswordUrl, resetPasswordEntity, headers, callback);

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :ResetPasswordService :resetNewPassword()",
                    WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,e.getMessage()));
        }
    }

    public void serviceCallForResetNewPassword(String resetNewPasswordUrl, ResetPasswordEntity resetPasswordEntity, Map<String, String> headers, final Result<ResetNewPasswordResponseEntity> callback) {
        //Call Service-getRequestId
        ICidaasSDKService cidaasSDKService = service.getInstance();
        cidaasSDKService.ResetNewPassword(resetNewPasswordUrl,headers, resetPasswordEntity)
                .enqueue(new Callback<ResetNewPasswordResponseEntity>() {
                    @Override
                    public void onResponse(Call<ResetNewPasswordResponseEntity> call, Response<ResetNewPasswordResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,
                                        "Service failure but successful response" , "Exception :ResetPasswordService :resetNewPassword()"));
                            }
                        }
                        else {
                            assert response.errorBody() != null;
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,response,
                                    "Exception :ResetPasswordService :resetNewPassword()"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetNewPasswordResponseEntity> call, Throwable t) {
                        callback.failure( WebAuthError.getShared(context).serviceCallFailureException(
                                WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,t.getMessage(), "Exception :ResetPasswordService :resetNewPassword()"));

                    }
                });
    }

}
