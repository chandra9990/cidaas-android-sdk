package de.cidaas.sdk.android.cidaasVerification.domain.Service.PendingNotification;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Settings.PendingNotification.PendingNotificationEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;

public class PendingNotificationService {
    private Context context;

    public static PendingNotificationService shared;

    CidaasSDK_V2_Service service;


    public PendingNotificationService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static PendingNotificationService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PendingNotificationService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PendingNotificationService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call PendingNotification Service
    public void callPendingNotificationService(@NonNull String pendingNotificationURL, Map<String, String> headers, PendingNotificationEntity pendingNotificationEntity,
                                               final Result<PendingNotificationResponse> pendingNotificationCallback) {
        final String methodName = "PendingNotificationService:-callPendingNotificationService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getPendingNotification(pendingNotificationURL, headers, pendingNotificationEntity).enqueue(new Callback<PendingNotificationResponse>() {
                @Override
                public void onResponse(Call<PendingNotificationResponse> call, Response<PendingNotificationResponse> response) {
                    if (response.isSuccessful()) {
                        pendingNotificationCallback.success(response.body());
                    } else {
                        pendingNotificationCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<PendingNotificationResponse> call, Throwable t) {
                    pendingNotificationCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            pendingNotificationCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}