package com.kekaton.hackathon.Activity;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;

        import com.kekaton.hackathon.API.VKApiCall;
        import com.kekaton.hackathon.Adapters.ProfilePhotosAdapter;
        import com.kekaton.hackathon.Model.Photo;
        import com.kekaton.hackathon.R;
        import com.vk.sdk.api.VKError;
        import com.vk.sdk.api.VKRequest;
        import com.vk.sdk.api.VKResponse;

        import org.json.JSONArray;
        import org.json.JSONException;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.Bind;
        import butterknife.ButterKnife;

/**
 * Created by User on 17.11.2016.
 */

public class GalleryActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    List<Photo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_challenge_view_deprecated);
        ButterKnife.bind(this);

        mToolbar.setTitle("Мои");
        VKApiCall vkApi = new VKApiCall(this);

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);

        list = new ArrayList<>();
        vkApi.getPhotos(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d("Tag", response.json.toString());
                try {
                    JSONArray result = response.json.getJSONObject("response").optJSONArray("items");
                    for (int i = 0; i < result.length(); i++) {
                        list.add(new Photo((Integer) result.getJSONObject(i).get("id"), result.getJSONObject(i).get("photo_604").toString()));
                    }

                    Log.d("Tag", list.toString());
                    ProfilePhotosAdapter adapter = new ProfilePhotosAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }

            @Override
            public void onError(VKError error) {
                Log.d("TAG", error.toString());
            }
        });

    }
}