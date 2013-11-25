package com.customevent.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.customevent.example.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;

public class CustomEvent extends Activity implements AdListener {

	private static final String LOGTAG = "AdMob_LOG";

	private AdView adView;
	private InterstitialAd interstitialAd;
	private LinearLayout layout;
	private String mediationId;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// android:id="@+id/layout" 속성이
		// 지정된 것으로 가정하여 Linearlayout 찾기
		layout = (LinearLayout) findViewById(R.id.layout);

	}
	// Banner 광고 호출 버튼이 main.xml에 구현되었고,
	// android:onClick="requestBanner" 속성이 지정된 것으로 가정하여
	// 버튼을 누른 경우 호출되는 메소드 구현
	public void requestBanner(View v) {

		// 테스트용 mediation ID : Android Adam > AdMob - d4e3509c80a44ab5
		// 테스트용 mediation ID : Android Cauly > AdMob - 898a1fe85bf1407e

		switch (v.getId()) {

		case R.id.AdamBanner:
			mediationId = "d4e3509c80a44ab5";
			break;

		case R.id.CaulyBanner:
			mediationId = "898a1fe85bf1407e";
			break;
		}

		// 이전 banner 광고 view 삭제
		if (adView != null)
			layout.removeView(adView);

		// 320x50 배너 사이즈 view 생성, mediation ID 할당
		adView = new AdView(this, AdSize.BANNER, mediationId);

		// 광고 view에 이벤트 추적 리스너 전달
		adView.setAdListener(this);

		// 찾은 RelativeLayout에 광고 view를 추가
		layout.addView(adView);

		// 기본 요청을 생성하며 배너 광고 불러오기
		adView.loadAd(new AdRequest());
	}

	// Interstitial 광고 호출 버튼이 main.xml에 구현되었고,
	// android:onClick="requestInterstitial" 속성이
	// 지정된 것으로 가정하여
	// 버튼을 누른 경우 호출되는 메소드 구현
	public void requestInterstitial(View v) {

		// 이전 banner 광고 view 삭제
		if (adView != null)
			layout.removeView(adView);

		// 테스트용 mediation ID : Android Adam > AdMob - 96cbe73e42dc4920
		// 테스트용 mediation ID : Android Cauly > AdMob - fa79ee4298aa49e8

		switch (v.getId()) {

		case R.id.AdamInterstitial:
			mediationId = "96cbe73e42dc4920";

			break;
		case R.id.CaulyInterstitial:
			mediationId = "fa79ee4298aa49e8";

			break;
		}
		// 전면 광고 생성
		interstitialAd = new InterstitialAd(this, mediationId);

		// 광고 view에 이벤트 추적 리스너 전달
		interstitialAd.setAdListener(this);

		// 기본 요청을 생성하며 전면 광고 불러오기
		interstitialAd.loadAd(new AdRequest());
	}

	// 광고 로딩이 완료된 경우 호출
	@Override
	public void onReceiveAd(Ad ad) {

		// 전면 광고 게재
		if (interstitialAd != null)
			interstitialAd.show();

		Log.i(LOGTAG, "Did Receive Ad");
	}

	// 광고 로딩이 실패한 경우 호출
	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode errorCode) {

		Log.i(LOGTAG, "failed to receive ad (" + errorCode + ")");

	}

	// onPresentScreen과 함께 표시된 전체 화면 Activity가 종료되고 컨트롤이 앱으로 반환될 때 호출
	@Override
	public void onDismissScreen(Ad ad) {
		Log.i(LOGTAG, "onDismissScreen");
	}

	// 사용자가 광고를 터치하여 새 앱이 실행될 때 호출
	@Override
	public void onLeaveApplication(Ad ad) {
		Log.i(LOGTAG, "onLeaveApplication");

	}

	// 사용자가 광고를 터치하여 앱에 Activity가 생성되고 전체 화면 광고 UI가 표시될 경우 호출
	@Override
	public void onPresentScreen(Ad ad) {
		Log.i(LOGTAG, "onPresentScreen");
	}
	// 광고가 refresh될 경우 호출
	@Override
	public void onDestroy() {
		// 이전 Custom Event 삭제
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

}