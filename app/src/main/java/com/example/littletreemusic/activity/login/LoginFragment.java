package com.example.littletreemusic.activity.login;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.presenter.LoginContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends Fragment implements LoginContract.ILoginView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CommunityRetrofitRequest communityRetrofitRequest;
    public static final String baseurl = "http://192.168.1.104:8080/";


    @BindView(R.id.button_register)
    Button btn_Register;
    @BindView(R.id.button_login)
    Button btn_Login;
    @BindView(R.id.button_changePassWord)
    Button btn_ChangePassWord;
    @BindView(R.id.editText_password)
    EditText editText_account;
    @BindView(R.id.editText_account)
    EditText editText_password;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textView0)
    TextView textview_Welcome;

    Unbinder unbinder;
    String account, passWord;
    @BindView(R.id.button_back)
    Button btnBack;

    private OnFragmentInteractionListener mListener;

//    @Inject
//    LoginPresenter loginPresenter;
//

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showLoginSucceed() {
        Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailedReason() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DaggerLO.builder().appComponent(MyApplication.getAppComponent())
//                .fragmentModule(new LoginModule(this)).build()

//        MyApplication.getAppComponent().newFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mListener = null;
        unbinder.unbind();
    }

    @OnClick({R.id.button_register, R.id.button_login, R.id.button_changePassWord, R.id.editText_password, R.id.editText_account, R.id.progressBar, R.id.textView0,R.id.button_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_register:

                break;
            case R.id.button_login:

//                Retrofit retrofit1 = new Retrofit.Builder()
//                        .baseUrl(baseurl)
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create())
////                        .addConverterFactory(ScalarsConverterFactory.create())
//                        .build();
//
//                communityRetrofitRequest = retrofit1.create(CommunityRetrofitRequest.class);
//
//                LoginInfo loginInfo=new LoginInfo();
//                loginInfo.setAccount(editText_account.getText().toString());
//                loginInfo.setPassword(editText_password.getText().toString());
//                Gson gson = new GsonBuilder().create();
//                String loginInfoJson = gson.toJson(loginInfo);
//                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), loginInfoJson);
//                communityRetrofitRequest.login(body).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Response<String>>() {
//                            @Override
//                            public void accept(Response<String> response) throws Exception {
//                                if (response.body().equals("register succeed")){
//                                    MainActivity mainActivity = (MainActivity) getActivity();
//                                    mainActivity.backFromLogin();
//                                    mainActivity.gotoCommunity();
//                                    Toast.makeText(getActivity(),"go to community",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//
//                            }
//                        });

//                String s=sharedPreferences.getString("a","a");
//                loginPresenter.login("a","a");


                break;
            case R.id.button_changePassWord:

                break;
            case R.id.editText_password:

                break;
            case R.id.editText_account:

                break;
            case R.id.progressBar:

                break;

            case R.id.button_back:
                break;
                default:break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
