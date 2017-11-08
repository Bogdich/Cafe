package com.bogdevich.cafe.command.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {

    private LocalizationManager() {
    }

    /**
     * Return value from bundle by its key
     * @param baseName {@code ResourceBundle} name
     * @param languageTag Example: "en_EN", "ru_RU"
     * @param key for a value
     * @return {@code String} value
     */
    public static String getMessage(String baseName, String languageTag, String key) {
        return ResourceBundle
                .getBundle(baseName, Locale.forLanguageTag(languageTag))
                .getString(key);
    }
}
