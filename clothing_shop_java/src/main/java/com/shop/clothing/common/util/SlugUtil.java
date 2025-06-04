package com.shop.clothing.common.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {
    public String slugify(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
    }
}
