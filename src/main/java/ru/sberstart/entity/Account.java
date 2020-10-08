package ru.sberstart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private long id;
    private List<Card> cards = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final Account account = (Account) obj;
        return this.id == account.id && this.cards.equals(account.cards);
    }
    @Override
    public int hashCode(){
        int hash = 3;
        hash = 53 * hash /*+ (this.cards != null ? this.cards.hashCode() : 0)*/;
        hash = 53 * hash + 17;
        return hash;
    }
}
