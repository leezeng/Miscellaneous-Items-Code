package com.server.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-07-02-20:20
 */
@Component
public class CardVO {

    @Value("${cardID}")
    private String cardID;

    @Value("${cardName}")
    private String cardName;

    @Override
    public String toString() {
        return "CardVO{" +
                "cardID='" + cardID + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
