/*****************************************************************************
 * Copyright [2013] [Jules White]                                            *
 *                                                                           *
 *  Licensed under the Apache License, Version 2.0 (the "License");          *
 *  you may not use this file except in compliance with the License.         *
 *  You may obtain a copy of the License at                                  *
 *                                                                           *
 *      http://www.apache.org/licenses/LICENSE-2.0                           *
 *                                                                           *
 *  Unless required by applicable law or agreed to in writing, software      *
 *  distributed under the License is distributed on an "AS IS" BASIS,        *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. *
 *  See the License for the specific language governing permissions and      *
 *  limitations under the License.                                           *
 ****************************************************************************/
package org.magnum.soda.android;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.magnum.soda.Soda;
import org.magnum.soda.msg.Protocol;
import org.magnum.soda.protocol.java.NativeJavaProtocol;
import org.magnum.soda.proxy.ProxyCreator;
import org.magnum.soda.transport.UriAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.magnum.soda.svc.NamingService;

import android.content.Context;
import android.os.Handler;

public class AndroidSoda extends Soda {
	private static final Logger Log = LoggerFactory
			.getLogger(AndroidSoda.class);
	
	private static final ExecutorService executor_ = Executors
			.newFixedThreadPool(5);

	public static void init(final Context ctx, final String host,
			final int port, final AndroidSodaListener l) {
		init(ctx,new NativeJavaProtocol(),host,port,l);
	}
	
	public static void init(final Context ctx, Protocol protocol, final String host,
			final int port, final AndroidSodaListener l) {
		context_ = ctx;
		final AndroidSoda soda = new AndroidSoda(protocol);
		soda.connect(new UriAddress("ws://" + host + ":" + port));
		Runnable r = new Runnable() {

			@Override
			public void run() {
				soda.setContext(ctx);
				soda.awaitConnect();
				l.connected(soda);
			}
		};

		executor_.submit(r);
	}

	public static Future<?> async(Runnable r) {
		return executor_.submit(r);
	}

	public static <T> Future<T> async(Callable<T> r) {
		return executor_.submit(r);
	}

	private CountDownLatch connectGate_ = new CountDownLatch(1);

	private static Context context_;

	private Handler handler_;

	private AndroidInvocationDispatcher dispatcher_;
	private AndroidInvocationDispatcher inUiDispatcher_;

	private AndroidSoda() {
		super();
		setTransport(new SodaAndroidTransport(getMsgBus(), getLocalAddress()));
	}
	
	private AndroidSoda(Protocol proto) {
		super();
		setTransport(new SodaAndroidTransport(proto, getMsgBus(), getLocalAddress()));
	}


	@Override
	public void connected() {
		super.connected();
		connectGate_.countDown();
	}

	public void awaitConnect() {
		try {
			connectGate_.await();
		} catch (Exception e) {
		}
	}

	public Context getContext() {
		return context_;
	}

//	@Override
//	protected synchronized ProxyCreator getProxyCreator() {	
//		return new DexProxyCreator(context_.getDir("dx", Context.MODE_PRIVATE));
//	}

	public void setContext(Context context) {
		context_ = context;
		handler_ = new Handler(context_.getMainLooper());
		dispatcher_ = new AndroidInvocationDispatcher(handler_);
		inUiDispatcher_ = new AndroidInvocationDispatcher(handler_, true);
		getInvoker().setDispatcher(dispatcher_);	
	}

	public <T> T invokeInUi(T obj) {
		return invoke(obj, inUiDispatcher_);
	}

	public void inUi(Runnable r) {
		handler_.post(r);
	}
}
