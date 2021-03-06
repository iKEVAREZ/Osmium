package com.kmecpp.osmium.api.command;

import com.kmecpp.osmium.api.Abstraction;

public interface CommandSender extends Abstraction, Messageable {

	/**
	 * Checks if this object is an operator
	 *
	 * @return true if this is an operator, otherwise false
	 */
	public boolean isOp();

	/**
	 * Sets whether or not this object is an operator
	 *
	 * @param value
	 *            true to make operator op false to remove operator status
	 */
	public void setOp(boolean value);

	/**
	 * Gets the value of the specified permission, if set.
	 *
	 * @param permission
	 *            the permission to check
	 * @return whether or not the object has that permission
	 */
	public boolean hasPermission(String permission);

	String getName();

	//	default void sendMessage(String style, String message) {
	//		sendMessage(ChatUtil.style(style, message));
	//	}

}
