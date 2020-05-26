/*
package widas.cidaassdkv2.cidaasVerificationV2.domain.Controller.MFASettings;

import android.content.Context;

import AccessTokenController;
import CidaasProperties;
import Result;
import WebAuthErrorCode;
import WebAuthError;
import DBHelper;
import LogFile;
import AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.MFA.DeleteMFA.DeleteMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import DenyNotificationRequestEntity;
import DenyNotificationResponseEntity;
import NotificationEntity;
import ConfiguredMFAListEntity;
import com.example.cidaasv2.Service.Repository.Verification.Settings.VerificationSettingsService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class VerificationSettingsController {



    private Context context;

    public static VerificationSettingsController shared;

    public VerificationSettingsController(Context contextFromCidaas) {

        context=contextFromCidaas;

    }

    public static VerificationSettingsController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new VerificationSettingsController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Service call To Get MFA list
    public void getmfaList(@NonNull final String sub, final Result<MFAListResponseEntity> result)
    {
        final String methodName="VerificationSettingsController :getmfaList()";
        try{

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesresult) {
                    String baseurl=loginPropertiesresult.get("DomainURL");
                    if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                        // Change service call to private

                        String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);
                        VerificationSettingsService.getShared(context).getmfaList(baseurl, sub, userDeviceId, result);

                    }
                    else
                    {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or Sub must not be null",
                                "Error :"+methodName));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                  result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.MFA_LIST_FAILURE,e.getMessage()));
        }
    }

    //Service call To Get MFA list
    public void getmfaListByEmail(@NonNull final String email, final Result<MFAListResponseEntity> result)
    {
        final String methodName="VerificationSettingsController :getmfaList()";
        try{

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesresult) {
                    String baseurl=loginPropertiesresult.get("DomainURL");
                    if (baseurl != null && !baseurl.equals("") && email != null && !email.equals("")) {
                        // Change service call to private

                        String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);
                        VerificationSettingsService.getShared(context).getmfaList(baseurl, email, userDeviceId, result);

                    }
                    else
                    {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or Email must not be null",
                                "Error :"+methodName));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.MFA_LIST_FAILURE,e.getMessage()));
        }
    }


    public void denyNotification(@NonNull final String sub, @NonNull final String reason, @NonNull final String statusId,
                                 final Result<DenyNotificationResponseEntity> result)
    {
        final String methodName="VerificationSettingsController :denyNotification()";
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    final String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");

                    getAccessTokenAndService(baseurl);
                }

                public void getAccessTokenAndService(final String baseurl) {
                    if (sub != null && !sub.equals("")) {

                        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {
                                accessTokenAndServiceCall(baseurl,accessTokenresult.getAccess_token(),reason,statusId,result);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });
                    } else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be empty", "Error :"+methodName));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }

    //SERVICE
    public void accessTokenAndServiceCall(final String baseurl,@NonNull final String accessToken, @NonNull final String reason, @NonNull final String statusId,
                                          final Result<DenyNotificationResponseEntity> result)
    {
        final String methodName="VerificationSettingsController :denyNotification()";
        try {

            if (!reason.equals("") && reason != null && statusId != null && !statusId.equals("")) {

                DenyNotificationRequestEntity denyNotificationRequestEntity = new DenyNotificationRequestEntity();
                denyNotificationRequestEntity.setReject_reason(reason);
                denyNotificationRequestEntity.setStatusId(statusId);

                denyNotification(baseurl, accessToken, denyNotificationRequestEntity, result);
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("Verification Type must not be empty", "Error :"+methodName));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException(e.getMessage(), WebAuthErrorCode.DENY_NOTIFICATION,"Error :"+methodName));
        }
    }


    //To get pending notification Service
    public void denyNotification(@NonNull final String baseurl, @NonNull final String accessToken, DenyNotificationRequestEntity denyNotificationRequestEntity,final Result<DenyNotificationResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("")  ) {

                VerificationSettingsService.getShared(context).denyNotification(baseurl,accessToken,denyNotificationRequestEntity, result);
            }
            else
            {
                String errorMessage="AccessToken or baseURL must not be empty ";

                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                        "Error :VerificationSettingsController :getPendingNotification()"));
            }
        }
        catch (Exception e)
        {

            result.failure(WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController : denyNotification",
                    WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }


    public void getPendingNotification(@NonNull final String sub,  final Result<NotificationEntity> result)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    final String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");


                    if(sub!=null && !sub.equals("")) {

                        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {

                                if(DBHelper.getShared().getUserDeviceId(baseurl)!=null && !DBHelper.getShared().getUserDeviceId(baseurl).equals("")) {
                                    String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);


                                    if (!userDeviceId.equals("") && userDeviceId != null) {

                                        getPendingNotification(baseurl, accessTokenresult.getAccess_token(), userDeviceId, result);
                                    } else {
                                        result.failure(WebAuthError.getShared(context).propertyMissingException(
                                                "User Device Id must not be empty", "Error :VerificationSettingsController :getPendingNotification()"));
                                    }
                                }
                                else
                                {
                                    result.failure( WebAuthError.getShared(context).propertyMissingException(
                                            "User deviceID must not be empty","Error :VerificationSettingsController :getPendingNotification()"));
                                }
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });
                    }
                    else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException(
                                "Sub must not be empty","Exception :VerificationSettingsController :getPendingNotification()"));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure( WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController :getPendingNotification()",
                    WebAuthErrorCode.MFA_LIST_FAILURE,e.getMessage()));
        }
    }
        //To deny notification Service
    public void getPendingNotification(@NonNull final String baseurl, @NonNull final String accessToken, @NonNull final String userDeviceId,final Result<NotificationEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("")&& userDeviceId != null && !userDeviceId.equals("")  ) {

                VerificationSettingsService.getShared(context).getPendingNotification(baseurl,accessToken,userDeviceId,  result);
            }
            else
            {
                String errorMessage="AccessToken or baseURL or UserDeviceId must not be empty ";

                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error :VerificationSettingsController :getPendingNotification"));
            }
        }
        catch (Exception e)
        {

            result.failure(WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController :getPendingNotification",
                    WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,e.getMessage()));

        }
    }


    public void getConfiguredMFAList(@NonNull final String sub,  final Result<ConfiguredMFAListEntity> result,final String... baseURL)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseUrltoSend="";
                    final String baseurlFromShared = lpresult.get("DomainURL");


                    if(baseURL!=null && baseURL.length>0 )
                    {
                        baseUrltoSend=baseURL[0];
                    }

                    if(baseUrltoSend==null && !baseUrltoSend.equals(""))
                    {
                        baseUrltoSend=baseurlFromShared;
                    }

                    if(sub!=null && !sub.equals("")) {

                        if(DBHelper.getShared().getUserDeviceId(baseUrltoSend)!=null && !DBHelper.getShared().getUserDeviceId(baseUrltoSend).equals("")) {

                            String userDeviceId = DBHelper.getShared().getUserDeviceId(baseUrltoSend);
                            getConfiguredMFAList(baseUrltoSend, sub, userDeviceId, result);
                        }
                        else
                        {
                            result.failure(WebAuthError.getShared(context).propertyMissingException(
                                    "User deviceID must not be empty","Error :VerificationSettingsController :getConfiguredMFAList"));
                        }

                    }
                    else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException(
                                "Sub must not be empty", "Error :VerificationSettingsController :getConfiguredMFAList"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure( WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController :getConfiguredMFAList",
                    WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,e.getMessage()));

        }
    }
    //To get Configured MFA List of a user
    public void getConfiguredMFAList(@NonNull final String baseurl, @NonNull final String sub, @NonNull final String userDeviceId,final Result<ConfiguredMFAListEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")&& userDeviceId != null && !userDeviceId.equals("")  ) {

                VerificationSettingsService.getShared(context).getConfiguredMFAList(baseurl,sub,userDeviceId,  result);
            }
            else
            {
                String errorMessage="sub or baseURL or UserDeviceId must not be empty ";

                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error :VerificationSettingsController :getConfiguredMFAList"));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController :getConfiguredMFAList",
                    WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,e.getMessage()));
        }

    }


    //update FCM TOken
    public void updateFCMToken(@NonNull final String sub, @NonNull final String FCMToken,final Result<Object> Callbackresult){
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success( Dictionary<String, String> result) {

                    final String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    String oldFCMToken=DBHelper.getShared().getFCMToken();


                    if(oldFCMToken.equalsIgnoreCase(FCMToken))
                    {
                        //Do nothing
                    }
                    else{
                        DBHelper.getShared().setFCMToken(FCMToken);

                        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenEntityresult) {
                                VerificationSettingsService.getShared(context).updateFCMToken(baseurl,accessTokenEntityresult.getAccess_token()
                                        ,FCMToken,  Callbackresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Callbackresult.failure(error);
                                LogFile.getShared(context).addFailureLog("Update FCM Token exception" + error.getMessage());
                            }
                        });


                    }


                }

                @Override
                public void failure(WebAuthError error) {
                    Callbackresult.failure(error);
                    LogFile.getShared(context).addFailureLog("Update FCM Token exception" + error.getMessage());
                }
            });
        }

        catch (Exception e)
        {
            Callbackresult.failure(WebAuthError.getShared(context).methodException("Exception :VerificationSettingsController :updateFCMToken()",
                    WebAuthErrorCode.UPDATE_FCM_TOKEN,e.getMessage()));
        }
    }

}
*/