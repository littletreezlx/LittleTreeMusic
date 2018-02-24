package com.example.littletreemusic.activity.login;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.pojo.RegisterInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
public class RegisterFragment extends Fragment{


    @BindView(R.id.button_register)
    Button btnRegister;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.editText_account)
    EditText editTextAccount;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.textView0)
    TextView textView0;
    @BindView(R.id.editText_nickname)
    EditText editTextNickname;
    @BindView(R.id.editText_age)
    EditText editTextAge;
    @BindView(R.id.radioButton_man)
    RadioButton radioButtonMan;
    @BindView(R.id.radioButton_woman)
    RadioButton radioButtonWoman;
    @BindView(R.id.button_back)
    Button btnBack;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.spinner_favouritrStyle)
    Spinner spinner0;

    Unbinder unbinder;
    private String mParam1;
    private String mParam2;
    private String gender = "man";
    private String favouriteStyle = "";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String baseurl = "http://192.168.1.104:8080/";
    private BaseAdapter myAdadpter = null;


    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });

        spinner0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                favouriteStyle=adapterView.getItemAtPosition(i).toString();
            }
        });




        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.button_register, R.id.editText_password, R.id.editText_account, R.id.editText_nickname, R.id.editText_age, R.id.radioButton_man, R.id.radioButton_woman, R.id.button_back,R.id.radioGroup,R.id.spinner_favouritrStyle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                Retrofit retrofit0 = new Retrofit.Builder()
                        .baseUrl(baseurl)
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
////                        .addConverterFactory(GsonConverterFactory.create())
//                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
                CommunityRetrofitRequest rq = retrofit0.create(CommunityRetrofitRequest.class);

                RegisterInfo registerInfo = new RegisterInfo();
                registerInfo.setAccount(editTextAccount.getText().toString());
                registerInfo.setPassword(editTextPassword.getText().toString());
                registerInfo.setNickName(editTextNickname.getText().toString());
                registerInfo.setAge(editTextAge.getText().toString());
                registerInfo.setFavouriteStyle(favouriteStyle);
                registerInfo.setGender(gender);

                Gson gson = new GsonBuilder().create();
                String registerInfoJson = gson.toJson(registerInfo);

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), registerInfoJson);
                rq.register(body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Response<String>>() {
                            @Override
                            public void accept(Response<String> response) throws Exception {
                                if (response.body().equals("register succeed")){
                                    Toast.makeText(getActivity(),"register succeed", Toast.LENGTH_SHORT).show();
                                    MainActivity mainActivity = (MainActivity) getActivity();

//                                    mainActivity.backFromRegister();
//                                    Toast.makeText(getActivity(),"go to community", Toast.LENGTH_SHORT).show();
//                                    mainActivity.gotoCommunity();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        });
                break;
            case R.id.editText_password:
                break;
            case R.id.editText_account:
                break;
            case R.id.editText_nickname:
                break;
            case R.id.editText_age:
                break;
            case R.id.radioButton_man:
                gender = "man";
                Toast.makeText(getActivity(), "man", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton_woman:
                gender = "woman";
                Toast.makeText(getActivity(), "woman", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioGroup:
                break;
            case R.id.spinner_favouritrStyle:

                break;
            case R.id.button_back:

                break;

            default:
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
