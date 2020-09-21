package bzh.strawberry.core.l10n;

import bzh.strawberry.api.l10n.ILang;
import bzh.strawberry.core.StrawCore;

import java.util.UUID;

/*
 * This file Lang is part of a project StrawAPI.core.
 * It was created on 21/09/2020 10:56 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public class Lang implements ILang {

    @Override
    public String getTranslation(UUID uuid, String key) {
//        if (StrawCore.getInstance().getStrawPlayer(uuid) == null)
//            return "Error occured";

        if (StrawCore.getInstance().getStrawPlayer(uuid).getLang() == null)
            return "Error occured";

        return StrawCore.getInstance().getStrawPlayer(uuid).getLang().getTranslate(key);
    }
}
