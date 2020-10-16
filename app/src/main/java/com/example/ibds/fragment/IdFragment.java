package com.example.ibds.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ibds.Activity.BaseActivity;
import com.example.ibds.Activity.MainActivity;
import com.example.ibds.Adaptor.IdAdaptor;
import com.example.ibds.CalendarUtils;
import com.example.ibds.CustomDialog;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.IdDo;
import com.example.ibds.Do.ProductDo;
import com.example.ibds.Do.TaskDo;
import com.example.ibds.Preference;
import com.example.ibds.R;

import java.util.ArrayList;
import java.util.List;

public class IdFragment extends Fragment {
    private RecyclerView rvId;
    private Context mcontext;
    private IdAdaptor adaptor;
    private List<IdDo> idList = new ArrayList<>();
    private TextView txtNoData;
    private DatabaseHelper databaseHelper;
    private Button btnNewId;
    public CustomDialog customDialog;
    public LayoutInflater inflater;
    Preference preference;
    public IdFragment(Context mcontext,List<IdDo> idList, LayoutInflater inflater,Preference preference) {
        this.mcontext = mcontext;
        this.idList = idList;
        this.inflater = inflater;
        this.preference = preference;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.id_fragment, container, false);
        rvId = view.findViewById(R.id.rvId);
        txtNoData = view.findViewById(R.id.txtNoData);
        btnNewId = view.findViewById(R.id.btnNewId);
        databaseHelper = new DatabaseHelper(mcontext);
        if(idList.size()>0){
            rvId.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            adaptor = new IdAdaptor(mcontext,idList);
            rvId.setLayoutManager(new LinearLayoutManager(mcontext));
            rvId.setAdapter(adaptor);
        }else{
            rvId.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
        btnNewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)getActivity()).runOnUiThread(new RunCustomEditTestDialog());
            }
        });
        return view;
    }
    public void refreshData(){
        rvId.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
        idList = databaseHelper.getIds();
        adaptor = new IdAdaptor(mcontext,idList);
        adaptor.refreshData(idList);
        rvId.setLayoutManager(new LinearLayoutManager(mcontext));
        rvId.setAdapter(adaptor);
        if(adaptor!=null)
            adaptor.notifyDataSetChanged();
    }
    class RunCustomEditTestDialog implements Runnable {
        private boolean isCancelable = false;

        public RunCustomEditTestDialog() {

        }

        @Override
        public void run() {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
            View view = inflater.inflate(R.layout.custome_popup_for_id, null);
            customDialog = new CustomDialog(mcontext, view, preference
                    .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 60,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            customDialog.setCancelable(isCancelable);
            customDialog.setCanceledOnTouchOutside(true);

            String UserType[] = {"Working", "Not Working"};
            ArrayAdapter<String> userTypeAdapter;

            final EditText edtName = view.findViewById(R.id.edtName);
            final EditText edtPassword = view.findViewById(R.id.edtPassword);
            final Spinner spinStatus = view.findViewById(R.id.spinStatus);
            Button btnAdd = view.findViewById(R.id.btnAdd);

            userTypeAdapter = new ArrayAdapter<>(mcontext, android.R.layout.simple_spinner_dropdown_item, UserType);
            userTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinStatus.setAdapter(userTypeAdapter);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String enteredName = edtName.getText().toString();
                    String enteredPassword = edtPassword.getText().toString();
                    String enteredStatus = spinStatus.getSelectedItem().toString();
                    if (enteredStatus.equalsIgnoreCase("") || enteredPassword.equalsIgnoreCase("") || enteredName.equalsIgnoreCase("")) {
                        ((BaseActivity) getActivity()).showCustomDialog(mcontext, getString(R.string.warning), "Enter the all fields", getString(R.string.OK), "", "");
                    } else {
                        IdDo idDo = new IdDo(enteredName,enteredPassword,enteredStatus);
                        if (databaseHelper.InsertId(idDo)) {
                            ((BaseActivity)getActivity()).showCustomeToast(""+ "'"+idDo.getName()+"'" +" a new user was saved.");
                            refreshData();
                        } else {
                            ((BaseActivity)getActivity()).showCustomeToast("Something went wrong");
                        }
                        customDialog.dismiss();
                    }
                }
            });
            try {
                if (!customDialog.isShowing())
                    customDialog.showCustomDialog();
            } catch (Exception e) {
            }
        }
    }
//    private List<ProductDo> getDummyData() {
//        List<ProductDo> ProductList = new ArrayList<>();
//        int rating = 2;
//        ProductDo product1 = new ProductDo(R.drawable.black_seed_oil_glass, "Black Seed Oil 16oz", "Ethiopian Black Seed Oil 16oz (Glass)", "89", (float) 3.9);
//        ProductDo product2 = new ProductDo(R.drawable.black_seed_oil_16oz_pet, "Black Seed Oil 16oz", "Ethiopian Black Seed Oil 16oz (PET)", "79", (float) 4.2);
//        ProductDo product3 = new ProductDo(R.drawable.black_seed_oil_4oz_glass, "Black Seed Oil 4oz", "Ethiopian Black Seed Oil 4oz (GLASS)", "37", (float) 4.2);
//        ProductDo product4 = new ProductDo(R.drawable.arugula_seed_oil_160z_glass, "Arugula Seed oil 16oz", "Egyptian desert Wide Leaf Arugula Seed Oil 16oz (GLASS)", "89", (float) 4.3);
//        ProductDo product5 = new ProductDo(R.drawable.jojoba_seed_oil_4oz, "Jojoba Seed oil 4oz", "Egyptian desert Jojoba Seed Oil 4oz (GLASS)", "42", (float) 4.3);
//        ProductDo product6 = new ProductDo(R.drawable.two_black_seed_soaps, "2 Black seed soaps", "2 Black Seed Soaps (Ethiopian Seeds)", "24", (float) 4.3);
//        ProductDo product7 = new ProductDo(R.drawable.black_seed_lotion_with_citrus_8oz, "Lotion with citurs 8oz", "Black Seed Lotion with Citrus 8oz (Ethiopian Seeds)", "39", (float) 4.5);
//        ProductDo product8 = new ProductDo(R.drawable.black_seed_mask_40z, "Black Seed Mask 3oz", "Black Seed Mask 4oz (Ethiopian Seeds)", "25", (float) 4.9);
//        ProductDo product9 = new ProductDo(R.drawable.ethiopoan_black_seeds_2oz, "Black seed 2oz", "Ethiopian Black Seeds 2oz", "15", (float) 4);
//        ProductDo product10 = new ProductDo(R.drawable.ehiopian_black_seed_powder_2oz, "Black seed powder 2oz", "Ethiopian Black Seeds powder 2oz", "15", (float) 4);
//
//        ProductList.add(product1);
//        ProductList.add(product2);
//        ProductList.add(product3);
//        ProductList.add(product4);
//        ProductList.add(product5);
//        ProductList.add(product6);
//        ProductList.add(product7);
//        ProductList.add(product8);
//        ProductList.add(product9);
//        ProductList.add(product10);
//        return ProductList;
//    }
}
