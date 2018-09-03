package com.dogs.pet.mylocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;
    private static Context _context;
    private FusedLocationProviderClient mFusedLocationClient ;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 377;
    private GoogleMap mapView;
    private ImageView Img_Hatch;
    private ImageView Img_Sedan;
    private ImageView Img_SUV;

    public double Lat;
    public double Lng;
    public EditText txtSlocation;
    public EditText txtDlocation;
    public String ChangedAddress;
    public Marker SLMarker;
    public Marker DLMarker;
    public boolean IsSrcLocation = true;

    public MapFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(Context context) {
        MapFragment fragment = new MapFragment();
        _context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtSlocation = (EditText) rootView.findViewById(R.id.txt_SLocation);
        txtDlocation = (EditText) rootView.findViewById(R.id.txt_DLocation);
        Img_Hatch = (ImageView) rootView.findViewById(R.id.imageView_hatch);
        Img_Sedan = (ImageView) rootView.findViewById(R.id.imageView_sedan);
        Img_SUV = (ImageView) rootView.findViewById(R.id.imageView_SUV);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        checkLocationPermission();
        return rootView;
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




    private void checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){

            }else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else{
            getCurrentLocation();
        }


    }

    private void getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mapView.clear();

                                LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                SLMarker = mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true));
                                mapView.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                                CameraPosition position = new CameraPosition.Builder().target(currentLocation).zoom(18.0f).build();

                                mapView.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                                SLMarker.setTitle("SMarker");
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                try {

                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                                    String address = addresses.get(0).getAddressLine(0);
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();
                                    String Locality = addresses.get(0).getLocality();
                                    String SubLocality = addresses.get(0).getSubLocality();

                                    txtSlocation.setText(address);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                Log.d("MyLocation:", location.toString());

                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:{
                if(grantResults.length>0
                        &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{

                }

            }
        }
    }
    public String OntextChange(String s) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocationName(s, 1);

            Lat = addresses.get(0).getLatitude();
            Lng = addresses.get(0).getLongitude();


            LatLng currentLocation = new LatLng(Lat,Lng);
           // mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true));
            mapView.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            CameraPosition position = new CameraPosition.Builder().target(currentLocation).zoom(18.0f).build();
            mapView.animateCamera(CameraUpdateFactory.newCameraPosition(position));

            if (IsSrcLocation)
            {

                SLMarker.remove();
                SLMarker = mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true));

            }else
            {
                if(DLMarker == null) {
                    DLMarker = mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
                else
                {
                    DLMarker.remove();
                    DLMarker = mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
                DLMarker.setTitle("DMarker");
            }





           return addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView = googleMap;

        getCurrentLocation();

       // txtSlocation.getOnFocusChangeListener();



        txtSlocation.setSelectAllOnFocus(true);
        txtSlocation.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    IsSrcLocation = true;
                    ChangedAddress = OntextChange(txtSlocation.getText().toString());
                    txtSlocation.setText(ChangedAddress);
                    return true;
                }
                return false;
            }
        });
        txtSlocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && SLMarker != null) {

                    mapView.animateCamera(CameraUpdateFactory.newLatLng(SLMarker.getPosition()));
                }
            }
        });
        txtDlocation.setSelectAllOnFocus(true);
        txtDlocation.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    IsSrcLocation = false;
                    ChangedAddress = OntextChange(txtDlocation.getText().toString());
                    txtDlocation.setText(ChangedAddress);
                    return true;
                }
                return false;
            }
        });
        txtDlocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && DLMarker != null) {

                    mapView.animateCamera(CameraUpdateFactory.newLatLng(DLMarker.getPosition()));
                }
            }
        });
       /* txtDlocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    ChangedAddress = OntextChange(txtDlocation.getText().toString());
                    txtDlocation.setText(ChangedAddress);
                }else{


                    txtDlocation.setSelectAllOnFocus(true);
                }

            }
        });
        /* txtSlocation.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 5)
                    OntextChange("chennai");
                LatLng currentLocation = new LatLng(Lat,Lng);
                mapView.addMarker(new MarkerOptions().position(currentLocation).draggable(true));
                mapView.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                CameraPosition position = new CameraPosition.Builder().target(currentLocation).zoom(18.0f).build();

                mapView.animateCamera(CameraUpdateFactory.newCameraPosition(position));

            }
        });
*/      Img_Hatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                Img_Hatch.setImageResource(R.drawable.hatch_click);
            }
        });
        //Img_Hatch.setOnClickListener((View.OnClickListener) this);

        mapView.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {


            @Override
            public void onMarkerDragStart(Marker marker) {

                String M = marker.getTitle();

                if(M.equals("SMarker"))
                    IsSrcLocation = true;
                else
                    IsSrcLocation = false;

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override

            public void onMarkerDragEnd(Marker marker) {
                try {
                Geocoder geocoder;
                List<Address> DragAddress;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                DragAddress = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);


                if (IsSrcLocation) {


                    txtSlocation.setText(DragAddress.get(0).getAddressLine(0));

                }else
                {
                    //DragAddress = geocoder.getFromLocation(DLMarker.getPosition().latitude, DLMarker.getPosition().longitude, 1);
                    txtDlocation.setText(DragAddress.get(0).getAddressLine(0));
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }



        });

    }
}
