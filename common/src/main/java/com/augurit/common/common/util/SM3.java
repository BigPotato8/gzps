package com.augurit.common.common.util;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs.util
 * @createTime 创建时间 ：2019-11-22
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-11-22
 * @modifyMemo 修改备注：
 */

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

public class SM3 {

    private static final String ENCODING = "UTF-8";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * SM3加密
     *
     * @param paramStr
     *          待加密字符串
     * @return 返回加密后，固定长度=32的16进制字符串
     */
    public static String encrypt(String paramStr) {
        // 将返回的hash值转换为16进制字符串
        String resultHexString = "";
        try {//5ded7db607f914be2e617d7751146841
            // 将字符串转换成byte数组
            byte[] srcData = paramStr.getBytes(ENCODING);//src://53,100,101,100,55,100,98,54,55,102,
//            byte[] srcData1 = paramStr.getBytes();
//
//            byte[] srcData = new byte[]{1,2,3,44,56,74,65,56,85,74,7,9,6,2,6,90,1,2,3,44,56,74,65,56,85,74,7,9,6,2,6,90};
            byte[] resultHash = hash(srcData);//res 64,-103,-75,120,-11,44,105,-109
            // 将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);//4099b578f52c69935012ef583e6d9624c976bdaea02ff50d222d9e490fe6a039
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultHexString;
    }

    /**
     * 返回长度为32的byte数组 生成对应的hash值
     *
     * @param srcData
     * @return
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 通过指定密钥进行加密
     *
     * @param key
     *          密钥
     * @param srcData
     *          被加密的byte数组
     * @return
     */
    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 判断数据源与加密数据是否一致，通过验证原数组和生成是hash数组是否为同一数组，验证二者是否为同一数据
     *
     * @param srcStr
     * @param sm3HexString
     * @return
     */
    public static boolean vertify(String srcStr, String sm3HexString) {
        boolean flag = false;
        try {
            byte[] srcData = srcStr.getBytes(ENCODING);
            byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
            byte[] newHash = hash(srcData);
            if (Arrays.equals(newHash, sm3Hash))
                ;
            flag = true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        // 测试
        String str = "5ded7db607f914be2e617d7751146841";
        String hex = SM3.encrypt(str);
        System.out.println(hex);
        // 验证加密后的16进制字符串与加密前的字符串是否相同
        boolean flag = SM3.vertify(str, hex);
        System.out.println(flag);

    }
}

