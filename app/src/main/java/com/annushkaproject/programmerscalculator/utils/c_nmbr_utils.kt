package com.annushkaproject.programmerscalculator.utils;

public class c_nmbr_utils {
    public static final String[] dizit_names_array = {"ziro","vn","tuu","Three","four","five","siks","seven","et","nine","ten","zilevn","kvAlv","dblyu","Aksen","phen"} ;
    public static final String[] ples_value_sphiks = {"","ti","so","to","zo","lo","mo","ko","po","bo","ro","do","co","go"} ;
    public static String heks_nmbr_string_to_vrdsstring(String heks_nmbr_string) {
        String nmbr_in_vrds = ""; char c; final int nmbr_string_length = heks_nmbr_string.length() ;
        for (int i = 0; i < nmbr_string_length ; i++){
            c = heks_nmbr_string.charAt(i);
            nmbr_in_vrds = nmbr_in_vrds + dizit_names_array[Character.digit(c,16)] + ples_value_sphiks[nmbr_string_length-i-1] ;
            if (i < nmbr_string_length-1) { nmbr_in_vrds = nmbr_in_vrds + " " ; }
        };
        return nmbr_in_vrds ;
    }
}