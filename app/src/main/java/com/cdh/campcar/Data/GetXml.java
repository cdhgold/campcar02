package com.cdh.campcar.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;

import com.cdh.campcar.UtilActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetXml extends Thread {
    URL url;
    Document doc = null;
    HttpURLConnection conn;
    InputStreamReader isr;
    private ProductDBHelper dbHelper;
    private Context context;
    public GetXml(Context ctx){
        this.context = ctx;
    }
    public void run() {
        try {
            url = new URL("http://konginfo.co.kr/car/downfile/getXml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Car");
            for (int i = 0; i < nodeList.getLength(); i++) {
                String sseq = "";
                String scarNm = "";
                String scarYear = "";
                String scarKm = "";
                String scarAddr = "";
                String scarTel = "";
                String scarFuel = "";
                String scarAmt = "";
                String scarInfo = "";
                String scarImg01 = "";
                String scarImg02 = "";
                String scarImg03 = "";
                String scarImg04 = "";
                String scarImg05 = "";
                String scarImg06 = "";
                String scarImg07 = "";
                String scarImg08 = "";
                String scarImg09 = "";
                String scarImg10 = "";


                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList seq = fstElmnt.getElementsByTagName("Seq");
                if (seq.getLength() > 0) {
                    sseq = seq.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carNm = fstElmnt.getElementsByTagName("CarNm");
                if (carNm.getLength() > 0) {
                    scarNm = carNm.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carYear = fstElmnt.getElementsByTagName("CarYear");
                if (carYear.getLength() > 0) {
                    scarYear = carYear.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carKm = fstElmnt.getElementsByTagName("CarKm");
                if (carKm.getLength() > 0) {
                    scarKm = carKm.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carAddr = fstElmnt.getElementsByTagName("CarAddr");
                if (carAddr.getLength() > 0) {
                    scarAddr = carAddr.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carTel = fstElmnt.getElementsByTagName("CarTel");
                if (carTel.getLength() > 0) {
                    scarTel = carTel.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carFuel = fstElmnt.getElementsByTagName("CarFuel");
                if (carFuel.getLength() > 0) {
                    scarFuel = carFuel.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carAmt = fstElmnt.getElementsByTagName("CarAmt");
                if (carAmt.getLength() > 0) {
                    scarAmt = carAmt.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carInfo = fstElmnt.getElementsByTagName("CarInfo");
                if (carInfo.getLength() > 0) {
                    scarInfo = carInfo.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg01 = fstElmnt.getElementsByTagName("CarImg01");
                if (carImg01.getLength() > 0) {
                    scarImg01 = carImg01.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg02 = fstElmnt.getElementsByTagName("CarImg02");
                if (carImg02.getLength() > 0) {
                    scarImg02 = carImg02.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg03 = fstElmnt.getElementsByTagName("CarImg03");
                if (carImg03 != null) {
                    scarImg03 = carImg03.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg04 = fstElmnt.getElementsByTagName("CarImg04");
                if (carImg04.getLength() > 0) {
                    scarImg04 = carImg04.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg05 = fstElmnt.getElementsByTagName("CarImg05");
                if (carImg05.getLength() > 0) {
                    scarImg05 = carImg05.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg06 = fstElmnt.getElementsByTagName("CarImg06");
                if (carImg06.getLength() > 0) {
                    scarImg06 = carImg06.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg07 = fstElmnt.getElementsByTagName("CarImg07");
                if (carImg07.getLength() > 0) {
                    scarImg07 = carImg07.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg08 = fstElmnt.getElementsByTagName("CarImg08");
                if (carImg08.getLength() > 0) {
                    scarImg08 = carImg08.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg09 = fstElmnt.getElementsByTagName("CarImg09");
                if (carImg09.getLength() > 0) {
                    scarImg09 = carImg09.item(0).getChildNodes().item(0).getNodeValue();
                }
                NodeList carImg10 = fstElmnt.getElementsByTagName("CarImg10");
                if (carImg10.getLength() > 0) {
                    scarImg10 = carImg10.item(0).getChildNodes().item(0).getNodeValue();
                }
                // insert
                dbHelper = ProductDBHelper.getInstance(context);
                ProductBean product = new ProductBean();
                product.setSeq(Integer.parseInt(sseq));
                product.setCarNm(scarNm);
                product.setCarYear(scarYear);
                product.setCarKm(scarKm);
                product.setCarAddr(scarAddr);
                product.setCarTel(scarTel);
                product.setCarFuel(scarFuel);
                product.setCarAmt(scarAmt);
                product.setCarInfo(scarInfo);
                // byte[] byteArray=str.getBytes();  string to byte[]
                // String strYes = new String(byteArray); byte[] to string
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
    }// end run

    // url 이미지를 set String
    private String LoadImageFromWebOperations(String url) {
        String imgStr = "";
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            imgStr = UtilActivity.BitmapToString(bitmap);
        } catch (Exception e) {
            System.out.println("Exc=" + e);
        }
        return imgStr;
    }
}
