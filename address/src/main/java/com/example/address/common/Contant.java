package com.example.address.common;


import com.example.address.app.MyApp;

import java.io.File;

public interface Contant {

    //网络缓存的地址
    public static final String PATH_DATA = MyApp.context.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/mallshopaddress";
    public static final int DATA_SUCCESS = 200;
}
