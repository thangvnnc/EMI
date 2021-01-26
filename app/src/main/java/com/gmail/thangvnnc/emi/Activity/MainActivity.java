package com.gmail.thangvnnc.emi.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBComment;
import com.gmail.thangvnnc.emi.DBServer.API.APIService;
import com.gmail.thangvnnc.emi.DBServer.API.ApiUtils;
import com.gmail.thangvnnc.emi.Dialog.DialogHistory;
import com.gmail.thangvnnc.emi.Model.MResponse;
import com.gmail.thangvnnc.emi.Model.MSupport;
import com.gmail.thangvnnc.emi.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String TAG = "MainActivity";

    private Menu _menu = null;
    private CalcInterestPercentFragment _frgInterest = null;
    private CalcEMIFragment _frgEmi = null;
    private AboutFragment _frgAbout = null;
    private CommentFragment _frgComment = null;
    private Context _context = null;
    private AdView adView = null;
    private APIService _apiService = null;
    private DBComment _dbComment = null;

    private final static String TITLE_INTEREST = "Tính lãi suất";
    private final static String TITLE_EMI = "Tính tiền góp";
    private final static String TITLE_DEVELOPER = "Nhà phát triển";
    private final static String TITLE_SEND = "Đóng góp ý kiến";

    String androidId = null;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        AudienceNetworkAds.initialize(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        _context = this;

        _frgInterest = CalcInterestPercentFragment.newInstance(_context);
        _frgEmi = CalcEMIFragment.newInstance(_context);
        _frgAbout = AboutFragment.newInstance(_context);
        _frgComment = CommentFragment.newInstance(_context);
        _apiService = ApiUtils.getAPIService();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(TITLE_INTEREST);
        replace(CalcInterestPercentFragment.newInstance(_context));


        try {
            androidId = Settings.Secure.getString(_context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        initAdView();

//        if ("691f5cd0002b2778".equals(androidId) == false) {
//        initAdView();
//        }
//        sendAndroid();
    }

//    private void sendAndroid() {
//        if (androidId == null) return;
//
//        _apiService.saveDevice(null, androidId, new Date().getTime()).enqueue(new Callback<MResponse>() {
//            @Override
//            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
//                Log.w(TAG, "sendAndroid");
//            }
//
//            @Override
//            public void onFailure(Call<MResponse> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void initAdView() {
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView);
//        adView = new AdView(this, "419658932419622_419681735750675", AdSize.RECTANGLE_HEIGHT_250);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.RECTANGLE_HEIGHT_250);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

        // GOOGLE ADMOD
//        MobileAds.initialize(_context, "ca-app-pub-1815534300099898~2918478086");
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Log.v("onAdFailedToLoad", errorCode+"");
//            }
//
//            @Override
//            public void onAdLeftApplication () {
//                sendAdmod();
//            }
//        });
    }

//    private void sendAdmod() {
//        if (androidId == null) return;
//
//        _apiService.saveAdmod(null, androidId, new Date().getTime()).enqueue(new Callback<MResponse>() {
//            @Override
//            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
//                Log.w(TAG, "sendAddmod");
//            }
//
//            @Override
//            public void onFailure(Call<MResponse> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
//            }
//        });
//    }

    public void replace(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frl_main, fragment);
        transaction.commit();
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        _menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogHistory dialogHistory = new DialogHistory(_context);
            dialogHistory.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        _menu.getItem(0).setVisible(false);

        switch (item.getItemId()) {
            case R.id.nav_calc_interest:
                setTitle(TITLE_INTEREST);
                replace(_frgInterest);
                _menu.getItem(0).setVisible(true);
                break;

            case R.id.nav_calc_emi:
                setTitle(TITLE_EMI);
                replace(_frgEmi);
                break;

            case R.id.nav_support:
                showDialogSupporter();
                break;

            case R.id.nav_developer:
                setTitle(TITLE_DEVELOPER);
                replace(_frgAbout);
                break;

            case R.id.nav_send:
                setTitle(TITLE_SEND);
                replace(_frgComment);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showDialogSupporter() {
        final String message = _context.getString(R.string.phone_support);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(_context);
        builder1.setTitle("Hổ trợ tư vấn lãi suất");
        builder1.setMessage("Bạn có nhu cầu vay vốn hãy liên hệ đến tôi qua số điện thoại " + message+ "\nGặp Cẩm Tiên");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Gọi ngay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: " + message));
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Nhắn tin",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String message = _context.getString(R.string.phone_support);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + message));
                        intent.putExtra("sms_body", "Hãy tư vấn vay giúp tôi. Tôi muốn vay ");
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    @Override
    protected void onResume() {
        super.onResume();
        _dbComment = new DBComment(_context);
        List<MSupport> mSupportList = _dbComment.getAll();

        for(int idx = 0; idx < mSupportList.size(); idx++) {
            MSupport mSupport = mSupportList.get(idx);
            sendSupport(mSupport);
        }
    }

    public void sendSupport(final MSupport mSupport) {
        _apiService.saveSupport(null, mSupport.getContent(), new Date().getTime()).enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(@NonNull Call<MResponse> call, @NonNull Response<MResponse> response) {
                boolean delete = _dbComment.delete(mSupport.getId());
                Log.w(TAG, "Trạng thái gửi: " + delete);
            }

            @Override
            public void onFailure(@NonNull Call<MResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
