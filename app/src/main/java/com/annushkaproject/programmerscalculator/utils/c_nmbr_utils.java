package com.annushkaproject.programmerscalculator.utils;

public class c_nmbr_utils {
    //    https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private static final char[] heks_u5_char_array = "0123456789ABCDEF".toCharArray();
    private static final char[] heks_u5h_char_array = "0123456789LJQWXF".toCharArray();
    private static final char[] heks_u8_char_array = "0123456789:;<=>?".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = heks_u5_char_array[v >>> 4];
            hexChars[j * 2 + 1] = heks_u5_char_array[v & 0x0F];
        }
        return new String(hexChars);
    }
//    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
//    public static String bytesToHex(byte[] bytes) {
//        byte[] hexChars = new byte[bytes.length * 2];
//        for (int j = 0; j < bytes.length; j++) {
//            int v = bytes[j] & 0xFF;
//            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
//            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
//        }
//        return new String(hexChars, StandardCharsets.UTF_8);
//    }

    public static final String[] dizit_names_array = {"ziro","vn","tuu","Three","four","five","siks","seven","et","nine","ten","zilevn","kvAlv","dblyu","Aksen","phen"} ;
    public static final String[] ples_value_sphiks = {"","ti","so","to","zo","lo","mo","ko","po","bo","ro","do","co","go"} ;
    public static String heks_nmbr_string_to_vrdsstring(String heks_nmbr_string) {
        String nmbr_in_vrds = ""; char c; final int nmbr_string_length = heks_nmbr_string.length() ; int cur_dizit=-1;
        for (int i = 0; i < nmbr_string_length ; i++){
            c = heks_nmbr_string.charAt(i); cur_dizit = Character.digit(c,16) ;
            if(cur_dizit > 0) {
                nmbr_in_vrds = nmbr_in_vrds + dizit_names_array[cur_dizit] + ples_value_sphiks[nmbr_string_length - i - 1];
                if (i < nmbr_string_length-1) { nmbr_in_vrds = nmbr_in_vrds + " " ; }
            }
        };
        return nmbr_in_vrds ;
    }
}
