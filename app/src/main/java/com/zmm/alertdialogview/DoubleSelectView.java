package com.zmm.alertdialogview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zmm.alertdialogview.wheelview.WheelView;
import com.zmm.alertdialogview.wheelview.adapter.ListStringWheelAdapter;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/16
 * Time:下午8:37
 */

public class DoubleSelectView extends View {

    private Context mContext;
    private int mScreenWidth;
    private String mTitle;
    private List<String> mList1, mList2;

    private ListStringWheelAdapter mListStringWheelAdapter1 = null;
    private ListStringWheelAdapter mListStringWheelAdapter2 = null;
    private LinearLayout mRootView;
    private TextView mTvTitle;
    private WheelView mWheelView1,mWheelView2;

    private OnSelectClickListener mOnSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener{

        void onCancel();

        void onConfirm(String content);
    }


    public DoubleSelectView(Context context, LinearLayout rootView, int screenWidth, String title, List<String> listone, List<String> listtwo) {
        super(context);
        mContext = context;
        mRootView = rootView;
        mScreenWidth = screenWidth;
        mTitle = title;
        mList1 = listone;
        mList2 = listtwo;
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.pop_double_select, null);
        Button cancel = view.findViewById(R.id.btn_select_cancel);
        Button confirm = view.findViewById(R.id.btn_select_confirm);
        mTvTitle = view.findViewById(R.id.pop_title);
        mWheelView1 = view.findViewById(R.id.pop_wl_1);
        mWheelView2 = view.findViewById(R.id.pop_wl_2);

        final PopupWindow popupWindow = new PopupWindow(view, mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);


        mTvTitle.setText(mTitle);


        mListStringWheelAdapter1 = new ListStringWheelAdapter(mContext, mList1);
        mWheelView1.setViewAdapter(mListStringWheelAdapter1);
        mListStringWheelAdapter1.setTextColor(R.color.black);
        mListStringWheelAdapter1.setTextSize(20);
        mWheelView1.setCyclic(false);
        mWheelView1.setCurrentItem(0);
        mWheelView1.setVisibleItems(4);
        mListStringWheelAdapter1.setItemTextResource(mWheelView1.getCurrentItem());


        mListStringWheelAdapter2 = new ListStringWheelAdapter(mContext, mList2);
        mWheelView2.setViewAdapter(mListStringWheelAdapter2);
        mListStringWheelAdapter2.setTextColor(R.color.black);
        mListStringWheelAdapter2.setTextSize(20);
        mWheelView2.setCyclic(false);
        mWheelView2.setCurrentItem(0);
        mWheelView2.setVisibleItems(4);
        mListStringWheelAdapter2.setItemTextResource(mWheelView2.getCurrentItem());


        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                if(mOnSelectClickListener != null){
                    mOnSelectClickListener.onCancel();
                }
            }
        });


        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(mOnSelectClickListener != null){
                    mOnSelectClickListener.onConfirm(mList2.get(mWheelView2.getCurrentItem()));
                }
            }
        });

        popupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }


}
