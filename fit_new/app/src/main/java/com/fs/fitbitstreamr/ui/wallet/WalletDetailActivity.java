package com.fs.fitbitstreamr.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fs.fitbitstreamr.R;
import com.fs.fitbitstreamr.base.BaseActivity;
import com.fs.fitbitstreamr.domain.ETHWallet;
import com.fs.fitbitstreamr.domain.TradeBeaen;
import com.fs.fitbitstreamr.utils.ToastUtils;
import com.fs.fitbitstreamr.utils.Utils;
import com.fs.fitbitstreamr.utils.WalletDaoUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.web3j.protocol.admin.AdminFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jnr.ffi.annotations.In;

public class WalletDetailActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn_copy)
    Button btnCopy;

    private ETHWallet ethWallet;
    private TradeAdapter adapter;
    private int page = 1;
    private boolean isShow = true;
    private DecimalFormat df;
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText("Wallet");
    }

    @Override
    public void initDatas() {
        df = new DecimalFormat("######0.0");
        if (WalletDaoUtils.getCurrent() != null) {
            ethWallet = WalletDaoUtils.getCurrent();
            tvAddress.setText(ethWallet.getAddress());
            showDialog("加载中...");
            LoadData();
            getBalance(ethWallet.getAddress());
        }
    }

    @Override
    public void configViews() {
        adapter = new TradeAdapter(null);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setOnLoadMoreListener(this, rv);
        rv.setAdapter(adapter);
    }

    private static final String DATA_PREFIX = "0x70a08231000000000000000000000000";
    private void getBalance(String adddress) {
        Observable.create((ObservableOnSubscribe<String>) e -> {
            String value = AdminFactory.build(new HttpService("https://mainnet.infura.io/"))
                    .ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(adddress,
                            "0x0Cf0Ee63788A0849fE5297F3407f701E122cC023", DATA_PREFIX + adddress.substring(2)), DefaultBlockParameterName.PENDING).send().getValue();
            BigInteger s = new BigInteger(value.substring(2), 16);
            e.onNext(toDecimal(18, s));
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String balance) {
                        tvData.setText(df.format(Double.parseDouble(balance)));
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        if (tvData != null)
                            tvData.setText("error");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void LoadData() {
        String url = "https://api.etherscan.io/api?module=account&action=tokentx&contractaddress=0x0Cf0Ee63788A0849fE5297F3407f701E122cC023&address=" + ethWallet.getAddress() + "&page=" + page + "&offset=20&sort=asc";
        OkGo.<String>get(url)
                .retryCount(3)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissDialog();
                        Gson gson = new Gson();
                        TradeBeaen bean = gson.fromJson(response.body(), TradeBeaen.class);
                        if ("1".equals(bean.getStatus())) {
                            adapter.addData(bean.getResult());
                            adapter.notifyDataSetChanged();
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        LoadData();
    }

    @OnClick({ R.id.btn_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_copy:
                if (isShow){
                    tvAddress.setVisibility(View.VISIBLE);
                    tvData.setVisibility(View.INVISIBLE);
                    tvShow.setVisibility(View.INVISIBLE);
                    btnCopy.setText("COPY ADDRESS");
                    isShow = false;
                }else{
//                    tvAddress.setVisibility(View.INVISIBLE);
//                    tvData.setVisibility(View.VISIBLE);
//                    tvShow.setVisibility(View.VISIBLE);
                    Utils.copy(mContext, tvAddress.getText().toString().trim());
                    btnCopy.setText("COPIED!");
                }
                break;
        }
    }

    public String toDecimal(int decimal, BigInteger integer) {
        StringBuffer sbf = new StringBuffer("1");
        for (int i = 0; i < decimal; i++) {
            sbf.append("0");
        }
        return new BigDecimal(integer).divide(new BigDecimal(sbf.toString()), 18, BigDecimal.ROUND_DOWN).toPlainString();
    }
}
