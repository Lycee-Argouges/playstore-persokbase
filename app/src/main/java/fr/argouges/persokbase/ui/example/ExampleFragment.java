package fr.argouges.persokbase.ui.example;
//Cette classe est la page TUTORIELS
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;

import fr.argouges.persokbase.MainActivity;
import fr.argouges.persokbase.R;
import fr.argouges.persokbase.ui.filemanager.NIODownloadStore;
import fr.argouges.persokbase.ui.filemanager.NIOFileReadExample;

public class ExampleFragment extends Fragment {

    private ExampleViewModel exampleViewModel;
    String[] GrilleTextPDF = new String[] {
            "Réinitialiser un mot de passe ENT",
            "Envoyer le trombinoscope"};
    String[] GrilleFilePDF = new String[] {
            "motdepasseENT.pdf",
            "trombinoscope.pdf"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exampleViewModel =
                new ViewModelProvider(this).get(ExampleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_example, container, false);
        //final TextView textView = root.findViewById(R.id.text_example);

        TextView notifmaj = (TextView) root.findViewById(R.id.notif_maj);
        notifmaj.setText("Les tutoriels sont en téléchargement depuis les paramètres de l'application.");
        TextView alertmaj = (TextView) root.findViewById(R.id.alert_maj);
        alertmaj.setText("Mettre le contenu à jour.");

        GridView gridView = (GridView) root.findViewById(R.id.grid_example);
        int GrilleIconBit[] = {R.drawable.ic_baseline_picture_as_pdf, R.drawable.ic_baseline_picture_as_pdf};
        ExampleFragment.CustomAdapter customAdapter = new ExampleFragment.CustomAdapter(getContext(), GrilleIconBit);
        gridView.setAdapter(customAdapter);

        exampleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    public class CustomAdapter extends BaseAdapter {
        Context context;
        int logos[];
        LayoutInflater inflter;
        public CustomAdapter(Context applicationContext, int[] logos) {
            this.context = applicationContext;
            this.logos = logos;
            inflter = (LayoutInflater.from(applicationContext));
        }
        @Override
        public int getCount() {
            return logos.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            /*Boolean ifExist = null;
            try {
                ifExist = NIOFileReadExample.main(GrilleFilePDF[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            String DownStore = null;
            try {
                DownStore = IfExist(GrilleFilePDF[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (DownStore != "") {
                view = inflter.inflate(R.layout.example_element, null); // inflate the layout
                ImageView icon = (ImageView) view.findViewById(R.id.icopdf); // get the reference of ImageView
                icon.setImageResource(logos[i]); // set logo images
                TextView text = (TextView) view.findViewById(R.id.textpdf);
                text.setText(GrilleTextPDF[i]);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean success = null;
                        success = openPdf(GrilleFilePDF[i]);
                    }
                });
            } else {
                view = inflter.inflate(R.layout.example_element, null); // inflate the layout
                ImageView icon = (ImageView) view.findViewById(R.id.icopdf); // get the reference of ImageView
                icon.setImageResource(logos[i]); // set logo images
                TextView text = (TextView) view.findViewById(R.id.textpdf);
                text.setText("NON DISPONIBLE");
                text.setTextSize(12);
            }
            return view;
        }
    }

    public Boolean openPdf(String pdfpath) {
        Intent shareIntent = new Intent(Intent.ACTION_VIEW);
        //On vérifie si le fichier existe sur le téléphone
        /*Boolean ifExist = null;
        try {
            ifExist = NIOFileReadExample.main(pdfpath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String DownStore = null;
        try {
            DownStore = IfExist(pdfpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (DownStore != "") {
            //String DownStore = NIODownloadStore.main();
            File file = new File(DownStore + pdfpath);
            String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.toString()));
            String authority = getContext().getPackageName() + ".fileprovider";
            Uri uriToFile = FileProvider.getUriForFile(getContext(), authority, file);
            shareIntent.setDataAndType(uriToFile, mimeType);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            shareIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Intent openpdf = Intent.createChooser(shareIntent, "Tutoriels");
            try {
                this.startActivity(openpdf);
            } catch (ActivityNotFoundException e) {
                //Instruct the user to install a PDF reader here, or something
                Toast.makeText(getContext(), "Aucun lecteur de fichiers PDF n'est installé !", Toast.LENGTH_LONG).show();
            }
            return true ;
        } else {
            return false;
        }
    }

    public String IfExist(String filepath) throws IOException {
        Boolean ifExist = true;
        String extcard = "";
        if (MainActivity.PATH_EMULATED != "") {
            extcard = MainActivity.PATH_EMULATED + filepath ;
            File mFile = new File(extcard);
            if (mFile.exists()) {
                return MainActivity.PATH_EMULATED;
            }
        } else {
            ifExist = NIOFileReadExample.main(filepath);
            if (ifExist == true) {
                return NIODownloadStore.main();
            }
        }
        return "";
    }
}
