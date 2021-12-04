package com.aj.pehliyan;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataProvider {

    private Context context;
    private ArrayList<Pahli> pehlis = new ArrayList<>();

    int[] filePath = Categories.getJsonFilePath();
    int pos;

    public DataProvider(Context context, int pos) {
        this.context = context;
        this.pos = pos;
        try {
            createJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readDataFromFile() {
        InputStream inputStream = context.getResources().openRawResource(filePath[pos]);
        StringBuilder sb = new StringBuilder();
        try {
            String read = null;
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while ((read = bf.readLine()) != null) {
                sb.append(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(sb);
    }

    private void createJson() throws JSONException {
        String jS = readDataFromFile();

        JSONArray jsonArray = new JSONArray(jS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            pehlis.add(new Pahli(jsonObject.getInt("Num"), jsonObject.getString("Question"), jsonObject.getString("Answer")));
        }

    }

    public ArrayList<Pahli> getPehlis() {
        return pehlis;
    }
}
