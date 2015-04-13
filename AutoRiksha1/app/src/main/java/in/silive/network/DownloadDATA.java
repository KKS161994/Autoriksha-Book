package in.silive.network;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by kartikey on 4/4/15.
 */
public class DownloadDATA extends AsyncTask<String,String,String>{
    String url=KeyValues.apiurl;
    StringBuilder sb=new StringBuilder();
    InputStream is;

    public DownloadDATA(String urldata){
        this.url=this.url+urldata;
    }
    @Override
    protected String doInBackground(String... params) {
        URL httpurl=null;
        String line="";
        try {
            httpurl=new URL(url);
            HttpURLConnection httpURL=(HttpURLConnection) httpurl.openConnection();
            is=httpURL.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
         while((line=br.readLine())!=null)
            sb.append(line);
         }  catch (MalformedURLException e)
        {
           e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}