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
    private static final List<String> DICTIONARY = Arrays.asList("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х");
    private final Random random = new Random();
    private static final int MAX_VALUE = 1728000;
    private final static int MAX_NUMBER = 999;

    /**
     * метод возвоащает строку с новым номером автомобиля
     * @return новый автомобильный номер
     * @throws AllNumbersUsedException ошибка возникает, если уже сгенерированы все номера автомобилейException
     */
    public String getRandomCarNumber() throws AllNumbersUsedException {
        if (map.size() == MAX_VALUE) {
            throw new AllNumbersUsedException();
        }
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
     * @throws AllNumbersUsedException ошибка возникает, если уже сгенерированы все номера автомобилей
     */
    public String getNextCarNumber() throws AllNumbersUsedException, EmptyCarNumberListException {
        if (map.isEmpty()) {
            throw new EmptyCarNumberListException();
        }
        if (map.size() == MAX_VALUE) {
            throw new AllNumbersUsedException();
        }
        CarNumber newCarNumber = (CarNumber) map.values().toArray()[map.entrySet().size() -1];
        while (map.containsKey(newCarNumber.toString())) {
            CarNumber lastCarNumber = new CarNumber(
                    newCarNumber.getFirst(),
                    newCarNumber.getNumber(),
                    newCarNumber.getSecond(),
                    newCarNumber.getThird(),
                    newCarNumber.getRegion());
            if (lastCarNumber.getNumber() == MAX_NUMBER) {
                newCarNumber.setNumber(0);
                if (lastCarNumber.getThird().equals(DICTIONARY.get(DICTIONARY.size() - 1))) {
                    newCarNumber.setThird(DICTIONARY.get(0));
                    if (lastCarNumber.getSecond().equals(DICTIONARY.get(DICTIONARY.size() - 1))) {
                        newCarNumber.setSecond(DICTIONARY.get(0));
                        if (lastCarNumber.getFirst().equals(DICTIONARY.get(DICTIONARY.size() - 1))) {
                            newCarNumber.setFirst(DICTIONARY.get(0));
                        } else {
                            newCarNumber.setFirst(DICTIONARY.get(DICTIONARY.indexOf(lastCarNumber.getFirst()) + 1));
                        }
                    } else {
                        newCarNumber.setSecond(DICTIONARY.get(DICTIONARY.indexOf(lastCarNumber.getSecond()) + 1));
                    }
                } else {
                    newCarNumber.setThird(DICTIONARY.get(DICTIONARY.indexOf(lastCarNumber.getThird()) + 1));
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
        return new CarNumber(
                DICTIONARY.get(random.nextInt(DICTIONARY.size())),
                random.nextInt(MAX_NUMBER),
                DICTIONARY.get(random.nextInt(DICTIONARY.size())),
                DICTIONARY.get(random.nextInt(DICTIONARY.size())),
                " 116 RUS"
        );
    }

}
