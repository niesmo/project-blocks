package com.osu.cse.projectblocks.data;

import com.google.gson.Gson;
import com.osu.cse.projectblocks.models.OrchestrateObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niesmo on 11/8/2015.
 */
public class OrchestrateDataParser<T> {
    private final Gson gson = new Gson();

    public OrchestrateDataParser(){}

    /**
     * This function will parse the json object and returns an array of object with class @param c.
     * It is assumed that the json object is the response of an orchestrate request (hence the name)
     * @param jsonObject
     * @param c The class of the object that will be returned (This is because Java does not allow T.class)
     * @return an array of objects of class T (which must be extend Orchestrate Object).
     * @throws JSONException
     */
    public List<T> parseArray(JSONObject jsonObject, Class c) throws JSONException {
        List<T> objects = new ArrayList<>();

        // get the count
        int count = jsonObject.getInt("count");

        // for all the elements in the
        for(int i=0;i<count;i++){
            // get the i-th result from what was sent
            JSONObject row = jsonObject.getJSONArray("results").getJSONObject(i);

            // get the orchestrate key
            String orchestrateKey = row.getJSONObject("path").getString("key");

            // get the collection
            String orchestrateCollection  = row.getJSONObject("path").getString("collection");

            // get the ref
            String orchestrateRef = row.getJSONObject("path").getString("key");

            // read one object from the array
            OrchestrateObject obj = (OrchestrateObject)this.parseObject(row.getJSONObject("value"), c);

            // add the orchestrate specific things to the object
            obj.setCollection(orchestrateCollection);
            obj.setRef(orchestrateRef);
            obj.setKey(orchestrateKey);

            // add it to the list
            //noinspection unchecked
            objects.add((T)obj);
        }

        // return the list
        return objects;
    }

    /**
     * This function will parse one object from the json object passed to type T
     * Type T must extend OrchestrateObject as that is assumed
     * @param jsonObject
     * @param c The class of the object that will be returned (This is because Java does not allow T.class)
     * @return The serialized object
     * @throws JSONException
     */
    public T parseObject(JSONObject jsonObject, Class c) throws JSONException {
        // get the object in String
        String objString = jsonObject.toString();

        // converting the json to an object
        OrchestrateObject obj = (OrchestrateObject)gson.fromJson(objString, c);

        //noinspection unchecked
        return (T)obj;
    }
}
