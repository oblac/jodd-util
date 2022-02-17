package jodd.bean.exception;

import jodd.bean.BeanException;
import jodd.bean.BeanProperty;

public class InvokePropertyBeanException extends BeanException {
	public InvokePropertyBeanException(String message, BeanProperty bp, Throwable cause) {
		super(message + " Property: " + bp.toString(), cause);
	}
}
