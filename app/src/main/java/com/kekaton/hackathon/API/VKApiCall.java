package com.kekaton.hackathon.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.kekaton.hackathon.MainActivity;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKPhotoArray;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 17.11.2016.
 */

public class VKApiCall {
    Context context;

    public VKApiCall(Context context) {
        this.context = context;
    }

    public void getUser(VKRequest.VKRequestListener mRequestListener){

        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
                        "photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
                        "online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
                        "universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
                        "status,last_seen,common_count,relation,relatives,counters"));
        request.secure = false;
        request.useSystemLanguage = false;

        request.executeWithListener(mRequestListener);
    }

    public void getPhotos(VKRequest.VKRequestListener mRequestListener){
        SharedPreferences sPref = context.getSharedPreferences("mysettings", MODE_PRIVATE);
        int id = sPref.getInt("id", 0);
        Log.d("VK", id + "");
        VKRequest request = new VKRequest("photos.getAll", VKParameters.from(VKApiConst.COUNT, 30, VKApiConst.OWNER_ID, id));

        request.executeWithListener(mRequestListener);
    }

    public void getChallengeData(VKRequest.VKRequestListener mRequestListener, int id) {
        VKRequest request = new VKRequest("users.get", VKParameters.from(VKApiConst.USER_ID, String.valueOf(id), VKApiConst.FIELDS,
                "first_name, last_name, photo_200"));

        request.executeWithListener(mRequestListener);

    }
}
