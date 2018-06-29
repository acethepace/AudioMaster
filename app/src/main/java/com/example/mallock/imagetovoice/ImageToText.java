package com.example.mallock.imagetovoice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mallock on 6/28/2018.
 */

public class ImageToText {

    private Context context;
    private final String TAG = this.getClass().getCanonicalName();
    private final String url = "https://api.ocr.space/parse/image";
    private String filePath;

    public ImageToText(Context context, String filePath) {
        this.filePath = filePath;
        this.context = context;
    }

    public String build() {
        sendPost();
    }

    private String sendPost(boolean isOverlayRequired, String language) throws Exception {
        URL obj = new URL(url); // OCR API Endpoints
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        JSONObject postDataParams = new JSONObject();
        postDataParams.put("apikey", getApiKey());
        postDataParams.put("isOverlayRequired", isOverlayRequired);
        postDataParams.put("base64Image", getImageAsBase64String().replace("\n", ""));
        postDataParams.put("language", language);
// Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(getPostDataString(postDataParams));
        wr.flush();
        wr.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
//return result
        return String.valueOf(response);
    }

    public String getImageAsBase64String() {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedString = Base64.encodeToString(byteArrayImage, Base64.URL_SAFE);
        Log.e(TAG, encodedString);
        return encodedString;
    }

    private String getApiKey() {
        return context.getResources().getString(R.string.ocr_space_api_key);
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }


}
