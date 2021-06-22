package com.kpi.web.systems.lab2.statics;

import com.kpi.web.systems.lab2.model.enums.Language;

public class StaticFieldsUtils {

    public static String getProceedText(Language language) {
        return language == Language.UA
                ? "Перейти"
                : "Open";
    }

    public static String getHeaderText(Language language) {
        return language == Language.UA
                ? "CMS Ядро"
                : "CMS Core";
    }

    public static String getFooterSignText(Language language) {
        return language == Language.UA
                ? "Студент групи ІП-03мн, Прохницький Федір"
                : "Prokhnitskij Fedir, student of IP-03mn group";
    }

    public static String getFooterCopyrightsText(Language language) {
        return language == Language.UA
                ? "(c) Всі права захищено"
                : "(c) All rights reserved.";
    }

    public static String getOpenText(Language language) {
        return language == Language.UA
                ? "Перейти в ->"
                : "Open ->";
    }

    public static String getOpenTextShortened(Language language) {
        return language == Language.UA
                ? "Перейти"
                : "Watch";
    }
}
