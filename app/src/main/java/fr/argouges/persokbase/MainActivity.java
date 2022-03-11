package fr.argouges.persokbase;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.telephony.ims.RegistrationManager;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;

import fr.argouges.persokbase.ui.filemanager.NIODownloadStore;
import fr.argouges.persokbase.ui.filemanager.NIOFileReadExample;
import fr.argouges.persokbase.ui.filemanager.NIOFileSelectStore;
import fr.argouges.persokbase.ui.security.SecurityFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ProgressDialog mProgressDialog;
    public static String PACKAGE_NAME;
    public static String KEY_ENCRYPT;
    public static String PATH_NAME;
    public static String PATH_ROOT;
    public static String PATH_EMULATED;
    public static boolean PATH_EMULATED_FILE = false;
    public static boolean PATH_DIR;
    public static boolean PATH_SDCARD = false;
    public static boolean REFRESHtag = false;
    public static boolean OPTIONtag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connect();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        PATH_ROOT = this.getFilesDir().toString();
        KEY_ENCRYPT = getKey();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String test = getFilesDir().getAbsolutePath();
                String test = getFilesDir().toString();
                String[] files = fileList();
                File res = new File(getFilesDir() + "/res/font/helvetica_ttf.ttf");
                if (res.exists()) {
                    //"Replace with your own action"
                    Snackbar.make(view, "Ca marche :)", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //"Replace with your own action"
                    Snackbar.make(view, Arrays.toString(files), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_security, R.id.nav_slideshow, R.id.nav_content, R.id.nav_activity, R.id.nav_example, R.id.nav_support)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Personalisation des fonctions de l'application ici...
        //La fonction Maj_app permet de télécharger un contenu stocké sur l'ENT
        //Une page dédiée pourra contenir les ressources de la section content
        selectDownFold();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent prefintent = new Intent(this, PreferenceActivity.class);
            this.startActivity(prefintent);
            OPTIONtag = true;
            return true;
        }
        if (id == R.id.action_synchro) {
            selectDownFold();
            String PATH_STORE = PATH_NAME;
            /*if (PATH_STORE == "/Android/data/" || PATH_STORE == "/Android/media/") {
                PATH_EMULATED = "/storage/emulated/0/Download/" + PACKAGE_NAME + "/files/";
            }*/
            if(connect()==true && DetectConnexionTypeWifi != false) {
                Maj_app("motdepasseENT.pdf", "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6064", false);
                Maj_app("trombinoscope.pdf", "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6063", true);
            } else {
                final Intent notificapps = new Intent().setClass(this, NotificationActivity.class);
                notificapps.putExtra("ErrNote","Connectez-vous à un réseau WIFI pour mettre à jour le contenu de l'application.");
                startActivity(notificapps);
            }
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.nav_example);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDownFold();
        //KEY_ENCRYPT = getKey();
        if (REFRESHtag == true) {
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.nav_security);
            REFRESHtag = false;
        }
        if (OPTIONtag == true) {
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.nav_home);
            KEY_ENCRYPT = getKey();
            OPTIONtag = false;
        }
    }

    //Pour la séléection des préférences
    public void storage() {
        String FAVORITE_STORAGE = "LinkStore";
        String GET_PREF = "LinkStoreDefault";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean FolderRoot = preferences.getBoolean("LinkStoreLocal",false);
        boolean PREFERRED = preferences.getBoolean("LinkStoreDefault",false);
        boolean KeyStatusS = preferences.getBoolean("LinkKeyEncrypt",false);
        if(!PREFERRED && !FolderRoot && !KeyStatusS) {
            clearSharedPref();
        }
        String PREFERRED_STORAGE = preferences.getString("LinkStore",null);
        if(PREFERRED_STORAGE==null || !PREFERRED) {
            setPath();
        }
    }
    public void setPath() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LinkStore", "/Android/data/");
        editor.apply();
    }
    public String getPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String FolderSave = preferences.getString("LinkStore", null);
        PATH_EMULATED = "";
        PATH_EMULATED_FILE = true;
        if (FolderSave.equals("/Android/data/") || FolderSave.equals("/Android/media/")) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                    File folder = new File(Environment.getExternalStorageDirectory().getPath());
                    PATH_EMULATED = folder.getPath() + "/Download/" + MainActivity.PACKAGE_NAME + "/files/";
                    PATH_EMULATED_FILE = true;
            } else {
                PATH_EMULATED_FILE = false;
            }
        }
        return FolderSave;
    }
    public Boolean getDir() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean FolderRoot = preferences.getBoolean("LinkStoreLocal",true);
        return FolderRoot;
    }
    public String getKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String KeySaved = preferences.getString("LinkKeySave",null);
        return KeySaved;
    }
    public void clearSharedPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    //Pour l'accès au dossier des téléchargements
    public void selectDownFold() {
        storage();
        PATH_NAME = getPref();
        PATH_DIR = getDir();
        Boolean Statut = false;
        try {
            Statut = NIOFileSelectStore.select(PATH_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Statut && OPTIONtag == true) {
            Toast.makeText(this, "Impossible d'enregistrer à l'emplacement sélectionné", Toast.LENGTH_LONG).show();
        }
    }

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

    private void Maj_app(String Filename, String URL, Boolean Queue) {
        if (PATH_EMULATED_FILE == false) {
            final Intent notificapps = new Intent().setClass(this, NotificationActivity.class);
            notificapps.putExtra("ErrNote","Vous devez être doté d'une carte SD pour télécharger les tutoriels. \n\n" +
                    "Sélectionnez un emplacement d'enregistrement externe depuis les paramètres de l'application.");
            startActivity(notificapps);
        } else {
            if (Queue == false) {
                mProgressDialog = new ProgressDialog(MainActivity.this);
                mProgressDialog.setMessage("Mise à jour des ressources externes");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            }
        }
        if (PATH_EMULATED != "" && PATH_EMULATED_FILE == true) {
            String extcard = "";
            Boolean FolDown = false ;
            try {
                FolDown = NIOFileSelectStore.extstore();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (FolDown == true) {
                extcard = PATH_EMULATED + Filename;
                DownloadFile downloadFile = new DownloadFile();
                File mFile = new File(extcard);
                if (!mFile.exists()) {
                    downloadFile.execute(URL, extcard);
                }
            } else {
                Toast.makeText(this, "Impossible de télécharger à l'endroit indiqué.", Toast.LENGTH_LONG).show();
            }
        }
        if (PATH_EMULATED == "" && PATH_EMULATED_FILE == true) {
            Boolean ifExist = true;
            try {
                ifExist = NIOFileReadExample.main(Filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ifExist == false) {
                DownloadFile downloadFile = new DownloadFile();
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

    //Pour télécharger le fichier de présentation mis à jour sur un serveur internet
    private class DownloadFile extends AsyncTask<String, Integer, String> {
        boolean isdone = false;
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
            return "Completed";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setProgress(progress[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(null);
            mProgressDialog.setMessage(result);
            //mProgressDialog.dismiss();
            isdone = true;
        }
    }

}