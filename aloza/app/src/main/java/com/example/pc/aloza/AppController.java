package com.example.pc.aloza;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static char[] KEYSTORE_PASSWORD = "test123".toCharArray();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                    /*new HttpClientStack(new MyHttpClient(getApplicationContext())));*/
            new HurlStack(null, newSslSocketFactory()));
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private SSLSocketFactory newSslSocketFactory() {
        try {

            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = getApplicationContext().getResources().openRawResource(R.raw.test);
            try {

                trusted.load(in, KEYSTORE_PASSWORD);
            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
            }finally {
                in.close();
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trusted);

            SSLContext context = SSLContext.getInstance("TLSv1");
            context.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory ret = new NoSSLv3SocketFactory(context.getSocketFactory());

            return ret;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}