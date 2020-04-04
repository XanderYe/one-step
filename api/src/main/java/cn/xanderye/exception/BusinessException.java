package cn.xanderye.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String snapshot;

    public BusinessException(int code, String message, String snapshotFormat, Object... argArray) {
        super(message);
        this.code = code;
        this.snapshot = MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 1;
    }

    public int getCode() {
        return code;
    }

    public String getSnapshot() {
        return snapshot;
    }
}