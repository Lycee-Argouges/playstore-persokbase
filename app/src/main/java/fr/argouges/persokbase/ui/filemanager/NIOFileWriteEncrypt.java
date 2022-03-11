package fr.argouges.persokbase.ui.filemanager;

import android.os.Environment;

import org.jasypt.util.text.BasicTextEncryptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.argouges.persokbase.MainActivity;

public class NIOFileWriteEncrypt {

    public static String main(String save) throws IOException {
        String myEncryptionPassword = MainActivity.KEY_ENCRYPT;
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(myEncryptionPassword);
        File mFile = null;
        if(MainActivity.PATH_DIR==true) {
            mFile = new File(Environment.getExternalStorageDirectory().getPath() + MainActivity.PATH_NAME + MainActivity.PACKAGE_NAME + "/files/notes.txt");
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                mFile.createNewFile();
                FileOutputStream output = new FileOutputStream(mFile);
                save = textEncryptor.encrypt(save);
                output.write(save.getBytes());
                if (output != null) {
                    output.close();
                    return "La note a été enregistrée !";
                } else {
                    return "Un problème est apparu. Configurez l'emplacement de stockage des notes.";
                }
            } else {
                return "Un problème est apparu lors de l'enregistrement";
            }
        }
        if(MainActivity.PATH_DIR==false) {
            if(MainActivity.PATH_SDCARD==false) {
                mFile = new File(Environment.getDataDirectory() + "/user/0/" + MainActivity.PACKAGE_NAME + "/files/notes.txt");
            } else {
                mFile = new File(MainActivity.PATH_ROOT + "/notes.txt");
            }
            mFile.createNewFile();
            FileOutputStream output = new FileOutputStream(mFile);
            save = textEncryptor.encrypt(save);
            output.write(save.getBytes());
            if (output != null) {
                output.close();
                return "La note a été enregistrée !";
            } else {
                return "Un problème est apparu. Configurez l'emplacement de stockage des notes.";
            }
        }
        return "Un problème est apparu lors de l'enregistrement";
    }
}
