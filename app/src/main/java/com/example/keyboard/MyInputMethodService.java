package com.example.keyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static com.example.keyboard.R.xml.number_pad2;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static final int pic_id = 123;
    EditText editText;
    @Override
    public View onCreateInputView() {
        // get the KeyboardView and add our Keyboard layout to it
        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.number_pad);
        Keyboard keyboard2 = new Keyboard(this, R.xml.number_pad2);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }




    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                CharSequence selectedText = ic.getSelectedText(0);
                if (TextUtils.isEmpty(selectedText)) {
                    // no selection, so delete previous character
                    ic.deleteSurroundingText(1, 0);
                } else {
                    // delete the selection
                    ic.commitText("", 1);
                }
                break;
            case 53:
                Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                break;
            case 55:
                NfcManager manager = (NfcManager) getSystemService(Context.NFC_SERVICE);
                NfcAdapter adapter = manager.getDefaultAdapter();
                if (adapter != null && adapter.isEnabled()) {
                    Toast.makeText(MyInputMethodService.this, "NFC włączone!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyInputMethodService.this, "NFC wyłączone!", Toast.LENGTH_LONG).show();
                }
                break;
            case 50:
                final MediaPlayer sawSound = MediaPlayer.create(this, R.raw.playagame);
                sawSound.start();
                break;
            case 51:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
                break;
            case 56:
                NfcManager manager2 = (NfcManager) getSystemService(Context.NFC_SERVICE);
                NfcAdapter adapter2 = manager2.getDefaultAdapter();
                if (adapter2 != null && adapter2.isEnabled()) {
                    Toast.makeText(MyInputMethodService.this, "NFC włączone!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyInputMethodService.this, "NFC wyłączone!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }

                break;
            case 52:
                Context context = getApplicationContext();
                File path = context.getExternalFilesDir(null);
                File file = new File(path, "Zadanie 4.txt");
                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    try {
                        stream.write("Próba pierwsza! Działa czy nie działą?".getBytes());
                        Toast.makeText(this, "Plik zapisano w " + getFilesDir() + "/",
                                Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 49:
                ic.commitText("I am a custom Keyboard!",0);
                break;
            case 54:
                Keyboard keyboard2 = new Keyboard(this, R.xml.number_pad2);

                break;

            case 57:
                Intent i;
                PackageManager manager3 = getPackageManager();
                try {
                    i = manager3.getLaunchIntentForPackage("pl.otomoto");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {

                }
                break;
            case 48:
                Intent facebookIntent = openWebsite(MyInputMethodService.this);
                startActivity(facebookIntent);
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
        }
    }

    public static Intent openWebsite(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.opera.browser", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.sport.pl"));
        } catch (Exception e){

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.wp.pl"));
        }


    }

    @Override
    public void onPress(int primaryCode) { }

    @Override
    public void onRelease(int primaryCode) { }

    @Override
    public void onText(CharSequence text) { }

    @Override
    public void swipeLeft() { }

    @Override
    public void swipeRight() { }

    @Override
    public void swipeDown() { }

    @Override
    public void swipeUp() { }



}