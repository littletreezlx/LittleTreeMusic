package com.example.littletreemusic.activity.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.base.BaseFragment;
import com.example.littletreemusic.di.Component.navigation.DaggerNavRegisterComponent;
import com.example.littletreemusic.di.Component.navigation.NavRegisterComponent;
import com.example.littletreemusic.di.Component.navigation.NavRegisterModule;
import com.example.littletreemusic.pojo.RegisterMsg;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;
import com.example.littletreemusic.presenter.navigation.NavRegisterContract;
import com.example.littletreemusic.presenter.navigation.NavRegisterPresenter;
import com.example.littletreemusic.util.common.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NavRegisterFragment extends BaseFragment implements NavRegisterContract.INavRegisterView {


    @BindView(R.id.nav_register_btn_back)
    ImageButton btn_Back;
    @BindView(R.id.nav_register_edt_account)
    EditText edt_Account;
    @BindView(R.id.nav_register_edt_password)
    EditText edt_Password;
    @BindView(R.id.nav_register_edt_confirmpassword)
    EditText edt_Confirmpassword;
    @BindView(R.id.nav_register_edt_nickname)
    EditText edt_Nickname;
    @BindView(R.id.nav_register_edt_age)
    EditText edt_Age;
    @BindView(R.id.nav_register_radiobtn_man)
    RadioButton radiobtn_Man;
    @BindView(R.id.nav_register_radiobtn_woman)
    RadioButton eadiobtn_Woman;
    @BindView(R.id.nav_register_radiogroup_gender)
    RadioGroup radiogroup_Gender;
    @BindView(R.id.nav_register_spinner_favourite)
    Spinner spinner_Favourite;
    @BindView(R.id.nav_register_edt_securitycode)
    EditText edt_Securitycode;
    @BindView(R.id.nav_register_btn_getsecuritycode)
    Button btn_Getsecuritycode;
    @BindView(R.id.nav_register_btn_register)
    Button btn_Register;
    Unbinder unbinder;

    @Inject
    NavRegisterPresenter navRegisterPresenter;
    @Inject
    NavFMPresenter navFMPresenter;
    @Inject
    ToastUtil toastUtil;
//    @Inject
//    MainActivity mainActivity;

    private String gender = "man";
    private String favouriteStyle = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavRegisterComponent navRegisterComponent = DaggerNavRegisterComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .navRegisterModule(new NavRegisterModule(this))
                .build();
        navRegisterComponent.inject(this);
        navRegisterComponent.inject(navRegisterPresenter);
        View view = inflater.inflate(R.layout.nav_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        radiogroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton_man:
                        gender="男";
                        break;
                    case R.id.radioButton_woman:
                        gender="女";
                    default:break;
                }
            }
        });


//        spinner_Favourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                favouriteStyle=adapterView.getItemAtPosition(i).toString();
//            }
//        });
        return view;
    }

    @Override
    public void showRegisterSucceed() {
        navFMPresenter.FromNavRegister();
        toastUtil.showShort("注册成功");
    }

    @Override
    public void showRegisterFailedReason(String reason) {
        toastUtil.showShort(reason);
    }

    @Override
    public void showProgressBar() {

    }

    @OnClick({R.id.nav_register_btn_back, R.id.nav_register_btn_getsecuritycode, R.id.nav_register_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_register_btn_back:
                navFMPresenter.FromNavRegister();
                break;
            case R.id.nav_register_btn_getsecuritycode:
//
                break;
            case R.id.nav_register_btn_register:
                String password = edt_Password.getText().toString();
                String confrimPassword = edt_Confirmpassword.getText().toString();
                if (password.equals(confrimPassword)){
                    RegisterMsg registerMsg = new RegisterMsg();
                    registerMsg.setUsername(edt_Account.getText().toString());
                    registerMsg.setPassword(edt_Password.getText().toString());
                    registerMsg.setnickname(edt_Nickname.getText().toString());
                    registerMsg.setAge(edt_Age.getText().toString());
                    registerMsg.setGender(gender);
                    registerMsg.setFavouriteStyle(favouriteStyle);
                    navRegisterPresenter.register(registerMsg);
                }else {
                    showRegisterFailedReason("两次输入密码不一致");
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
