package jodd.bean.exception;

import jodd.bean.BeanException;
import jodd.bean.BeanProperty;

public class NullPropertyBeanException extends BeanException {
	public NullPropertyBeanException(String message, BeanProperty bp) {
		super(message + " Property: " + bp.toString());
	}
}
