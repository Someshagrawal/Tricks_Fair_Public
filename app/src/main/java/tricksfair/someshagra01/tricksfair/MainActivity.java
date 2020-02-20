package tricksfair.someshagra01.tricksfair;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apptracker.android.track.AppTracker;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    slideshowAdapter adapter;
    Toolbar toolbar;
    CircleIndicator indicator;
    CustomTabs customTabs;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference AdReference = databaseReference.child("Ads");
    DocumentReference documentReference  = FirebaseFirestore.getInstance().document("Notification/Notify");
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,sub_title;
    ImageButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
    CardView[] small_cardView = new CardView[10];
    //String web_site,web_site1,web_site2,web_site3,web_site4,web_site5,web_site6,web_site7,web_site8,web_site9,web_site10,
    String web_site, notiClose="no",webCloseAd="0",notiOpenAd="0",directAd="no";
    String version,updates,keysSlideShow,keysNotification,url_img;
    String[] keysArraySlideShow,url_image_Array,webSiteArray = new String[10];
    List<String> images = new ArrayList<>();
    int n=0,iwebCloseAd,web_open_no=0;
    public List<String> slideImageUrl = new ArrayList<>();
    SharedPreferences sharedPreferences;
    //AlertDialog.Builder versionUpdateDialog;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        //OneSignal.startInit(this).setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler()).init();


        haveNetwork();

        AppLog.enableLog(true);
        AppTracker.startSession(getApplicationContext(), "REMOVED_IN_PUBLIC_REPOSITORY");

        setSlideImages();
        indicator = (CircleIndicator) findViewById(R.id.circleindicator_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new slideshowAdapter(MainActivity.this,images,slideImageUrl);
        indicator.setViewPager(viewPager);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        b1 = (ImageButton) findViewById(R.id.imageButton1);
        b2 = (ImageButton) findViewById(R.id.imageButton2);
        b3 = (ImageButton) findViewById(R.id.imageButton3);
        b4 = (ImageButton) findViewById(R.id.imageButton4);
        b5 = (ImageButton) findViewById(R.id.imageButton5);
        b6 = (ImageButton) findViewById(R.id.imageButton6);
        b7 = (ImageButton) findViewById(R.id.imageButton7);
        b8 = (ImageButton) findViewById(R.id.imageButton8);
        b9 = (ImageButton) findViewById(R.id.imageButton9);
        b10 = (ImageButton) findViewById(R.id.imageButton10);

        Glide.with(this).asGif().load(R.drawable.loading5).into(b1);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b2);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b3);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b4);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b5);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b6);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b7);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b8);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b9);
        Glide.with(this).asGif().load(R.drawable.loading5).into(b10);

        t1 = (TextView) findViewById(R.id.storename1);
        t2 = (TextView) findViewById(R.id.storename2);
        t3 = (TextView) findViewById(R.id.storename3);
        t4 = (TextView) findViewById(R.id.storename4);
        t5 = (TextView) findViewById(R.id.storename5);
        t6 = (TextView) findViewById(R.id.storename6);
        t7 = (TextView) findViewById(R.id.storename7);
        t8 = (TextView) findViewById(R.id.storename8);
        t9 = (TextView) findViewById(R.id.storename9);
        t10 = (TextView) findViewById(R.id.storename10);
        sub_title = (TextView) findViewById(R.id.sub_title);

        Typeface Roboto_Regular = Typeface.createFromAsset(getAssets(),  "fonts/RobotoSlab-Regular.ttf");
        sub_title.setTypeface(Roboto_Regular);
        t1.setTypeface(Roboto_Regular);
        t2.setTypeface(Roboto_Regular);
        t3.setTypeface(Roboto_Regular);
        t4.setTypeface(Roboto_Regular);
        t5.setTypeface(Roboto_Regular);
        t6.setTypeface(Roboto_Regular);
        t7.setTypeface(Roboto_Regular);
        t8.setTypeface(Roboto_Regular);
        t9.setTypeface(Roboto_Regular);
        t10.setTypeface(Roboto_Regular);

        small_cardView[0] = (CardView) findViewById(R.id.small_cardview1);
        small_cardView[1] = (CardView) findViewById(R.id.small_cardview2);
        small_cardView[2] = (CardView) findViewById(R.id.small_cardview3);
        small_cardView[3] = (CardView) findViewById(R.id.small_cardview4);
        small_cardView[4] = (CardView) findViewById(R.id.small_cardview5);
        small_cardView[5] = (CardView) findViewById(R.id.small_cardview6);
        small_cardView[6] = (CardView) findViewById(R.id.small_cardview7);
        small_cardView[7] = (CardView) findViewById(R.id.small_cardview8);
        small_cardView[8] = (CardView) findViewById(R.id.small_cardview9);
        small_cardView[9] = (CardView) findViewById(R.id.small_cardview10);

        setData("Tricks");

        /*DatabaseReference infoReference = databaseReference.child("Info");
        versionUpdateDialog = new AlertDialog.Builder(this);
        infoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                version = dataSnapshot.child("Version").getValue().toString();
                updates = dataSnapshot.child("Updates").getValue().toString();
                if(!(version.equals(BuildConfig.VERSION_NAME))){
                    showVersionUpdateDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        sharedPreferences = this.getSharedPreferences("tricksfair.someshagra01.tricksfair",Context.MODE_PRIVATE);

        if(sharedPreferences.getString("All_Notifications_Subscribed","no").equals("no")) {
            FirebaseMessaging.getInstance().subscribeToTopic("All_Notifications")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Subscribed";
                            sharedPreferences.edit().putString("All_Notifications_Subscribed", "yes").commit();
                            if (!task.isSuccessful()) {
                                msg = "Unable to Subscribe";
                                sharedPreferences.edit().putString("All_Notifications_Subscribed", "no").commit();
                            }
                            Log.d("Noti subscription", msg);
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        AdReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Ads a = dataSnapshot.getValue(Ads.class);
                notiClose=a.notiClose;
                webCloseAd=a.webClose;
                notiOpenAd=a.notiOpen;
                directAd=a.directAd;

                if(!(webCloseAd.equals("0"))){
                    iwebCloseAd = Integer.parseInt(webCloseAd);
                    AppTracker.destroyModule();
                    AppTracker.loadModuleToCache(getApplicationContext(), "inapp");
                }

                setDirectAd(directAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(menu!=null)
            setNotificationIcon();
    }

    private void setNotificationIcon() {
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    keysNotification = document.getString("keys");
                    if (!(sharedPreferences.getString("Notifications", "").equals(keysNotification))) {
                        menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_noti_dot4));
                    }
                }
            });

    }

    private void setDirectAd(String directAd){

        //sharedPreferences.edit().putString("directAd",directAd).apply();
    }

    private void haveNetwork(){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI=true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData=true;
        }

        if(!(have_MobileData||have_WIFI))
        {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setCancelable(false);
            builder.setTitle("No Internet")
                    .setMessage("Please turn on your internet connection")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            haveNetwork();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }

    /*private void showVersionUpdateDialog(){

        versionUpdateDialog.setIcon(R.drawable.settings_icon)
        .setTitle("New Update Available")
        .setMessage(updates)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        })
        .setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }*/

    public void setSlideImages(){

        DatabaseReference slideImageData = databaseReference.child("slideShowImages");
        slideImageData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                images.clear();
                slideImageUrl.clear();
                keysSlideShow = dataSnapshot.child("keys").getValue().toString();
                keysArraySlideShow = keysSlideShow.split(",",-2);
                for(String s: keysArraySlideShow){
                    url_img = dataSnapshot.child(s).getValue().toString();
                    url_image_Array = url_img.split(" --- ",-2);
                    images.add(url_image_Array[1]);
                    slideImageUrl.add(url_image_Array[0]);
                    adapter.notifyDataSetChanged();
                }
                viewPager.setAdapter(adapter);
                indicator.setViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setData(String s) {
        sub_title.setText(s);
        DatabaseReference mref = databaseReference.child(s);
        ValueEventListener datalistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sub_title.setText(dataSnapshot.getKey().toString());
                web_data site_data = dataSnapshot.getValue(web_data.class);

                t1.setText(site_data.Site1.split(",",-2)[0]);
                t2.setText(site_data.Site2.split(",",-2)[0]);
                t3.setText(site_data.Site3.split(",",-2)[0]);
                t4.setText(site_data.Site4.split(",",-2)[0]);
                t5.setText(site_data.Site5.split(",",-2)[0]);
                t6.setText(site_data.Site6.split(",",-2)[0]);
                t7.setText(site_data.Site7.split(",",-2)[0]);
                t8.setText(site_data.Site8.split(",",-2)[0]);
                t9.setText(site_data.Site9.split(",",-2)[0]);
                t10.setText(site_data.Site10.split(",",-2)[0]);

                Glide.with(getApplicationContext()).load(site_data.Site1.split(",",-2)[2].trim()).into(b1);
                Glide.with(getApplicationContext()).load(site_data.Site2.split(",",-2)[2].trim()).into(b2);
                Glide.with(getApplicationContext()).load(site_data.Site3.split(",",-2)[2].trim()).into(b3);
                Glide.with(getApplicationContext()).load(site_data.Site4.split(",",-2)[2].trim()).into(b4);
                Glide.with(getApplicationContext()).load(site_data.Site5.split(",",-2)[2].trim()).into(b5);
                Glide.with(getApplicationContext()).load(site_data.Site6.split(",",-2)[2].trim()).into(b6);
                Glide.with(getApplicationContext()).load(site_data.Site7.split(",",-2)[2].trim()).into(b7);
                Glide.with(getApplicationContext()).load(site_data.Site8.split(",",-2)[2].trim()).into(b8);
                Glide.with(getApplicationContext()).load(site_data.Site9.split(",",-2)[2].trim()).into(b9);
                Glide.with(getApplicationContext()).load(site_data.Site10.split(",",-2)[2].trim()).into(b10);

                webSiteArray[0] = site_data.Site1.split(",",-2)[1].trim();
                webSiteArray[1] = site_data.Site2.split(",",-2)[1].trim();
                webSiteArray[2] = site_data.Site3.split(",",-2)[1].trim();
                webSiteArray[3] = site_data.Site4.split(",",-2)[1].trim();
                webSiteArray[4] = site_data.Site5.split(",",-2)[1].trim();
                webSiteArray[5] = site_data.Site6.split(",",-2)[1].trim();
                webSiteArray[6] = site_data.Site7.split(",",-2)[1].trim();
                webSiteArray[7] = site_data.Site8.split(",",-2)[1].trim();
                webSiteArray[8] = site_data.Site9.split(",",-2)[1].trim();
                webSiteArray[9] = site_data.Site10.split(",",-2)[1].trim();

                for(int i=0; i<webSiteArray.length; i++){
                    if(webSiteArray[i].equals("no"))
                        small_cardView[i].setVisibility(View.GONE);
                    else
                        small_cardView[i].setVisibility(View.VISIBLE);
                }

                View.OnClickListener buttonlistener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == b1)
                            web_site = webSiteArray[0];
                        else if(v == b2)
                            web_site = webSiteArray[1];
                        else if(v == b3)
                            web_site = webSiteArray[2];
                        else if(v == b4)
                            web_site = webSiteArray[3];
                        else if(v == b5)
                            web_site = webSiteArray[4];
                        else if(v == b6)
                            web_site = webSiteArray[5];
                        else if(v == b7)
                            web_site = webSiteArray[6];
                        else if(v == b8)
                            web_site = webSiteArray[7];
                        else if(v == b9)
                            web_site = webSiteArray[8];
                        else if(v == b10)
                            web_site = webSiteArray[9];

                        if(web_site.equalsIgnoreCase("none")){
                            AlertDialog.Builder noweb = new AlertDialog.Builder(MainActivity.this);
                            noweb.setIcon(R.drawable.ic_dailog_info_black)
                                    .setTitle("Stay tuned")
                                    .setMessage("The website is under review it will available soon")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                        else{
                            if(!(webCloseAd.equals("0")))
                                if(web_open_no%iwebCloseAd==0)
                                    if(AppTracker.isAdReady("inapp"))
                                        AppTracker.loadModule(getApplicationContext(), "inapp");

                            customTabs.openTab(MainActivity.this,web_site);
                            web_open_no+=1;
                        }
                    }
                };

                b1.setOnClickListener(buttonlistener);
                b2.setOnClickListener(buttonlistener);
                b3.setOnClickListener(buttonlistener);
                b4.setOnClickListener(buttonlistener);
                b5.setOnClickListener(buttonlistener);
                b6.setOnClickListener(buttonlistener);
                b7.setOnClickListener(buttonlistener);
                b8.setOnClickListener(buttonlistener);
                b9.setOnClickListener(buttonlistener);
                b10.setOnClickListener(buttonlistener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Failed Getting data !",Toast.LENGTH_SHORT).show();
                finish();

            }
        };

        mref.addValueEventListener(datalistener);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        setNotificationIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_os_notification_fallback_white_24dp));
            sharedPreferences.edit().putString("Notifications",keysNotification).commit();
            Intent notification = new Intent(getApplicationContext(),notification.class);
            notification.putExtra("notiClose",notiClose);
            notification.putExtra("notiOpenAd",notiOpenAd);
            notification.putExtra("isComingFromPushNotification","no");
            startActivity(notification);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tricks) {
            setData("Tricks");
        }
        else if (id == R.id.coupons) {
            setData("Coupons");
        }
        else if (id == R.id.cashback) {
            setData("Cashback");
        }
        else if (id == R.id.share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tricks Fair");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        else if (id == R.id.contact) {
            AlertDialog.Builder contactUs = new AlertDialog.Builder(MainActivity.this);
            contactUs.setCancelable(true)
                    .setIcon(R.drawable.contact_us)
                    .setTitle("Contact Us")
                    .setMessage("Write your query/review to us through \n\n Email:- someshagra01@gmail.com")
                    .setPositiveButton("Copy Email", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Tricks Fair contact", "someshagra01@gmail.com");
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this,"Copied",Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            contactUs.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
