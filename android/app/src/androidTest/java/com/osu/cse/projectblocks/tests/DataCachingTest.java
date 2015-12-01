package com.osu.cse.projectblocks.tests;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.osu.cse.projectblocks.data.DataApi;
import com.osu.cse.projectblocks.data.OrchestrateDataParser;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Cafeteria;
import com.osu.cse.projectblocks.models.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by niesmo on 11/30/2015.
 */
public class DataCachingTest extends InstrumentationTestCase {
    private DataApi db;
    private Context context;
    private final OrchestrateDataParser<Cafeteria> cafeteriaParser = new OrchestrateDataParser();
    private final OrchestrateDataParser<Food> foodParser = new OrchestrateDataParser();
    private String mockCafeteriaKey;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = DataApi.getInstance();
        context = getInstrumentation().getContext();
        mockCafeteriaKey = "0d1ef1c890208e2a"; // terra byte cafe id
    }

    public void testCafeteriaFoodsGetCached(){
        db.getFoodsInCafeteria(context, mockCafeteriaKey, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Food> foods = foodParser.parseArray(response, Food.class);

                    assertNotNull(foods);
                    assertTrue(foods.size() != 0);

                    Repository.cacheFoods(foods);

                    // getting the foods again
                    List<Food> cachedFoods = Repository.getAllFoods();

                    assertNotNull(cachedFoods);
                    assertTrue(cachedFoods.size() != 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertTrue(false);
            }
        });
    }

    public void testCafeteriasGetCached(){
        db.getCafeterias(context, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Cafeteria> cafeterias = cafeteriaParser.parseArray(response, Cafeteria.class);

                    // assert that the some cafeterias were returned
                    assertNotNull(cafeterias);

                    // assert that there are some cafeterias in the list
                    assertTrue(cafeterias.size() != 0);

                    // cache the cafeterias
                    Repository.cacheCafeterias(cafeterias);

                    // get the cached cafeterias
                    List<Cafeteria> cachedCafeterias = Repository.getAllCafeterias();

                    assertNotNull(cachedCafeterias);
                    assertTrue(cachedCafeterias.size() != 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assertTrue(false);
            }
        });
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
