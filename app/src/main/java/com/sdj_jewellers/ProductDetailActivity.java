package com.sdj_jewellers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sdj_jewellers.model.CategoryInfo;
import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements ResponseListener{

    ImageView productImage;
    TextView productName,productExcerpt;
    CategoryInfo model;
    String cartId;
    ImageView toolbarBasket;

    Handler h;
    Preference preference;
    Typeface montserrat_light,montserrat_regular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        preference=new Preference(this);
        h=new Handler();
        montserrat_light=Utils.getCustomFont(this, FontType.MONESTER_RAT_LIGHT);
        montserrat_regular=Utils.getCustomFont(this, FontType.MONESTER_RAT_REGULAR);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Detail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImage= (ImageView) findViewById(R.id.product_image);
        productName= (TextView) findViewById(R.id.product_name_text);
        productExcerpt= (TextView) findViewById(R.id.product_excerpt_text);
        toolbarBasket= (ImageView) findViewById(R.id.toolbar_basket);
        productExcerpt.setTypeface(montserrat_light);
        productName.setTypeface(montserrat_regular);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            model= (CategoryInfo) bundle.get("model");

            Glide.with(this)
                    .load(model.getImagePath())
                    .into(productImage);

            productName.setText(model.getPostName());
            productExcerpt.setText(model.getPostExcerpt());
        }

        toolbarBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetailActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clickAddToCart(View view){
        checkCartProduct();
    }
    public void productAddToCart(String quantity,int addOrUpdate){

        if(addOrUpdate==0){
            addProductsToCart(model,quantity);
        }else if(addOrUpdate==1){
            updateCartProduct(model,quantity);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;

        }

        return true;
    }


    /*CHECK CART PRODUCTS*/
    public void checkCartProduct(){

        if (Utils.ChechInternetAvalebleOrNot(ProductDetailActivity.this)) {

            Utils.showLoader(ProductDetailActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "check_cartProducts",
                            getCheckCartProductData(preference.getUSER_ID(),model.getCatID()),
                            ProductDetailActivity.this,
                            ResponseListener.REQUEST_CHECK_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(ProductDetailActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getCheckCartProductData(String userId, int postId) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("postId", postId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    /*UPDATE PRODUCTS QUANTITY*/
    public void updateCartProduct(CategoryInfo model,String quantity){
        if (Utils.ChechInternetAvalebleOrNot(ProductDetailActivity.this)) {

            Utils.showLoader(ProductDetailActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "update_cartProducts",
                            getUpdateCartProductData(preference.getUSER_ID(),model.getCatID(),quantity),
                            ProductDetailActivity.this,
                            ResponseListener.REQUEST_UPDATE_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(ProductDetailActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getUpdateCartProductData(String userId,int cartId,String qty) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("cartId", cartId);
            json.put("qty", qty);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    /*ADD PRODUCTS TO CART*/
    public void addProductsToCart(CategoryInfo model,String quantity){
        if (Utils.ChechInternetAvalebleOrNot(ProductDetailActivity.this)) {

            Utils.showLoader(ProductDetailActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "save_addToCart",
                            getaddProductsToCartData(model,quantity),
                            ProductDetailActivity.this,
                            ResponseListener.REQUEST_ADD_PRODUCT_TO_CART);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(ProductDetailActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getaddProductsToCartData(CategoryInfo model,String qty) {
        JSONObject json = new JSONObject();
        try {

            json.put("postId", model.getCatID());
            json.put("postName", model.getPostName());
            json.put("postExcerpt", model.getPostExcerpt());
            json.put("userId", preference.getUSER_ID());
            json.put("quantity", qty);
            json.put("postimagePath", model.getImagePath());
            json.put("categoryID", model.getTermTaxonomyID());
            json.put("term_taxonomy_Id", model.getTermTaxonomyID());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    @Override
    public void onResponse(final Response response, final int rid) {


        h.post(new Runnable() {

            @Override
            public void run() {
                Utils.dismissLoader();
                if (rid == ResponseListener.REQUEST_CHECK_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(ProductDetailActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                jsonObject = jsonObject1.getJSONObject("record");
                                if(jsonObject!=null) {
                                    cartId = jsonObject.getString("cart_id");
                                    Utils.showQuantityPrompt(ProductDetailActivity.this, model.getPostTitle(), -1, "Please Enter quantity to order", 1, jsonObject.getString("cart_quantity"),"ProductDetail");
                                }
                            }
                            else if(status.equalsIgnoreCase("false") && jsonObject1.getString("msg").equalsIgnoreCase("No Record found")){
                                Utils.showQuantityPrompt(ProductDetailActivity.this,model.getPostTitle(),-1,"Please Enter quantity to order",0,"","ProductDetail");
                            }else{
                                Toast.makeText(ProductDetailActivity.this, jsonObject1.getString("msg"), Toast.LENGTH_LONG).show();
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_ADD_PRODUCT_TO_CART) {

                    if (response.isError()) {
                        Toast.makeText(ProductDetailActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                Utils.showCommonInfoPrompt(ProductDetailActivity.this,"Success",jsonObject1.getString("msg"));
                            } else{
                                Utils.showCommonInfoPrompt(ProductDetailActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_UPDATE_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(ProductDetailActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                Utils.showCommonInfoPrompt(ProductDetailActivity.this,"Success",jsonObject1.getString("msg"));
                            } else{
                                Utils.showCommonInfoPrompt(ProductDetailActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }


}
