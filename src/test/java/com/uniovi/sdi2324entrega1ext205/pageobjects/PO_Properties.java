package com.uniovi.sdi2324entrega1ext205.pageobjects;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class PO_Properties {
    public static int getSPANISH() {
        return SPANISH;
    }

    public static int getENGLISH() {
        return ENGLISH;
    }

    public static int getFRENCH() {
        return ENGLISH;
    }

    public static Locale[] getIDIOMS() {

        return idioms;
    }

    static private String Path;
    static final int SPANISH = 0;
    static final int ENGLISH = 1;
    static final int FRENCH = 2;
    static final Locale[] idioms = new Locale[] {new Locale("ES"), new Locale("EN")
            , new Locale("FR")};
    static final Locale[] idiomsBtn = new Locale[] {new Locale("ES"), new Locale("EN")
            , new Locale("FR")};
    public PO_Properties(String Path) {
        PO_Properties.Path = Path;
    }
    public String getString(String prop, int locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(Path, idioms[locale]);
        String value = bundle.getString(prop);
        String result;
        result = new String(value.getBytes(StandardCharsets.UTF_8),  StandardCharsets.UTF_8);
        return result;
    }
}
