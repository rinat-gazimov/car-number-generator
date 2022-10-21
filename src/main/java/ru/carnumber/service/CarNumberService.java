package ru.carnumber.service;

import org.springframework.stereotype.Service;
import ru.carnumber.exception.AllNumbersUsedException;
import ru.carnumber.exception.EmptyCarNumberListException;
import ru.carnumber.model.CarNumber;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class CarNumberService {

    private final Map<String, CarNumber> map = new LinkedHashMap<>();
    private final List<String> dictionary = Arrays.asList("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х");
    private final Random random = new Random();

    public String getRandomCarNumber() {
        CarNumber carNumber = generateCarNumber();
        while (map.containsKey(carNumber.toString())) {
            carNumber = generateCarNumber();
        }

        map.put(carNumber.toString(), carNumber);
        return carNumber.toString();
    }

    /**
     * метод возвращает следующий по порядку автомобильный номер
     * @return следующий автомобильный номер
     * @throws AllNumbersUsedException ошибка возникает, если
     */
    public String getNextCarNumber() throws AllNumbersUsedException, EmptyCarNumberListException {
        if (map.isEmpty()) {
            throw new EmptyCarNumberListException();
        }
        CarNumber newCarNumber = (CarNumber) map.values().toArray()[map.entrySet().size() -1];
        while (map.containsKey(newCarNumber.toString())) {
            CarNumber lastCarNumber = new CarNumber(
                    newCarNumber.getFirst(),
                    newCarNumber.getNumber(),
                    newCarNumber.getSecond(),
                    newCarNumber.getThird(),
                    newCarNumber.getRegion());
            if (lastCarNumber.getNumber() == 999) {
                newCarNumber.setNumber(0);
                if (lastCarNumber.getThird().equals(dictionary.get(dictionary.size() - 1))) {
                    newCarNumber.setThird(dictionary.get(0));
                    if (lastCarNumber.getSecond().equals(dictionary.get(dictionary.size() - 1))) {
                        newCarNumber.setSecond(dictionary.get(0));
                        if (lastCarNumber.getFirst().equals(dictionary.get(dictionary.size() - 1))) {
                            throw new AllNumbersUsedException();
                        } else {
                            newCarNumber.setFirst(dictionary.get(dictionary.indexOf(lastCarNumber.getFirst()) + 1));
                        }
                    } else {
                        newCarNumber.setSecond(dictionary.get(dictionary.indexOf(lastCarNumber.getSecond()) + 1));
                    }
                } else {
                    newCarNumber.setThird(dictionary.get(dictionary.indexOf(lastCarNumber.getThird()) + 1));
                }
            } else {
                newCarNumber.setNumber(lastCarNumber.getNumber() + 1);
            }
        }

        map.put(newCarNumber.toString(), newCarNumber);

        return newCarNumber.toString();
    }

    /**
     * Метод возвращает сгенерированный автомобильный номер
     * @return объект CarNumber
     */
    private CarNumber generateCarNumber() {
        String region = " 116 RUS";
        return new CarNumber(
                dictionary.get(random.nextInt(dictionary.size())),
                random.nextInt(999),
                dictionary.get(random.nextInt(dictionary.size())),
                dictionary.get(random.nextInt(dictionary.size())),
                region
        );
    }

}
