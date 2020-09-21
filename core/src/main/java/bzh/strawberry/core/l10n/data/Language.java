package bzh.strawberry.core.l10n.data;

import org.json.simple.JSONObject;

/*
 * This file Language is part of a project StrawAPI.core.
 * It was created on 21/09/2020 10:56 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */


public class Language {

    private String lang;
    private JSONObject translates;

    public Language(String lang, JSONObject translates) {
        this.lang = lang;
        this.translates = translates;
    }

    public String getLang() {
        return lang;
    }

    public void addTranslation(JSONObject translates) {
        this.translates.putAll(translates);
    }

    public JSONObject getTranslations() {
        return translates;
    }

    public String getTranslate(String key) {
        return this.translates.get(key).toString();
    }

}
