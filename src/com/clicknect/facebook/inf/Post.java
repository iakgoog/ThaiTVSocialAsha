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
package com.clicknect.facebook.inf;

import java.util.Date;

import com.clicknect.facebook.AsyncCallback;
import javax.microedition.lcdui.Image;

public interface Post extends com.clicknect.facebook.inf.Object {

	// Properties

	public int getLikes();

	public Profile getFrom();

	public Profile[] getTo();

	public String getMessage();

	public Image getPicture();

	public Image getPicture(final AsyncCallback listener, final java.lang.Object state);

	public String getPictureUrl();

	public String getLink();

	public String getName();

	public String getCaption();

	public String getDescription();

	public String getSource();

	public Image getIcon();

	public Image getIcon(final AsyncCallback listener, final java.lang.Object state);

	public String getAttribution();

	// public ??? getActions();

	// public ??? getPrivacy();

	public Date getCreatedTime();

	public String getCreatedTimeAsString();

	public Date getUpdatedTime();

	public String getUpdatedTimeAsString();

	public String getType();

	// public ??? getTargetting();

	// Connections

	public Comment[] getComments();

	public Comment[] getComments(final AsyncCallback listener, final java.lang.Object state);

	public Profile[] getLikedUsers();

	public Profile[] getLikedUsers(final AsyncCallback listener, final java.lang.Object state);

}