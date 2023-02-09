package com.example.c880872_mapoverlays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Variable initialize
    GoogleMap gMap;

    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<LatLng> routePts = new ArrayList<>();
    //List<Double> latList = new ArrayList<>();
    double latList[] = new double[10];
    double longList[] = new double[10];
    List<Marker> markerList = new ArrayList<>();
    double end_latitude,end_longitude;

    int red=0,green=255,blue=0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
        getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync( this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;



        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = gMap.addMarker(markerOptions);

                latLngList.add(latLng);
                markerList.add(marker);

                end_latitude=marker.getPosition().latitude;
                end_longitude = marker.getPosition().longitude;


                if (polygon !=null)polygon.remove();
                PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList).clickable(true);
                polygon = gMap.addPolygon(polygonOptions);

                polygon.setStrokeColor(Color.rgb(red,green,blue));
                polygon.setPoints(latLngList);

                if (polygon==null)return;
                polygon.setFillColor(Color.rgb(255,0,0));


                float results1[] = new float[10];
                float results2[] = new float[10];
                float results3[] = new float[10];
                String a = null;
                String b=null;
                String c=null;

                for (int i = 0; i <latLngList.size() ; i++) {
                    latList [i]=latLngList.get(i).latitude;
                    longList[i]=latLngList.get(i).longitude;
                }

                Location.distanceBetween(latList[0],longList[0],latList[1],longList[1],results1);
                Location.distanceBetween(latList[1],longList[1],latList[2],longList[2],results2);
                Location.distanceBetween(latList[2],longList[2],latList[3],longList[3],results3);
                //Location.distanceBetween(latList[1],longList[1],latList[2],longList[2],results);
                //Location.distanceBetween(latList[2],longList[2],latList[3],longList[3],results);

                System.out.println("1:-"+results1[0]);
                System.out.println("2:-"+results2[0]);
                System.out.println("3:-"+results3[0]);

                a=Float.toString(results1[0]);
                b=Float.toString(results2[0]);
                c=Float.toString(results3[0]);




                addTextToPolyline(googleMap,latLngList,Float.toString(results1[count]));
//                addTextToPolyline(googleMap,latLngList,Float.toString(results1[1]));
                count = count+ 1;

                if(count > 2){
                    count = 1;
                }



//
//
//                    //System.out.println(latLngList);
//
//                    latList [i]=latLngList.get(i).latitude;
//                    longList[i]=latLngList.get(i).longitude;
//
//                    System.out.println("Latitude"+latList[i]);
//                    System.out.println("Longitude"+longList[i]);
//
//                    for (int j = 0; j < 3; j++) {
//                        for (int k = 0; k < 3; k++) {
//                            markerOptions.position(new LatLng(latList[i],longList[i]));
//                            markerOptions.title("Tfds");
//                            //Location.distanceBetween(latList[j],longList[k],latList[j+1],longList[k+1],results);
//
//                        }
//                    }
//
//                    Location.distanceBetween(latList[0],longList[0],latList[1],longList[1],results);
//                    Location.distanceBetween(latList[1],longList[1],latList[2],longList[2],results);
//                    Location.distanceBetween(latList[2],longList[2],latList[3],longList[3],results);
//
//                    a=Float.toString(results[0]);
//                    b=Float.toString(results[1]);
//                    c=Float.toString(results[2]);
//
//                    System.out.println("First-"+results[0]);
//                    System.out.println("Second-"+results[1]);
//                    System.out.println("Third-"+results[2]);
//
//                }

//                Marker melbourne = googleMap.addMarker(new MarkerOptions().position(latLng).title(a));
//                melbourne.showInfoWindow();
//
//                Marker melbourn = googleMap.addMarker(new MarkerOptions().position(latLng).title(b));
//                melbourne.showInfoWindow();
//
//                Marker melbour = googleMap.addMarker(new MarkerOptions().position(latLng).title(c));
//                melbourne.showInfoWindow();




//               float results[] = new float[10];
//               Location.distanceBetween(latLngList[0],,end_latitude,end_longitude,results);
//               markerOptions.snippet(String.valueOf(results[0]));



            }



        });
    }

    private void addTextToPolyline(GoogleMap googleMap, List<LatLng> points, String text) {
        // Add the polyline to the map
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(5).color(Color.RED);
        Polyline polyline = googleMap.addPolyline(polylineOptions);

        // Find the midpoint of the polyline
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        LatLngBounds bounds = builder.build();
        LatLng midpoint = bounds.getCenter();

        // Add a marker at the midpoint with the text
        MarkerOptions markerOptions = new MarkerOptions().position(midpoint).title(text);
        googleMap.addMarker(markerOptions);
    }


    public Marker addText(final Context context, final GoogleMap map,
                          final LatLng location, final String text, final int padding,
                          final int fontSize) {
        Marker marker = null;

        if (context == null || map == null || location == null || text == null
                || fontSize <= 0) {
            return marker;
        }

        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(fontSize);

        final Paint paintText = textView.getPaint();

        final Rect boundsText = new Rect();
        paintText.getTextBounds(text, 0, textView.length(), boundsText);
        paintText.setTextAlign(Paint.Align.CENTER);

        final Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        final Bitmap bmpText = Bitmap.createBitmap(boundsText.width() + 2
                * padding, boundsText.height() + 2 * padding, conf);

        final Canvas canvasText = new Canvas(bmpText);
        paintText.setColor(Color.BLACK);

        canvasText.drawText(text, canvasText.getWidth() / 2,
                canvasText.getHeight() - padding - boundsText.bottom, paintText);

        final MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(bmpText))
                .anchor(0.5f, 1);

        marker = map.addMarker(markerOptions);

        return marker;
    }

}

