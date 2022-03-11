package fr.argouges.persokbase.ui.filemanager;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fr.argouges.persokbase.MainActivity;

public class NIOFileReadExample {

    public static Boolean main(String args) throws IOException {
        File mFile = null;
        int value;
        if(MainActivity.PATH_DIR==true) {
            mFile = new File(Environment.getExternalStorageDirectory().getPath() + MainActivity.PATH_NAME + MainActivity.PACKAGE_NAME + "/files/" + args);
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                if (mFile.exists()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return null;
            }
        }
        if(MainActivity.PATH_DIR==false) {
            if(MainActivity.PATH_SDCARD==false) {
                mFile = new File(Environment.getDataDirectory() + "/user/0/" + MainActivity.PACKAGE_NAME + "/files/" + args);
            } else {
                mFile = new File(MainActivity.PATH_ROOT + "/" + args);
            }
            if (mFile.exists()) {
                return true;
            } else {
                return false;
            }
        }
        return null;
    }


}
