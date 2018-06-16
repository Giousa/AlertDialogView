package com.zmm.alertdialogview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class SimpleInputDialog3 extends Dialog {

    private TextView mTitle;
    private EditText mEditText;
    private Button mCancel;
    private Button mConfirm;
    private String mTitleStr;
    private String mHintStr;

    private Context context;
    private boolean cancelTouchout;
    private View view;



    private SimpleInputDialog3.OnSimpleClickListener OnSimpleClickListener;

    public void setOnSimpleClickListener(SimpleInputDialog3.OnSimpleClickListener onClickListener) {
        OnSimpleClickListener = onClickListener;
    }

    public interface OnSimpleClickListener{

        void onCancel();

        void onConfirm(String content);
    }


    private SimpleInputDialog3(Builder builder) {
        super(builder.context);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        mTitleStr = builder.mTitleStr;
        mHintStr = builder.mHintStr;
        view = builder.view;
    }


    private SimpleInputDialog3(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        mTitleStr = builder.mTitleStr;
        mHintStr = builder.mHintStr;
        view = builder.view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_input_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        initView();


    }


    private void initView() {
        mTitle = findViewById(R.id.tv_dialog_title);
        mEditText = findViewById(R.id.et_dialog_input);
        mCancel = findViewById(R.id.btn_dialog_cancel);
        mConfirm = findViewById(R.id.btn_dialog_confirm);

        mTitle.setText(mTitleStr);
        mEditText.setHint(mHintStr);

    }



    public static final class Builder {


        private Context context;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;
        private String mTitleStr;
        private String mHintStr;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder title(String mTitle){
            this.mTitleStr = mTitle;
            return this;
        }

        public Builder hint(String mHint){
            this.mHintStr = mHint;
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnclick(int viewRes,OnSimpleClickListener onSimpleClickListener){
            view.findViewById(viewRes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return this;
        }


        public SimpleInputDialog3 build() {
            if (resStyle != -1) {
                return new SimpleInputDialog3(this, resStyle);
            } else {
                return new SimpleInputDialog3(this);
            }
        }
    }

}
