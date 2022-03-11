package fr.argouges.persokbase.ui.security;
//Cette classe est la page COFFRE-FORT
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import fr.argouges.persokbase.EditActivity;
import fr.argouges.persokbase.MainActivity;
import fr.argouges.persokbase.R;
import fr.argouges.persokbase.ui.filemanager.NIOFileDecryptExample;

public class SecurityFragment extends Fragment {

    private SecurityViewModel securityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        securityViewModel =
                new ViewModelProvider(this).get(SecurityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_security, container, false);
        final TextView textView = root.findViewById(R.id.text_security);
        /*String myEncryptionPassword = "TOTO38!!";
        String myTextUTF = "Hého papa noël :)";
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(myEncryptionPassword);
        String myEncryptedText = textEncryptor.encrypt(myTextUTF);
        String plainText = textEncryptor.decrypt(myEncryptedText);*/

        securityViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                lecture(textView);
            }
        });



        FloatingActionButton fabsecurity = root.findViewById(R.id.fabsecurity);
        fabsecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.KEY_ENCRYPT != null) {
                    final Intent appsedit = new Intent().setClass(getContext(), EditActivity.class);
                    startActivity(appsedit);
                    MainActivity.REFRESHtag = true;
                } else {
                    Snackbar.make(view, "Saisissez une clé secrète via le bouton supérieur des paramètres.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        return root;
    }

    public static void lecture(TextView textView) {
        String NoteN = null;
        if(MainActivity.KEY_ENCRYPT != null && MainActivity.KEY_ENCRYPT != "") {
            try {
                NoteN = NIOFileDecryptExample.main();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("".equals(NoteN) || NoteN == null) {
                textView.setText("Example");
            } else {
                textView.setText(NoteN);
            }
        } else {
            textView.setText("Cette page permet d'enregistrer une note chiffrée sur le téléphone. L'algorithme issu de la librairie Jasypt est utilisé.");
        }
    }

}
