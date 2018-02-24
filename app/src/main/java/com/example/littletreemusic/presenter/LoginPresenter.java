package com.example.littletreemusic.presenter;
/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */



public class LoginPresenter implements LoginContract.ILoginPresenter{


//    @Inject
//    LoginContract.ILoginView mILoginView;

//    @Inject
//    LoginManager loginManager;
//    @Inject
//    SharedPreferences sharedPreferences;
//    @Inject
//    Retrofit retrofit;
//    @Inject
//    Gson gson;

//    @Override
//    public void init(Context context) {
//
//    }


//    @Inject
//    public LoginPresenter(){
//        super();
//    }

//    @Inject
//    public LoginPresenter(LoginContract.ILoginView iLoginView){
//        mILoginView=iLoginView;
//    }

//    @Override
//    public void bindView(LoginContract.ILoginView view) {
//        mILoginView=view;
////        MyApplication.getAppComponent().newLoginPresenterComponent().inject(this);
//    }

    @Override
    public void login(String account, String password) {
//        验证账号密码是否符合规则

//        loginManager.login(account, password, new LoginManager.LoginListener() {
//            @Override
//            public void onSuccessLogin(UserEntity user) {
//
//            }
//
//            @Override
//            public void onFailedLogin(Failed failed) {
//
//            }
//        });


//        mILoginView.showLoginSucceed();



    }

    @Override
    public void toRegisterFragment() {

    }

    @Override
    public void toAlterPasswordFragment() {

    }

}


