package com.coldlake.app.payment.domain.paypal.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    public static final Link EMPTY = new Link();
    private String href;
    private String rel;
    private String method;
}
