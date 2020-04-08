package gst.trainingcourse.manylanguage.lib_Helper;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.model.LinkVideo;

public class ReadJsonFile {

    public static ArrayList<LinkVideo> readLinkVideoJson(Context context) throws IOException, JSONException {
        String jsonText = readText(context, R.raw.test);

        JSONArray jsonRoot = new JSONArray(jsonText);
        ArrayList<LinkVideo> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonRoot.length(); i++) {
            JSONObject jsonObject = jsonRoot.getJSONObject(i);
            String name = jsonObject.getString("ten");
            JSONArray jsonArray = jsonObject.getJSONArray("link");
            String[] link = new String[jsonArray.length()];
            for (int j = 0; j < jsonArray.length(); j++) {
                link[j] = jsonArray.getString(j);
            }
            arrayList.add(new LinkVideo(name, link));
        }

        return arrayList;
    }

    private static String readText(Context context, int file) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        return stringBuilder.toString();
    }
}
