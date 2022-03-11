package fr.argouges.persokbase.ui.filemanager;

import android.os.Environment;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fr.argouges.persokbase.MainActivity;

public class NIOFileDecryptExample {

    public static String main() throws IOException {
        String myEncryptionPassword = MainActivity.KEY_ENCRYPT;
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(myEncryptionPassword);
        File mFile = null;
        int value;
        if(MainActivity.PATH_DIR==true) {
            mFile = new File(Environment.getExternalStorageDirectory().getPath() + MainActivity.PATH_NAME + MainActivity.PACKAGE_NAME + "/files/notes.txt");
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                StringBuffer lu = new StringBuffer();
                FileInputStream input = new FileInputStream(mFile);
                if (mFile.exists()) {
                    while ((value = input.read()) != -1)
                        lu.append((char) value);
                    String lecture = lu.toString();
                    try {
                        lecture = textEncryptor.decrypt(lecture);
                    } catch (EncryptionOperationNotPossibleException e) {
                        lecture = "Unable to decrypt text. Decryption of Properties failed,  make sure encryption/decryption " +
                                "passwords match";
                    }
                    String encodedWithISO88591 = lecture;
                    String decodedToUTF8 = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
                    if (input != null)
                        input.close();
                    //return decodedToUTF8;
                    return lecture;
                } else {
                    return null;
                }
            } else {
                return "Erreur de lecture du fichier...";
            }
        }
        if(MainActivity.PATH_DIR==false) {
            if(MainActivity.PATH_SDCARD==false) {
                mFile = new File(Environment.getDataDirectory() + "/user/0/" + MainActivity.PACKAGE_NAME + "/files/notes.txt");
            } else {
                mFile = new File(MainActivity.PATH_ROOT + "/notes.txt");
            }
                StringBuffer lu = new StringBuffer();
                FileInputStream input = new FileInputStream(mFile);
                if (mFile.exists()) {
                    while ((value = input.read()) != -1)
                        lu.append((char) value);
                    String lecture = lu.toString();
                    try {
                        lecture = textEncryptor.decrypt(lecture);
                    } catch (EncryptionOperationNotPossibleException e) {
                        lecture = "Unable to decrypt text. Decryption of Properties failed,  make sure encryption/decryption " +
                        "passwords match";
                    }
                    String encodedWithISO88591 = lecture;
                    String decodedToUTF8 = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
                    if (input != null)
                        input.close();
                    //return decodedToUTF8;
                    return lecture;
                } else {
                    return null;
                }
        }
        return "Erreur de lecture du fichier...";
    }

}
