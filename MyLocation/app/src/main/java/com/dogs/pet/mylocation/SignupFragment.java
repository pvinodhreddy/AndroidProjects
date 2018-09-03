package com.dogs.pet.mylocation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dogs.pet.mylocation.HomeActivity.generateRandom;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_SignUp;
    private EditText Email;
    private EditText password;
    private EditText Cpassword;
    private OnFragmentInteractionListener mListener;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        View fragment = inflater.inflate(R.layout.fragment_signup, container, false);

       btn_SignUp = (Button) fragment.findViewById(R.id.btn_signup);
       Email = (EditText) fragment.findViewById(R.id.txt_Email);
       password = (EditText) fragment.findViewById(R.id.txt_Pwd);
       Cpassword = (EditText) fragment.findViewById(R.id.txt_CPwd);

        btn_SignUp.setOnClickListener(this);
        // Inflate the layout for this fragment
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
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }




    @Override
    public void onClick(View v) {

        String email = Email.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String cpwd = Cpassword.getText().toString().trim();

        if (mListener != null) {
            if(email.equals("")){
                Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            }else if(!emailValidator(email)) {
                Toast.makeText(getContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }else if (pwd.equals("")){
                Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            }else if(!pwd.equals(cpwd)){
                Toast.makeText(getContext(), "Password not Matched", Toast.LENGTH_SHORT).show();
            }else
            {
                MyDBHandler dbHandler = new MyDBHandler(getActivity());
                HomeActivity homeactivity = new HomeActivity();


                String RanID = generateRandom();

                String datetime = homeactivity.getDateTime();

               // boolean delete = dbHandler.Deleteuser("Vinodh@gmail.com");

                 boolean Response = dbHandler.insertuser( RanID, email, pwd, null, null, null, null, datetime );

                    if(Response)
                    mListener.onButtonClick(v);

             }
        }



        // boolean Response = dbHandler.insertuser( RanID,String.valueOf(R.id.txt_Email), String.valueOf(R.id.txt_Pwd), null, null, null, null, homeactivity.getDateTime() );


    }




    }
