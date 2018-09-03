package com.dogs.pet.mylocation;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button btnLogin;
    private TextView txtSignUp;
    private EditText txtLemail;
    private EditText txtLpwd;
    private View rootView;
    public String GetEmail = null;
    public String GetPwd = null;
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View fragment = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = (Button) fragment.findViewById(R.id.btn_login);
        txtSignUp = (TextView) fragment.findViewById(R.id.txt_signup);
        txtLemail = (EditText) fragment.findViewById(R.id.txt_Lemail);
        txtLpwd = (EditText) fragment.findViewById(R.id.txt_Lpwd);

        txtSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        return fragment;
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

    public boolean Validation() {
        try {
            MyDBHandler dbHandler = new MyDBHandler(getActivity());
            String Uemail = txtLemail.getText().toString().trim();
            String Upwd = txtLpwd.getText().toString().trim();
            if ((Uemail.equals("")) || (Upwd.equals(""))) {
                Toast.makeText(getContext(), "Enter Credentials", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Cursor res = dbHandler.getuserdata(Uemail);
                res.moveToFirst();
                GetEmail = res.getString(res.getColumnIndex(dbHandler.USER_EMAIL));
                GetPwd = res.getString(res.getColumnIndex(dbHandler.USER_PASSWORD));
                if (GetEmail.equals(Uemail) && GetPwd.equals(Upwd) && !(GetEmail.equals("") && GetPwd.equals(""))) {
                    return true;
                }
                return false;

            }
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public void onClick(View v) {

        if (mListener != null) {
            if(v.getId() == R.id.txt_signup)
            {
                mListener.onButtonClick(v);

            }

        boolean valid = Validation();
        if(!valid) {

            Toast.makeText(getContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();


            txtLemail.setBackgroundColor(Color.RED);
            txtLpwd.setBackgroundColor(Color.RED);

        }else {

            mListener.onButtonClick(v);
        }

        }
    }
}
