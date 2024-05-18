package com.coldlake.app.payment.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

@Slf4j
public class HashUtils {
    //计算数据的md5值
    public static String md5(String original) {
        MessageDigest md = DigestUtils.getDigest("MD5");
        String charsetName = "utf-8";
        try {
            byte[] digest = md.digest((original).getBytes(charsetName));
            return Hex.encodeHexString(digest);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding Charset:{}", charsetName, e);
            return null;
        }
    }

    //计算数据的SHA1值
    public static String encryptSHA1(byte[] msg) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(msg);
            return DatatypeConverter.printHexBinary(sha1.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("encryptSHA1 err, error:{}", e.getMessage());
            return "";
        }
    }

    public static String encryptSHA1(String msg) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(msg.getBytes());
            return DatatypeConverter.printHexBinary(sha1.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("encryptSHA1 err, error:{}", e.getMessage());
            return "";
        }
    }

    //字符串转换为CRC32整数
    public static Long stringToCrc32(String str) {
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
        return crc32.getValue();
    }
}
