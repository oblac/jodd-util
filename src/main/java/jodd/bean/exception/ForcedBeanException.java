package jodd.bean.exception;

import jodd.bean.BeanException;
import jodd.bean.BeanProperty;

public class ForcedBeanException extends BeanException {
	public ForcedBeanException(String message, BeanProperty bp, Throwable cause) {
		super(message + " Property: " + bp.toString(), cause);
	}
}
