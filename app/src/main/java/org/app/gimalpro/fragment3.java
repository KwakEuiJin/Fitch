package org.app.gimalpro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fragment3 extends Fragment {
    private View view;
    String search="추천";
    Button button;
    Button bt_auto,bt_muscle_vi,bt_fat_vi,bt_music;

    AsyncTask<?, ?, ?> searchTask;

    ArrayList<SearchData> sdata = new ArrayList<SearchData>();

    final String serverKey="AIzaSyBVYooHOM99Nzvf4fnLqAuV0Tp8Rs9v-dw";


    RecyclerView recyclerview;

    UtubeAdapter utubeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3,container,false);

        bt_auto=view.findViewById(R.id.bt_auto);
        bt_muscle_vi=view.findViewById(R.id.bt_muscle_vi);
        bt_fat_vi=view.findViewById(R.id.bt_fat_vi);
        bt_music=view.findViewById(R.id.bt_music);


        bt_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search="추천";
                searchTask = new searchTask().execute();
            }
        });
        bt_muscle_vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search="근력운동";
                searchTask = new searchTask().execute();
            }
        });

        bt_fat_vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search="지방감량운동";
                searchTask = new searchTask().execute();
            }
        });

        bt_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search="운동할 때 듣는 노래";
                searchTask = new searchTask().execute();
            }
        });









        //유튜브 api코드

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //제목을 입력하면 제목을 키워드로 하여 비동기 식으로 영상에 대한 정보를 가지고 온다.
                searchTask = new searchTask().execute();

            }
        });



        recyclerview= view.findViewById(R.id.recyclerview);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerview.setLayoutManager(mLinearLayoutManager);




            return view;

    }
    private class searchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                paringJsonData(getUtube());
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



            utubeAdapter = new UtubeAdapter(view.getContext(), sdata);
            recyclerview.setAdapter(utubeAdapter);
            utubeAdapter.notifyDataSetChanged();
        }
    }

    //유튜브 url에 접근하여 검색한 결과들을 json 객체로 만들어준다
    public JSONObject getUtube() throws IOException {


        String originUrl = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + search
                + "&key="+ serverKey+"&maxResults=50";

        String myUrl = String.format(originUrl);

        URL url = new URL(myUrl);

        HttpURLConnection connection =(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.connect();

        String line;
        String result="";
        InputStream inputStream=connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        System.out.println("검색결과"+ response);
        result=response.toString();


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    //json 객체를 가지고 와서 필요한 데이터를 파싱한다.
    private void paringJsonData(JSONObject jsonObject) throws JSONException {
        //재검색할때 데이터들이 쌓이는걸 방지하기 위해 리스트를 초기화 시켜준다.
        sdata.clear();

        JSONArray contacts = jsonObject.getJSONArray("items");

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);
            String kind =  c.getJSONObject("id").getString("kind"); // 종류를 체크하여 playlist도 저장
            if(kind.equals("youtube#video")){
                vodid = c.getJSONObject("id").getString("videoId"); // 유튜브
                // 동영상 아이디 값
                // 재생시
                // 필요합니다.
            }else{
                vodid = c.getJSONObject("id").getString("playlistId"); // 유튜브
            }

            String title = c.getJSONObject("snippet").getString("title"); //유튜브 제목을 받아옵니다
            String changString = stringToHtmlSign(title);


            String date = c.getJSONObject("snippet").getString("publishedAt") //등록날짜
                    .substring(0, 10);
            String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("default").getString("url");  //썸네일 이미지 URL값

            //JSON으로 파싱한 정보들을 객체화 시켜서 리스트에 담아준다.
            sdata.add(new SearchData(vodid, changString, imgUrl, date));
        }
    }

    String vodid = "";


    //영상 제목을 받아올때 " ' 문자가 그대로 출력되기 때문에 다른 문자로 대체 해주기 위해 사용하는 메서드
    private String stringToHtmlSign(String str) {

        return str.replaceAll("&amp;", "[&]")

                .replaceAll("[<]", "&it;")

                .replaceAll("[>]", "&gt")

                .replaceAll("&quot;","\"")
                .replaceAll("&#39;", "'");
    }


}

