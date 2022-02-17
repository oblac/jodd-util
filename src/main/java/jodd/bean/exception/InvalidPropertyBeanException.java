package jodd.bean.exception;

import jodd.bean.BeanException;
import jodd.bean.BeanProperty;

public class InvalidPropertyBeanException extends BeanException {
	public InvalidPropertyBeanException(String message, BeanProperty bp) {
		super(message + " Property: " + bp.toString());
	}
}
