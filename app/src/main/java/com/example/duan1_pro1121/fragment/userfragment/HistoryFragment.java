package com.example.duan1_pro1121.fragment.userfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.user.UserMainActivity;
import com.example.duan1_pro1121.adapter.user.HistoryDatSanAdapter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    private TextView tvCount;
    private Spinner spinner;
    private List<Order> orders;
    private RecyclerView recyclerView;
    HistoryDatSanAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataWithStatus(-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCount = view.findViewById(R.id.tv_count_order_user);
        spinner = view.findViewById(R.id.spn_type_order_user);
        recyclerView = view.findViewById(R.id.recy_history_user);

        adapter = new HistoryDatSanAdapter(getContext(),orders);
        recyclerView.setAdapter(adapter);
        setUp();

        ArrayList<String> list = new ArrayList<>(Arrays.asList("Tất cả","Chưa đá","Đang đá","Đang nghỉ","Đã đá"));
        ArrayAdapter adapter1 = new ArrayAdapter(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    setDataWithStatus(-1);
                }else if(position == 1){
                    setDataWithStatus(MyApplication.CHUA_STATUS);
                }else if(position == 2){
                    setDataWithStatus(MyApplication.DANG_STATUS);
                }else if(position == 3){
                    setDataWithStatus(MyApplication.NGHI_STATUS);
                }else if(position == 4){
                    setDataWithStatus(MyApplication.DA_STATUS);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    public void setDataWithStatus(int status){
        if(status == -1){
            orders = MyDatabase.getInstance(getContext()).orderDAO()
                    .getOrderWithCustomerId(UserMainActivity.customer.getId());
            if(adapter!=null){
                setUp();
            }
        }else {
            if (status == MyApplication.CHUA_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.CHUA_STATUS);
            } else if (status == MyApplication.DANG_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.DANG_STATUS);
            } else if (status == MyApplication.NGHI_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.NGHI_STATUS);
            } else if (status == MyApplication.DA_STATUS) {
                orders = MyDatabase.getInstance(getContext()).orderDAO()
                        .getOrderWithCustomerIdAndStatus(UserMainActivity.customer.getId(), MyApplication.DA_STATUS);
            }
            setUp();
        }
    }

    public void setUp(){
        Log.e("123","12345");
        adapter.setData(orders);
        tvCount.setText(orders.size()+"");
    }
}