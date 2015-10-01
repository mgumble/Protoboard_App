package com.example.emilie.practiceapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class UploadFile extends AsyncTask {

    private DropboxAPI dropboxApi;
    private String path;
    private Context context;

    public UploadFile(Context context, DropboxAPI dropboxApi,
                      String path) {
        this.context = context.getApplicationContext();
        this.dropboxApi = dropboxApi;
        this.path = path;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        final File tempDropboxDirectory = context.getCacheDir();
        File tempFileToUploadToDropbox;
        FileWriter fileWriter = null;

        try {
            // Creating a temporal file.
            tempFileToUploadToDropbox = File.createTempFile("file", ".txt", tempDropboxDirectory);
            fileWriter = new FileWriter(tempFileToUploadToDropbox);
            fileWriter.write("Hello World drom Android Dropbox Implementation Example!");
            fileWriter.close();

            // Uploading the newly created file to Dropbox.
            FileInputStream fileInputStream = new FileInputStream(tempFileToUploadToDropbox);
            dropboxApi.putFile(path + "hello_world.txt", fileInputStream,
                    tempFileToUploadToDropbox.length(), null, null);
            tempFileToUploadToDropbox.delete();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        return false;
    }


    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "File has been successfully uploaded!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "An error occured while processing the upload request.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
