package fr.enssat.gazouilli.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.gilles.myapplication.backend.messaging.Messaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Adrien on 18/01/2016.
 */
public class GcmSendAsyncTask extends AsyncTask<String,Void,Void> {

    private Context context;

    public GcmSendAsyncTask(Context context){
        this.context = context;
    }


    @Override
    protected Void doInBackground(String... params) {

        if (params.length != 3) {
            Log.e("MYAPP", "GcmSendAsyncTask : wrong number of arguments (must be 3)");
            return null;
        }

        Messaging msgService;

        Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                // otherwise they can be skipped
                .setRootUrl("https://perfect-stock-115314.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                            throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        msgService = builder.build();
        try {
            msgService.messagingEndpoint().sendMessage(params[0]+","+params[1]+","+params[2]).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void param) {
        //Toast.makeText(context, "message sent", Toast.LENGTH_LONG).show();
        Logger.getLogger("MSG SENT").log(Level.INFO, "message sent");
    }
}
