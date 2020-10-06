package ru.sberstart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private long id;
    private long accountId;
    private BigDecimal balance;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final Card card = (Card) obj;
        return this.id == card.id && this.accountId == card.accountId && this.balance == card.balance;
    }
    @Override
    public int hashCode(){
        int hash = 3;
        hash = 53 * hash + (this.balance != null ? this.balance.hashCode() : 0);
        hash = 53 * hash + 17;
        return hash;
    }
}
