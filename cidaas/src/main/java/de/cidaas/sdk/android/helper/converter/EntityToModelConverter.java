package de.cidaas.sdk.android.helper.converter;

import android.content.Context;

import java.util.UUID;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.models.dbmodel.AccessTokenModel;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.helper.crypthelper.AESCrypt;


/**
 * Created by widasrnarayanan on 13/2/18.
 */

public class EntityToModelConverter {
    //Convert AccessTokenEntity to Model
    Context context;
    public static EntityToModelConverter sharedinstance;

    public EntityToModelConverter(Context contextFromCidaas) {
        context = contextFromCidaas;
    }

    public static EntityToModelConverter getShared(Context contextFromCidaas) {
        if (sharedinstance == null) {
            sharedinstance = new EntityToModelConverter(contextFromCidaas);
        }
        return sharedinstance;
    }

    //Entity to Model Conversion

    // convert accessTokenEntity To AccessTokenModel
    public void accessTokenEntityToAccessTokenModel(AccessTokenEntity accessTokenEntity, String userId, EventResult<AccessTokenModel> callback) {
        String methodName = "accessTokenEntityToAccessTokenModel";
        try {
            String EncryptedToken = "";

            AccessTokenModel.getShared().setExpires_in(accessTokenEntity.getExpires_in());
            AccessTokenModel.getShared().setId_token(accessTokenEntity.getId_token());
            AccessTokenModel.getShared().setRefresh_token(accessTokenEntity.getRefresh_token());
            AccessTokenModel.getShared().setScope(accessTokenEntity.getScope());
            AccessTokenModel.getShared().setUserState(accessTokenEntity.getUserstate());

            //Additional Details to store token in Local DB
            AccessTokenModel.getShared().setUserId(userId);
            AccessTokenModel.getShared().setSalt(UUID.randomUUID().toString());

            //AccessTokenModel.getShared().setKey(UUID.randomUUID().toString());
            //Convert Milliseconds into seconds
            AccessTokenModel.getShared().setSeconds(System.currentTimeMillis() / 1000);

            //Encrypt the AccessToken
            try {
                EncryptedToken = AESCrypt.encrypt(AccessTokenModel.getShared().getSalt(), accessTokenEntity.getAccess_token());
            } catch (Exception e) {
                EncryptedToken = "";
            }
            if (EncryptedToken != "") {
                AccessTokenModel.getShared().setAccess_token(EncryptedToken);
                AccessTokenModel.getShared().setEncrypted(true);
            } else {
                //   AccessTokenModel.getShared().setAccess_token(accessTokenEntity.getAccess_token());
                AccessTokenModel.getShared().setEncrypted(false);
                //  AccessTokenModel.getShared().setPlainToken(accessTokenEntity.getAccess_token());
            }
            DBHelper.getShared().setAccessToken(AccessTokenModel.getShared());
            callback.success(AccessTokenModel.getShared());

        } catch (Exception e) {
            // Handle Error
            callback.failure(WebAuthError.getShared(context).accessTokenException(e.getMessage(), methodName));
            LogFile.getShared(context).addFailureLog(e.getMessage() + WebAuthErrorCode.ACCESS_TOKEN_CONVERSION_FAILURE);
        }
    }


    // Model to Entity  Conversion

    // convert accessTokenModel to AccessTokenEntity
    public void accessTokenModelToAccessTokenEntity(AccessTokenModel accessTokenModel, String userId, EventResult<AccessTokenEntity> callback) {
        try {
            AccessTokenEntity accessTokenEntity = new AccessTokenEntity();


            if (accessTokenModel.getAccess_token() != null && !accessTokenModel.getAccess_token().equals("")) {
                accessTokenEntity.setAccess_token(accessTokenModel.getAccess_token());
            }

            if (accessTokenModel.getId_token() != null && !accessTokenModel.getId_token().equals("")) {
                accessTokenEntity.setId_token(accessTokenModel.getId_token());
            }

            if (accessTokenModel.getRefresh_token() != null && !accessTokenModel.getRefresh_token().equals("")) {
                accessTokenEntity.setRefresh_token(accessTokenModel.getRefresh_token());
            }
            if (accessTokenModel.getScope() != null && !accessTokenModel.getScope().equals("")) {
                accessTokenEntity.setScope(accessTokenModel.getScope());
            }
            if (accessTokenModel.getUserState() != null && !accessTokenModel.getUserState().equals("")) {
                accessTokenEntity.setUserstate(accessTokenModel.getUserState());
            }

            accessTokenEntity.setExpires_in(accessTokenModel.getExpires_in());

            //Decrypt the AccessToken
            if (accessTokenModel.isEncrypted()) {
                accessTokenEntity.setAccess_token(AESCrypt.decrypt(accessTokenModel.getSalt(), accessTokenEntity.getAccess_token()));

            } else {
              /*  if(accessTokenModel.getPlainToken()==null || (accessTokenModel.getPlainToken().equals("")))
                {
                    accessTokenModel.setPlainToken(accessTokenEntity.getAccess_token());
                }
                accessTokenEntity.setAccess_token(accessTokenModel.getPlainToken());*/
            }
            callback.success(accessTokenEntity);
        } catch (Exception e) {
            // Handle Error
            callback.failure(WebAuthError.getShared(context).accessTokenException(e.getMessage(), "Methodname"));
            LogFile.getShared(context).addFailureLog(e.getMessage() + WebAuthErrorCode.ACCESS_TOKEN_CONVERSION_FAILURE);
        }
    }

}
