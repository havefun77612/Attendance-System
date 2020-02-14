package com.havefun.attendancesystem.Chat;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.havefun.attendancesystem.R;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcelData extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }
    public void order(View v){

        try {


            AssetManager am = getAssets();
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

            }



        }
        catch (Exception e)
        {

        }
    }


}
