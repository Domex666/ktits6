package com.example.ktits;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "https://domex666.000webhostapp.com/";
    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Обработка");
        progressDialog.setMessage("Подождите");

    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();

    }
    public void fetchUserDataInBackground(User user, GetUserCallback callBack){
    progressDialog.show();
    new fetchUserDataAsyncTask(user, callBack).execute();
    }


    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;
        public StoreUserDataAsyncTask(User user, GetUserCallback usercallback){
            this.user = user;
            this.userCallback = usercallback;

        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair>dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("id_group", user.id_group + ""));
            dataToSend.add(new BasicNameValuePair("login", user.login));
            dataToSend.add(new BasicNameValuePair("pass", user.pass));
            dataToSend.add(new BasicNameValuePair("FIO", user.FIO));
            HttpParams httpRequstParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequstParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequstParams, CONNECTION_TIMEOUT);
            HttpClient client = new DefaultHttpClient(httpRequstParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback usercallback) {
            this.user = user;
            this.userCallback = usercallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair>dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("login", user.login));
            dataToSend.add(new BasicNameValuePair("pass", user.pass));
            HttpParams httpRequstParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequstParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequstParams, CONNECTION_TIMEOUT);
            HttpClient client = new DefaultHttpClient(httpRequstParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");
            User returnedUser=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                Log.i("tagconvertstr", "["+result+"]");
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.length() == 0){
                returnedUser=null;
            }else {
                int id_group = jsonObject.getInt("id_group");
                String FIO = jsonObject.getString("FIO");
                returnedUser = new User(FIO, id_group, user.login,user.pass);


                }

            }

            catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }

    }
}
