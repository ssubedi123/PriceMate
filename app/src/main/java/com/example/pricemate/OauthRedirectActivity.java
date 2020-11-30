package com.example.pricemate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ebay.api.client.auth.oauth2.OAuthService;
import com.ebay.api.client.auth.oauth2.model.ApiConfiguration;
import com.ebay.api.client.auth.oauth2.model.ApiEnvironment;
import com.ebay.api.client.auth.oauth2.model.ApiSessionConfiguration;

public class OauthRedirectActivity extends AppCompatActivity {

    private static final int RC_EBAY_AUTH = 1;
    ApiEnvironment apiEnvironment;
    ApiConfiguration apiConfiguration;
    OAuthService oAuthService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oAuthService = new OAuthService(this, RC_EBAY_AUTH);
        //there can be a go to ebay button

        ApiSessionConfiguration.Companion.initialize(
                apiEnvironment = ApiEnvironment.PRODUCTION,
                apiConfiguration = new ApiConfiguration(
                        "Benjamin-PriceMat-SBX-47cf12021-a56ab0f6",
                        "https://auth.sandbox.ebay.com/oauth2/authorize?client_id=Benjamin-PriceMat-SBX-47cf12021-a56ab0f6&response_type=code&redirect_uri=Benjamin_Kim-Benjamin-PriceM-nmmgne&scope=https://api.ebay.com/oauth/api_scope https://api.ebay.com/oauth/api_scope/buy.order.readonly https://api.ebay.com/oauth/api_scope/buy.guest.order https://api.ebay.com/oauth/api_scope/sell.marketing.readonly https://api.ebay.com/oauth/api_scope/sell.marketing https://api.ebay.com/oauth/api_scope/sell.inventory.readonly https://api.ebay.com/oauth/api_scope/sell.inventory https://api.ebay.com/oauth/api_scope/sell.account.readonly https://api.ebay.com/oauth/api_scope/sell.account https://api.ebay.com/oauth/api_scope/sell.fulfillment.readonly https://api.ebay.com/oauth/api_scope/sell.fulfillment https://api.ebay.com/oauth/api_scope/sell.analytics.readonly https://api.ebay.com/oauth/api_scope/sell.marketplace.insights.readonly https://api.ebay.com/oauth/api_scope/commerce.catalog.readonly https://api.ebay.com/oauth/api_scope/buy.shopping.cart https://api.ebay.com/oauth/api_scope/buy.offer.auction https://api.ebay.com/oauth/api_scope/commerce.identity.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.email.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.phone.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.address.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.name.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.status.readonly https://api.ebay.com/oauth/api_scope/sell.finances https://api.ebay.com/oauth/api_scope/sell.item.draft https://api.ebay.com/oauth/api_scope/sell.payment.dispute https://api.ebay.com/oauth/api_scope/sell.item",
                        "https://api.ebay.com/oauth/api_scope"
                )
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_redirect);
    }

    public void ebayConsent(View view){

        oAuthService.performUserAuthorization();
    }

}