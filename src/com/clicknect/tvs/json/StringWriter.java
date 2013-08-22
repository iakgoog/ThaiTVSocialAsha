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
package com.clicknect.tvs.json;

import java.io.IOException;
import java.io.Writer;

/**
 * A simple StringBuffer-based implementation of StringWriter
 */
public class StringWriter extends Writer {

	final private StringBuffer buf;

	public StringWriter() {
		super();
		buf = new StringBuffer();
	}

	public StringWriter(int initialSize) {
		super();
		buf = new StringBuffer(initialSize);
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		buf.append(cbuf, off, len);
	}

	public void write(String str) throws IOException {
		buf.append(str);
	}

	public void write(String str, int off, int len) throws IOException {
		buf.append(str.substring(off, len));
	}

	public void flush() throws IOException {
	}

	public void close() throws IOException {
	}
}