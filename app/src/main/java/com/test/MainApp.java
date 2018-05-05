package com.test;

import android.app.Application;

import com.parse.Parse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainApp extends Application {

    private static final String PARSE_APP_ID = "myAppId";
    private static final String PARSE_CLOUD_SERVER_URL = "http://myherokuapp.herokuapp.com/parse/";

    // Make sure to update gcm_sender_id in strings.xml!!

    @Override
    public void onCreate() {
        super.onCreate();

        // FCM token will be automatically registered by ParseFirebaseJobService
        // Look for ParseFCM log messages to confirm
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APP_ID)
                .clientKey(null) // no client key needed in Parse open source
                .server(PARSE_CLOUD_SERVER_URL) // do not forget the URL needs to end with a trailing slash
                .clientBuilder(builder)
                .build());

    }
}
