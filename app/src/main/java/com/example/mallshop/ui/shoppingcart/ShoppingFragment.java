package com.example.mallshop.ui.shoppingcart;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseFragment;
import com.example.mallshop.base.IBasePresenter;
import com.example.mallshop.bean.AddToCartBean;
import com.example.mallshop.bean.DeleteFromCartBean;
import com.example.mallshop.constract.cart.ICart;
import com.example.mallshop.presenter.cart.CartListPresenter;
import com.example.mallshop.ui.shoppingcart.adapter.ShoppingCartListAdapter;
import com.example.mallshop.utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingFragment extends BaseFragment<ICart.ICartListPresenter> implements ICart.ICartListView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.layout_top)
    FrameLayout layoutTop;
    @BindView(R.id.radio_select)
    CheckBox radioSelect;
    @BindView(R.id.txt_allPrice)
    TextView txtAllPrice;
    @BindView(R.id.txt_edit)
    TextView txtEdit;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    @BindView(R.id.tv_select)
    TextView txtSelectAll;


    ShoppingCartListAdapter cartListAdapter;
    List<AddToCartBean.DataBean.CartListBean> list;

    private int allNumber;
    private int allPrice;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initView(View view) {
        cartListAdapter = new ShoppingCartListAdapter(context);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(cartListAdapter);
        txtSelectAll.setText("全选(0)");
        cartListAdapter.setOnItemCheckBoxClickListener(new ShoppingCartListAdapter.CheckBoxClick() {
            @Override
            public void checkChange() {
                //判断当前是否全选
                boolean bool = CheckSelectAll();
                txtSelectAll.setText("全选("+allNumber+")");
                txtAllPrice.setText("￥"+allPrice);
                radioSelect.setSelected(bool);
                cartListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected ICart.ICartListPresenter initPresenter() {
        return new CartListPresenter();
    }

    @Override
    protected void initData() {
        //homeViewModel.getHomeData();
        presneter.getCartList("cart/index");
    }

    @Override
    public void setCartList(AddToCartBean result) {
        list = result.getData().getCartList();
        cartListAdapter.setData(list);
    }

    @OnClick({R.id.radio_select, R.id.txt_edit, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_select:
                selectAll();
                break;
            case R.id.txt_edit:
                clickEdit();
                break;
            case R.id.txt_submit:
                submitData();
                break;
        }
    }

    /**
     * 全选状态的切换
     */
    private void selectAll(){
        //设置当前是否是全选
        resetSelect(!radioSelect.isSelected());
        radioSelect.setSelected(!radioSelect.isSelected());
        txtSelectAll.setText("全选("+allNumber+")");
        txtAllPrice.setText("￥"+allPrice);
        cartListAdapter.notifyDataSetChanged();
    }

    /**
     * 编辑状态的切换
     */
    private void clickEdit(){
        //当前是默认的状态---编辑状态
        if("编辑".equals(txtEdit.getText())){
            cartListAdapter.isEditor = true;
            txtEdit.setText("完成");
            txtSubmit.setText("删除所选");
            txtAllPrice.setVisibility(View.GONE);
        }else if("完成".equals(txtEdit.getText())){   //编辑状态进入默认状态
            cartListAdapter.isEditor = false;
            txtEdit.setText("编辑");
            txtSubmit.setText("下单");
            txtAllPrice.setVisibility(View.VISIBLE);
            txtAllPrice.setText("￥0");
        }
//        resetSelect(false);
        cartListAdapter.notifyDataSetChanged();
    }

    /**
     * 提交
     */
    private void submitData(){
        if("下单".equals(txtSubmit.getText())){
            //提交数据
            Intent intent = new Intent(context, OrderActiivity.class);
            intent.putExtra("allprice",allPrice);
            startActivity(intent);
        }else if ("删除所选".equals(txtSubmit.getText().toString())){
            StringBuilder sb = new StringBuilder();
            List<Integer> ids = getSelectProducts();
            for(Integer id:ids){
                sb.append(id);
                sb.append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length()-1);
                String productIds = sb.toString();
                presneter.delete(productIds);
            }else {
                Toast.makeText(context, "没有选中要删除的商品", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void deleteResult(DeleteFromCartBean result) {
        DeleteFromCartBean.DataBean data = result.getData();
        List<AddToCartBean.DataBean.CartListBean> listBeans = new ArrayList<>();
        if (data != null){
            for (DeleteFromCartBean.DataBean.CartListBean cartBean : data.getCartList()) {
                for (AddToCartBean.DataBean.CartListBean cartListBean : list) {
                    if (cartBean.getId() == cartListBean.getId()){
                        listBeans.add(cartListBean);
                    }
                }
            }
            cartListAdapter.setData(listBeans);
        }else {
            MyUtil.toast(context,result.getErrmsg());
        }
    }

    /**
     * 重置选中状态
     * @param bool
     */
    private void resetSelect(boolean bool){
        allNumber = 0;
        allPrice = 0;
        for(AddToCartBean.DataBean.CartListBean item:list){
            item.select = bool;
            if(bool){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
        }
        if(!bool){
            allNumber = 0;
            allPrice = 0;
        }
    }

    /**
     * 获取当前选中的商品
     * @return
     */
    private List<Integer> getSelectProducts(){
        List<Integer> ids = new ArrayList<>();
        for(AddToCartBean.DataBean.CartListBean item:list){
            if(item.select){
                ids.add(item.getProduct_id());
            }
        }
        return ids;
    }

    /**
     * 判断当前数据是否选中
     * @return
     */
    private boolean CheckSelectAll(){
        allNumber = 0;
        allPrice = 0;
        boolean isSelectAll = true;
        for(AddToCartBean.DataBean.CartListBean item:list){
            if(item.select){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
            if(isSelectAll && !item.select){
                isSelectAll = false;
            }
        }
        return isSelectAll;
    }
}
