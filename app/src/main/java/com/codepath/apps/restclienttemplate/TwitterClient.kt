package com.codepath.apps.restclienttemplate

import android.content.Context
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.oauth.OAuthBaseClient
import com.github.scribejava.apis.FlickrApi
import com.github.scribejava.apis.TwitterApi
import com.github.scribejava.core.builder.api.BaseApi

// TwitterClient.kt is where we do our api call

class TwitterClient(context: Context) : OAuthBaseClient(
    context, REST_API_INSTANCE, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET,
    null, String.format(
        REST_CALLBACK_URL_TEMPLATE,
        context.getString(R.string.intent_host),
        context.getString(R.string.intent_scheme),
        context.packageName,
        FALLBACK_URL
    )
) {

    companion object {
        val REST_API_INSTANCE = TwitterApi.instance() // Connect to TwitterApi

        const val REST_URL = "https://api.twitter.com/1.1" // base API URL

        const val REST_CONSUMER_KEY =
            BuildConfig.CONSUMER_KEY // Change this inside apikey.properties

        const val REST_CONSUMER_SECRET =
            BuildConfig.CONSUMER_SECRET // Change this inside apikey.properties

        // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
        const val FALLBACK_URL =
            "https://codepath.github.io/android-rest-client-template/success.html"

        // See https://developer.chrome.com/multidevice/android/intents
        const val REST_CALLBACK_URL_TEMPLATE =
            "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end"
    }

    fun getHomeTimeline(handler: JsonHttpResponseHandler) {
        val apiUrl =
            getApiUrl("statuses/home_timeline.json") // get a list of tweet objects

        // Can specify query string params directly or through RequestParams.
        val params = RequestParams()
        params.put("count", "25")
        params.put("since_id", 1)
        client.get(apiUrl, params, handler)
    }

    fun publishTweet(tweetContent: String, handler: JsonHttpResponseHandler) {
        val apiUrl =
            getApiUrl("statuses/update.json") // get a list of tweet objects

        // Can specify query string params directly or through RequestParams.
        val params = RequestParams()
        params.put("status", tweetContent)
        client.post(apiUrl, params, "", handler)
    }
}