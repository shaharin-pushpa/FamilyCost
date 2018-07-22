package com.example.kowshick.bazarcost.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kowshick.bazarcost.FamilyCost;
import com.example.kowshick.bazarcost.ShopList;

import java.util.ArrayList;
import java.util.List;

public class CostDatabase {
    private CostDatabaseHelper helper;
    private SQLiteDatabase db;
    private int deletedRow;
    private int count;

    public CostDatabase(Context context){
        helper=new CostDatabaseHelper(context);

    }

    public void open(){
        db=helper.getWritableDatabase();

    }
    public void close()
    {
        db.close();
    }

    public boolean insertCost(FamilyCost fc){
        this.open();
        ContentValues values=new ContentValues();
        values.put(CostDatabaseHelper.TBL_COST_DATE,fc.getDate());
        values.put(CostDatabaseHelper.TBL_COST_CATEGORY,fc.getCategory());
        values.put(CostDatabaseHelper.TBL_COST_PRUDUCT,fc.getProduct());
        values.put(CostDatabaseHelper.TBL_COST_QUANTITY,fc.getQuantity());
        values.put(CostDatabaseHelper.TBL_COST_PRICE,fc.getPrice());
        long insertedRow= db.insert(CostDatabaseHelper.TABLE_COST,null,values);
        this.close();
        if(insertedRow>0){
            return true;
        }
        else{
            return false;
        }
    }

//  List
    public boolean insertList(String list){
        this.open();
        ContentValues values=new ContentValues();
        values.put(CostDatabaseHelper.TBL_LIST_Name,list);
        long insertedRow= db.insert(CostDatabaseHelper.TABLE_LIST,null,values);
        this.close();
        if(insertedRow>0){
            return true;
        }
        else{
            return false;
        }
    }
    public ArrayList<ShopList> getAllList(){
        this.open();
        ArrayList<ShopList>items=new ArrayList<>();
        Cursor c= db.query(CostDatabaseHelper.TABLE_LIST,null,null,null,null,null,null);
        if(c!=null && c.getCount()>0){
            c.moveToFirst();
            do{
                int id=c.getInt(c.getColumnIndex(CostDatabaseHelper.TBL_LIST_ID));
                String name=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_LIST_Name));
                ShopList sl=new ShopList(id,name);
                items.add(sl);
            }while(c.moveToNext());
        }
        c.close();
        this.close();
        return items;
    }

    public boolean deleteList(int itemId){
        this.open();
        /*count=0;
        for (int i=0;i<itemId.size();i++) {
             deletedRow = db.delete(CostDatabaseHelper.TABLE_LIST, CostDatabaseHelper.TBL_COST_ID + " = " + itemId, null);
            if(deletedRow > 0)
                count++;
        }
        this.close();
        if(count==itemId.size()){
            return true;
        }*/
        deletedRow = db.delete(CostDatabaseHelper.TABLE_LIST, CostDatabaseHelper.TBL_COST_ID + " = " + itemId, null);
        if(deletedRow>0){
            return true;
        }
        else{
            return false;
        }
    }

    //get All cost

    public List<FamilyCost> getAllCost(){
        this.open();
        List<FamilyCost>costs=new ArrayList<>();
        Cursor c= db.query(CostDatabaseHelper.TABLE_COST,null,null,null,null,null,null);
        if(c!=null && c.getCount()>0){
            c.moveToFirst();
            do{
                int id=c.getInt(c.getColumnIndex(CostDatabaseHelper.TBL_COST_ID));
                String date=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_DATE));
                String category=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_CATEGORY));
                String product=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRUDUCT));
                String quantity=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_QUANTITY));
                double price=c.getDouble(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRICE));

                FamilyCost fc=new FamilyCost(id,date,category,product,quantity,price);
                costs.add(fc);
            }while(c.moveToNext());
        }
        c.close();
        this.close();
        return costs;
    }

    public boolean deleteCost(int costId){
        this.open();
        int deletedRow = db.delete(CostDatabaseHelper.TABLE_COST,CostDatabaseHelper.TBL_COST_ID+" = "+costId,null);
        this.close();
        if(deletedRow > 0){
            return true;
        }else{
            return false;
        }
    }
    public FamilyCost getCostId(int rowId){
        this.open();
        FamilyCost familycost = null;
        Cursor c = db.query(CostDatabaseHelper.TABLE_COST,null,CostDatabaseHelper.TBL_COST_ID+" = "+rowId,null,null,null,null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex(CostDatabaseHelper.TBL_COST_ID));
            String date = c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_DATE));
            String category = c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_CATEGORY));
            String product = c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRUDUCT));
            String quantity = c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_QUANTITY));
            double price = c.getDouble(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRICE));

            familycost = new FamilyCost(id,date,category,product,quantity,price);
        }
        c.close();
        this.close();
        return familycost;
    }

    public boolean updateCost(FamilyCost fc){
        this.open();
        ContentValues values = new ContentValues();
        values.put(CostDatabaseHelper.TBL_COST_DATE,fc.getDate());
        values.put(CostDatabaseHelper.TBL_COST_CATEGORY,fc.getCategory());
        values.put(CostDatabaseHelper.TBL_COST_PRUDUCT,fc.getProduct());
        values.put(CostDatabaseHelper.TBL_COST_QUANTITY,fc.getQuantity());
        values.put(CostDatabaseHelper.TBL_COST_PRICE,fc.getPrice());
        int updatedRow = db.update(CostDatabaseHelper.TABLE_COST,values,CostDatabaseHelper.TBL_COST_ID+" = "+fc.getId(),null);
        this.close();
        if(updatedRow > 0){
            return true;
        }else{
            return false;
        }
    }
    public List<FamilyCost> getSearchCost(String cate,String from,String to){
        this.open();
        List<FamilyCost>costs=new ArrayList<>();
        String selectQuery;
        if (cate.equals("All")  && from.isEmpty() && to.isEmpty()){
            selectQuery="select "+ "* From "+ CostDatabaseHelper.TABLE_COST;
        }
        else if (!cate.equals("All")&& from.isEmpty() && to.isEmpty()){
            selectQuery = "select " + "* From " + CostDatabaseHelper.TABLE_COST+" where " + CostDatabaseHelper.TBL_COST_CATEGORY + " = " + "'" + cate + "'"+" ORDER BY "+ CostDatabaseHelper.TBL_COST_DATE+ " ASC";
        }
        else if(cate.equals("All")){
             selectQuery="select "+ "* From "+ CostDatabaseHelper.TABLE_COST+" where "+CostDatabaseHelper.TBL_COST_DATE +" BETWEEN "+ "'"+from+"'" + " and "+ "'"+to+"'"+ " ORDER BY "+ CostDatabaseHelper.TBL_COST_DATE+ " ASC";

        }
        else {
            selectQuery = "select " + "* From " + CostDatabaseHelper.TABLE_COST + " where " + CostDatabaseHelper.TBL_COST_DATE + " BETWEEN " + "'" + from + "'" + " and " + "'" + to + "'" + " and " + CostDatabaseHelper.TBL_COST_CATEGORY + " = " + "'" + cate + "'"+" ORDER BY "+ CostDatabaseHelper.TBL_COST_DATE+ " ASC";
        }
        Cursor c= db.rawQuery(selectQuery,null);
        if(c!=null && c.getCount()>0){
            c.moveToFirst();
            do{
                int id=c.getInt(c.getColumnIndex(CostDatabaseHelper.TBL_COST_ID));
                String date=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_DATE));
                String category=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_CATEGORY));
                String product=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRUDUCT));
                String quantity=c.getString(c.getColumnIndex(CostDatabaseHelper.TBL_COST_QUANTITY));
                double price=c.getDouble(c.getColumnIndex(CostDatabaseHelper.TBL_COST_PRICE));

                FamilyCost fc=new FamilyCost(id,date,category,product,quantity,price);
                costs.add(fc);
            }while(c.moveToNext());
        }
        c.close();
        this.close();
        return costs;
    }

}
