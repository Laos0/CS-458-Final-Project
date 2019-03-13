package com.example.finalproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class FilterFragment extends Fragment {

    private Button filterBtn1, filterBtn2, filterBtn3, saveFilterPhotoBtn;
    private ImageView editPhoto;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container,false);

        filterBtn1 = (Button) view.findViewById(R.id.filter1);
        filterBtn2 = (Button) view.findViewById(R.id.filter2);
        filterBtn3 = (Button) view.findViewById(R.id.filter3);
        saveFilterPhotoBtn = (Button) view.findViewById(R.id.filterSaveBtn);

        editPhoto = (ImageView) view.findViewById(R.id.photoFilter);
        bitmap = ((MainPage)getActivity()).getTargetPhoto();
        editPhoto.setImageBitmap(bitmap);
        editPhoto.setRotation(90);


        filterBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //filterBtn1.setText("GAGA");
                editPhoto.getDrawable().setColorFilter(0x763c3686, PorterDuff.Mode.DARKEN );
            }
        });

        filterBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Black and white filter
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                editPhoto.setColorFilter(filter);
            }
        });

        filterBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhoto.getDrawable().setColorFilter(0x76f70000, PorterDuff.Mode.DARKEN );
                bitmap = Bitmap.createBitmap(editPhoto.getWidth(),editPhoto.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                editPhoto.draw(canvas);
            }
        });

        saveFilterPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //editPhoto.setRotation(270);

                //editPhoto.invalidate();
                //BitmapDrawable drawable = (BitmapDrawable) editPhoto.getDrawable();
                //bitmap = drawable.getBitmap();
                saveBitmapToDevice(bitmap);
                getFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();

                // TODO: Change tagetPhoto in MainPage to null or clear it out
                // TODO: Do a Toast: that shows that a photo has been saved
                toastSavePhoto();

            }
        });

        return view;
    }


    // Creating the file of the photo
    private File preparePhotoFile() {
        String pattern = "yyyy-MM-dd";
        String name = new SimpleDateFormat(pattern).format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("myLog", "Excep: " + e.toString());
        }

        return image;
    }

        public void saveBitmapToDevice(Bitmap bitmap){
            //String root = Environment.getExternalStorageDirectory().toString();
            File myDir = preparePhotoFile();
            myDir.mkdirs();
            //Random generator = new Random();
            //int n = 10000;
            //n = generator.nextInt(n);
            //String fname = "Image-" + n + ".jpg";
            //File file = new File(myDir, fname);
            //Log.i(TAG, "" + file);
            if (myDir.exists())
                myDir.delete();
            try {
                FileOutputStream out = new FileOutputStream(myDir);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    void toastSavePhoto(){
        Toast.makeText(getActivity(), "Photo saved", Toast.LENGTH_LONG).show();
    }
}
