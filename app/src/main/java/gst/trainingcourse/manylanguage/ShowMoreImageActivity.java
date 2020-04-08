package gst.trainingcourse.manylanguage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gst.trainingcourse.manylanguage.adapter.MoreImageAdapter;
import gst.trainingcourse.manylanguage.lib_Helper.XMLDOMParser;
import gst.trainingcourse.manylanguage.model.FullImage;

public class ShowMoreImageActivity extends AppCompatActivity {

    private ArrayList<FullImage> mFullImages = new ArrayList<>();
    private MoreImageAdapter mMoreImageAdapter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;
    private String mNameGirl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_image);

        initView();
        initData();
        setUpToolBar();
    }

    private void setUpToolBar() {
        mToolBar.setTitle(mNameGirl);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String s = intent.getStringExtra("linkRss");
            mNameGirl = intent.getStringExtra("nameGirl");
            new ReadRSS().execute(s);
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerviewMoreImg);
        mToolBar = findViewById(R.id.toolBar);
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return getXmlFromUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            XMLDOMParser xmldomParser = new XMLDOMParser();
            Document document = xmldomParser.getDocument(result);

            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescreption = document.getElementsByTagName("description");
            String linkHinhAnh = "";

            for (int i = 0; i < nodeList.getLength(); i++) {
                //lấy phần tử description thứ 2
                String cdata = nodeListDescreption.item(i + 1).getTextContent();
                //tìm ra thẻ img trong description
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);

                if (matcher.find()) {
                    linkHinhAnh = matcher.group(1);
                }
                mFullImages.add(new FullImage(linkHinhAnh));
            }
            addShowImageAdapter();
        }
    }

    private static String getXmlFromUrl(String strings) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(strings);
            //tạo kết nối
            URLConnection connection = url.openConnection();
            //bọc connection trong buffer
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private void addShowImageAdapter() {
        mMoreImageAdapter = new MoreImageAdapter(getApplicationContext(), mFullImages);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMoreImageAdapter);
    }
}
