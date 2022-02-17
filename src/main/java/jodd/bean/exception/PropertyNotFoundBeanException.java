package jodd.bean.exception;

import jodd.bean.BeanException;
import jodd.bean.BeanProperty;

public class PropertyNotFoundBeanException extends BeanException {
	public PropertyNotFoundBeanException(String message, BeanProperty bp) {
		super(message + " Property: " + bp.toString());
	}
}
