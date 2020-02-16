package com.havefun.attendancesystem.Chat;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.havefun.attendancesystem.R;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
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
            String xx=" ";
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    Cell c = s.getCell(j,i);
                    xx=xx+c.getContents();

                }
                xx=xx+"\n";
                Log.i("ExcellNow", "coulme  "+i+" "+xx);

            }



        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "Error reading the file "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
