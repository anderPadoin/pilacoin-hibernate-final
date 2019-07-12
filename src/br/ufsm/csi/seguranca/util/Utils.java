package br.ufsm.csi.seguranca.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static byte[] serializa(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oops = new ObjectOutputStream(byteArrayOutputStream);
        oops.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    public static Serializable deserializa(byte[] objetoSerializado) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objetoSerializado);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Serializable) objectInputStream.readObject();
    }

    public static byte[] hashObject(byte[] byteArray, String algoritmo) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
        return messageDigest.digest(byteArray);
    }

    public static byte[] decriptografar(String algoritmo, Key chave, byte[] byteArray) throws Exception {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.DECRYPT_MODE, chave);
        return cipher.doFinal(byteArray);
    }

    public static byte[] criptografar(String algoritmo, Key chave, byte[] byteArray) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        return cipher.doFinal(byteArray);
    }



}
