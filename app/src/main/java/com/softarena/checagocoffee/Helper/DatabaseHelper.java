package com.softarena.checagocoffee.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantItemModel;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantsModel;
import com.softarena.checagocoffee.Acitivity.Sizes.ProductSize;


@SuppressWarnings("ALL")
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "chicagocoffee";

    // Table Names
    private static final String TABLE_PRODUCTS = "tbl_products";
    private static final String TABLE_INGREDIENTS = "tbl_ingredients";


    //	Field names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SHOP_ID = "shop_id";
    private static final String KEY_SHOP_NAME = "shop_name";
    private static final String KEY_SHOP_ADDRESS = "shop_address";
    private static final String KEY_PRODUCT_IMAGE = "product_image";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_WEIGHT = "weight";
    private static final String KEY_PRODUCT_WEIGHT_UNIT = "weight_unit";
    private static final String KEY_PRODUCT_QUANTITY = "product_quantity";
    private static final String KEY_PRODUCT_SIZE_ID = "product_size_id";
    private static final String KEY_SIZE_NAME = "size_name";
    private static final String KEY_SIZE_PRICE = "size_price";
    private static final String KEY_ING_TYPE_ID = "ing_type_id";
    private static final String KEY_ING_ID = "ing_id";
    private static final String KEY_CAT_ID = "cat_id";
    private static final String KEY_ING_NAME = "ing_name";
    private static final String KEY_ING_PRICE = "ing_price";
    private static final String KEY_SERVICE_FEE = "service_fee";



    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "
            + TABLE_PRODUCTS
            + "(" + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRODUCT_NAME + " TEXT,"
            + KEY_PRODUCT_IMAGE + " TEXT,"
            + KEY_PRODUCT_QUANTITY + " TEXT,"
            + KEY_PRODUCT_SIZE_ID + " TEXT,"
            + KEY_SIZE_NAME + " TEXT,"
            + KEY_SIZE_PRICE + " TEXT,"
            + KEY_SHOP_ID + " TEXT,"
            + KEY_SHOP_NAME + " TEXT,"
            + KEY_SHOP_ADDRESS + " TEXT,"
            + KEY_SERVICE_FEE + " TEXT,"
            + KEY_CAT_ID + " TEXT,"
            + KEY_PRODUCT_WEIGHT + " TEXT,"
            + KEY_PRODUCT_WEIGHT_UNIT + " TEXT"
            + ")";
    // Todo table runs create statement
    private static final String CREATE_TABLE_INGREDIENTS = "CREATE TABLE "
            + TABLE_INGREDIENTS
            + "("
            + KEY_ING_ID + " INTEGER,"
            + KEY_PRODUCT_ID + " INTEGER,"
            + KEY_ING_TYPE_ID + " TEXT,"
            + KEY_ING_NAME + " TEXT,"
            + KEY_ING_PRICE + " TEXT"
            + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_INGREDIENTS);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);

        // create new tables
        onCreate(db);
    }


    public boolean saveProduct(AllProduct allProduct, List<IngrediantItemModel> ingrediantItemModelList, AllShop allShop,String quantity,ProductSize productSize) {
        Boolean returnvalue=false;
        List<IngrediantsModel> list = new ArrayList<IngrediantsModel>();
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE "
                + KEY_PRODUCT_ID + " = '" + allProduct.getProductId()+"'";


        Cursor c1 = db.rawQuery(selectQuery, null);
        int count = c1.getCount();
        c1.close();
        if (count == 0) {

            db = this.getWritableDatabase();
            String sql1 = "INSERT INTO " + TABLE_PRODUCTS + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement1 = db.compileStatement(sql1);
            db.beginTransaction();

            try {

                statement1.clearBindings();
                statement1.bindLong(1, Long.parseLong(allProduct.getProductId()));
                statement1.bindString(2, allProduct.getProductName());
                statement1.bindString(3, allProduct.getProductImage());
                statement1.bindString(4, quantity);
                statement1.bindString(5, productSize.getProductSizeId());
                statement1.bindString(6, productSize.getProductSizeName());
                statement1.bindString(7, productSize.getProductPrice());
                statement1.bindString(8, String.valueOf(allShop.getShopId()));
                statement1.bindString(9, String.valueOf(allShop.getShopName()));
                statement1.bindString(10, String.valueOf(allShop.getShopAddress()));
                statement1.bindString(11, allProduct.getProduct_service_fee());
                statement1.bindString(12, String.valueOf(allProduct.getProduct_cat_id()));
                if (String.valueOf(allProduct.getProduct_cat_id()).equals("2")){
                    statement1.bindString(13, String.valueOf(allProduct.getProduct_weight()));
                    statement1.bindString(14, String.valueOf(allProduct.getProduct_weight_unit()));
                }else {
                    statement1.bindString(13, "");
                    statement1.bindString(14, "");
                }

                statement1.execute();

                db.setTransactionSuccessful();
            } catch (Exception e) {

            } finally {
                db.endTransaction();
            }


        //save ingredients


                    db = this.getWritableDatabase();
                    String sql = "INSERT INTO " + TABLE_INGREDIENTS + " VALUES(?, ?, ?, ?, ?)";
                    SQLiteStatement statement = db.compileStatement(sql);
                    db.beginTransaction();
                    try {
                        for (IngrediantItemModel c : ingrediantItemModelList) {
                            statement.clearBindings();
                            statement.bindLong(1, Long.parseLong(c.getIngredientId()));
                            statement.bindLong(2, Long.parseLong(allProduct.getProductId()));
                            statement.bindString(3, c.getProductIngredientTypeId());
                            statement.bindString(4, c.getIngredientName());
                            statement.bindString(5, c.getIngredientPrice());
                            statement.execute();
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

            returnvalue=true;



    }else {
            returnvalue=false;
        }

        db.close();
        return returnvalue;
    }
    public void removeProductonproductId(String prodid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_PRODUCTS + " where " + KEY_PRODUCT_ID + "=" + prodid);
        db.execSQL("delete from " + TABLE_INGREDIENTS + " where " + KEY_PRODUCT_ID + "=" + prodid);

        db.close();
    }
    public boolean shopAlreadyaddedIncart(String shopId,String catId){
        Boolean returnvalue=false;
        SQLiteDatabase db = this.getReadableDatabase();
//        product already exists for some shop
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCTS;
        Cursor c2 = db.rawQuery(selectQuery1, null);
        int count2 = c2.getCount();
        c2.close();
//      product exists for my shop
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE "
                + KEY_SHOP_ID + " = '" + shopId+"' AND "+ KEY_CAT_ID + " = '" + catId+"'";
        Cursor c1 = db.rawQuery(selectQuery, null);
        int count = c1.getCount();
        c1.close();
        if (count > 0) {
            returnvalue=true;
        }
        else if (count == 0 && count2 == 0){
            returnvalue=true;
        }
        else if (count2 > 0){
            returnvalue=false;
        }
        return returnvalue;
    }
    public int getItemsincart(){
        Boolean returnvalue=false;
        SQLiteDatabase db = this.getReadableDatabase();
//        product already exists for some shop
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCTS;
        Cursor c2 = db.rawQuery(selectQuery1, null);
        int count2 = c2.getCount();
        c2.close();
        db.close();
        return count2;
    }
    public int getProductItemsincart(String prodId){
        Boolean returnvalue=false;
        AllProduct allProduct =new AllProduct();
        SQLiteDatabase db = this.getReadableDatabase();
//        product already exists for some shop
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCTS+ " WHERE "
                + KEY_PRODUCT_ID + " = '" + prodId+"'";;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor c1 = db1.rawQuery(selectQuery1, null);

        if (c1.moveToFirst()) {
            do {
                 allProduct = new AllProduct();

                allProduct.setProductQuantity(c1.getString(c1.getColumnIndex(KEY_PRODUCT_QUANTITY)));

            } while (c1.moveToNext());
        }
        c1.close();
        db1.close();
        int count=0;
        if (allProduct.getProductQuantity()==null||allProduct.getProductQuantity().equals("")){
            count=0;
        }else {
            count=Integer.parseInt(allProduct.getProductQuantity());
        }
        return count;
    }

    public List<AllProduct> getAllData() {
        List<AllProduct> list = new ArrayList<AllProduct>();
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCTS + " ORDER BY " + KEY_PRODUCT_ID + " ASC";


        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor c1 = db1.rawQuery(selectQuery1, null);

        if (c1.moveToFirst()) {
            do {
                AllProduct allProduct = new AllProduct();
                allProduct.setProductId(String.valueOf((c1.getInt(c1.getColumnIndex(KEY_PRODUCT_ID)))));
                allProduct.setProductImage(c1.getString(c1.getColumnIndex(KEY_PRODUCT_IMAGE)));
                allProduct.setProductName(c1.getString(c1.getColumnIndex(KEY_PRODUCT_NAME)));
                allProduct.setProduct_weight(c1.getString(c1.getColumnIndex(KEY_PRODUCT_WEIGHT)));
                allProduct.setProductShopId(c1.getString(c1.getColumnIndex(KEY_SHOP_ID)));
                allProduct.setProduct_weight_unit(c1.getString(c1.getColumnIndex(KEY_PRODUCT_WEIGHT_UNIT)));
                allProduct.setProduct_cat_id(Integer.parseInt(c1.getString(c1.getColumnIndex(KEY_CAT_ID))));
                allProduct.setProduct_service_fee(c1.getString(c1.getColumnIndex(KEY_SERVICE_FEE)));
                allProduct.setProductQuantity(c1.getString(c1.getColumnIndex(KEY_PRODUCT_QUANTITY)));
                List<ProductSize> productSizeList=new ArrayList<ProductSize>();
                productSizeList.add(new ProductSize(
                        c1.getString(c1.getColumnIndex(KEY_PRODUCT_SIZE_ID)),
                        c1.getString(c1.getColumnIndex(KEY_SIZE_NAME)),
                        c1.getString(c1.getColumnIndex(KEY_SIZE_PRICE))
                        ));
                allProduct.setProductSizes(productSizeList);
                AllShop allShop=new AllShop();
                allShop.setShopId(Integer.parseInt(c1.getString(c1.getColumnIndex(KEY_SHOP_ID))));
                allShop.setShopName(c1.getString(c1.getColumnIndex(KEY_SHOP_NAME)));
                allShop.setShopAddress(c1.getString(c1.getColumnIndex(KEY_SHOP_ADDRESS)));
                allProduct.setAllShop(allShop);

                List<IngrediantItemModel> list1 = new ArrayList<IngrediantItemModel>();
                String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS  + " WHERE "
                        + KEY_PRODUCT_ID + " = " + String.valueOf((c1.getInt(c1.getColumnIndex(KEY_PRODUCT_ID))));


                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.rawQuery(selectQuery, null);
                if (c.moveToFirst()) {
                    do {
                        IngrediantItemModel ingrediantItemModel = new IngrediantItemModel();
                        ingrediantItemModel.setProductIngredientTypeId(String.valueOf((c.getInt(c.getColumnIndex(KEY_ING_TYPE_ID)))));
                        ingrediantItemModel.setIngredientId(String.valueOf((c.getInt(c.getColumnIndex(KEY_ING_ID)))));
                        ingrediantItemModel.setIngredientName(c.getString(c.getColumnIndex(KEY_ING_NAME)));
                        ingrediantItemModel.setIngredientPrice(c.getString(c.getColumnIndex(KEY_ING_PRICE)));


                        // adding to todo list
                        list1.add(ingrediantItemModel);
                    } while (c.moveToNext());
                }
                c.close();
                allProduct.setIngredientAll(list1);

                // adding to todo list
                list.add(allProduct);
            } while (c1.moveToNext());
        }
        c1.close();
        db1.close();
        return list;
    }

    /**
     * get datetime
     */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd-yyyy hh:mm a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



}
