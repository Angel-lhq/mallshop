package com.example.mallshop.ui.shoppingcart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseAdapter;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.common.CartCustomView;

public class ShoppingCartListAdapter extends BaseAdapter {

    public boolean isEditor; //是否是编辑状态
    private CheckBoxClick click;

    public ShoppingCartListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_cart_list;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Object dataBean) {
        AddToCartBean.DataBean.CartListBean bean = (AddToCartBean.DataBean.CartListBean) dataBean;

        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox_select);
        TextView txtName = (TextView) holder.getView(R.id.txt_name);
        TextView txtNumber = (TextView) holder.getView(R.id.txt_number);
        TextView txtPrice = (TextView) holder.getView(R.id.txt_price);
        TextView txtSelect = (TextView) holder.getView(R.id.txt_select);
        CartCustomView cartCustomView = (CartCustomView) holder.getView(R.id.view_number);

        //通过编辑状态控制界面组件
        if(isEditor){
            txtName.setVisibility(View.GONE);
            txtNumber.setVisibility(View.GONE);
            txtSelect.setVisibility(View.VISIBLE);
            cartCustomView.setVisibility(View.VISIBLE);
        }else{
            txtName.setVisibility(View.VISIBLE);
            txtNumber.setVisibility(View.VISIBLE);
            txtSelect.setVisibility(View.GONE);
            cartCustomView.setVisibility(View.GONE);
        }
        txtName.setText(bean.getGoods_name());
        txtNumber.setText("X"+bean.getNumber());
        txtPrice.setText("￥"+bean.getRetail_price());
        cartCustomView.initView();
        cartCustomView.setValue(bean.getNumber());
        cartCustomView.setOnIClickListener(new CartCustomView.IClick() {
            @Override
            public void clickCB(int value) {
                bean.setNumber(value);
            }
        });
        //checkBox.setChecked(bean.select);
        checkBox.setSelected(bean.select);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.select = !bean.select;
                if(click != null){
                    click.checkChange();
                }
            }
        });

    }

    public void setOnItemCheckBoxClickListener(CheckBoxClick click){
        this.click = click;
    }


    public interface CheckBoxClick{
        void checkChange();
    }
}
