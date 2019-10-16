package com.fs.fitbitstreamr.ui.wallet;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.domain.ETHWallet;
import com.fs.fitbitstreamr.utils.ETHWalletUtils;
import com.fs.fitbitstreamr.utils.PermissionUtils;
import com.fs.fitbitstreamr.utils.ToastUtils;
import com.fs.fitbitstreamr.utils.WalletDaoUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateWalletActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.tv_password)
    TextInputEditText tvPassword;
    private final int REQUEST_CODE_PERMISSIONS = 2;
    private final String[] PERMISSIONS = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_wallet;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("Create a Wallet");
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }


    @OnClick(R.id.btn_create)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(tvPassword.getText())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestMorePermissions();
            }else{
                initPermissons();
            }
        }

    }

    @NonNull
    private static String generateNewWalletName() {
        char letter1 = (char) (int) (Math.random() * 26 + 97);
        char letter2 = (char) (int) (Math.random() * 26 + 97);
        String walletName = String.valueOf(letter1) + String.valueOf(letter2) + "-新钱包";
        while (WalletDaoUtils.walletNameChecking(walletName)) {
            letter1 = (char) (int) (Math.random() * 26 + 97);
            letter2 = (char) (int) (Math.random() * 26 + 97);
            walletName = String.valueOf(letter1) + String.valueOf(letter2) + "-新钱包";
        }
        return walletName;
    }

    // 普通申请多个权限
    private void requestMorePermissions() {
        PermissionUtils.checkAndRequestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS, this::  initPermissons);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull @io.reactivex.annotations.NonNull String[] permissions, @androidx.annotation.NonNull @io.reactivex.annotations.NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            PermissionUtils.onRequestMorePermissionsResult(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {
                    initPermissons();
                }

                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) {
                    Toast.makeText(mContext, "We need" + Arrays.toString(permission), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                    Toast.makeText(mContext, "We need" + Arrays.toString(permission), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initPermissons() {
        showDialog("Creating");
        Observable.create((ObservableOnSubscribe<ETHWallet>) e -> {
            ETHWallet ethWallet = ETHWalletUtils.generateMnemonic(generateNewWalletName(), tvPassword.getText().toString());
            WalletDaoUtils.insertNewWallet(ethWallet);
            e.onNext(ethWallet);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ETHWallet>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dismissDialog();
                    }

                    @Override
                    public void onNext(ETHWallet wallet) {
                        ToastUtils.showLongToast("success");
                        startActivity(new Intent(mContext,WalletDetailActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLongToast(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

}
