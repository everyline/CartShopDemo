package com.hyyt.cartshopdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyyt.cartshopdemo.adapter.ExpandleCartAdapter;
import com.hyyt.cartshopdemo.bean.ShopEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.hyyt.cartshopdemo.R.id.btn_check_all_deit;
import static com.hyyt.cartshopdemo.R.id.cb_shop;
import static com.hyyt.cartshopdemo.R.id.layout_edit_bar;
import static com.hyyt.cartshopdemo.R.id.layout_pay_bar;
import static com.hyyt.cartshopdemo.R.id.rl_layout_bar;
import static com.hyyt.cartshopdemo.R.id.tv_count;
import static com.hyyt.cartshopdemo.R.id.tv_edit_cart;
import static com.hyyt.cartshopdemo.R.id.tv_money;

public class MainActivity extends AppCompatActivity implements ExpandleCartAdapter.selectCartOnClickListener {
    private static final String base_url = "http://122.114.34.91:8085/app/customer/shoppingCart/getShoppingCartData";
    @BindView(tv_edit_cart)
    TextView m_TvEditCart;
    @BindView(R.id.lv_shop)
    ExpandableListView m_LvShop;
    @BindView(cb_shop)
    CheckBox m_CbShop;
    @BindView(tv_money)
    TextView m_TvMoney;
    @BindView(tv_count)
    TextView m_TvCount;
    @BindView(R.id.ll_pay)
    LinearLayout m_LlPay;
    @BindView(layout_pay_bar)
    LinearLayout m_LayoutPayBar;
    @BindView(btn_check_all_deit)
    CheckBox m_BtnCheckAllDeit;
    @BindView(R.id.btn_delete)
    Button m_BtnDelete;
    @BindView(layout_edit_bar)
    LinearLayout m_LayoutEditBar;
    @BindView(rl_layout_bar)
    RelativeLayout m_RlLayoutBar;
    private double price; // 总价
    private int num; // 选中的商品数
    private boolean isEdit; // 是否正在编辑
    private ExpandleCartAdapter adapter;
    private ArrayList<ShopEntity.ShoppingCartBean> list = new ArrayList<>();
    private CompoundButton.OnCheckedChangeListener checkAllListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            m_CbShop.setChecked(isChecked);
            m_BtnCheckAllDeit.setChecked(isChecked);
            if (isChecked) {
                checkAll();
            } else {
                clearAll();
            }
            adapter.notifyDataSetChanged();
            notifMoneyChanged();
        }
    };

    //取消全选
    private void clearAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getShopInfo().setChoosed(false);
            for (int j = 0; j < list.get(i).getProductList().size(); j++) {
                list.get(i).getProductList().get(j).setChoosed(false);
            }
        }
    }

    //全部选中
    private void checkAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getShopInfo().setChoosed(true);
            for (int j = 0; j < list.get(i).getProductList().size(); j++) {
                list.get(i).getProductList().get(j).setChoosed(true);
            }
        }
    }

    private void notifMoneyChanged() {
        price = 0;
        num = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getProductList().size(); j++) {
                ShopEntity.ShoppingCartBean.ProductListBean entity = list.get(i).getProductList().get(j);
                if (entity.isChoosed()) {
                    num += entity.getQuantity();
                    price += entity.getQuantity() * entity.getSalesPrice();

                }
            }

        }

        m_TvMoney.setText("总额：" + formatPrice(price));
        m_TvCount.setText("数量：" + num);

    }

    /**
     * 格式化价格，强制保留2位小数
     *
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        String format = "¥" + df.format(price);
        return format;
    }

    private void editInCart() {
        isEdit = !isEdit;
        if (isEdit) {
            m_TvEditCart.setText("完成");
            m_LayoutPayBar.setVisibility(View.GONE);
            m_LayoutEditBar.setVisibility(View.VISIBLE);
        } else {
            m_TvEditCart.setText("编辑");
            m_LayoutPayBar.setVisibility(View.VISIBLE);
            m_LayoutEditBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new ExpandleCartAdapter(this, list);
        m_LvShop.setAdapter(adapter);
        adapter.setCartOnClickListener(this);
        initData();
    }

    private void initData() {
        OkGo.post(base_url).params("customerId", "1122")
                .params("customerToken", "935ddf2ac25f9ff0_1122_20161021150135973")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShopEntity entity = ShopEntity.objectFromData(s);
                        if (entity != null && entity.isSuccess()) {
                            list.addAll(entity.getShoppingCart());
                            adapter.notifyDataSetChanged();

                        }
                        if (isEdit) {
                            m_LayoutEditBar.setVisibility(View.VISIBLE);
                            m_LayoutPayBar.setVisibility(View.GONE);
                        } else {
                            m_LayoutEditBar.setVisibility(View.GONE);
                            m_LayoutPayBar.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < adapter.getGroupCount(); i++) {
                            m_LvShop.expandGroup(i);
                        }
                        m_LvShop.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                return true;
                            }
                        });
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    public void notifyCheckedChanged() {
        boolean AllIsChecked = true;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getShopInfo().isChoosed()) {
                AllIsChecked = false;
            }
        }
        if (!AllIsChecked) {
            m_CbShop.setOnCheckedChangeListener(null);
            m_CbShop.setChecked(false);
            m_CbShop.setOnCheckedChangeListener(checkAllListener);
            m_BtnCheckAllDeit.setOnCheckedChangeListener(null);
            m_BtnCheckAllDeit.setChecked(false);
            m_BtnCheckAllDeit.setOnCheckedChangeListener(checkAllListener);
        } else {
            m_CbShop.setChecked(true);
            m_BtnCheckAllDeit.setChecked(true);
        }
        notifMoneyChanged();
    }

    @OnClick(R.id.tv_edit_cart)
    public void onClick() {
        editInCart();
    }

}
