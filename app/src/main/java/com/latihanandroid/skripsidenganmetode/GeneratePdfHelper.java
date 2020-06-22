package com.latihanandroid.skripsidenganmetode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.latihanandroid.skripsidenganmetode.dao.DailySchedule;
import com.latihanandroid.skripsidenganmetode.dao.RolesAndGoals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GeneratePdfHelper {
    private Context context;
    private PdfDocument pdfDocument;
    private PdfDocument.PageInfo pageInfo;
    private PdfDocument.Page page;
    private Canvas canvas;
    private final int pageHeight=420;
    private final int pageWidth=298;
    private final int headerY=40;
    private final int tableHeaderY=70;
    private final int tableDataYFirstPage=85;
    private final int tableDataYSecondPage=70;
    private final String TAG="TAG";
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GeneratePdfHelper(Context context) {
        this.context = context;
        this.pdfDocument=new PdfDocument();

        this.pageInfo=new PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create();
        page=this.pdfDocument.startPage(pageInfo);
        this.canvas=page.getCanvas();
    }



    private void writeHeaderDailySchedule(String hari){
        final int headX=pageInfo.getPageWidth()/2;
        final int headY=40;

        Paint paint=new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(16f);
        paint.setFakeBoldText(true);

        canvas.drawText(hari+"'s Schedule",headX,headY,paint);
    }
    private void writeTableHeaderDailySchedule(){
        Paint paint=new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(12f);
        paint.setFakeBoldText(true);

        final int tableHeadY=70;
        final int waktuHeadX=20;
        final int tempatHeadX=65;
        final int keteranganX=130;

        canvas.drawText("Time",waktuHeadX,tableHeadY,paint);
        canvas.drawText("Place",tempatHeadX,tableHeadY,paint);
        canvas.drawText("Activity",keteranganX,tableHeadY,paint);
    }
    private int writeSingleRowDailySchedule(int yCoor, String waktu, String tempat, String keterangan){
        final int maxCharOfKeterangan=21;
        final int maxCharOfTempat=10;
        int tempatRow= tempat.length()/(maxCharOfTempat+1)+(tempat.length()%(maxCharOfTempat+1)==0?0:1);
        int keteranganRow= keterangan.length()/(maxCharOfKeterangan+1)+(keterangan.length()%(maxCharOfKeterangan+1)==0?0:1);
        int longestRow= Math.max(tempatRow, keteranganRow);
        final int wktX=20;
        final int tmpX=65;
        final int ketX=130;
        final int lineHeight=15;
        Paint paint=new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10f);

        if (yCoor>pageHeight-20){
            canvas.drawText(waktu,wktX,tableHeaderY,paint);
        }else {
            canvas.drawText(waktu,wktX,yCoor,paint);
        }
        int yCoorNow=tableDataYFirstPage;
        int j=0;
        for (int i = 0; i < longestRow; i++) {
            int startIndexTempat=i*maxCharOfTempat;
            int endIndexTempat=startIndexTempat+maxCharOfTempat;
            int startIndexKeterangan=i*maxCharOfKeterangan;
            int endIndexKeterangan=startIndexKeterangan+maxCharOfKeterangan;
            if (endIndexTempat>tempat.length()-1){
                endIndexTempat=tempat.length();
            }
            if (endIndexKeterangan>keterangan.length()-1){
                endIndexKeterangan=keterangan.length();
            }
            yCoorNow= yCoor+j*lineHeight;
            if (yCoorNow>pageHeight-20){
                pdfDocument.finishPage(page);
                page=pdfDocument.startPage(pageInfo);
                canvas=page.getCanvas();
                yCoorNow=tableHeaderY;
                yCoor=tableHeaderY;
                j=0;
            }
            if (startIndexTempat<tempat.length()){
                canvas.drawText(tempat.substring(startIndexTempat,endIndexTempat),tmpX,yCoorNow,paint);
            }
            if (startIndexKeterangan<keterangan.length()){
                canvas.drawText(keterangan.substring(startIndexKeterangan,endIndexKeterangan),ketX,yCoorNow,paint);
            }
            j++;
        }

        return yCoorNow+lineHeight;
    }


    public void createPdfDailySchedule(ArrayList<DailySchedule> dailySchedules){
        String hari=dailySchedules.get(0).getHariAsString();
        writeHeaderDailySchedule(hari);
        writeTableHeaderDailySchedule();
        int lastYCoord= tableDataYFirstPage;
        for (int i=0;i<dailySchedules.size();i++){
            lastYCoord=writeSingleRowDailySchedule(lastYCoord,dailySchedules.get(i).getWaktuAsString(),dailySchedules.get(i).getTempat(),dailySchedules.get(i).getAktiviasAsString());
        }
        pdfDocument.finishPage(page);
        openPdfFile(pdfDocument,hari+"'s Schedule Report");
        pdfDocument.close();
    }
    private Uri createUriPdfFile(PdfDocument pdfDocument,String fileName){
        File file=new File(Environment.getExternalStorageDirectory(),"/"+fileName+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }
    private void openPdfFile(PdfDocument pdfDocument,String fileName){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(createUriPdfFile(pdfDocument,fileName),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }
    private int writeSingleRowDataRolesGoals(int yCoor, String roles, String goals){
        final int maxCharOfRoles=11;
        final int maxCharOfGoals=29;
        int rolesRow= roles.length()/(maxCharOfRoles+1)+(roles.length()%(maxCharOfRoles+1)==0?0:1);
        int goalsRow= goals.length()/(maxCharOfGoals+1)+(goals.length()%(maxCharOfGoals+1)==0?0:1);
        int longestRow= Math.max(rolesRow, goalsRow);
        final int rX=20;
        final int gX=100;
        final int lineHeight=15;

        Paint paint=new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10f);
        int j=0;
        int yCoorNow=0;
        for (int i=0;i<longestRow;i++){
            int starindexR= i*maxCharOfRoles;
            int endindexR=starindexR+maxCharOfRoles;
            int startindexG=i*maxCharOfGoals;
            int endIndexG=startindexG+maxCharOfGoals;

            if (endIndexG>goals.length()){
                endIndexG=goals.length();
            }
            if (endindexR>roles.length()-1){
                endindexR=roles.length();
            }
            yCoorNow=yCoor+j*lineHeight;
            if (yCoorNow>pageHeight-20){
                pdfDocument.finishPage(page);
                page=pdfDocument.startPage(pageInfo);
                yCoorNow=70;
                yCoor=70;
                j=0;
            }


            if (startindexG<goals.length()){
                canvas =page.getCanvas();
                canvas.drawText(goals.substring(startindexG,endIndexG),gX,yCoorNow,paint);
            }
            if (starindexR<roles.length()){
                canvas =page.getCanvas();
                canvas.drawText(roles.substring(starindexR,endindexR),rX,yCoorNow,paint);
            }
            Log.d(TAG, String.valueOf(yCoorNow));

            j++;
        }
        return yCoorNow+lineHeight;
    }
    public void createPdfRolesGoals(ArrayList<RolesAndGoals> rolesAndGoals){

        writeHeaderRolesGoals();
        writeTableHeadRolesGoals();
        int lastYCoord= tableDataYFirstPage;
        for (int i=0;i<rolesAndGoals.size();i++){
            lastYCoord=writeSingleRowDataRolesGoals(lastYCoord,rolesAndGoals.get(i).getRoles(),rolesAndGoals.get(i).getGoals());
        }
        pdfDocument.finishPage(page);
        openPdfFile(pdfDocument,"RolesAndGoalsReport");
        pdfDocument.close();
    }

    private void writeHeaderRolesGoals(){
        Paint paint=new Paint();
        final int headerY=40;
        final int headerX=pageInfo.getPageWidth()/2;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(16f);
        paint.setFakeBoldText(true);
        canvas.drawText("Weekly Goals",headerX,headerY,paint);
    }
    private void writeTableHeadRolesGoals(){
        Paint paint=new Paint();
        final int tableHeadY=70;
        final int rolesHeadX=20;//20-59 11 huruf
        final int goalsHeadX=100;//60-n 29 huruf
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(12f);
        paint.setFakeBoldText(true);
        canvas.drawText("Roles",rolesHeadX,tableHeadY,paint);
        canvas.drawText("Goals",goalsHeadX,tableHeadY,paint);
    }

}
