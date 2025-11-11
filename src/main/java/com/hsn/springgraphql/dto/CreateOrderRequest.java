package com.hsn.springgraphql.dto;
import com.hsn.springgraphql.enums.PaymentMethod;

import java.util.List;

public record CreateOrderRequest(List<CreateNewOrderItemRequest> items , PaymentMethod paymentMethod){
}
