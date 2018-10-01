package com.example.ma.myapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ma.myapplication.R;
import com.example.ma.myapplication.mclass.Expression;
import com.example.ma.myapplication.mclass.Helper;
import com.example.ma.myapplication.mclass.MyKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MyClass";
    public static final String PREF_RESULT = "PREF_RESULT";
    private Button mButtonZero, mButtonOne, mButtonTwo, mButtonThree, mButtonFour, mButtonFive,
            mButtonSix, mButtonSeven, mButtonEight, mButtonNiNe, mButtonAdd, mButtonSub,
            mButtonMulti, mButtonDiv, mButtonResult, mButtonClear, mButtonOpenBracket,
            mButtonCloseBrackets, mButtonDot;
    private TextView mTextResult, mTextExpression;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private double mLastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_number_zero:
                displayCalculator(getString(R.string.number_zero));
                break;
            case R.id.button_number_one:
                displayCalculator(getString(R.string.number_one));
                break;
            case R.id.button_number_two:
                displayCalculator(getString(R.string.number_two));
                break;
            case R.id.button_number_three:
                displayCalculator(getString(R.string.number_three));
                break;
            case R.id.button_number_four:
                displayCalculator(getString(R.string.number_four));
                break;
            case R.id.button_number_five:
                displayCalculator(getString(R.string.number_five));
                break;
            case R.id.button_number_six:
                displayCalculator(getString(R.string.number_six));
                break;
            case R.id.button_number_seven:
                displayCalculator(getString(R.string.number_seven));
                break;
            case R.id.button_number_eight:
                displayCalculator(getString(R.string.number_eight));
                break;
            case R.id.button_number_nine:
                displayCalculator(getString(R.string.number_nine));
                break;
            case R.id.button_add:
                displayCalculator(getString(R.string.add));
                break;
            case R.id.button_sub:
                displayCalculator(getString(R.string.subtraction));
                break;
            case R.id.button_multi:
                displayCalculator(getString(R.string.multiplication));
                break;
            case R.id.button_div:
                displayCalculator(getString(R.string.division));
                break;
            case R.id.button_open_brackets:
                displayCalculator(getString(R.string.open_brackets));
                break;
            case R.id.button_close_brackets:
                displayCalculator(getString(R.string.close_brackets));
                break;
            case R.id.button_dot:
                displayCalculator(getString(R.string.dot));
                break;
            case R.id.button_clear:
                clearScreen();
                break;
            case R.id.button_equal:
                mTextExpression.setText(mTextResult.getText().toString());
                mTextResult.setText(retypeResult(getValue(mTextResult.getText().toString())));
                mLastResult = Double.parseDouble(mTextResult.getText().toString());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_clear:
                clearScreen();
                break;
            case R.id.menu_save_last_result:
                saveLastResult();
                break;
            case R.id.menu_last_result:
                getLastResult();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mContext = getApplicationContext();
        mButtonZero = findViewById(R.id.button_number_zero);
        mButtonOne = findViewById(R.id.button_number_one);
        mButtonTwo = findViewById(R.id.button_number_two);
        mButtonThree = findViewById(R.id.button_number_three);
        mButtonFour = findViewById(R.id.button_number_four);
        mButtonFive = findViewById(R.id.button_number_five);
        mButtonSix = findViewById(R.id.button_number_six);
        mButtonSeven = findViewById(R.id.button_number_seven);
        mButtonEight = findViewById(R.id.button_number_eight);
        mButtonNiNe = findViewById(R.id.button_number_nine);
        mButtonAdd = findViewById(R.id.button_add);
        mButtonSub = findViewById(R.id.button_sub);
        mButtonMulti = findViewById(R.id.button_multi);
        mButtonDiv = findViewById(R.id.button_div);
        mButtonResult = findViewById(R.id.button_equal);
        mButtonClear = findViewById(R.id.button_clear);
        mButtonOpenBracket = findViewById(R.id.button_open_brackets);
        mButtonCloseBrackets = findViewById(R.id.button_close_brackets);
        mButtonDot = findViewById(R.id.button_dot);
        mTextResult = findViewById(R.id.text_result_screen);
        mTextExpression = findViewById(R.id.text_expression_screen);

        mButtonZero.setOnClickListener(this);
        mButtonOne.setOnClickListener(this);
        mButtonTwo.setOnClickListener(this);
        mButtonThree.setOnClickListener(this);
        mButtonFour.setOnClickListener(this);
        mButtonFive.setOnClickListener(this);
        mButtonSix.setOnClickListener(this);
        mButtonSeven.setOnClickListener(this);
        mButtonEight.setOnClickListener(this);
        mButtonNiNe.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonSub.setOnClickListener(this);
        mButtonMulti.setOnClickListener(this);
        mButtonDiv.setOnClickListener(this);
        mButtonResult.setOnClickListener(this);
        mButtonClear.setOnClickListener(this);
        mButtonOpenBracket.setOnClickListener(this);
        mButtonCloseBrackets.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);

        mPreferences = mContext.getSharedPreferences(getString(R.string.preference_file_key),mContext.MODE_PRIVATE);
        mEditor = mPreferences.edit();

    }

    private boolean isEqual(String s1, String s2) {
        return s2.compareToIgnoreCase(s1) == 0;
    }

    private void displayCalculator(String s) {
        if (!isEqual(mTextExpression.getText().toString(), MyKey.NULL)) {
            mTextExpression.setText(MyKey.NULL);
            mTextResult.setText(s);
        } else if (isEqual(mTextResult.getText().toString(), MyKey.ZERO)) {
            mTextResult.setText(s);
        } else {
            mTextResult.setText(mTextResult.getText().toString().concat(s));
        }
    }

    private Double getValue(String sMath) {
        Expression expression = new Expression();
        return expression.getValue(sMath);
    }

    private String retypeResult(Double value) {
        if (value - (value.intValue()) ==0) {
            return String.valueOf(value.intValue());
        } else {
            return String.format("%6f", value);
        }
    }
    private void clearScreen(){
        mTextResult.setText(MyKey.ZERO);
        mTextExpression.setText(MyKey.NULL);
    }
    private void saveLastResult(){
        mEditor.putFloat(PREF_RESULT,(float) mLastResult);
        mEditor.commit();
        Helper.showToast(mContext,getString(R.string.result_saved));
    }
    private void getLastResult(){
        SharedPreferences pref = getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        Helper.showToast(mContext,getString(R.string.menu_last_result).concat(Float.toString(pref.getFloat(PREF_RESULT,0))) );
    }
}
