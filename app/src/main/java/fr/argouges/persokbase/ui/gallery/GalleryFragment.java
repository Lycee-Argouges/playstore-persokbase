package fr.argouges.persokbase.ui.gallery;
//Cette classe est la page PHOTOS
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.InputStream;

import fr.argouges.persokbase.MainActivity;
import fr.argouges.persokbase.NotificationActivity;
import fr.argouges.persokbase.R;
import fr.argouges.persokbase.ScrollingActivity;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private DownloadImageTask downloadBitmap;
    String[] GrillePhotoBit = new String[]{
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6060",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6073",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6058",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6062",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6055",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6059",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6057",
            "https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=6056"};
    String[] GrilleTextBit = new String[] {
            "Salle CDI",
            "Salle Design graphique",
            "Salle Louis Lumière",
            "Salle d’examen",
            "Poste informatique et automate",
            "Salle polyvalente",
            "Imprimante dans l’espace commun",
            "Poste informatique administration"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);

        if(MainActivity.DetecConnexion!=true && MainActivity.DetectConnexionTypeMobile !=false) {
            final Intent notificapps = new Intent().setClass(getContext(), NotificationActivity.class);
            notificapps.putExtra("ErrNote","Vous devez vous connecter à internet pour afficher ce contenu. " +
                    "Le wifi n'étant pas présent dans l'établissement, activez votre connexion mobile.");
            startActivity(notificapps);
        }

        GridView gridView = (GridView) root.findViewById(R.id.grid_gallery);
        int GrilleIconBit[] = {R.drawable.ic_baseline_photo, R.drawable.ic_baseline_photo,
                R.drawable.ic_baseline_photo, R.drawable.ic_baseline_photo,
                R.drawable.ic_baseline_photo, R.drawable.ic_baseline_photo,
                R.drawable.ic_baseline_photo, R.drawable.ic_baseline_photo};
        CustomAdapter customAdapter = new CustomAdapter(getContext(), GrilleIconBit);
        gridView.setAdapter(customAdapter);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        return root;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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
            view = inflter.inflate(R.layout.gallery_element, null); // inflate the layout
            ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
            icon.setImageResource(logos[i]); // set logo images
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(GrilleTextBit[i]);
            ImageView PhoGall01 = (ImageView) view.findViewById(R.id.photo);
            downloadBitmap = (DownloadImageTask) new DownloadImageTask(PhoGall01)
                    .execute(GrillePhotoBit[i]);
            return view;
        }
    }

}