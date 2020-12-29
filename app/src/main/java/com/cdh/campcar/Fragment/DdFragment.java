package com.cdh.campcar.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.CartRecyclerAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;
import com.cdh.campcar.UtilActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
data refresh : xml로 daun받고 , db에 insert
 */
public class DdFragment extends Fragment  {
    private View view;
    private RecyclerView recyclerView;
    private CartRecyclerAdapter adapter;
    private ArrayList<ProductBean> data;
    private ProductDBHelper dbHelper;
    ProgressBar progressBar;
    String xml;
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    TextView tv;
    Handler handler;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_dd_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* Google 에서 StrickMode 정책 적용은 실제 배포할 때는 적용하지 않도록 가이드
        main thread에서 thread분리 , 개발시에만 적용
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        */

        tv = view.findViewById(R.id.tv);
        progressBar = (ProgressBar) view.findViewById(R.id.h_progressbar) ;

        progressBar.setMax(100);
        progressBar.setProgress(0);
        // main thread 아닌곳에서 view변경시
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                tv.setText(msg.arg1+"%");
                if(msg.arg1==100){
                    tv.setText("완료!");
                }
            }
        };
        // 최신 다운 여부확인
        conFirmDialog();

    }
    public void conFirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("CmapCar");
        builder.setMessage("최신정보를 다운받을래요?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        downXml();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        HomeFragment frg = new HomeFragment();
                        ((MainActivity)getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달

                    }
                });
        builder.show();
    }
    private void downXml() {
        try {
            campXml xml = new campXml();
            xml.start();
            //xml.join();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    class campXml extends Thread {
        public void run() {
            try {
                url = new URL("http://49.50.167.90/car/downfile/getXml");// 공인IP 49.50.167.90
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                dbHelper = ProductDBHelper.getInstance(getContext());
                dbHelper.deleteAllProduct();// 삭제하고 다운처리 20-07-10
                NodeList nodeList = doc.getElementsByTagName("Car");
                for(int i = 0; i< nodeList.getLength(); i++){
                    String sseq	    = "";
                    String scarNm	    = "";
                    String semail	    = "";
                    String scarYear      = "";
                    String scarKm        = "";
                    String scarAddr      = "";
                    String scarTel       = "";
                    String scarFuel      = "";
                    String scarAmt       = "";
                    String scarInfo      = "";
                    String scarUpdDt      = "";
                    String scarImg01     = "";
                    String scarImg02     = "";
                    String scarImg03     = "";
                    String scarImg04     = "";
                    String scarImg05     = "";
                    String scarImg06     = "";
                    String scarImg07     = "";
                    String scarImg08     = "";
                    String scarImg09     = "";
                    String scarImg10     = "";


                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;
                    NodeList seq = fstElmnt.getElementsByTagName("Seq");
                    if(seq.getLength() > 0) {
                        sseq = seq.item(0).getChildNodes().item(0).getNodeValue();
                    }
                    NodeList email = fstElmnt.getElementsByTagName("Email");
                    if(email.getLength() > 0) {
                        semail = email.item(0).getChildNodes().item(0).getNodeValue();
                    }
                    NodeList carNm = fstElmnt.getElementsByTagName("CarNm");
                    if(carNm.getLength() > 0) {
                        scarNm = carNm.item(0).getChildNodes().item(0).getNodeValue();
                    }
                    NodeList carYear = fstElmnt.getElementsByTagName("CarYear");
                    if(carYear.getLength() > 0) {
                        scarYear =  carYear.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carKm  = fstElmnt.getElementsByTagName("CarKm");
                    if(carKm.getLength() > 0) {
                        scarKm =  carKm.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carAddr  = fstElmnt.getElementsByTagName("CarAddr");
                    if(carAddr.getLength() > 0) {
                        scarAddr =  carAddr.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carTel  = fstElmnt.getElementsByTagName("CarTel");
                    if(carTel.getLength() > 0) {
                        scarTel =  carTel.item(0).getChildNodes().item(0).getNodeValue() ;
                    }
                    NodeList carFuel  = fstElmnt.getElementsByTagName("CarFuel");
                    if(carFuel.getLength() > 0) {
                        scarFuel =  carFuel.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carAmt  = fstElmnt.getElementsByTagName("CarAmt");
                    if(carAmt.getLength() > 0) {
                        scarAmt =  carAmt.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carInfo  = fstElmnt.getElementsByTagName("CarInfo");
                    if(carInfo.getLength() > 0) {
                        scarInfo =  carInfo.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carUpdDt  = fstElmnt.getElementsByTagName("UpdDt");
                    if(carUpdDt.getLength() > 0) {
                        scarUpdDt =  carUpdDt.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }

                    NodeList carImg01  = fstElmnt.getElementsByTagName("CarImg01");
                    if(carImg01.getLength() > 0 ) {
                        scarImg01 =  carImg01.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg02  = fstElmnt.getElementsByTagName("CarImg02");
                    if(carImg02.getLength() > 0) {
                        scarImg02 =  carImg02.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg03  = fstElmnt.getElementsByTagName("CarImg03");
                    if(carImg03 != null) {
                        scarImg03 =  carImg03.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg04  = fstElmnt.getElementsByTagName("CarImg04");
                    if(carImg04.getLength() > 0) {
                        scarImg04 =  carImg04.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg05  = fstElmnt.getElementsByTagName("CarImg05");
                    if(carImg05.getLength() > 0) {
                        scarImg05 =  carImg05.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg06  = fstElmnt.getElementsByTagName("CarImg06");
                    if(carImg06.getLength() > 0) {
                        scarImg06 =  carImg06.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg07  = fstElmnt.getElementsByTagName("CarImg07");
                    if(carImg07.getLength() > 0) {
                        scarImg07 =  carImg07.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg08  = fstElmnt.getElementsByTagName("CarImg08");
                    if(carImg08.getLength() > 0) {
                        scarImg08 =  carImg08.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg09  = fstElmnt.getElementsByTagName("CarImg09");
                    if(carImg09.getLength() > 0) {
                        scarImg09 =  carImg09.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    NodeList carImg10  = fstElmnt.getElementsByTagName("CarImg10");
                    if(carImg10.getLength() > 0) {
                        scarImg10 =  carImg10.item(0).getChildNodes().item(0).getNodeValue()  ;
                    }
                    // insert
                    ProductBean product = new ProductBean();
                    product.setSeq	(Integer.parseInt(sseq) );
                    product.setCarEmail	(semail);
                    product.setCarNm	(scarNm);
                    product.setCarYear	(scarYear);
                    product.setCarKm	(scarKm);
                    product.setCarAddr	(scarAddr);
                    product.setCarTel	(scarTel);
                    product.setCarFuel	(scarFuel);
                    product.setCarAmt	(scarAmt);
                    product.setCarInfo	(scarInfo);
                    product.setUpdDt	(scarUpdDt);
                    product.setCarImg01(new String(LoadImageFromWebOperations(scarImg01)) );
                    product.setCarImg02(new String(LoadImageFromWebOperations(scarImg02)));
                    product.setCarImg03(new String(LoadImageFromWebOperations(scarImg03)));
                    product.setCarImg04(new String(LoadImageFromWebOperations(scarImg04)));
                    product.setCarImg05(new String(LoadImageFromWebOperations(scarImg05)));
                    product.setCarImg06(new String(LoadImageFromWebOperations(scarImg06)));
                    product.setCarImg07(new String(LoadImageFromWebOperations(scarImg07)));
                    product.setCarImg08(new String(LoadImageFromWebOperations(scarImg08)));
                    product.setCarImg09(new String(LoadImageFromWebOperations(scarImg09)));
                    product.setCarImg10(new String(LoadImageFromWebOperations(scarImg10)));
                    dbHelper.insertProduct(product);
                    int percent = 100/nodeList.getLength()*(i+1);
                    if(percent > 95)percent = 100;
                    progressBar.setProgress(percent);
                    Message msg = handler.obtainMessage();
                    msg.arg1 = percent;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

// Log.d("xml ",s);
                }// end for

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }// end class

    // url 이미지를 set String
    private String LoadImageFromWebOperations(String url) {
        // unique 키 생성
        String imgStr =  UUID.randomUUID().toString().replaceAll("-", "");
        try {
            String sext = url.substring(url.lastIndexOf("."));
            imgStr+=sext;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if(bitmap != null) {
                saveBitmapToJpeg(bitmap, imgStr ); // 이미지 파일생성 및 저장
            }else{
                imgStr = "";
            }
            //imgStr = UtilActivity.BitmapToString(bitmap);
        } catch (Exception e) {
            System.out.println("Exc=" + e);
        }
        return imgStr;
    }
    private void saveBitmapToJpeg(Bitmap bitmap, String name ) {
        //내부저장소 파일저장
        //File storage = new File(Environment.DIRECTORY_PICTURES , name);
        File storage = new File(getContext().getFilesDir(), name);
        try {
            FileOutputStream out = new FileOutputStream(storage);
            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
            // 스트림 사용후 닫아줍니다.
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("campcar","FileNotFoundExce ption : " + e.getMessage());
        } catch (IOException e) {
            Log.e("campcar","IOException : " + e.getMessage());
        }
    }
}