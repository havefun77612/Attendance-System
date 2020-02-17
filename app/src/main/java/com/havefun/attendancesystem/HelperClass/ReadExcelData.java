package com.havefun.attendancesystem.HelperClass;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.havefun.attendancesystem.FirebaseClass.WriteToFirebase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcelData  {

Context mContext;

    public ReadExcelData(Context mContext) {
        this.mContext = mContext;
    }

    public void order(){

        try {


            AssetManager am = mContext.getAssets();
            InputStream is = am.open("student1.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            int row = s.getRows();
            int col = s.getColumns();
         //   String xx=" ";
            String temp[];
            HashMap<String,String>HElement=new HashMap<String, String>();
            HashMap<String,HashMap<String,String>>Container=new HashMap<String, HashMap<String, String>>();
            ArrayList<String>field=new ArrayList<String>();
            String sField = "";
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    Cell c = s.getCell(j,i);
                    sField=sField+c.getContents()+"/";
                  //  xx=xx+"/"+c.getContents();

                }
                field.add(sField);
                Log.i("ExcellNow", "coulme  "+ sField);
                sField="";
               //temp=xx.split("/");
                //Log.i("ExcellNow", "coulme  "+ Integer.toString(temp.length));

                //xx=xx+"\n";
                //Log.i("ExcellNow", "coulme  "+i+" "+xx);


            }
            for(int i=1;i<field.size();i++){
               temp=field.get(i).split("/");
                HElement.put("Name",temp[0]);
                HElement.put("Gender",temp[1]);
                HElement.put("AcadYear",temp[2]);
                HElement.put("ID",temp[3]);
                HElement.put("Email",temp[4]);
                HElement.put("National-ID",temp[5]);
                HElement.put("Supervisor",temp[6]);
                HElement.put("Nationality",temp[7]);
                HElement.put("BDate",temp[8]);
                HElement.put("Phone",temp[9]);
                HElement.put("NID-place",temp[10]);
                HElement.put("BPlace",temp[11]);
                HElement.put("Religion",temp[12]);
                HElement.put("AcademicQualification",temp[13]);
                HElement.put("Address",temp[14]);
                HElement.put("EnrollmentStatus",temp[15]);
                HElement.put("Department",temp[16]);
                HElement.put("Status",temp[17]);
                HElement.put("MinorDep",temp[18]);
                HElement.put("Activity",temp[19]);
                HElement.put("Enlistment",temp[20]);
                HElement.put("MService",temp[21]);
                new WriteToFirebase(mContext).addAllStudentList(HElement, String.valueOf(i));
                //  Container.put("Student"+Integer.toString(i),HElement);

                //Log.i("array", "size  "+ Integer.toString(temp.length));
                //Log.i("array", "size  "+ field.get(i));

            }


            Log.i("array", "size  "+Container.size());




        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "Error reading the file "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
