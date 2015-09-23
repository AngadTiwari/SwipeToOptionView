package com.example.controller.swipetooptionview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView list;
    private ListViewAdapter listViewAdapter;

    private ArrayList<String> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data.add("First data");
        data.add("Second data");
        data.add("Third data");

        list=(ListView)findViewById(R.id.list);
        listViewAdapter=new ListViewAdapter(MainActivity.this, data);
        list.setAdapter(listViewAdapter);
    }

    public class ListViewAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<String> data;

        private static final int SWIPE_THRESHOLD = 60;
        private static final int SWIPE_VELOCITY_THRESHOLD = 60;

        public ListViewAdapter(final Context context, ArrayList<String> data)
        {
            this.context=context;
            this.data=data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ListViewAdapterHolder listViewAdapterHolder;
            if(convertView==null)
            {
                listViewAdapterHolder=new ListViewAdapterHolder();

                convertView= ((Activity)context).getLayoutInflater().inflate(R.layout.listview_row, parent, false);
                convertView.setTag(listViewAdapterHolder);
            }
            else
            {
                listViewAdapterHolder=(ListViewAdapterHolder)convertView.getTag();
            }

            listViewAdapterHolder.frame=(FrameLayout)convertView.findViewById(R.id.frame);
            listViewAdapterHolder.textViewFront=(TextView)convertView.findViewById(R.id.front);
            listViewAdapterHolder.textViewBack=(TextView)convertView.findViewById(R.id.back);

            /*listViewAdapterHolder.frame.removeAllViews();
            listViewAdapterHolder.frame.addView(listViewAdapterHolder.textViewBack);
            listViewAdapterHolder.frame.addView(listViewAdapterHolder.textViewFront);*/

            listViewAdapterHolder.textViewFront.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listViewAdapterHolder.gestureDetectorFront.onTouchEvent(event);
                    return true;
                }
            });
            listViewAdapterHolder.textViewBack.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listViewAdapterHolder.gestureDetectorBack.onTouchEvent(event);
                    return true;
                }
            });

            listViewAdapterHolder.gestureDetectorFront=new GestureDetector(this.context, new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    boolean result = false;
                    try {
                        float diffY = e2.getY() - e1.getY();
                        float diffX = e2.getX() - e1.getX();
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    //onSwipeRight();
                                } else {
                                    listViewAdapterHolder.textViewBack.setAnimation(AnimationUtils.loadAnimation(context, R.anim.front_show_menu));
                                    listViewAdapterHolder.textViewFront.setAnimation(AnimationUtils.loadAnimation(context, R.anim.front_hide_menu));
                                    listViewAdapterHolder.frame.bringChildToFront(listViewAdapterHolder.textViewBack);
                                    listViewAdapterHolder.frame.requestLayout();
                                    listViewAdapterHolder.frame.invalidate();
                                }
                            }
                            result = true;
                        }
                        else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                //onSwipeBottom();
                            } else {
                                //onSwipeTop();
                            }
                        }
                        result = true;

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return result;
                }
            });

            listViewAdapterHolder.gestureDetectorBack=new GestureDetector(this.context, new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    boolean result = false;
                    try {
                        float diffY = e2.getY() - e1.getY();
                        float diffX = e2.getX() - e1.getX();
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    listViewAdapterHolder.textViewFront.setAnimation(AnimationUtils.loadAnimation(context, R.anim.back_show_menu));
                                    listViewAdapterHolder.textViewBack.setAnimation(AnimationUtils.loadAnimation(context, R.anim.back_hide_menu));
                                    listViewAdapterHolder.frame.bringChildToFront(listViewAdapterHolder.textViewFront);
                                    listViewAdapterHolder.frame.requestLayout();
                                    listViewAdapterHolder.frame.invalidate();
                                } else {
                                    //onSwipeLeft();
                                }
                            }
                            result = true;
                        }
                        else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                //onSwipeBottom();
                            } else {
                                //onSwipeTop();
                            }
                        }
                        result = true;

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return result;
                }
            });

            return convertView;
        }

        @Override
        public int getCount() {
            if(data==null)
                return 0;
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
    }

    public static class ListViewAdapterHolder
    {
        FrameLayout frame;
        TextView textViewFront;
        TextView textViewBack;
        GestureDetector gestureDetectorFront,gestureDetectorBack;
    }
}
