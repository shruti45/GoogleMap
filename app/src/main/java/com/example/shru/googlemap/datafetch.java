package com.example.shru.googlemap;

/**
 * Created by Shru on 2/9/2016.
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class datafetch {
    static String response = null;
    datafetch()
    {

    }
    public String datafetch(String URL)
    {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            //if (method == POST) {
            HttpPost httpPost = new HttpPost(URL);
            // adding post params


            httpResponse = httpClient.execute(httpPost);


        /*  else if (method == GET) {


                HttpGet httpGet = new HttpGet(URL);


                httpResponse = httpClient.execute(httpGet);

            }*/
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }
}
