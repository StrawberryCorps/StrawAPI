package bzh.strawberry.api;

/*
This file StrawAPI is part of a project StrawAPI.
It was created on 15/06/2020 at 22:34 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public abstract class StrawAPI {

    private static StrawAPI API;

    public StrawAPI(){ API = this;}

    public static StrawAPI getAPI(){ return API;}

}
