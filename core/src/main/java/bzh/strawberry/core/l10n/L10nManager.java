package bzh.strawberry.core.l10n;

import bzh.strawberry.core.l10n.data.Language;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * This file L10nManager is part of a project StrawAPI.core.
 * It was created on 21/09/2020 10:56 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class L10nManager {

    private List<Language> languages;

    public L10nManager() {
        this.languages = new ArrayList<>();
    }

    public void addLanguage(String lang, JSONObject translate) {
        if (languages.stream().filter(language1 -> language1.getLang().equals(lang)).findFirst().orElse(null) != null) {
            languages.stream().filter(language1 -> language1.getLang().equals(lang)).findFirst().get().addTranslation(translate);
        } else {
            languages.add(new Language(lang, translate));
            System.out.println("Ajout new " + lang + " / " + translate.toString());
        }
    }

    public Language getLanguage(String lang) {
        return this.languages.stream().filter(language -> language.getLang().equals(lang)).findFirst().orElse(null);
    }

}
