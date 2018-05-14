package eu.epfc.pocketmovie.model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 0107anocchilupo on 7/05/2018.
 */

public class HttpRequestService extends IntentService {

    private OkHttpClient httpClient = new OkHttpClient();

    private static final String EXTRA_KEY_URL = "extra_key_url";

    public HttpRequestService() {
        super("HttpRequestService");
    }

    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_KEY_URL, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

            final String url = intent.getStringExtra(EXTRA_KEY_URL);
            System.out.println(this.getClass().getName() + " - url param " + url);
            try {
                String responseString = startRequest(url);
                System.out.println(this.getClass().getName() + " - response " + responseString);

                // the request succeeded -> send a brodcase message "httpRequestComplete"
                Intent completeIntent = new Intent("httpRequestComplete");
                completeIntent.putExtra("responseString",responseString);
                sendBroadcast(completeIntent);
            }
            catch (IOException e) {

                // the request failed -> send a brodcase message "httpRequestFailed"
                Intent completeIntent = new Intent("httpRequestFailed");
                System.out.println(this.getClass().getName() + " - exception: " + e.toString());
                sendBroadcast(completeIntent);
            }
        }
    }

    private String startRequest(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        System.out.println(this.getClass().getName() + " startRequest begin");
        Response response = httpClient.newCall(request).execute();  // blocking HTTP call
        System.out.println(this.getClass().getName() + " startRequest end");
        return   response.body().string();
    }
}
