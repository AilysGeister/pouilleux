package pouilleux.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropretiesReader {

    private static final String BUNDLE_NAME = "pouilleux.strings";

    @SuppressWarnings("deprecation")
	public static String getString(String key) {
        String language;

        //We get the language from the settings controller:
        try {
            language = Settings.getLanguage();
        } catch (IOException e) {
            language = "en"; //If the controller can't read the language it set english by default.
        }

        //Once we got the language we search the key in the corresponding file:
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
