package com.example.mallshop.ui.own.activities;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mallshop.R;
import com.example.mallshop.base.BaseActivity;
import com.example.mallshop.bean.AddressBean;
import com.example.mallshop.constract.own.IOwn;
import com.example.mallshop.presenter.own.AddressPresenter;
import com.example.mallshop.utils.SystemUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAddressActivity extends BaseActivity<IOwn.IAddressPresenter> implements IOwn.IAddressView {
    @BindView(R.id.layout_address)
    ConstraintLayout layoutAddress;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.ck_default)
    CheckBox ckDefault;
    @BindView(R.id.txt_default)
    TextView txtDefault;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save)
    Button btnSave;
    private PopupWindow popupWindow;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvArea;
    private TextView tvSubmit;
    private LoopView loopProvince;
    private LoopView loopCity;
    private LoopView loopArea;
    private Map<Integer, List<AddressBean.DataBean>> addressMap;
    private int curProvinceId;
    private int curCityId;
    private int curAreaId;
    private String provinceName;
    private String cityName;
    private String areaName;

    @Override
    protected IOwn.IAddressPresenter initPresenter() {
        return new AddressPresenter();
    }

    @Override
    protected void initData() {
        presenter.getAddress("region/list?parentId=1");
    }

    @Override
    protected void initView() {
        addressMap = new HashMap<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_address;
    }

    @OnClick({R.id.img_back, R.id.et_address, R.id.ck_default, R.id.btn_cancel, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.et_address:
                showPopupWindow();
                break;
            case R.id.ck_default:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_save:
                break;
        }
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_pop_address, null);
        int height = SystemUtils.dp2px(this, 250);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, height);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(view);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tvProvince = view.findViewById(R.id.txt_province);
        tvCity = view.findViewById(R.id.txt_city);
        tvArea = view.findViewById(R.id.txt_area);
        tvSubmit = view.findViewById(R.id.txt_submit);
        loopProvince = view.findViewById(R.id.loop_province);
        loopCity = view.findViewById(R.id.loop_city);
        loopArea = view.findViewById(R.id.loop_area);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provinceName != null && cityName != null && areaName != null)
                    etAddress.setText(provinceName +"-"+ cityName +"-"+ areaName);
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
        popupWindow.showAtLocation(layoutAddress, Gravity.BOTTOM,0,0);
        //省份
        loopProvince.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                AddressBean.DataBean dataBean = proviceList.get(index);
                curProvinceId = dataBean.getId();
                presenter.getAddress("region/list?parentId="+curProvinceId);
                List<String> items = new ArrayList<>();
                items.add("城市");
                loopCity.setItems(items);
                tvProvince.setText(dataBean.getName());
                provinceName = dataBean.getName();
                tvCity.setText("城市");
                tvArea.setText("区县");
            }
        });
        //城市
        loopCity.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                if (cityList != null){
                    AddressBean.DataBean dataBean = cityList.get(index);
                    curCityId = dataBean.getId();
                    presenter.getAddress("region/list?parentId="+curCityId);
                    loopArea.setItems(new ArrayList<>());
                    tvCity.setText(dataBean.getName());
                    cityName = dataBean.getName();
                    tvArea.setText("区县");
                }
            }
        });
        //区县
        loopArea.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                AddressBean.DataBean dataBean = areaList.get(index);
                curAreaId = dataBean.getId();
                tvArea.setText(dataBean.getName());
                areaName = dataBean.getName();
                tvSubmit.setEnabled(true);
            }
        });
        //初始化省份的数据
        List<AddressBean.DataBean> pList = addressMap.get(1);
        if(pList == null) return;
        List<String> adresses = getAddressStrings(pList);
        if(pList == null || adresses.size() == 0){
            presenter.getAddress("region/list?parentId=1");
        }else{
            loopProvince.setItems(adresses);
            curProvinceId = pList.get(0).getId();
            tvProvince.setText(adresses.get(0));
        }
    }

    //获取省市区名字
    private List<String> getAddressStrings(List<AddressBean.DataBean> list) {
        List<String> addresses = new ArrayList<>();
        for(AddressBean.DataBean item:list){
            addresses.add(item.getName());
        }
        return addresses;
    }

    @Override
    public void setAddress(AddressBean result) {
        List<AddressBean.DataBean> list = null;
        int type = 0;
        for(AddressBean.DataBean item:result.getData()){
            int key = item.getParent_id();
            list = addressMap.get(key);
            if(list == null){
                list = new ArrayList<>();
                addressMap.put(key,list);
            }
            boolean bool = hasList(item.getId(),list);
            if(!bool) list.add(item);
            if(type == 0){
                type = item.getType();
            }
        }
        if(list == null) return;
        List<String> adresses = getAddressStrings(list);
        if(type == 1){
            //刷新省的数据
            if(loopProvince != null){
                curProvinceId = list.get(0).getId();
                tvProvince.setText(list.get(0).getName());
                loopProvince.setItems(adresses);
            }
        }else if(type == 2){
            //刷新市的数据
            if(loopCity != null){
                curCityId = list.get(loopCity.getSelectedItem()).getId();
                tvCity.setText(list.get(loopCity.getSelectedItem()).getName());
                presenter.getAddress("region/list?parentId=" + curCityId);
                loopCity.setItems(adresses);
            }
        }else{
            //区
            if(loopArea != null){
                curAreaId = list.get(loopArea.getSelectedItem()).getId();
                tvArea.setText(list.get(loopArea.getSelectedItem()).getName());
                loopArea.setItems(adresses);
            }
        }
    }
    /**
     * 判断当前的地址列表中是否有这个地址
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AddressBean.DataBean> list){
        boolean bool = false;
        for(AddressBean.DataBean item:list){
            if(item.getId() == id){
                bool = true;
                break;
            }
        }
        return bool;
    }
}
