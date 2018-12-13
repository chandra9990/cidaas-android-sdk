package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;

public class TOTPMFA extends AppCompatActivity {

    Cidaas cidaas;
    String statusId,deviceID,trackid,consentName,sub;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totpmf);

        verificationCodeTextbox=findViewById(R.id.totpverificationcode);
        cidaas=new Cidaas(this);
        Intent intent=getIntent();

        statusId=intent.getStringExtra("statusId");
        deviceID=intent.getStringExtra("deviceID");
        trackid=intent.getStringExtra("trackid");
        consentName=intent.getStringExtra("consentName");
        sub=intent.getStringExtra("sub");
    }


    public void TOTPVerifyCode(View view){
        String verificationCode=verificationCodeTextbox.getText().toString();

        cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

                PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
                passwordlessEntity.setUsageType(UsageType.MFA);
                passwordlessEntity.setTrackId(trackid);
                passwordlessEntity.setRequestId(result.getData().getRequestId());
                passwordlessEntity.setSub(sub);
                passwordlessEntity.setMobile("");
                passwordlessEntity.setEmail("");


                cidaas.loginWithSmartPush(passwordlessEntity,new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        Intent intent=new Intent(TOTPMFA.this,Smartpush.class);
                        //  intent.putExtra("statusId",result.getData().getStatusId());
                        intent.putExtra("deviceID",deviceID);
                        intent.putExtra("trackid",trackid);
                        // intent.putExtra("RandomNumber",result.getData().getRandomNumber());
                        intent.putExtra("sub",sub);
                        startActivity(intent);
                        Toast.makeText(TOTPMFA.this, "+result.getData().getRandomNumber()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(TOTPMFA.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                cidaas.loginWithTOTP(passwordlessEntity,new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity authresult) {
                        if(trackid!=null && trackid!=""){
                            ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                    /*resumeLoginRequestEntity.setSub(authresult.getData().getSub());
                    resumeLoginRequestEntity.setTrack_id(trackid);
                    resumeLoginRequestEntity.setTrackingCode(authresult.getData().getTrackingCode());
                    resumeLoginRequestEntity.setVerificationType("SMS");
                    resumeLoginRequestEntity.setUsageType("PASSWORDLESS_AUTHENTICATION");
                    cidaas.resumeLogin(resumeLoginRequestEntity, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            Toast.makeText(TOTPMFA.this, ""+result.getAccess_token(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(TOTPMFA.this,SuccessfulLogin.class);
                            intent.putExtra("sub",result.getSub());
                            intent.putExtra("accessToken",result.getAccess_token());
                            startActivity(intent);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Toast.makeText(TOTPMFA.this, "Login Failed"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
*/
                        }
                        else
                        {
                            Toast.makeText(TOTPMFA.this, "Track id is null"+trackid, Toast.LENGTH_SHORT).show();
                        }
                        // Toast.makeText(SMSMFAActivity.this, "Sub"+result.getData().getSub()+" Tracking code = "+result.getData().getTrackingCode(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(TOTPMFA.this, "Result Failure"+error.ErrorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
    }

}
