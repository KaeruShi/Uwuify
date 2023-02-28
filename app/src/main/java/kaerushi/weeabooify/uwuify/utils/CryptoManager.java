package kaerushi.weeabooify.uwuify.utils;

import android.annotation.SuppressLint;

import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kaerushi.weeabooify.uwuify.BuildConfig;

public class CryptoManager {

    private static byte[] getKey() {
        return BuildConfig.DECRYPTION_KEY;
    }

    private static byte[] getIvKey() {
        return BuildConfig.IV_KEY;
    }

    private static void decrypt(String file) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.replace(".enc", ""));

        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secret = new SecretKeySpec(getKey(), "AES");
        IvParameterSpec iv = new IvParameterSpec(getIvKey());
        cipher.init(Cipher.DECRYPT_MODE, secret, iv);
        CipherInputStream cis = new CipherInputStream(fis, cipher);

        int b;
        byte[] d = new byte[8];
        while ((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }

        fos.flush();
        fos.close();
        cis.close();
    }

    public static void decryptFileRecursively(File pFile) {
        for (File file : Objects.requireNonNull(pFile.listFiles())) {
            if (file.isDirectory()) {
                decryptFileRecursively(file);
            } else {
                try {
                    CryptoManager.decrypt(file.getAbsolutePath());
                    Shell.cmd("rm -f " + file.getAbsolutePath()).exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
