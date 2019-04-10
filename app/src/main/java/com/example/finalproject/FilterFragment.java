package com.example.finalproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
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

    private Button filterBtn1, filterBtn2, filterBtn3, saveFilterPhotoBtn, cropBtn;
    private ImageView editPhoto;
    private EditText editToCaption;
    private Bitmap bitmap;
    private Uri photoUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container,false);

        filterBtn1 = (Button) view.findViewById(R.id.filter1); // light blue
        filterBtn2 = (Button) view.findViewById(R.id.filter2); // black n white
        filterBtn3 = (Button) view.findViewById(R.id.filter3); // red
        saveFilterPhotoBtn = (Button) view.findViewById(R.id.filterSaveBtn); // saved button
        cropBtn = (Button) view.findViewById(R.id.cropButton); // cropped button

        editToCaption = (EditText)view.findViewById(R.id.editCaption);
        editToCaption = (EditText)view.findViewById(R.id.editCaption);

        editPhoto = (ImageView) view.findViewById(R.id.photoFilter); // current photo being edited
        bitmap = ((MainPage)getActivity()).getTargetPhoto(); // getting bitmap from the MainPage that we saved from the HomeFragment
        editPhoto.setImageBitmap(bitmap); // setting imageView's bitmap to the one we saved in MainPage or after taking a photo
        editPhoto.setRotation(90); // Setting the imageView's rotation

        photoUri = getImageUri(this.getContext(), bitmap);

        // Functionality of filterBtn1: Changes the photo to light blue
        filterBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changing the color of a photo
                // editPhoto is an ImageView
                editPhoto.getDrawable().setColorFilter(0x763c3686, PorterDuff.Mode.DARKEN );

                // Since I changed the editPhoto, I have to also update its bitmap and save it and use it later
                bitmap = Bitmap.createBitmap(editPhoto.getWidth(),editPhoto.getHeight(), Bitmap.Config.ARGB_8888);


               // The width and height of the photo ImageView
                int bitW = bitmap.getWidth();
                int bitH = bitmap.getHeight();
                String bWidth = Integer.toString(bitW);
                String bHeight = Integer.toString(bitH);

                // I Do not need the two lines below to display it,
                // However, I need them to display in the gallery or apply changes to the bitmap
                Canvas canvas = new Canvas(bitmap);
                editPhoto.draw(canvas);

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(1);
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);

                String myTxt = editToCaption.getText().toString();
                float txtWidth = paint.measureText(myTxt);
                float x = bitW/2 - txtWidth/2;
                float y = bitH/2 - 6;
                canvas.drawText(myTxt, x, y, paint);

                editPhoto.setImageBitmap(bitmap);

                // After changes on the bitmap, we need to update the photoURI for cropping purposes
                photoUri = getImageUri(getContext(), bitmap);

                // The width of the photo is: 774
                // The height of the photo is: 1137
                Toast.makeText(getActivity(), "Photo width: " + bWidth + " " + "Photo height: " + bHeight, Toast.LENGTH_LONG).show();
            }
        });

        // Functionality of filterBtn2: Changes the photo to black and white
        filterBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Black and white filter
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                editPhoto.setColorFilter(filter);
                bitmap = Bitmap.createBitmap(editPhoto.getWidth(),editPhoto.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                editPhoto.draw(canvas);
            }
        });

        // Functionality of filterBtn3: Changes the photo to red
        filterBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhoto.getDrawable().setColorFilter(0x76f70000, PorterDuff.Mode.DARKEN );
                bitmap = Bitmap.createBitmap(editPhoto.getWidth(),editPhoto.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                editPhoto.draw(canvas);
            }
        });

        // Opens a crop activity to crop the photo
        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropRequest(photoUri);
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
                // After saving the photo, return to the HomeFragment
                getFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();
                // After photo is saved, display a toast
                toastSavePhoto();

            }
        });
        return view;
    }


    // Creating the file of the photo
    private File preparePhotoFile() {
        String pattern = "yyyy-MM-dd"; // name of the photo file
        String name = new SimpleDateFormat(pattern).format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); // get file path to pictures on mobile
        File image = null;
        try {
            // If image throws an exception
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            // print out the exception
            Log.d("myLog", "Excep: " + e.toString());
        }

        return image;
    }

        public void saveBitmapToDevice(Bitmap bitmap){
            //String root = Environment.getExternalStorageDirectory().toString();
            File myDir = preparePhotoFile(); // give myDir a file
            myDir.mkdirs();
            //Random generator = new Random();
            //int n = 10000;
            //n = generator.nextInt(n);
            //String fname = "Image-" + n + ".jpg";
            //File file = new File(myDir, fname);
            //Log.i(TAG, "" + file);

            // If the file exists, delete it
            if (myDir.exists())
                myDir.delete();
            try {
                // Create a file output stream that writes to a file with a specific name
                FileOutputStream out = new FileOutputStream(myDir);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                // After writing to a file, force any buffered output bytes to be written out
                out.flush();
                // close file output stream aka out
                out.close();
            } catch (Exception e) {
                // print exception
                e.printStackTrace();
            }

        }

    void toastSavePhoto(){
        // After an image has been successfully saved
        Toast.makeText(getActivity(), "Photo saved", Toast.LENGTH_LONG).show();
    }

    private void cropRequest(Uri imageUri){
        // Load up the Crop activity
        CropImage.activity(imageUri).start(this.getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result from cropping activity
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                try{
                    //editPhoto.setImageBitmap(bitmap);
                    //photoUri = result.getUri(); <--- The original
                    photoUri = CropImage.getPickImageResultUri(this.getContext(),data); //<-- The new one
                    editPhoto.setImageURI(result.getUri());

                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),photoUri);
                    //editPhoto.setImageBitmap(bitmap);

                    //Toast.makeText(getActivity(), "Photo Cropped", Toast.LENGTH_LONG).show();
                    //Log.d("CROPPED PHOTO", "PHOTO CROPPPPPPPED");

                    // I believe I might have to save the photo in the MainPage
                    // Then bring it back to the filter page
                    // This will involved in getting the bitmap from a Uri
                    // and putting it in the imageView
                    // I might have to use bundle or intent as well.

                    //((MainPage)getActivity()).savePhoto();
                }
                catch(Exception e){
                    // print the exception
                    e.printStackTrace();
                }
            }
        }
    }

    // convert bitmap to URI
    private Uri getImageUri(Context context, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // Combine two bitmaps: imageView and editTextView
    // the background is the photo and the foreground is the text
    // Delete whenever, was one of the method to combined an editText bitmap and imageview together
    public Bitmap combineImages(Bitmap background, Bitmap foreground){
        Bitmap cs;

        int width = this.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = this.getActivity().getWindowManager().getDefaultDisplay().getHeight();

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height,true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground,0,0,null);

        return cs;
    }

    // Delete whenever, was one of the method to combined an editText bitmap and imageview together
    public static byte[] mergeImages(Bitmap baseImage, Bitmap captionImg){

        Bitmap finalImage = Bitmap.createBitmap(baseImage.getWidth(), baseImage.getHeight(), baseImage.getConfig());
        Canvas canvas = new Canvas(finalImage);
        canvas.drawBitmap(baseImage, new Matrix(), null);

        if(captionImg != null){
            canvas.drawBitmap(captionImg, new Matrix(), null);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        finalImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return bytes;
    }

    public void writeText(){

    }


}
