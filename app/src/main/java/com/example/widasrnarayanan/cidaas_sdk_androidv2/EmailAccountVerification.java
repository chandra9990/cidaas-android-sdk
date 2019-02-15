package com.example.widasrnarayanan.cidaas_sdk_androidv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;

public class EmailAccountVerification extends AppCompatActivity {


    Cidaas cidaas;
    String accvid;
    EditText verificationCodeTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_account_verification);


        cidaas=new Cidaas(this);
        verificationCodeTextbox=findViewById(R.id.emailaccountverification);
        Intent intent=getIntent();

        accvid=intent.getStringExtra("accvid");

        LoginEntity loginEntity=new LoginEntity();
    }
    public void ButtonClickVerifyEmail(View view){
        try {
            String verificationCode = verificationCodeTextbox.getText().toString();
            if (accvid != null && accvid!="") {
                cidaas.verifyAccount( verificationCode, accvid,new Result<RegisterUserAccountVerifyResponseEntity>() {
                    @Override
                    public void success(RegisterUserAccountVerifyResponseEntity result) {
                        Toast.makeText(EmailAccountVerification.this, "Success"+result.getStatus(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Toast.makeText(EmailAccountVerification.this, "Error on Verifying"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            cidaas.getRequestId(null,new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                }

                @Override
                public void failure(WebAuthError error) {

                }
            });

            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
