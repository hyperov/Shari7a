package com.nabil.ahmed.shari7a.ui.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.AdView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

/**
 * A Jetpack Compose wrapper for the AdMob Next-Gen Banner Ad.
 */
@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = "ca-app-pub-3940256099942544/6300978111" // Test ad unit ID
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                val adRequest = BannerAdRequest.Builder(adUnitId, AdSize.BANNER).build()
                loadAd(adRequest, object : AdLoadCallback<BannerAd> {
                    override fun onAdLoaded(ad: BannerAd) {
                        // Next-Gen SDK requires registering the ad to the view with an Activity context
                        context.findActivity()?.let { activity ->
                            registerBannerAd(ad, activity)
                        }
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        // Handle error (e.g., hide the view or log)
                    }
                })
            }
        },
        update = { _ ->
            // Update logic if ad unit ID or size changes dynamically
        }
    )
}

/**
 * Helper to find the Activity from a Context, which may be wrapped.
 */
private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
