package com.sivaram.session19assignment2.network;

import com.sivaram.session19assignment2.CommonUtilities;

/**
 * Created by User on 03/11/2017.
 */

// Create An Interface and which will be implemented from MainActivity to retrieve webresponse
public interface OnWebServiceResult {
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type);

}
