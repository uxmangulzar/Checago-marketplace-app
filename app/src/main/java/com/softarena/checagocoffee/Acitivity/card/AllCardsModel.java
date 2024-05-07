package com.softarena.checagocoffee.Acitivity.card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCardsModel {
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("card_stripe_card_id")
    @Expose
    private String cardStripeCardId;
    @SerializedName("card_user_id")
    @Expose
    private String cardUserId;
    @SerializedName("card_holder_name")
    @Expose
    private String cardHolderName;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("card_expiry")
    @Expose
    private String cardExpiry;
    @SerializedName("card_cvv")
    @Expose
    private String cardCvv;
    @SerializedName("card_status")
    @Expose
    private String cardStatus;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("card_created_at")
    @Expose
    private String cardCreatedAt;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardStripeCardId() {
        return cardStripeCardId;
    }

    public void setCardStripeCardId(String cardStripeCardId) {
        this.cardStripeCardId = cardStripeCardId;
    }

    public String getCardUserId() {
        return cardUserId;
    }

    public void setCardUserId(String cardUserId) {
        this.cardUserId = cardUserId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardCreatedAt() {
        return cardCreatedAt;
    }

    public void setCardCreatedAt(String cardCreatedAt) {
        this.cardCreatedAt = cardCreatedAt;
    }
}
