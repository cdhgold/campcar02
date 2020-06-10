package com.cdh.campcar.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.cdh.campcar.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class ProductDBHelper extends SQLiteOpenHelper {

    private static ProductDBHelper dbHelper = null;

    private static final String DATABASE_NAME = "productdb";
    private static final String TABLE_NAME = "product";
    private static int DB_VERSION = 4;

    private Context mContext;

    public static ProductDBHelper getInstance(Context context ){
        if(dbHelper == null){
            dbHelper = new ProductDBHelper(context.getApplicationContext());
        }

        return dbHelper;
    }
    public static ProductDBHelper getInstance(Context context, int ver){
        DB_VERSION = ver;
        if(dbHelper == null){
            dbHelper = new ProductDBHelper(context.getApplicationContext());
        }

        return dbHelper;
    }
    private ProductDBHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.mContext = context;
        //deleteAllProduct();
        //initProduct();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("campcar","onCreate "  );
        String sql = "create table " + TABLE_NAME + " ( "
                + " seq  integer primary key , "
                + " email		text not null , "
                + " carNm		text not null , "
                + " carYear     text not null , "
                + " carKm       text not null , "
                + " carAddr     text not null , "
                + " carTel      text not null , "
                + " carFuel     text not null , "
                + " carAmt      text not null , "
                + " carImg01    text not null , "
                + " carImg02    text not null , "
                + " carImg03    text not null , "
                + " carImg04    text , "
                + " carImg05    text , "
                + " carImg06    text , "
                + " carImg07    text , "
                + " carImg08    text , "
                + " carImg09    text , "
                + " carImg10    text ,  "
                + " carInfo    text   "
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("campcar","onUpgrade "+newVersion );
        String sql = "drop table " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public long insertProduct(ProductBean product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int iseq = product.getSeq();
        values.put("seq"	    , iseq);
        values.put("email"	    , product.getCarEmail());
        values.put("carNm"	    , product.getCarNm());
        values.put("carYear"	, product.getCarYear());
        values.put("carKm"		, product.getCarKm());
        values.put("carAddr"	, product.getCarAddr());
        values.put("carTel"		, product.getCarTel());
        values.put("carFuel"  , product.getCarFuel());
        values.put("carAmt"   , product.getCarAmt());
        values.put("carInfo"  , product.getCarInfo());
        values.put("carImg01" , product.getCarImg01());
        values.put("carImg02" , product.getCarImg02());
        values.put("carImg03" , product.getCarImg03());
        values.put("carImg04" , product.getCarImg04());
        values.put("carImg05" , product.getCarImg05());
        values.put("carImg06" , product.getCarImg06());
        values.put("carImg07" , product.getCarImg07());
        values.put("carImg08" , product.getCarImg08());
        values.put("carImg09" , product.getCarImg09());
        values.put("carImg10" , product.getCarImg10());
        values.put("carInfo" , product.getCarInfo());

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME  + " where seq ="+iseq, null );

        long ret = 0;
        if (cursor.moveToNext()) {
            String usql = "UPDATE ";
            usql += TABLE_NAME + " set carNm = '" +product.getCarNm()+"' " ;
            usql += ", email    = '" +product.getCarEmail()+"'  ";
            usql += ", carYear  = '" +product.getCarYear() +"'  ";
            usql += ", carKm    = '" +product.getCarKm()+"'  ";
            usql += ", carAddr  = '" +product.getCarAddr()+"'  ";
            usql += ", carTel   = '" +product.getCarTel()+"'  ";
            usql += ", carFuel  = '" +product.getCarFuel()+"'  ";
            usql += ", carAmt   = '" +product.getCarAmt()+"'  ";
            usql += ", carInfo  = '" +product.getCarInfo()+"'  ";
            usql += ", carImg01 = '" +product.getCarImg01()+"'  ";
            usql += ", carImg02 = '" +product.getCarImg02()+"'  ";
            usql += ", carImg03 = '" +product.getCarImg03()+"'  ";
            usql += ", carImg04 = '" +product.getCarImg04()+"'  ";
            usql += ", carImg05 = '" +product.getCarImg05()+"'  ";
            usql += ", carImg06 = '" +product.getCarImg06()+"'  ";
            usql += ", carImg07 = '" +product.getCarImg07()+"'  ";
            usql += ", carImg08 = '" +product.getCarImg08()+"'  ";
            usql += ", carImg09 = '" +product.getCarImg09()+"'  ";
            usql += ", carImg10 = '" +product.getCarImg10()+"'  ";
            usql += ", carInfo  = '" +product.getCarInfo()+"'  ";

            usql += "where seq = " + iseq ;
            db.execSQL(usql);

            Log.d("campCar"," update");
        }else{
            ret = db.insert(TABLE_NAME, null, values);
            Log.d("campCar"," insert");
        }
        return ret;
    }

    public ArrayList<ProductBean> getAllProduct() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME  + " order by seq desc", null );

        ArrayList<ProductBean> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            ProductBean product = new ProductBean();

            product.setSeq(cursor.getInt(cursor.getColumnIndex("seq")));
            product.setCarEmail(cursor.getString	(cursor.getColumnIndex("email")));
            product.setCarNm(cursor.getString	(cursor.getColumnIndex("carNm")));
            product.setCarYear(cursor.getString	(cursor.getColumnIndex("carYear")));
            product.setCarKm(cursor.getString	(cursor.getColumnIndex("carKm")));
            product.setCarAddr(cursor.getString	(cursor.getColumnIndex("carAddr")));
            product.setCarTel(cursor.getString	(cursor.getColumnIndex("carTel")));
            product.setCarFuel(cursor.getString	(cursor.getColumnIndex("carFuel")));
            product.setCarAmt(cursor.getString	(cursor.getColumnIndex("carAmt")));
            product.setCarInfo(cursor.getString	(cursor.getColumnIndex("carInfo")));
            product.setCarImg01(cursor.getString	(cursor.getColumnIndex("carImg01")));
            product.setCarImg02(cursor.getString	(cursor.getColumnIndex("carImg02")));
            product.setCarImg03(cursor.getString	(cursor.getColumnIndex("carImg03")));
            product.setCarImg04(cursor.getString	(cursor.getColumnIndex("carImg04")));
            product.setCarImg05(cursor.getString	(cursor.getColumnIndex("carImg05")));
            product.setCarImg06(cursor.getString	(cursor.getColumnIndex("carImg06")));
            product.setCarImg07(cursor.getString	(cursor.getColumnIndex("carImg07")));
            product.setCarImg08(cursor.getString	(cursor.getColumnIndex("carImg08")));
            product.setCarImg09(cursor.getString	(cursor.getColumnIndex("carImg09")));
            product.setCarImg10(cursor.getString	(cursor.getColumnIndex("carImg10")));

            result.add(product);
        }

        return result;
    }
    /*하단에 상품정보를 보여준다
     */
    public ArrayList<ProductBean> getRandomProduct(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME  + " order by seq desc", null );
        ArrayList<ProductBean> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            ProductBean product = new ProductBean();

            product.setSeq(cursor.getInt(cursor.getColumnIndex("seq")));
            product.setCarEmail(cursor.getString	(cursor.getColumnIndex("email")));
            product.setCarNm(cursor.getString	(cursor.getColumnIndex("carNm")));
            product.setCarYear(cursor.getString	(cursor.getColumnIndex("carYear")));
            product.setCarKm(cursor.getString	(cursor.getColumnIndex("carKm")));
            product.setCarAddr(cursor.getString	(cursor.getColumnIndex("carAddr")));
            product.setCarTel(cursor.getString	(cursor.getColumnIndex("carTel")));
            product.setCarFuel(cursor.getString	(cursor.getColumnIndex("carFuel")));
            product.setCarAmt(cursor.getString	(cursor.getColumnIndex("carAmt")));
            product.setCarInfo(cursor.getString	(cursor.getColumnIndex("carInfo")));
            product.setCarImg01(cursor.getString	(cursor.getColumnIndex("carImg01")));
            product.setCarImg02(cursor.getString	(cursor.getColumnIndex("carImg02")));
            product.setCarImg03(cursor.getString	(cursor.getColumnIndex("carImg03")));
            product.setCarImg04(cursor.getString	(cursor.getColumnIndex("carImg04")));
            product.setCarImg05(cursor.getString	(cursor.getColumnIndex("carImg05")));
            product.setCarImg06(cursor.getString	(cursor.getColumnIndex("carImg06")));
            product.setCarImg07(cursor.getString	(cursor.getColumnIndex("carImg07")));
            product.setCarImg08(cursor.getString	(cursor.getColumnIndex("carImg08")));
            product.setCarImg09(cursor.getString	(cursor.getColumnIndex("carImg09")));
            product.setCarImg10(cursor.getString	(cursor.getColumnIndex("carImg10")));

            result.add(product);
        }

        return result;
    }
    /*
    param : amt, year, km
     */
    public ArrayList<ProductBean> getProductbySeq(String gbn){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if("amt".equals(gbn) ){
            cursor = db.query(TABLE_NAME, null, null, null, null, null, "carAmt asc" );
        }
        else if("year".equals(gbn) ){
            cursor = db.query(TABLE_NAME, null, null, null, null, null, "carYear desc" );
        }
        else if("km".equals(gbn) ){
            cursor = db.query(TABLE_NAME, null, null, null, null, null, "carKm asc" );
        }
        ArrayList<ProductBean> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            ProductBean product = new ProductBean();

            product.setSeq(cursor.getInt(cursor.getColumnIndex("seq")));
            product.setCarEmail(cursor.getString	(cursor.getColumnIndex("email")));
            product.setCarNm(cursor.getString	(cursor.getColumnIndex("carNm")));
            product.setCarYear(cursor.getString	(cursor.getColumnIndex("carYear")));
            product.setCarKm(cursor.getString	(cursor.getColumnIndex("carKm")));
            product.setCarAddr(cursor.getString	(cursor.getColumnIndex("carAddr")));
            product.setCarTel(cursor.getString	(cursor.getColumnIndex("carTel")));
            product.setCarFuel(cursor.getString	(cursor.getColumnIndex("carFuel")));
            product.setCarAmt(cursor.getString	(cursor.getColumnIndex("carAmt")));
            product.setCarInfo(cursor.getString	(cursor.getColumnIndex("carInfo")));
            product.setCarImg01(cursor.getString	(cursor.getColumnIndex("carImg01")));
            product.setCarImg02(cursor.getString	(cursor.getColumnIndex("carImg02")));
            product.setCarImg03(cursor.getString	(cursor.getColumnIndex("carImg03")));
            product.setCarImg04(cursor.getString	(cursor.getColumnIndex("carImg04")));
            product.setCarImg05(cursor.getString	(cursor.getColumnIndex("carImg05")));
            product.setCarImg06(cursor.getString	(cursor.getColumnIndex("carImg06")));
            product.setCarImg07(cursor.getString	(cursor.getColumnIndex("carImg07")));
            product.setCarImg08(cursor.getString	(cursor.getColumnIndex("carImg08")));
            product.setCarImg09(cursor.getString	(cursor.getColumnIndex("carImg09")));
            product.setCarImg10(cursor.getString	(cursor.getColumnIndex("carImg10")));

            result.add(product);
        }

        return result;
    }

    public long deleteAllProduct(){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }
    public long deleteProduct(String seq ){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "seq = ?", new String[]{seq});
    }
    private void initProduct(){
        // init("product", 1, "청자켓", 73000, getByteArrayFromDrawable(R.drawable.top_blue_jacket), "top");
    }

    private void init(String tableName, int id, String pName, int pPrice, byte[] pImage, String type){
        ProductBean productBean = new ProductBean();


        insertProduct(productBean);
    }

    // drawable 이미지를 sqlite에 넣기 위해 byteArray로 변환하는 함수
    private byte[] getByteArrayFromDrawable(int image){
        Drawable drawable = mContext.getDrawable(image);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, stream);
        byte[] dataByte = stream.toByteArray();

        return dataByte;
    }
}