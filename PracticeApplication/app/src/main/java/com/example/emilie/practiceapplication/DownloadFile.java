package com.example.emilie.practiceapplication;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxInputStream;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mose on 10/7/2015.
 */

public class DownloadFile {

    private DropboxAPI dropboxApi;
    private String path;

    public DownloadFile( DropboxAPI dropboxApi   ,
                      String path) {
        this.dropboxApi = dropboxApi;
        this.path = path;
    }

    /*public String execute () {

        try {
            File file = new File(path);
            FileOutputStream outputStream = new FileOutputStream(file);
            DropboxAPI.DropboxFileInfo info = dropboxApi.getFile(path, null, outputStream, null);
            FileDescriptor fd = outputStream.getFD();
            Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
            return  fd.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        return "";
    }*/
    public boolean downloadDropboxFile(String clickedFile) {// , String
        // localFilePath)
        // {
        Entry fileSelected = null;

        Entry directory;
        try {
            directory = dropboxApi.metadata(path, 1000, null, true, null);
            for (Entry file : directory.contents) {
                if (file.fileName() == clickedFile) {
                    fileSelected = file;
                }
            }
        } catch (DropboxException e) {
            e.printStackTrace();
        }

        if (fileSelected == null) return false;

        File dir = new File(Utils.getPath());
        if (!dir.exists())
            dir.mkdirs();
        try {
            File localFile = new File(dir + "/" + fileSelected.fileName());
            if (!localFile.exists()) {
                localFile.createNewFile();
                copy(fileSelected, localFile);

            }// else {
                //showFileExitsDialog(fileSelected, localFile);
           // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void copy(final Entry fileSelected, final File localFile) {
        //final ProgressDialog pd = ProgressDialog.show(DownloadFile.this,
                //"Downloading...", "Please wait...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedInputStream br = null;
                BufferedOutputStream bw = null;
                DropboxInputStream fd;
                try {
                    fd = dropboxApi.getFileStream(fileSelected.path,
                            localFile.getPath());
                    br = new BufferedInputStream(fd);
                    bw = new BufferedOutputStream(new FileOutputStream(
                            localFile));

                    byte[] buffer = new byte[4096];
                    int read;
                    while (true) {
                        read = br.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        bw.write(buffer, 0, read);
                    }
                    //pd.dismiss();
                    //Message message = new Message();
                    //message.obj = localFile.getAbsolutePath();
                    //message.what = 1;
                    //mHandler.sendMessage(message);
                } catch (DropboxException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();

    }

}
