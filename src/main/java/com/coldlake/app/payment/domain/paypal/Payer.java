package com.coldlake.app.payment.domain.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payer {
    @JsonProperty("name")
    private Name name;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("payer_id")
    private String payerId;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("status")
    private String status;

    // 构造函数、getter 和 setter 方法省略

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        @JsonProperty("given_name")
        private String givenName;

        @JsonProperty("surname")
        private String surname;

        // 构造函数、getter 和 setter 方法省略
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        @JsonProperty("country_code")
        private String countryCode;

        // 构造函数、getter 和 setter 方法省略
    }
}
