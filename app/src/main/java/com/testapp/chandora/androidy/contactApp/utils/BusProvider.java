package com.testapp.chandora.androidy.contactApp.utils;

import com.squareup.otto.Bus;

/**
 * Created by chandora on 25-May-2019
 */
public class BusProvider {

    private static Bus BUS;

    public static Bus getInstance() {
        synchronized (BusProvider.class) {
            if(BUS == null){
                BUS = new Bus();
            }
        }
        return BUS;
    }

    private BusProvider(){
        //prevent direct instantiation
    }
}
