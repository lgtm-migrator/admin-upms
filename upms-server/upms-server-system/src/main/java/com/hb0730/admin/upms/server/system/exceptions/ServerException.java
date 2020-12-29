package com.hb0730.admin.upms.server.system.exceptions;

import com.hb0730.admin.upms.commons.exceptions.UpmsException;

/**
 * @author bing_huang
 */
public class ServerException extends UpmsException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String format, Object... objects) {
        super(String.format(format, objects));
    }
}
