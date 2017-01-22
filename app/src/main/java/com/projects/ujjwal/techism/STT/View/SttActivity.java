package com.projects.ujjwal.techism.STT.View;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.ujjwal.techism.R;
import com.projects.ujjwal.techism.STT.Model.RetrofitSttHelper;
import com.projects.ujjwal.techism.STT.Presenter.SttPresenter;
import com.projects.ujjwal.techism.STT.Presenter.SttPresenterImpl;
import com.projects.ujjwal.techism.SensorsActivity;
import com.projects.ujjwal.techism.TtsActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class SttActivity extends AppCompatActivity implements View.OnClickListener, SttView {

	private TextView voiceInput;
	private TextView speakButton;
	private String objectName;
	private SttPresenter sttPresenter;
	private final int REQ_CODE_SPEECH_INPUT = 100;

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stt);
		voiceInput = (TextView) findViewById(R.id.voiceInput);
		speakButton = (TextView) findViewById(R.id.btnSpeak);
		sttPresenter=new SttPresenterImpl(this,new RetrofitSttHelper());
		speakButton.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		if (v == speakButton) {
			askSpeechInput();
		}
	}

	private void askSpeechInput() {

			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"How may i help you.??");
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Log.d("Speech To Text ", "askSpeechInput: "+a);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case REQ_CODE_SPEECH_INPUT: {
				if (resultCode == RESULT_OK && null != data) {
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					voiceInput.setText(result.get(0));
					objectName=result.get(0);
					sttPresenter.getUserInput(objectName);
			}
				break;
			}
		}
	}

	@Override
	public void showMessage(String s,String x,String y) {

		Intent intent = new Intent(this, TtsActivity.class);
		intent.putExtra("message", s);
		intent.putExtra("xCoordinate",x);
		intent.putExtra("yCoordinate",y);
		startActivity(intent);

		//Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
	}

}