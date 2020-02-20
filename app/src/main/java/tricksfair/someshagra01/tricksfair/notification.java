package tricksfair.someshagra01.tricksfair;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apptracker.android.util.AppLog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import com.apptracker.android.track.AppTracker;


public class notification extends AppCompatActivity {

    DocumentReference documentReference  = FirebaseFirestore.getInstance().document("Notification/Notify");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Ads");
    List<String> keys = new ArrayList<>();
    List<String> values = new ArrayList<>();
    List<String> url = new ArrayList<>();
    List<String> images =  new ArrayList<>();
    List<String> dates = new ArrayList<>();
    String[] rawValues = new String[]{};
    LinearLayout lm,main;
    CustomTabs customTabs;
    Toolbar toolbar;
    int i,length,notiOpenNo=0;
    public String notiClose,notiOpenAd,isComingFromPushNotification="no";
    ImageView ic_notification_loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = (Toolbar) findViewById(R.id.toolbar_noti);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Bundle extras = getIntent().getExtras();
            isComingFromPushNotification = extras.getString("isComingFromPushNotification");

            if(isComingFromPushNotification.equals("yes")){
                AppLog.enableLog(true);
                AppTracker.startSession(getApplicationContext(), "REMOVED_IN_PUBLIC_REPOSITORY");
            }

            notiClose = extras.getString("notiClose");
            notiOpenAd = extras.getString("notiOpenAd");
            if (!(notiClose.equalsIgnoreCase("no"))) {
                AppTracker.destroyModule();
                AppTracker.loadModuleToCache(getApplicationContext(), "inapp");
            }
        }
        catch(Exception e){

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Ads a = dataSnapshot.getValue(Ads.class);
                    notiClose=a.notiClose;
                    notiOpenAd=a.notiOpen;
                    if(!(notiClose.equalsIgnoreCase("no"))){
                        AppTracker.destroyModule();
                        AppTracker.loadModuleToCache(getApplicationContext(), "inapp");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //AppLog.enableLog(true);
        ic_notification_loading = (ImageView) findViewById(R.id.ic_loading_notification);
        Glide.with(this).asGif().load(R.drawable.loading5).into(ic_notification_loading);

        //AppTracker.startSession(getApplicationContext(), "0H9UgT2sktyBmz3XF4y2OqTam78FWuYu");

        lm = (LinearLayout) findViewById(R.id.noti_linearLayout);
        getNotifications();

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void getNotifications(){

        try {
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    String k = document.getString("keys");
                    String[] a = k.split(",", -2);
                    int num = a.length;
                    keys.clear();values.clear();url.clear();images.clear();dates.clear();
                    for (int j = 0; j < num; j++) {
                        String tempKey = a[j];
                        keys.add(tempKey);
                        rawValues = (document.getString(tempKey)).split(" --- ", -2);
                        values.add(rawValues[0]);
                        url.add(rawValues[1]);
                        images.add(rawValues[2]);
                        dates.add(rawValues[3]);
                    }
                    setNotifications();
                }
            });
        }
        catch(Exception e){

            AlertDialog.Builder slowInternetAlert = new AlertDialog.Builder(notification.this);
            slowInternetAlert.setTitle("Slow Internet Connection")
                    .setMessage("It seems that you Internet connection is slow, Please allow us some time to Setup :) \n Thank you for your patience!")
                    .setIcon(R.drawable.ic_dailog_info_black)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if(isComingFromPushNotification.equals("no")) {
            super.onBackPressed();
        }
        else{
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        if(AppTracker.isAdReady("inapp"))
        AppTracker.loadModule(getApplicationContext(), "inapp");
    }

    private void setNotifications(){

        ic_notification_loading.setVisibility(View.GONE);

        length = keys.size();
        for (i=0; i<length; i++) {
            main = (LinearLayout) View.inflate(this, R.layout.notification_layout, null);
            //notification_layout = (LinearLayout) View.inflate(this,R.layout.notification_layout,null);
            main.setId(1000 + i);
            ((TextView) main.findViewById(R.id.noti_heading)).setText(keys.get(i));
            ((TextView) main.findViewById(R.id.noti_info)).setText(values.get(i));
            ((TextView) main.findViewById(R.id.noti_date)).setText(dates.get(i));
            Glide.with(getApplicationContext()).load(images.get(i))
                    .into(((ImageView) main.findViewById(R.id.noti_imageView)));
            main.setOnClickListener(Onclick());
            lm.addView(main);
        }
    }

    View.OnClickListener Onclick(){
        return new View.OnClickListener(){
            public void onClick(View v){
                int id = v.getId();
                for(int k=0;k<length;k++){
                    if(id==(1000+k))
                        if(!((url.get(k).equalsIgnoreCase("none")) || (url.get(k).equalsIgnoreCase("no")))){
                            if(!notiOpenAd.equals("0"))
                                if(notiOpenNo%Integer.parseInt(notiOpenAd)==0)
                                    if(AppTracker.isAdReady("inapp"))
                                        AppTracker.loadModule(getApplicationContext(), "inapp");
                            customTabs.openTab(notification.this,url.get(k).trim());
                            notiOpenNo+=1;
                    }
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==android.R.id.home)
            notification.super.onBackPressed();

        return true;
    }
}
