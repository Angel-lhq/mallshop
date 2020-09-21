package com.example.mallshop.common;


import com.example.mallshop.app.MyApp;

import java.io.File;

public interface Contant {

    //网络缓存的地址
    public static final String PATH_DATA = MyApp.context.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/mallshop";
    public static final int DATA_SUCCESS = 200;
}
