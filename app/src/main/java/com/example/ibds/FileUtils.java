package com.example.ibds;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {

    public static File getOutputImageFile(String folder)
    {

        File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/IBDS/"+folder);

        if (!captureImagesStorageDir.exists())
        {
            if (!captureImagesStorageDir.mkdirs())
            {
                Log.d("Unilever", "Oops! Failed create ");
                return null;
            }
        }

        String timestamp = System.currentTimeMillis()+"";
        File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
                + "CAPTURE_" + timestamp + ".jpg");


        return imageFile;
    }
}
