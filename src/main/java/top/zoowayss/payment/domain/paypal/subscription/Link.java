package top.zoowayss.payment.domain.paypal.subscription;

import lombok.Data;

@Data
public class Link {
    public static final Link EMPTY = new Link();
    private String href;
    private String rel;
    private String method;
}
