package com.bjcabral.historyphoto.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bruno on 18/10/2015.
 */
public class History {
   @SerializedName("historyPhoto")
   public List<HistoryPhoto> historyPhoto;
}
