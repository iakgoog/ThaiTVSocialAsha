/**
 * Copyright (c) E.Y. Baskoro, Research In Motion Limited.
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without 
 * restriction, including without limitation the rights to use, 
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR 
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This License shall be included in all copies or substantial 
 * portions of the Software.
 * 
 * The name(s) of the above copyright holders shall not be used 
 * in advertising or otherwise to promote the sale, use or other 
 * dealings in this Software without prior written authorization.
 * 
 */
package com.clicknect.facebook.dao;

import java.util.Date;

import com.clicknect.facebook.Facebook;
import com.clicknect.facebook.FacebookException;
import com.clicknect.facebook.inf.Application;
import com.clicknect.facebook.inf.Checkin;
import com.clicknect.facebook.inf.Location;
import com.clicknect.facebook.inf.Profile;
import com.clicknect.tvs.util.DateUtils;
import com.clicknect.tvs.json.JSONArray;
import com.clicknect.tvs.json.JSONObject;

public class FacebookCheckin extends FacebookObject implements Checkin {

	public FacebookCheckin(Facebook pfb, JSONObject pJsonObject) throws FacebookException {
		super(pfb, pJsonObject);
	}

	public Profile getFrom() {
		Profile result = null;
		try {
			JSONObject jo = jsonObject.optJSONObject("from");
			if ((jo != null) && (jo.length() > 0)) {
				result = fb.getProfile(jo);
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Profile[] getTags() {
		Profile[] result = null;
		try {
			JSONObject jo = jsonObject.optJSONObject("tags");
			if ((jo != null) && (jo.length() > 0)) {
				JSONArray ja = jo.optJSONArray("data");
				if ((ja != null) && (ja.length() > 0)) {
					result = new Profile[ja.length()];
					for (int i = 0; i < ja.length(); i++) {
						result[i] = fb.getProfile(ja.optJSONObject(i));
					}
				}
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Profile getPlace() {
		Profile result = null;
		try {
			JSONObject jo = jsonObject.optJSONObject("place");
			if ((jo != null) && (jo.length() > 0)) {
				result = fb.getProfile(jo);
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Location getLocation() {
		Location result = null;
		try {
			JSONObject jo = jsonObject.optJSONObject("place");
			if ((jo != null) && (jo.length() > 0)) {
				result = fb.getLocation(jo.optJSONObject("location"));
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getMessage() {
		return jsonObject.optString("message");
	}

	public Application getApplication() {
		// TODO
		return null;
	}

	public Date getCreatedTime() {
		return DateUtils.parse(getCreatedTimeAsString());
	}

	public String getCreatedTimeAsString() {
		return jsonObject.optString("created_time");
	}

}
