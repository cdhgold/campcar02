package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.CartRecyclerAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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


        downXml();
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
                url = new URL("http://konginfo.co.kr/car/downfile/getXml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("Car");
                for(int i = 0; i< nodeList.getLength(); i++){
                    String sseq	    = "";
                    String scarNm	    = "";
                    String scarYear      = "";
                    String scarKm        = "";
                    String scarAddr      = "";
                    String scarTel       = "";
                    String scarFuel      = "";
                    String scarAmt       = "";
                    String scarInfo      = "";
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
                    dbHelper = ProductDBHelper.getInstance(getContext());
                    ProductBean product = new ProductBean();
                    product.setSeq	(Integer.parseInt(sseq) );
                    product.setCarNm	(scarNm);
                    product.setCarYear	(scarYear);
                    product.setCarKm	(scarKm);
                    product.setCarAddr	(scarAddr);
                    product.setCarTel	(scarTel);
                    product.setCarFuel	(scarFuel);
                    product.setCarAmt	(scarAmt);
                    product.setCarInfo	(scarInfo);
                    product.setCarImg01	(scarImg01);
                    product.setCarImg02	(scarImg02);
                    product.setCarImg03	(scarImg03);
                    product.setCarImg04	(scarImg04);
                    product.setCarImg05	(scarImg05);
                    product.setCarImg06	(scarImg06);
                    product.setCarImg07	(scarImg07);
                    product.setCarImg08	(scarImg08);
                    product.setCarImg09	(scarImg09);
                    product.setCarImg10	(scarImg10);
                    dbHelper.insertProduct(product);
                    int percent = 100/nodeList.getLength()*(i+1);
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
    }
}