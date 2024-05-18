package com.coldlake.app.payment.controller.domain.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResultCodeEnum {
    RPC_0(0, "操作成功", "success"),

    // File upload
    UPLOAD_TOKEN_ERROR(144141107, "Get token internal error", "Get token internal error"),
    MEDIA_UPLOAD_REQUEST_ERROR(1441415005, "Failed to upload file request", "Failed to upload file request"),
    MEDIA_UPLOAD_FILE_NOT_EXISTS(1441415013, "Error uploading file parameters", "Error uploading file parameters"),
    MEDIA_URL_UPLOAD_SIZE_ERROR(1441415010, "File size exceeded", "File size exceeded"),
    MEDIA_UPLOAD_WIDTH_HEIGHT_EXCEED(1441415014, "Image width and height exceeded the limit", "Image width and height exceeded the limit"),

    MEDIA_UPLOAD_IMAGE_TYPE_ERROR(1441415008, "File type error", "File type error"),
    MEDIA_URL_UPLOAD_PARAM_EMPTY(1441415009, "The file URL cannot be empty", "The file URL cannot be empty"),
    MEDIA_URL_UPLOAD_MEDIATYPE_ERROR(1441415011, "File type error", "File type error"),
    MEDIA_URL_UPLOAD_STATUS_ERROR(1441415012, "Remote file response failed", "Remote file response failed"),
    MEDIA_UPLOAD_TEMP_FILE_FAIL(1441415007, "Failed to upload file request", "Failed to upload file request"),
    MEDIA_UPLOAD_REQUEST_FAIL(1441415006, "Failed to upload file request", "Failed to upload file request"),


    // payment

    SKU_NOT_FOUND(1441412004, "Sku not found", "Sku not found");

    public int code;

    /**
     *
     */
    public String message;

    /**
     * 英文信息
     */
    public String enMessage;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultCodeEnum(int code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }
}
