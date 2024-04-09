package top.zoowayss.payment.domain.paypal.subscription;

import lombok.Data;

@Data
public class Link {
    private String href;
    private String rel;
    private String method;
}
