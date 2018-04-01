package com.thephoenixit.getjobsapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.thephoenixit.getjobsapp.R;
import com.thephoenixit.getjobsapp.app.MyApp;
import com.thephoenixit.getjobsapp.utils.Constant;
import com.thephoenixit.getjobsapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity
        implements com.mobsandgeeks.saripaar.Validator.ValidationListener {

    private class MyTaskLogin extends AsyncTask
    {

        protected Object doInBackground(Object aobj[])
        {
            return doInBackground((String[])aobj);
        }

        protected String doInBackground(String as[])
        {
            return JsonUtils.getJSONString(as[0]);
        }

        protected void onPostExecute(Object obj)
        {
            onPostExecute((String)obj);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog != null && pDialog.isShowing())
                pDialog.dismiss();
            if(s == null || s.length() == 0)
            {
                showToast(getString(R.string.sign_up));
                return;
            }
            JSONArray jsonarray = null;
            try {
                jsonarray = (new JSONObject(s)).getJSONArray("JOBS_APP");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int i = 0;
            do
            {
                try
                {
                    if(i < jsonarray.length())
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        if(jsonobject.has("msg"))
                        {
                            strMessage = jsonobject.getString("msg");
                            Constant.GET_SUCCESS_MSG = jsonobject.getInt("success");
                            break;
                        }
                        try
                        {
                            Constant.GET_SUCCESS_MSG = jsonobject.getInt("success");
                            strName = jsonobject.getString("name");
                            strPassengerId = jsonobject.getString("user_id");
                            strIsProvider = jsonobject.getString("user_type");
                            break;
                        }
                        catch(JSONException jsonexception)
                        {
                            jsonexception.printStackTrace();
                        }
                    }
                    setResult();
                    return;
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                i++;
            } while(true);
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ProgressDialog pDialog;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        myApp = MyApp.getInstance();
        edtEmail = (EditText)findViewById(R.id.edt_email);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        btnSingIn = (Button)findViewById(R.id.button_log_in);
        btnSkip = (Button)findViewById(R.id.button_skip);
        textForgot = (TextView)findViewById(R.id.text_forgot);
        textSignUp = (TextView)findViewById(R.id.text_sign_up);
        btnSingIn.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                validator.validateAsync();
            }
        });
        textSignUp.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnSkip.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textForgot.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    mAdView = (com.google.android.gms.ads.AdView) findViewById(R.id.signinadView);
    adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        validator = new Validator(this);
        validator.setValidationListener(this);
    }
com.google.android.gms.ads.AdView mAdView;
    com.google.android.gms.ads.AdRequest adRequest;
    @Override
    public void onValidationSucceeded() {
        strEmail = edtEmail.getText().toString();
        strPassword = edtPassword.getText().toString();
        if(JsonUtils.isNetworkAvailable(this))
        {
            MyTaskLogin mytasklogin = new MyTaskLogin();
            String as[] = new String[1];
            as[0] = (new StringBuilder()).append(Constant.LOGIN_URL).append(strEmail).append("&password=").append(strPassword).toString();
            mytasklogin.execute(as);
            return;
        } else
        {
            showToast(getString(R.string.sign_up));
            return;
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String s = failedRule.getFailureMessage();
        if(failedView instanceof EditText)
        {
            failedView.requestFocus();
            ((EditText)failedView).setError(s);
            return;
        } else
        {
            Toast.makeText(this, "Record Not Saved", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void setResult()
    {
        if(Constant.GET_SUCCESS_MSG == 0)
        {
            showToast((new StringBuilder()).append("Opps. \n").append(strMessage).toString());
            return;
        }
        myApp.saveIsLogin(true);
        myApp.saveIsJobProvider(strIsProvider.equals("2"));
        myApp.saveLogin(strPassengerId, strName, strEmail);
        Object obj;
        if(myApp.getIsJobProvider())
            obj = JobProviderMainActivity.class;
        else
            obj = MainActivity.class;
        startActivity(new Intent(this, ((Class) (obj))));
        finish();
    }
    public void showToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    MyApp myApp;
    Button btnSingIn;
    Button btnSkip;
    EditText edtEmail;
    EditText edtPassword;
    String strEmail;
    String strIsProvider;
    String strMessage;
    String strName;
    String strPassengerId;
    String strPassword;
    TextView textForgot;
    TextView textSignUp;
    private Validator validator;
}
