package com.ww.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.entity.OtherEntity;
import com.ww.sign.entity.SubListEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    String TAG = "AppSigning";

    // 展讯media签名
    // 5F:95:F1:F3:0F:89:73:87:76:9A:E2:2A:97:03:27:56:9C:6D:0B:89
    private static final String SIGN_SPRD_MEDIA_SHA1 = "5F95F1F30F897387769AE22A970327569C6D0B89";
    // F7:92:DC:56:0D:CB:42:D0:2E:A2:B8:77:E0:D8:B9:E6:DA:36:E0:3A:2F:23:19:FC:EC:07:E1:7D:E1:C9:88:F7
    private static final String SIGN_SPRD_MEDIA_SHA256 = "F792DC560DCB42D02EA2B877E0D8B9E6DA36E03A2F2319FCEC07E17DE1C988F7";

    // 沃特media签名
    // F8:FE:7E:C5:E9:55:ED:DF:62:0D:10:E4:E0:74:C7:CD:E3:93:CB:BE
    private static final String SIGN_WW_MEDIA_SHA1 = "F8FE7EC5E955EDDF620D10E4E074C7CDE393CBBE";
    // 7D:A6:06:4B:63:84:32:59:0C:E1:F2:A9:44:1A:47:1E:D4:45:AF:83:98:77:04:17:E7:AD:81:65:F8:AE:C8:36
    private static final String SIGN_WW_MEDIA_SHA256 = "7DA6064B638432590CE1F2A9441A471ED445AF8398770417E7AD8165F8AEC836";

    // 展讯platform签名
    // F1:A5:4A:3F:02:4A:8D:1B:74:D1:FF:1F:74:D3:BE:66:ED:79:31:2E
    private static final String SIGN_SPRD_PLATFORM_SHA1 = "F1A54A3F024A8D1B74D1FF1F74D3BE66ED79312E";
    // 53:04:91:5C:4B:B7:BA:CA:28:77:62:31:99:39:96:FD:E1:BA:FF:CB:BE:65:00:FB:0F:C7:F2:D3:A2:88:8C:B7
    private static final String SIGN_SPRD_PLATFORM_SHA256 = "5304915C4BB7BACA28776231993996FDE1BAFFCBBE6500FB0FC7F2D3A2888CB7";

    // 沃特platform签名
    // 0F:49:19:CC:80:AE:F9:40:E4:D3:D0:38:31:2D:B3:30:64:8F:A6:9D
    private static final String SIGN_WW_PLATFORM_SHA1 = "0F4919CC80AEF940E4D3D038312DB330648FA69D";
    // AC:FC:D8:16:F4:C4:84:89:79:E9:96:8A:20:3D:9C:1B:2B:4A:35:E3:65:C0:FD:DD:22:82:03:15:CD:A1:8D:A4
    private static final String SIGN_WW_PLATFORM_SHA256 = "ACFCD816F4C4848979E9968A203D9C1B2B4A35E365C0FDDD22820315CDA18DA4";

    private static final String SIGN_BLU_SHA1 = "23F68E794D093C7D7F78D81C7D358FB70E18D49C";
    private static final String SIGN_BLU_SHA256 = "5B56FA35FD4C4082DC2F9390C9DC1470802ECD5B623BDF55FC61EABF840EFACC";

    private static final String SIGN_MAINLINE_SHA1 = "B99DD2248A4882E560503BA1B6CB10EFB3808F21";
    private static final String SIGN_MAINLINE_SHA256 = "184605095BE6CA22D055F34EFAF01344FD3AB3B5638C30627610EEAE8A260B29";

    private static final String SIGN_GOOGLE_SHA1 = "38918A453D07199354F8B19AF05EC6562CED5788";
    private static final String SIGN_GOOGLE_SHA256 = "F0FD6C5B410F25CB25C3B53346C8972FAE30F8EE7411DF910480AD6B2D60DB83";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadApps();
    }

    private void initView() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    /**
     * 主要方法
     */
    @SuppressLint("QueryPermissionsNeeded")
    private void loadApps() {
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        Log.d(TAG, "chuxiao showTotalTestResult, chuxiaoSize apps.size: " + apps.size());
        showTotalTestResult(apps);
    }

    @SuppressLint("SetTextI18n")
    private void showTotalTestResult(List<PackageInfo> list) {
        Log.d(TAG, "chuxiao showTotalTestResult, chuxiaoSize list.size: " + list.size());
        if (list.isEmpty()) {
            return;
        }
        List<PackageInfo> listTotalPass = new ArrayList<>();
        List<PackageInfo> listTotalFail = new ArrayList<>();
        List<PackageInfo> listSprdPlatform = new ArrayList<>();
        List<PackageInfo> listSprdMedia = new ArrayList<>();
        List<PackageInfo> listWwPlatform = new ArrayList<>();
        List<PackageInfo> listWwMedia = new ArrayList<>();
        List<PackageInfo> listBlu = new ArrayList<>();
        List<PackageInfo> listMainLine = new ArrayList<>();
        List<PackageInfo> listGoogle = new ArrayList<>();
        List<PackageInfo> listOther = new ArrayList<>();
        for (PackageInfo item : list) {
            if (item == null) {
                continue;
            }
            String packageName = item.packageName;
            String tempSHA1 = getSingInfo(this, packageName, "SHA1");
            String tempSHA256 = getSingInfo(this, packageName, "SHA256");
            if (SIGN_SPRD_MEDIA_SHA1.equalsIgnoreCase(tempSHA1) || SIGN_SPRD_MEDIA_SHA256.equalsIgnoreCase(tempSHA256)) {
                // 异常展讯platform签名
                Log.d(TAG, "chuxiao showTotalTestResult fail, media packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult fail, media SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult fail, media SHA256: " + tempSHA256);
                listTotalFail.add(item);
                listSprdMedia.add(item);
            } else if (SIGN_SPRD_PLATFORM_SHA1.equalsIgnoreCase(tempSHA1) || SIGN_SPRD_PLATFORM_SHA256.equalsIgnoreCase(tempSHA256)) {
                // 异常展讯media签名
                Log.d(TAG, "chuxiao showTotalTestResult fail, platform packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult fail, platform SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult fail, platform SHA256: " + tempSHA256);
                listTotalFail.add(item);
                listSprdPlatform.add(item);
            } else {
                // 无展讯签名即pass
                Log.d(TAG, "chuxiao showTotalTestResult pass, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult pass, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult pass, SHA256: " + tempSHA256);
                listTotalPass.add(item);
            }

            if (SIGN_WW_MEDIA_SHA1.equalsIgnoreCase(tempSHA1) && SIGN_WW_MEDIA_SHA256.equalsIgnoreCase(tempSHA256)) {
                // 正常沃特media签名
                Log.d(TAG, "chuxiao showTotalTestResult ww media, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult ww media, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult ww media, SHA256: " + tempSHA256);
                listWwMedia.add(item);
            }
            if (SIGN_WW_PLATFORM_SHA1.equalsIgnoreCase(tempSHA1) && SIGN_WW_PLATFORM_SHA256.equalsIgnoreCase(tempSHA256)) {
                // 正常沃特platfrom签名
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA256: " + tempSHA256);
                listWwPlatform.add(item);
            }
            if (SIGN_BLU_SHA1.equalsIgnoreCase(tempSHA1) && SIGN_BLU_SHA256.equalsIgnoreCase(tempSHA256)) {
                // blu签名
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA256: " + tempSHA256);
                listBlu.add(item);
            }
            if (SIGN_MAINLINE_SHA1.equalsIgnoreCase(tempSHA1) && SIGN_MAINLINE_SHA256.equalsIgnoreCase(tempSHA256)) {
                // mainLine签名
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA256: " + tempSHA256);
                listMainLine.add(item);
            }
            if (SIGN_GOOGLE_SHA1.equalsIgnoreCase(tempSHA1) && SIGN_GOOGLE_SHA256.equalsIgnoreCase(tempSHA256)) {
                // google签名
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult ww platform, SHA256: " + tempSHA256);
                listGoogle.add(item);
            }
            if (!SIGN_SPRD_PLATFORM_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_SPRD_PLATFORM_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_SPRD_MEDIA_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_SPRD_MEDIA_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_WW_PLATFORM_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_WW_PLATFORM_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_WW_MEDIA_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_WW_MEDIA_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_BLU_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_BLU_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_MAINLINE_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_MAINLINE_SHA256.equalsIgnoreCase(tempSHA256)
                    && !SIGN_GOOGLE_SHA1.equalsIgnoreCase(tempSHA1)
                    && !SIGN_GOOGLE_SHA256.equalsIgnoreCase(tempSHA256)) {
                // 其它签名
                Log.d(TAG, "chuxiao showTotalTestResult other, packageName: " + packageName);
                Log.d(TAG, "chuxiao showTotalTestResult other, SHA1: " + tempSHA1);
                Log.d(TAG, "chuxiao showTotalTestResult other, SHA256: " + tempSHA256);
                listOther.add(item);
            }
        }
        ((TextView) findViewById(R.id.tv_total_test_result)).setText(listTotalPass.size() + "/" + list.size());
        ((TextView) findViewById(R.id.tv_abnormal_sprd_platform)).setText(listSprdPlatform.size() + "");
        ((TextView) findViewById(R.id.tv_abnormal_sprd_media)).setText(listSprdMedia.size() + "");
        ((TextView) findViewById(R.id.tv_normal_ww_platform)).setText(listWwPlatform.size() + "");
        ((TextView) findViewById(R.id.tv_normal_ww_media)).setText(listWwMedia.size() + "");
        ((TextView) findViewById(R.id.tv_blu)).setText(listBlu.size() + "");
        ((TextView) findViewById(R.id.tv_mainline)).setText(listMainLine.size() + "");
        ((TextView) findViewById(R.id.tv_google)).setText(listGoogle.size() + "");
        ((TextView) findViewById(R.id.tv_other)).setText(listOther.size() + "");
        // 总测试结果设置字体颜色
        if (listTotalFail == null || listTotalFail.isEmpty()) {
            ((TextView) findViewById(R.id.tv_total_test_result)).setTextColor(Color.GREEN);
            ((TextView) findViewById(R.id.tv_total_test_result)).append(":pass");
        } else {
            ((TextView) findViewById(R.id.tv_total_test_result)).setTextColor(Color.RED);
            ((TextView) findViewById(R.id.tv_total_test_result)).append(":fail");
        }

        ((LinearLayout) findViewById(R.id.tv_total_test_result).getParent()).setOnClickListener(view -> {
            if (listTotalFail == null || listTotalFail.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listTotalFail));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_abnormal_sprd_platform).getParent()).setOnClickListener(view -> {
            if (listSprdPlatform == null || listSprdPlatform.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listSprdPlatform));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_abnormal_sprd_media).getParent()).setOnClickListener(view -> {
            if (listSprdMedia == null || listSprdMedia.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listSprdMedia));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_normal_ww_platform).getParent()).setOnClickListener(view -> {
            if (listWwPlatform == null || listWwPlatform.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listWwPlatform));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_normal_ww_media).getParent()).setOnClickListener(view -> {
            if (listWwMedia == null || listWwMedia.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listWwMedia));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_blu).getParent()).setOnClickListener(view -> {
            if (listBlu == null || listBlu.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listBlu));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_mainline).getParent()).setOnClickListener(view -> {
            if (listMainLine == null || listMainLine.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listMainLine));
            startActivity(intent);
        });
        ((LinearLayout) findViewById(R.id.tv_google).getParent()).setOnClickListener(view -> {
            if (listGoogle == null || listGoogle.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listGoogle));
            startActivity(intent);
        });

        // 初始化其它未知签名分类的列表
        initOtherRv(listOther);

        ((LinearLayout) findViewById(R.id.tv_other).getParent()).setOnClickListener(view -> {
            if (listOther == null || listOther.isEmpty()) {
                Toast.makeText(MainActivity.this, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(listOther));
            startActivity(intent);
        });
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化其它未知签名分类的RecyclerView
     */
    private void initOtherRv(List<PackageInfo> listOther) {
        Set<String> set = getSignSet(listOther);
        List<OtherEntity> otherEntityList = getOtherList(set, listOther);
        RecyclerView rvOther = findViewById(R.id.rv_other);
        rvOther.setLayoutManager(new LinearLayoutManager(this));
        rvOther.setAdapter(new OtherAdapter(this, otherEntityList));
    }

    /**
     * 获取要显示在首页的分类签名
     */
    private List<OtherEntity> getOtherList(Set<String> set, List<PackageInfo> list) {
        List<OtherEntity> resultList = new ArrayList<>();
        List<PackageInfo> otherSingleList = new ArrayList<>();
        for (String str : set) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            List<PackageInfo> typeList = new ArrayList<>();
            for (PackageInfo item : list) {
                if (str.equals(getSingInfo(this, item.packageName, SHA1))) {
                    typeList.add(item);
                }
            }
            if (typeList.size() <= 1) {
                // 独立签名：只有一个app有这个签名
                otherSingleList.addAll(typeList);
                continue;
            }
            // 数量多于一个的分类签名
            OtherEntity entity = new OtherEntity();
            entity.typeList = typeList;
            resultList.add(entity);
        }
        // 其它独立签名，放在一起显示
        OtherEntity entity = new OtherEntity();
        entity.typeList = otherSingleList;
        entity.isOtherSingle = true;
        resultList.add(entity);
        return resultList;
    }

    /**
     * 去重
     */
    private Set<String> getSignSet(List<PackageInfo> list) {
        Set<String> set = new LinkedHashSet<>();
        for (PackageInfo item : list) {
            set.add(getSingInfo(this, item.packageName, SHA1));
        }
        return set;
    }

    /**
     * Created by lWX537240 on 2019/6/13.
     */
    public final static String MD5 = "MD5";
    public final static String SHA1 = "SHA1";
    public final static String SHA256 = "SHA256";

    /**
     * 返回一个签名的对应类型的字符串
     */
    public static String getSingInfo(Context context, String packageName, String type) {
        String tmp = "error!";
        try {
            Signature[] signs = getSignatures(context, packageName);
//                Log.e(TAG, "signs =  " + Arrays.asList(signs));
            Signature sig = null;
            if (signs != null) {
                sig = signs[0];
            }

            if (MD5.equals(type)) {
                tmp = getSignatureString(sig, MD5);
            } else if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
            } else if (SHA256.equals(type)) {
                tmp = getSignatureString(sig, SHA256);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     */
    public static String getSignatureString(Signature sig, String type) {
        if (sig == null) {
            return "";
        }
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            StringBuilder builder = new StringBuilder();
            MessageDigest digest = MessageDigest.getInstance(type);
            digest.reset();
            digest.update(hexBytes);
            byte[] byteArray = digest.digest();
            for (byte b : byteArray) {
                if (Integer.toHexString(0xFF & b).length() == 1) {
                    builder.append("0").append(Integer.toHexString(0xFF & b)); //补0，转换成16进制
                } else {
                    builder.append(Integer.toHexString(0xFF & b));//转换成16进制
                }
            }
            fingerprint = builder.toString().toUpperCase(); //转换成大写
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fingerprint;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            loadApps();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
