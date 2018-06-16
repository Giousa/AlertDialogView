package com.zmm.alertdialogview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private SimpleInputDialog3 mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

        final SimpleInputDialog simpleInputDialog = new SimpleInputDialog(this,"修改用户名","请输入你。。。");

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

    }

    private void selectPic() {

    }
}
