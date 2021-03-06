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
package org.magnum.soda.svc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class InvocationInfo {

	private String method_;

	private Object[] parameters_;

	private Class<?>[] parameterTypes_;

	public String getMethod() {
		return method_;
	}

	public void setMethod(String method) {
		method_ = method;
	}
	
	public void bind(Object target){}

	public Object[] getParameters() {
		return parameters_;
	}

	public void setParameters(Object[] parameters) {
		parameters_ = parameters;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		parameterTypes_ = parameterTypes;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes_;
	}

	public Object invoke(Object target) throws Exception {
		bind(target);
		Method m = resolve(target.getClass());
		if(!m.isAccessible() && Modifier.isPublic(m.getModifiers())){ m.setAccessible(true); }
		Object rslt = m.invoke(target, getParameters());
		return rslt;
	}
	
	public Method resolve(Class<?> c) {
		Class<?>[] types = getParameterTypes();
		Method m = null;
		
		try{
			m = c.getMethod(method_, types);
		}catch(Exception e){}
		
		return m;
	}
	
	public boolean checkForAnnotation(Class<?>[] targets, Class<? extends Annotation> anno){
		boolean hasit = false;
		for(Class<?> c : targets){
			Method m = resolve(c);
		    if( m != null && m.getAnnotation(anno) != null){
		    	hasit = true;
		    	break;
		    }
		}
		return hasit;
	}

	@Override
	public String toString() {
		return "InvocationInfo [method_=" + method_ + ", parameters_="
				+ Arrays.toString(parameters_) + ", parameterTypes_="
				+ Arrays.toString(parameterTypes_) + "]";
	}

}
