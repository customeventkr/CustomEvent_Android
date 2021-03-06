package com.customevent.example;

// Adam 전면광고 라이브러리  
// Adam 웹사이트에서 전면광고 구현 가이드를 참고하여 파일 등을 프로젝트에 추가하여야 함
import net.daum.adam.publisher.AdInterstitial;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.impl.AdError;

import android.app.Activity;
import android.util.Log;
import com.google.ads.mediation.MediationAdRequest;

//AdMob 전면광고 Custom Event 구현 라이브러리  
import com.google.ads.mediation.customevent.CustomEventInterstitial;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;


public class CustomEventAdamInterstitial implements CustomEventInterstitial,
		OnAdLoadedListener, OnAdClosedListener, OnAdFailedListener {

	private static final String LOGTAG = "Ad@mInterstitial_LOG";

	private AdInterstitial mAdInterstitial;
	private CustomEventInterstitialListener interstitialListener;
	private Activity InterstitialActivity;

	// AdMob custom event callback. Adam 전면광고를 요청하기 위해 AdMob이 호출해줌.
	@Override
	public void requestInterstitialAd(
			final CustomEventInterstitialListener listener,
			final Activity activity, String label, String serverParameter,
			MediationAdRequest mediationAdRequest, Object extra) {

		Log.i(LOGTAG, "Ad@m Interstitial");

		this.interstitialListener = listener;
		this.InterstitialActivity = activity;

		// Ad@m 전면형 광고 객체 생성
		mAdInterstitial = new AdInterstitial(InterstitialActivity);

		// AdMob mediation UI상에 입력한 값이 serverParameter 인자로 전달됨.
		// 전면형 광고 클라이언트 ID를 설정
		// Adam의 경우, Adam 승인 이전에는 "InterstitialTestClientId"를 지정해야 테스트가 가능.
		mAdInterstitial.setClientId("InterstitialTestClientId");

		// 전면형 광고를 정상적으로 내려받았을 경우에 실행할 리스너
		mAdInterstitial.setOnAdLoadedListener(this);

		// 전면형 광고 다운로드 실패시에 실행할 리스너
		mAdInterstitial.setOnAdFailedListener(this);

		// 전면형 광고를 닫을 시에 실행할 리스너
		mAdInterstitial.setOnAdClosedListener(this);

		// 전면광고를 호출하고 게재함.
		// AdMob custom event 구현상 보여주는 것은 전면광고 요청이 성공했을 때 진행하도록 되어 있으나 Adam SDK가
		// 호출과 노출을 하나로 처리하고 있어서 예외적으로 구현함.
		// failure 테스트
		
			// 광고 요청과 동시에 add된 광고 view를 제거함
			mAdInterstitial.loadAd();
		

	}

	@Override
	public void destroy() {

	}

	@Override
	public void showInterstitial() {

		// Ad@m은 광고 호출과 게재가 loadAd()메소드로 함께 진행되므로 로그만 출력.
		Log.i(LOGTAG, " showInterstitial() ");

	}

	@Override
	public void OnAdLoaded() {
		Log.i(LOGTAG, "adam Interstitial - OnAdLoaded");
		// AdMob custom event에 전면광고가 성공했음을 알림
		interstitialListener.onReceivedAd();

	}

	@Override
	public void OnAdFailed(AdError error, String errorMessage) {

		// AdMob custom event에 전면광고가 실패했음을 알려 다음 mediation network를 호출하도록 함.
		// Adam의 경우 3분 이내 전면광고 재호출(requestAndPresent)이 발생한 경우, 실패로 간주하고
		// 광고를 노출하지 않으므로 fail error가 발생함.
		interstitialListener.onFailedToReceiveAd();
		Log.i(LOGTAG, "adam Interstitial - OnFailed");

	}

	@Override
	public void OnAdClosed() {
		Log.i(LOGTAG, "광고를 닫았습니다. ");
	}
}
