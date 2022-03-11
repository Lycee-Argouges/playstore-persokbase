package fr.argouges.persokbase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import fr.argouges.persokbase.ui.filemanager.NIODownloadStore;
import fr.argouges.persokbase.ui.filemanager.NIOFileReadExample;
import fr.argouges.persokbase.ui.filemanager.NIOFileSelectStore;

public class DownloadActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;

    //Pour la connectivité au réseau
    public static Boolean DetecConnexion = null;
    public static Boolean DetectConnexionTypeMobile = null;
    public static Boolean DetectConnexionTypeWifi = null;
    public Boolean connect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Boolean statement = null;
        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            boolean wifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean forfait = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            //Log.d("NetworkState", "L'interface de connexion active est du Wifi : " + wifi);
            statement = true;
            DetecConnexion =  statement;
            DetectConnexionTypeMobile = forfait ;
            DetectConnexionTypeWifi = wifi ;
        }
        return statement;
    }

    public void Maj_app(String Filename, String URL) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Mise à jour des ressources externes");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Boolean ifExist = true;
        String extcard = "";
        Boolean FolDown = null ;
        if (MainActivity.PATH_EMULATED_FILE == false) {
            final Intent notificapps = new Intent().setClass(this, NotificationActivity.class);
            notificapps.putExtra("ErrNote","Vous devez être doté d'une carte SD pour télécharger les tutoriels. \n\n" +
                    "Sélectionnez un emplacement d'enregistrement externe depuis les paramètres de l'application.");
            startActivity(notificapps);
        } else {
            if (MainActivity.PATH_EMULATED != "") {
                try {
                    FolDown = NIOFileSelectStore.extstore();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (FolDown == true) {
                    extcard = MainActivity.PATH_EMULATED + Filename;
                } else {
                    Toast.makeText(this, "Impossible de télécharger à l'endroit indiqué.", Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    ifExist = NIOFileReadExample.main(Filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connect()) {
                DownloadFile downloadFile = new DownloadFile();
                if (MainActivity.PATH_EMULATED != "") {
                    if (FolDown == true) {
                        File mFile = new File(extcard);
                        if (!mFile.exists()) {
                            downloadFile.execute(URL, extcard);
                        }
                    }
                }
                if (ifExist == false) {
                    try {
                        String DownStore = NIODownloadStore.main();
                        String sdcard = DownStore + Filename;
                        downloadFile.execute(URL, sdcard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //Pour télécharger le fichier de présentation mis à jour sur un serveur internet
    private class DownloadFile extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                String fName = sUrl[1];
                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
                int fileLength = connection.getContentLength();
                // download the file
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(fName);
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            mProgressDialog.dismiss();
            return null;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setProgress(progress[0]);
        }
        protected void onPostExecute() {
            super.onPostExecute(null);
            mProgressDialog.cancel();
        }
    }
}
