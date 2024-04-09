package top.zoowayss.payment.domain.paypal.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.payment.domain.paypal.ApplicationContext;
import top.zoowayss.payment.domain.paypal.ShippingAmount;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {

    private String id;

    private String status;

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("application_context")
    private ApplicationContext applicationContext;

    private List<Link> links;

    @JsonProperty("shipping_amount")
    private ShippingAmount shippingAmount;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("start_time")
    private String startTime;
}
