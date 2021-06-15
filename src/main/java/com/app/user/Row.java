package com.app.user;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user_id",
        "email",
        "commission",
        "swaps",
        "profit"
})
@Generated("jsonschema2pojo")

public class Row {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("commission")
    private float commission;
    @JsonProperty("swaps")
    private float swaps;
    @JsonProperty("profit")
    private float profit;

    private float pnL;

}