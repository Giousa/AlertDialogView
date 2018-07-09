package com.zmm.alertdialogview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zmm.alertdialogview.wheelview.OnWheelChangedListener;
import com.zmm.alertdialogview.wheelview.OnWheelScrollListener;
import com.zmm.alertdialogview.wheelview.WheelView;
import com.zmm.alertdialogview.wheelview.adapter.ListStringWheelAdapter;
import com.zmm.alertdialogview.wheelview.adapter.NumericWheelAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @OnClick({R.id.btn_input, R.id.btn_select_gender, R.id.btn_select_pic,R.id.btn_select_double})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_input:
                inputString();

                break;
            case R.id.btn_select_gender:
                selectGender();
                makeWindowDark();
                break;
            case R.id.btn_select_pic:
//                dealtAge();
                dateSelect();
                break;

            case R.id.btn_select_double:

                doubleSelect();
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

        mDepartmentLists = new ArrayList<>();
        mDepartmentLists.add("咽喉科");
        mDepartmentLists.add("外科");
        mDepartmentLists.add("骨科");
        mDepartmentLists.add("内科");

        mHospitalLists = new ArrayList<>();
        mHospitalLists.add("上海医院");
        mHospitalLists.add("北京医院");
        mHospitalLists.add("济南医院");
        mHospitalLists.add("人民医院");
        mHospitalLists.add("甲等医院");

        SingleSelectView singleSelectView = new SingleSelectView(
                this,mRootView,mScreenWidth,"选择部门",mDepartmentLists
        );

        singleSelectView.setOnSelectClickListener(new SingleSelectView.OnSelectClickListener() {
            @Override
            public void onCancel() {
                System.out.println("取消");
            }

            @Override
            public void onConfirm(String content) {
                System.out.println("content = "+content);
            }
        });


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
                makeWindowLight();
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


    /**
     * 让屏幕变暗
     */
    protected void makeWindowDark(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }
    /**
     * 让屏幕变亮
     */
    protected void makeWindowLight(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }



    private WheelView wl_start_year;
    private WheelView wl_start_month;
    private WheelView wl_start_day;

    private final int START_YEAR = 1940;
    private final int MIDDLE_YEAR = 1940;
    private final int END_YEAR = 2030;
    private final int START_MONTH = 1;
    private final int END_MONTH = 12;

    private int curYear;
    private int curMonth;

    /**
     * 时间选择
     */
    private void dealtAge() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.pop_date_select, null, false);
        Button btn_cancel = (Button) popupWindowView.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) popupWindowView.findViewById(R.id.btn_ok);
//        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        final PopupWindow popupWindow = new PopupWindow(popupWindowView, 4 * mScreenWidth / 5, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);

        initAgeWheelView(popupWindowView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                makeWindowLight();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("getDate  = "+getDate());
                popupWindow.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow.showAtLocation(rl_main, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);

//        makeWindowDark();
    }


    private void initAgeWheelView(View view) {
        Calendar c = Calendar.getInstance();
        curYear = c.get(Calendar.YEAR);
        curMonth = c.get(Calendar.MONTH) + 1;
        wl_start_year = (WheelView) view.findViewById(R.id.wl_start_year);
        wl_start_month = (WheelView) view.findViewById(R.id.wl_start_month);
        wl_start_day = (WheelView) view.findViewById(R.id.wl_start_day);

        NumericWheelAdapter numericWheelAdapterStart1 = new NumericWheelAdapter(this, START_YEAR, END_YEAR);
        numericWheelAdapterStart1.setLabel(" ");
        wl_start_year.setViewAdapter(numericWheelAdapterStart1);
        numericWheelAdapterStart1.setTextColor(R.color.black);
        numericWheelAdapterStart1.setTextSize(20);
        wl_start_year.setCyclic(true);
        wl_start_year.setVisibleItems(5);
        wl_start_year.setCurrentItem(20);
        wl_start_year.addScrollingListener(startAgeScrollListener);
        wl_start_year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curYear = newValue + MIDDLE_YEAR;
                initStartDayAdapter();
            }
        });

        NumericWheelAdapter numericWheelAdapterStart2 = new NumericWheelAdapter(this, START_MONTH, END_MONTH, "%02d");
        numericWheelAdapterStart2.setLabel(" ");
        wl_start_month.setViewAdapter(numericWheelAdapterStart2);
        numericWheelAdapterStart2.setTextColor(R.color.black);
        numericWheelAdapterStart2.setTextSize(20);
        wl_start_month.setCyclic(true);
        wl_start_month.setVisibleItems(5);
        wl_start_month.setCurrentItem(5);
        wl_start_month.addScrollingListener(startAgeScrollListener);
        wl_start_month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curMonth = newValue + 1;
                initStartDayAdapter();
            }
        });
        initStartDayAdapter();
    }

    private void initStartDayAdapter() {
        NumericWheelAdapter numericWheelAdapterStart3 = new NumericWheelAdapter(this, 1, getDay(curYear, curMonth), "%02d");
        numericWheelAdapterStart3.setLabel(" ");
        wl_start_day.setViewAdapter(numericWheelAdapterStart3);
        numericWheelAdapterStart3.setTextColor(R.color.black);
        numericWheelAdapterStart3.setTextSize(20);
        wl_start_day.setCyclic(true);
        wl_start_day.setVisibleItems(5);
        wl_start_day.setCurrentItem(14);
        wl_start_day.addScrollingListener(startAgeScrollListener);
    }

    OnWheelScrollListener startAgeScrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
//            mAddEtAge.setText(getDate());//若是需要值随之改变,可调用此方法
            getDate();
        }
    };

    private String getDate() {
        int n_year = wl_start_year.getCurrentItem() + MIDDLE_YEAR;
        int n_month = wl_start_month.getCurrentItem() + 1;
        int n_day = wl_start_day.getCurrentItem() + 1;
        String switchDate = n_year + " 年 " + n_month + " 月 " + n_day + " 日";
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = new Date(time);
        String nowYear = format.format(date);
//        age = Integer.parseInt(nowYear) - n_year;
//        birthday1 = n_year + " 年 " + n_month + " 月 " + n_day + " 日";
//        birthday2 = n_year + "-" + n_month + "-" + n_day;

        return switchDate;
    }

    /**
     * 根据年月获得 这个月总共有几天
     * @param year
     * @param month
     * @return
     */
    public int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }



    private String mDate;
    private void dateSelect() {
        DateSelectView dateSelectView = new DateSelectView(this,mRootView,mScreenWidth,mDate);

        dateSelectView.setOnDateClickListener(new DateSelectView.OnDateClickListener() {
            @Override
            public void onDateClick(String date) {
                mDate = date;
                System.out.println("date = "+date);
            }
        });
    }



    private void doubleSelect() {

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list1.add("上海");
        list1.add("北京");
        list1.add("南京");
        list1.add("广东");
        list1.add("山西");
        list1.add("浙江");

        list2.add("上海医院");
        list2.add("北京医院");
        list2.add("深圳医院");
        list2.add("厦门医院");
        list2.add("江苏医院");

        DoubleSelectView doubleSelectView = new DoubleSelectView(this,mRootView,mScreenWidth,"医院",list1,list2);

        doubleSelectView.setOnSelectClickListener(new DoubleSelectView.OnSelectClickListener() {
            @Override
            public void onCancel() {
                System.out.println("cancel");
            }

            @Override
            public void onConfirm(String content) {
                System.out.println("content = "+content);
            }
        });
    }


}
