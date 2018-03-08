package com.jpmorgan.main.java.util;

import java.math.BigDecimal;

import com.jpmorgan.main.java.product.OperationType;
import com.jpmorgan.main.java.product.Product;
import com.jpmorgan.main.java.product.ProductType;

public class MessageParser {
    private Product product;

    public MessageParser(Product product){
        this.product=product;
    }

    public Product getProduct() {
        return product;
    }

    public void parseInputMessage(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        String[] messageArray = message.trim().split("\\s+");
        String firstWord = messageArray[0];
        if (firstWord.matches("Add|Subtract|Multiply")) {
             parseMessageType3(messageArray);
        } else if (firstWord.matches("^\\d+")) {
             parseMessageType2(messageArray);
        } else if (messageArray.length == 3 && ProductType.fromCode(parseProdType(messageArray[0]))!=null) {
             parseMessageType1(messageArray);
        } else {
            System.out.println("This message type is not supported");
        }
    }

    // Parse message type 1
    private void parseMessageType1(String[] messageArray) {
        product.setProductType(ProductType.fromCode(parseProdType((messageArray[0]))));
        product.setPrice(parsePrice(messageArray[2]));
        product.setQuantity(1); //This will be always 1
    }

    // Parse message type 2
    private void parseMessageType2(String[] messageArray) {
        if(messageArray.length > 7 || messageArray.length < 7) return;
        product.setProductType(ProductType.fromCode(parseProdType(messageArray[3])));
        product.setPrice(parsePrice(messageArray[5]));
        product.setQuantity(Long.parseLong(messageArray[0]));
    }

    // Parse message type 3
    private void parseMessageType3(String[] messageArray) {
        if(messageArray.length > 3 || messageArray.length < 3) return;
        product.setOperationType(OperationType.fromCode(messageArray[0]));
        product.setProductType(ProductType.fromCode(parseProdType(messageArray[2])));
        product.setPrice(parsePrice(messageArray[1]));
    }

    // handle the plural cases of the products
    // @return[String] parsed string of productType e.g 'apple' will become 'apples'
    public String parseProdType(String prodType) {
        String parsedType;
        String typeWithoutLastChar = prodType.substring(0, prodType.length() - 1);
        if (prodType.endsWith("o")) {
            parsedType = String.format("%soes", typeWithoutLastChar);
        } else if (prodType.endsWith("y")) {
            parsedType = String.format("%sies", typeWithoutLastChar);
        } else if (prodType.endsWith("h")) {
            parsedType = String.format("%shes", typeWithoutLastChar);
        } else if (!prodType.endsWith("s")) {
            parsedType = String.format("%ss", prodType);
        } else {
            parsedType = String.format("%s", prodType);
        }
        return parsedType.toLowerCase();
    }

    public BigDecimal parsePrice(String prodPrice) {
        BigDecimal price = new BigDecimal(prodPrice.replaceAll("p", ""));
        return price;
    }

}