/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package smack.testing;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IPlatformRunnable {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
	 */
	public Object run(Object args) throws Exception {
    try {
      XMPPConnection con = new XMPPConnection("eclipsercp.org");
      con.login("reader", "secret",
          Long.toString(System.currentTimeMillis()));
      Chat chat = con.createChat("eliza@eclipsercp.org");
      chat.sendMessage("Hi There!");
      Message message = chat.nextMessage(5000);
      System.out.println("Returned message: "
          + (message == null ? "<timed out>" : message.getBody()));
    } catch (XMPPException e) {
      e.printStackTrace();
    }
    return IPlatformRunnable.EXIT_OK;

	}
}
