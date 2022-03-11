package fr.argouges.persokbase.ui.filemanager;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fr.argouges.persokbase.MainActivity;

public class NIODownloadStore {
    public static String main() throws IOException {
        File mFile = null;
        String APP = "PersoKbase.txt";
        int value;
        if(MainActivity.PATH_DIR==true) {
            mFile = new File(Environment.getExternalStorageDirectory().getPath() + MainActivity.PATH_NAME + MainActivity.PACKAGE_NAME + "/files/" + APP);
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                if (mFile.exists()) {
                    return Environment.getExternalStorageDirectory().getPath() + MainActivity.PATH_NAME + MainActivity.PACKAGE_NAME + "/files/";
                } else {
                    return null;
                }
            } else {
                return "Erreur de lecture du fichier...";
            }
        }
        if(MainActivity.PATH_DIR==false) {
            if(MainActivity.PATH_SDCARD==false) {
                mFile = new File(Environment.getDataDirectory() + "/user/0/" + MainActivity.PACKAGE_NAME + "/files/" + APP);
                if (mFile.exists()) {
                    return Environment.getDataDirectory() + "/user/0/" + MainActivity.PACKAGE_NAME + "/files/";
                } else {
                    return null;
                }
            } else {
                mFile = new File(MainActivity.PATH_ROOT + "/" + APP);
                if (mFile.exists()) {
                    return MainActivity.PATH_ROOT + "/" + APP;
                } else {
                    return null;
                }
            }
        }
        return "Erreur de la sélection du dossier de destination...";
    }
}
