package ru.carnumber.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CarNumber {

    private String first;
    private int number;
    private String second;
    private String third;
    private String region;

    @Override
    public String toString() {
        String numberStr = String.valueOf(number);
        if (number < 10) {
            numberStr = "00" + number;
        } else if (number < 100) {
            numberStr = "0" + number;
        }
        return first + numberStr + second + third + region;
    }
}
