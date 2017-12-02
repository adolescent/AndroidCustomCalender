package com.example.linkio.androidcustomcalendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class CalendarCustomView extends LinearLayout{
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 35;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private DatabaseQuery mQuery;
    private int firstDayOfTheMonth;
    private List<Events> mEvents = new ArrayList<>();
    private Database db;

    public CalendarCustomView(Context context) {
        super(context);
    }
    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }
    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calender_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView) view.findViewById(R.id.calendar_grid);
    }

    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(context, "Clicked " + position, Toast.LENGTH_SHORT).show();

                Database db = new Database(context);

                int mSelectedMonth = cal.get(Calendar.MONTH)+1;
                int mDayOfMonth = position - firstDayOfTheMonth + 1;
                boolean mFountIt = false;

                mEvents = db.getAllEvents();

                    for(Events myEvent : mEvents) {
                        if(myEvent.getEventDate() != null && myEvent.getEventDate().equals(mDayOfMonth + "-" + mSelectedMonth +  "-" + cal.get(Calendar.YEAR))) {
                            //Process data do whatever you want
                            //System.out.println("Found it!");

                            db.deleteEvent(new Events("SwitchLight",  mDayOfMonth + "-" + mSelectedMonth +  "-" + cal.get(Calendar.YEAR)));
                            calendarGridView.getChildAt(position).setBackgroundColor(Color.GRAY);
                            mFountIt = true;
                        }
                    }

                    if(!mFountIt) {
                        db.addEvent(new Events("SwitchLight", mDayOfMonth + "-" + mSelectedMonth + "-" + cal.get(Calendar.YEAR)));
                        calendarGridView.getChildAt(position).setBackgroundColor(Color.GREEN);
                    }

                /*
                mEvents.add(new Events("SwitchLight", "2-12-2017"));
                mEvents.add(new Events("SwitchLight", "3-12-2017"));
                mEvents.add(new Events("SwitchLight", "5-12-2017"));*/
               //setUpCalendarAdapter(mEvents);
               //mAdapter.notifyDataSetChanged();

            }
        });
    }


    private void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();

        //mQuery = new DatabaseQuery(context);
        //List<EventObjects> mEvents = mQuery.getAllFutureEvents();
        //db = new Database(context);
        //List<Events> mEvents = new ArrayList<>(); // = db.getAllEvents();

        db = new Database(context);
        List<Events> mEvents = db.getAllEvents();

        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new GridAdapter(context, dayValueInCells, cal, mEvents);
        calendarGridView.setAdapter(mAdapter);
    }

}
