package com.example.lab4;

import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private AlertDialog.Builder mDialogBuilder;
  private View mRootView;
  private static final String correctSid = "123456";
  private static final String correctPwd = "6666";
  private ImageView mAvatar;
  private TextInputLayout mSidInput;
  private EditText mSidText;
  private TextInputLayout mPwdInput;
  private EditText mPwdText;
  private RadioGroup mRadioGroup;
  private RadioButton mStudentButton, mStuffButton;
  private Button mLogin, mRegister;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
    setupOnClickCallback();
  }

  private void init() {
    this.mRootView = findViewById(R.id.root_view);
    this.mAvatar = (ImageView)findViewById(R.id.avatar);
    this.mSidInput = (TextInputLayout)findViewById(R.id.sid_input);
    this.mSidText = (EditText)findViewById(R.id.sid_text);
    this.mPwdInput = (TextInputLayout)findViewById(R.id.pwd_input);
    this.mPwdText = (EditText)findViewById(R.id.pwd_text);
    this.mRadioGroup = (RadioGroup)findViewById(R.id.radio_grp);
    this.mStudentButton = (RadioButton)findViewById(R.id.radio_student);
    this.mStuffButton = (RadioButton)findViewById(R.id.radio_stuff);
    this.mLogin = (Button)findViewById(R.id.login);
    this.mRegister = (Button)findViewById(R.id.register);

    this.mDialogBuilder = new AlertDialog.Builder(this);
    this.mDialogBuilder.setNegativeButton(
        "取消", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            MainActivity.this.showMsgOnToast("您选择了[取消]");
          }
        });
  }
  private void setupOnClickCallback() {
    this.mAvatar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.this.selectAvatar();
      }
    });
    this.mRadioGroup.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            MainActivity.this.showMsgOnSnackBar(
                "您选择了" + MainActivity.this.getSelectedRadio());
          }
        });
    this.mLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.this.triggerLogin();
      }
    });
    this.mRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.this.triggerRegister();
      }
    });
  }

  private void selectAvatar() {
    final String[] dialogList = {"拍摄", "从相册选择"};
    this.mDialogBuilder.setTitle("上传头像")
        .setItems(dialogList,
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int i) {
                      MainActivity.this.showMsgOnToast("您选择了[" +
                                                       dialogList[i] + "]");
                    }
                  })
        .show();
  }

  private void triggerLogin() {
    String sid = this.mSidText.getText().toString();
    String pwd = this.mPwdText.getText().toString();
    boolean sidEmpty = TextUtils.isEmpty(sid);
    boolean pwdEmpty = TextUtils.isEmpty(pwd);
    this.mSidInput.setError("学号不能为空");
    this.mSidInput.setErrorEnabled(sidEmpty);
    this.mPwdInput.setError("密码不能为空");
    this.mPwdInput.setErrorEnabled(!sidEmpty && pwdEmpty);
    if (!sidEmpty && !pwdEmpty) {
      String msg = "学号或密码错误";
      if (sid.equals(MainActivity.correctSid) &&
          pwd.equals(MainActivity.correctPwd)) {
        msg = "登录成功";
      }
      this.showMsgOnSnackBar(msg);
    }
  }

  private void triggerRegister() {
    String selected = MainActivity.this.getSelectedRadio();
    if (selected != null) {
      MainActivity.this.showMsgOnSnackBar(selected + "注册功能尚未启用");
    }
  }

  private String getSelectedRadio() {
    switch (this.mRadioGroup.getCheckedRadioButtonId()) {
    case R.id.radio_student:
      return this.mStudentButton.getText().toString();
    case R.id.radio_stuff:
      return this.mStuffButton.getText().toString();
    default:
      return "";
    }
  }

  private void showMsgOnToast(String msg) {
    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
  }
  private void showMsgOnSnackBar(String msg) {
    Snackbar.make(mRootView, msg, Snackbar.LENGTH_SHORT)
        .setAction("确定",
                   new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                       MainActivity.this.showMsgOnToast(
                           "Snackbar的确定按钮被点击了");
                     }
                   })
        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
        .setDuration(5000)
        .show();
  }
}
