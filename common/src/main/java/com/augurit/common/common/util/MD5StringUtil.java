package com.augurit.common.common.util;

import java.security.MessageDigest;

public class MD5StringUtil
{
//    private static final MessageDigest digest;
//
//    static
//    {
//        try
//        {
//            digest = MessageDigest.getInstance("MD5");
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String md5StringFor(String s)
//    {
//         byte[] hash = null;
//        final StringBuilder builder = new StringBuilder();
//        try {
//            hash = digest.digest(s.getBytes("UTF-8"));
//            for (byte b : hash)
//            {
//                builder.append(Integer.toString(b & 0xFF, 16));
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return builder.toString();
//    }
//
    public static void main(String[] args) {
        // 测试

//        String flag = md5StringFor("36y2SII7");
//        System.out.println(flag);
        System.out.println(MD5Encode("36y2SII7","utf8"));

    }

    /**
     * @Author:Starry
     * @Description:
     * @Date:Created in 9:46 2018/4/13
     * Modified By:
     */

        private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

        /**
         * MD5加密
         * @param origin 字符
         * @param charsetname 编码
         * @return
         */
        public static String MD5Encode(String origin, String charsetname){
            String resultString = null;
            try{
                resultString = new String(origin);
                MessageDigest md = MessageDigest.getInstance("MD5");
                if(null == charsetname || "".equals(charsetname)){
                    resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
                }else{
                    resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
                }
            }catch (Exception e){
            }
            return resultString;
        }


        public static String byteArrayToHexString(byte b[]){
            StringBuffer resultSb = new StringBuffer();
            for(int i = 0; i < b.length; i++){
                resultSb.append(byteToHexString(b[i]));
            }
            return resultSb.toString();
        }

        public static String byteToHexString(byte b){
            int n = b;
            if(n < 0){
                n += 256;
            }
            int d1 = n / 16;
            int d2 = n % 16;
            return hexDigIts[d1] + hexDigIts[d2];
        }

}
