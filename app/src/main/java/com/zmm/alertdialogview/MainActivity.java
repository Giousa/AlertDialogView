package com.zmm.alertdialogview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zmm.alertdialogview.wheelview.WheelView;
import com.zmm.alertdialogview.wheelview.adapter.ListStringWheelAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private final int TYPE_NAME = 0;
    private final int TYPE_GENDER = 1;
    private final int TYPE_BIRTHDAY = 2;
    private final int TYPE_EDUCATION = 3;
    private final int TYPE_HOSPITAL = 4;
    private final int TYPE_DEPARTMENT = 5;
    private final int TYPE_SKILL = 6;
    private final int TYPE_WORK = 7;
    private final int TYPE_CERIFICATES = 8;
    private final int TYPE_ROLE = 9;


    private List<String> mGenderLists;
    private List<String> mEducationLists;
    private List<String> mHospitalLists;
    private List<String> mDepartmentLists;
    private List<String> mRoleLists;


    private WheelView wl_start_single;
    private ListStringWheelAdapter mListStringWheelAdapter = null;
    private int mScreenWidth;

    @BindView(R.id.root_view)
    LinearLayout mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mScreenWidth = getScreenWidth();
    }

    protected int getScreenWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @OnClick({R.id.btn_input, R.id.btn_select_gender, R.id.btn_select_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_input:
                inputString();
                break;
            case R.id.btn_select_gender:
                selectGender();
                break;
            case R.id.btn_select_pic:
                selectPic();
                break;
        }
    }


    private void inputString() {

        final SimpleInputDialog simpleInputDialog = new SimpleInputDialog(this, "修改用户名", "请输入你。。。");

        simpleInputDialog.setOnClickListener(new SimpleInputDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleInputDialog.dismiss();
                System.out.println("---cancel---");

            }

            @Override
            public void onConfirm(String content) {
                simpleInputDialog.dismiss();
                System.out.println("---confirm---" + content);
            }
        });

        simpleInputDialog.show();


    }

    private void selectGender() {
        showPopSingle(TYPE_EDUCATION);
    }

    private void selectPic() {

    }


    /**
     * 单选
     *
     * @param item
     */
    private void showPopSingle(final int item) {

//        hideKeyBoard();

        LayoutInflater inflater = LayoutInflater.from(this);
        final View popupWindowView = inflater.inflate(R.layout.pop_single_select, null);
        Button btn_cancel = popupWindowView.findViewById(R.id.btn_select_cancel);
        Button btn_ok = popupWindowView.findViewById(R.id.btn_select_confirm);
        final PopupWindow popupWindow = new PopupWindow(popupWindowView, mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        initSingleWheel(popupWindowView, item);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (item) {

                    case TYPE_GENDER:
                        int genderInt = wl_start_single.getCurrentItem();
                        break;

                    case TYPE_EDUCATION:
                        int eduInt = wl_start_single.getCurrentItem();
                        break;

                    case TYPE_HOSPITAL:
                        int hosInt = wl_start_single.getCurrentItem();
                        break;

                    case TYPE_DEPARTMENT:
                        int departInt = wl_start_single.getCurrentItem();
                        break;

                    case TYPE_ROLE:
                        int roleInt = wl_start_single.getCurrentItem();
                        break;

                }
                popupWindow.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }

    private void initSingleWheel(View view, int item) {

        TextView title = view.findViewById(R.id.pop_title);
        wl_start_single = view.findViewById(R.id.pop_wl);
        switch (item) {

            case TYPE_GENDER:
                title.setText("选择性别");

                mGenderLists = new ArrayList<>();
                mGenderLists.add("男");
                mGenderLists.add("女");

                mListStringWheelAdapter = new ListStringWheelAdapter(this, mGenderLists);

                break;

            case TYPE_EDUCATION:
                title.setText("选择学历");

                mEducationLists = new ArrayList<>();
                mEducationLists.add("博士后");
                mEducationLists.add("博士");
                mEducationLists.add("硕士");
                mEducationLists.add("学士");
                mEducationLists.add("本科");
                mEducationLists.add("专科");
                mEducationLists.add("职高");

                mListStringWheelAdapter = new ListStringWheelAdapter(this, mEducationLists);
                break;

            case TYPE_HOSPITAL:
                title.setText("选择医院");

                mHospitalLists = new ArrayList<>();
                mHospitalLists.add("上海医院");
                mHospitalLists.add("北京医院");
                mHospitalLists.add("济南医院");
                mHospitalLists.add("人民医院");
                mHospitalLists.add("甲等医院");

                mListStringWheelAdapter = new ListStringWheelAdapter(this, mHospitalLists);
                break;

            case TYPE_DEPARTMENT:
                title.setText("选择科室");

                mDepartmentLists = new ArrayList<>();
                mDepartmentLists.add("内科");
                mDepartmentLists.add("外科");
                mDepartmentLists.add("骨科");
                mDepartmentLists.add("咽喉科");

                mListStringWheelAdapter = new ListStringWheelAdapter(this, mDepartmentLists);
                break;

            case TYPE_ROLE:
                title.setText("选择角色");

                mRoleLists = new ArrayList<>();
                mRoleLists.add("院长");
                mRoleLists.add("副院长");
                mRoleLists.add("主任");
                mRoleLists.add("副主任");
                mRoleLists.add("科长");

                mListStringWheelAdapter = new ListStringWheelAdapter(this, mRoleLists);
                break;
        }

        wl_start_single.setViewAdapter(mListStringWheelAdapter);
        mListStringWheelAdapter.setTextColor(R.color.black);
        mListStringWheelAdapter.setTextSize(20);
        wl_start_single.setCyclic(false);
        wl_start_single.setCurrentItem(0);
        mListStringWheelAdapter.setItemTextResource(wl_start_single.getCurrentItem());

    }

}
