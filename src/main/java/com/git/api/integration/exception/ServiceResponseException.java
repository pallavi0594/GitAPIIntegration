/**
 * 
 */
package com.git.api.integration.exception;

import com.git.api.integration.dto.ResponseStatus;

/**
 * @author - pallavi.mhetre
 *    file - ServiceResponseException.java
 *    date - 01-Dec-2019
 */
public class ServiceResponseException extends Exception {

		private static final long serialVersionUID = 1L;

		private ResponseStatus status;

		public ServiceResponseException(String message) {
			super(message);
		}

		public ServiceResponseException(ResponseStatus status) {

			super(status.getStatusMessage());
			this.status = status;
		}

		public ResponseStatus getStatus() {
			return status;
		}

		public void setStatus(ResponseStatus status) {
			this.status = status;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ServiceException [status=");
			builder.append(status);
			builder.append("]");
			return builder.toString();
		}
}
