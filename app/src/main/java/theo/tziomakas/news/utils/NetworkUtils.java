package theo.tziomakas.news.utils;


import android.text.TextUtils;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.news.model.News;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public NetworkUtils(){

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createURL(String stringURL){

        URL url = null;

        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        //Log.v(LOG_TAG,jsonResponse);
        return jsonResponse;
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    public static List<News> extractNewsFromJson(String newsJson){
        if(TextUtils.isEmpty(newsJson)){
            return null;
        }

        List<News> news = new ArrayList<>();

        String author;
        String title;
        String description;
        String url;
        String urlToImage;
        String publishedDate;


        try {
            JSONObject baseJson = new JSONObject(newsJson);
            JSONArray articlesArray = baseJson.getJSONArray("articles");

            for(int i = 0; i < articlesArray.length(); i++){

                JSONObject item = articlesArray.getJSONObject(i);

                if(!item.isNull("author")) {
                    author = item.getString("author");
                }else{
                    author = "No author";
                }

                if(!item.isNull("title")) {
                    title = item.getString("title");
                }else{
                    title = "No title";
                }

                description = item.getString("description");
                url = item.getString("url");
                urlToImage = item.getString("urlToImage");

                if(!item.isNull("publishedAt")) {
                    publishedDate = item.getString("publishedAt");
                }else{
                    publishedDate = "No date found";
                }

                News n = new News(author,title,description,url,urlToImage,publishedDate);

                news.add(n);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }

    /**
     * Query the dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> news = extractNewsFromJson(jsonResponse);

        return news;
    }
}
