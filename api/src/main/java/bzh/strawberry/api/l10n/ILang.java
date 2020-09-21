package bzh.strawberry.api.l10n;

import java.util.UUID;

/*
 * This file ILang is part of a project StrawAPI.api.
 * It was created on 21/09/2020 10:55 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */

public interface ILang {

    String getTranslation(UUID uuid, String key);

}