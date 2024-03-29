// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.bean;

import jodd.introspector.ClassDescriptor;
import jodd.introspector.ClassIntrospector;
import jodd.introspector.Getter;
import jodd.introspector.PropertyDescriptor;
import jodd.introspector.Setter;

import java.util.function.Supplier;

/**
 * Represents a bean named property. Contains two information:
 * <ol>
 * <li>Bean instance (and cached class descriptor)</li>
 * <li>Property name</li>
 * </ol>
 * Used only internally by {@link BeanUtil} and similar utils.
 */
public class BeanProperty {

	BeanProperty(final BeanUtilBean beanUtilBean, final Object bean, final String propertyName, final boolean isSet) {
		this.introspector = beanUtilBean.introspector;
		setName(propertyName);
		updateBean(bean);
		this.last = true;
		this.first = true;
		this.fullName = bean.getClass().getSimpleName() + '#' + propertyName;
//		this.isSet = isSet;

		// when setting, allowing creating simple properties in forced mode
		// when reading, forced mode should not create the last property and should be ignored
		this.isForced = isSet && beanUtilBean.isForced;
	}

	// ---------------------------------------------------------------- bean and descriptor

	final String fullName;  // initial name
	final ClassIntrospector introspector;
	Object bean;
	private Object lastBean;
	private ClassDescriptor cd;
	String name;        // property name
	boolean last;       // is it a last property (when nested)
	boolean first;		// is it first property (when nested)
	String indexString;	// indexString for index property
//	final boolean isSet;// indicate read or write (isSet)
	final boolean isForced;

	String lastName;    // tracking last name for debugging and information purposes

	/**
	 * Sets current property name.
	 */
	public void setName(final String name) {
		this.lastName = this.name;
		this.name = name;
		this.updateProperty = true;
	}

	/**
	 * Sets new bean instance.
	 */
	private void setBean(final Object bean) {
		this.lastBean = this.bean;
		this.bean = bean;
		this.cd = (bean == null ? null : introspector.lookup(bean.getClass()));
		this.first = false;
		this.updateProperty = true;
	}

	/**
	 * Updates the bean. Detects special case of suppliers.
	 */
	public void updateBean(final Object bean) {
		this.setBean(bean);

		if (this.cd != null && this.cd.isSupplier()) {
			final Object newBean = ((Supplier<?>)this.bean).get();
			setBean(newBean);
		}
		else {
			this.lastBean = null;
		}
	}

	public void updateBeanClassFromProperty() {
		final Class<?> c = propertyDescriptor.getType();
		this.setBean(null);
		this.cd = introspector.lookup(c);
	}

	// ---------------------------------------------------------------- simple properties

	// indicates that property descriptor has to be updated
	// since there was some property-related change of BeanProperty state
	private boolean updateProperty = true;

	// most recent property descriptor
	private PropertyDescriptor propertyDescriptor;
	private PropertyDescriptor lastPropertyDescriptor;

	public boolean isExistingParentNull() {
		return lastPropertyDescriptor != null && this.lastBean == null;
	}

	public boolean currentPropertyExistOnParent(boolean declared) {
		PropertyDescriptor pd = this.propertyDescriptor;
		if (pd == null) {
			ClassDescriptor lastClassDescriptor = lastPropertyDescriptor.getClassDescriptor();
			pd = lastClassDescriptor.getPropertyDescriptor(this.lastName, declared);
			ClassDescriptor cs = introspector.lookup(pd.getType());
			pd = cs.getPropertyDescriptor(this.name, declared);
		}
		return pd != null;
	}

	/**
	 * Loads property descriptor, if property was updated.
	 */
	private void loadPropertyDescriptor() {
		if (updateProperty) {
			this.lastPropertyDescriptor = this.propertyDescriptor;
			if (cd == null) {
				propertyDescriptor = null;
			} else {
				propertyDescriptor = cd.getPropertyDescriptor(name, true);
			}
			updateProperty = false;
		}
	}

	/**
	 * Returns getter.
	 */
	public Getter getGetter(final boolean declared) {
		loadPropertyDescriptor();
		return propertyDescriptor != null ? propertyDescriptor.getGetter(declared) : null;
	}

	/**
	 * Returns setter.
	 */
	public Setter getSetter(final boolean declared) {
		loadPropertyDescriptor();
		return propertyDescriptor != null ? propertyDescriptor.getSetter(declared) : null;
	}

	/**
	 * Returns <code>true</code> if class is a map.
	 */
	public boolean isMap() {
		return cd != null && cd.isMap();
	}

	String index;

	// ---------------------------------------------------------------- toString

	@Override
	public String toString() {
		return fullName + " (" + (bean != null ? bean.getClass().getSimpleName() : "?") + '#' + name + ')';
	}
}
