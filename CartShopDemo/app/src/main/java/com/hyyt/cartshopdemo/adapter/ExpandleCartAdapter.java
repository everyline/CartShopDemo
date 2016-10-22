package com.hyyt.cartshopdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyyt.cartshopdemo.R;
import com.hyyt.cartshopdemo.bean.ShopEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/21.
 */

public class ExpandleCartAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ShopEntity.ShoppingCartBean> list;
    private  selectCartOnClickListener selectClickListener;
    public  void  setCartOnClickListener(selectCartOnClickListener selectClickListener){
        this.selectClickListener=selectClickListener;
    }

    public ExpandleCartAdapter(Context context, ArrayList<ShopEntity.ShoppingCartBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getProductList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getProductList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_layout_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

       ShopEntity.ShoppingCartBean shopInfoBean= list.get(groupPosition);
        groupViewHolder.m_TvShopName.setText(shopInfoBean.getShopInfo().getShopName());
        groupViewHolder.m_CbCheck.setChecked(shopInfoBean.getShopInfo().isChoosed());
        groupViewHolder.m_CbCheck.setOnClickListener(new Group_CheckBox_Click(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       ChildViewHolder childViewHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_layout_child, null);
            childViewHolder=new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        ShopEntity.ShoppingCartBean.ProductListBean productListBean= list.get(groupPosition).getProductList().get(childPosition);
        childViewHolder.m_CheckBoxGoods.setChecked(productListBean.isChoosed());
        childViewHolder.m_TvShopName.setText(productListBean.getProductFullName());
        childViewHolder.m_TvShopPrice.setText("¥"+productListBean.getSalesPrice());
        childViewHolder.m_BtNum.setText(productListBean.getQuantity()+"");
        Glide.with(context).load(productListBean.getLogoImg()).into(childViewHolder.m_IvShop);
        childViewHolder.m_CheckBoxGoods.setOnClickListener(new Child_CheckBox_Click(groupPosition,childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class  Group_CheckBox_Click implements View.OnClickListener{
        private int groupPosition;
        public Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            list.get(groupPosition).getShopInfo().toggle();
            int childrenCount =list.get(groupPosition).getProductList().size();
            boolean groupIsChecked =list.get(groupPosition).getShopInfo().isChoosed();
            for (int i = 0; i < childrenCount; i++) {
                list.get(groupPosition).getProductList().get(i).setChoosed(groupIsChecked);
            }
            notifyDataSetChanged();
            if (selectClickListener !=null){
                selectClickListener.notifyCheckedChanged();
            }
        }
    }
    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        public Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            handleClick(childPosition, groupPosition);

        }

    }

    private void handleClick(int childPosition, int groupPosition) {
        list.get(groupPosition).getProductList().get(childPosition).toggle();
        int childrenCount = list.get(groupPosition).getProductList().size();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!list.get(groupPosition).getProductList().get(i).isChoosed()){
                childrenAllIsChecked=false;
            }
        }
        list.get(groupPosition).getShopInfo().setChoosed(childrenAllIsChecked);
        notifyDataSetChanged();
        if (selectClickListener !=null){
            selectClickListener.notifyCheckedChanged();
        }
    }

    static class GroupViewHolder {
        @BindView(R.id.cbCheck)
        CheckBox m_CbCheck;
        @BindView(R.id.tv_shop_name)
        TextView m_TvShopName;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.checkBox_goods)
        CheckBox m_CheckBoxGoods;
        @BindView(R.id.iv_shop)
        ImageView m_IvShop;
        @BindView(R.id.tv_shop_name)
        TextView m_TvShopName;
        @BindView(R.id.tv_shop_price)
        TextView m_TvShopPrice;
        @BindView(R.id.downbtn1)
        Button m_Downbtn1;
        @BindView(R.id.bt_num)
        Button m_BtNum;
        @BindView(R.id.upbtn1)
        Button m_Upbtn1;
        @BindView(R.id.ll_shop_intent)
        LinearLayout m_LlShopIntent;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public  interface  selectCartOnClickListener{
        void   notifyCheckedChanged();
    }
}
