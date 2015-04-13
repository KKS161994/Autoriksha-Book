package in.silive.listener;

/**
 * Created by kartikey on 4/5/15.
 */
public interface NetworkResponseListener {
public void onPreExecute();
public void onPostExecute(String data);
}
