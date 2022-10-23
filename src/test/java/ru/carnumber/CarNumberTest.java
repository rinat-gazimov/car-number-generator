package ru.carnumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.carnumber.exception.AllNumbersUsedException;
import ru.carnumber.exception.EmptyCarNumberListException;
import ru.carnumber.model.CarNumber;
import ru.carnumber.service.CarNumberService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarNumberTest {

	@Autowired
	private CarNumberService service;

	private static final String REGEX = "[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2} 116 RUS";
	private static final List<String> DICTIONARY = Arrays.asList("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х");

	@Test
	@DisplayName("Когда генерируем номер автомобиля," +
			"то ожидаем получить ответ определенного формата")
	void testGenerator() throws AllNumbersUsedException {
		String carNumber = service.getRandomCarNumber();
		assertTrue(carNumber.matches(REGEX));
	}

	@Test
	@DisplayName("Когда запрашиваем следующий номер автомобиля," +
			"то ожидаем получить корректный ответ")
	void testNextCarNumber() throws AllNumbersUsedException, EmptyCarNumberListException {
		CarNumber carNumber = parsedCarNumber(service.getRandomCarNumber());
		CarNumber nextCarNumber = parsedCarNumber(service.getNextCarNumber());
		assertTrue(checkNextCarNumber(carNumber, nextCarNumber));

		CarNumber wrongNextCarNumber = parsedCarNumber(service.getNextCarNumber());
		wrongNextCarNumber.setNumber(wrongNextCarNumber.getNumber() + 1);
		assertFalse(checkNextCarNumber(nextCarNumber, wrongNextCarNumber));
	}

	/**
	 * метод проверяет следующий автомобилный номер
	 * @param carNumber номер автомобиля
	 * @param nextCarNumber следующий номер автомобиля
	 * @return результат проверки следующего номера автомобиля
	 */
	private boolean checkNextCarNumber(CarNumber carNumber, CarNumber nextCarNumber) {

		if (DICTIONARY.indexOf(carNumber.getFirst()) == DICTIONARY.size() -1) {
			if (!nextCarNumber.getFirst().equals(DICTIONARY.get(0))) {
				return false;
			}
		} else if (nextCarNumber.getFirst().equals(DICTIONARY.get(DICTIONARY.indexOf(carNumber.getFirst()) + 1))) {
			return false;
		}

		if (carNumber.getNumber() == 999) {
			if (nextCarNumber.getNumber() != 0) {
				return false;
			}
		} else if (nextCarNumber.getNumber() != carNumber.getNumber() + 1) {
			return false;
		}

		if (DICTIONARY.indexOf(carNumber.getSecond()) == DICTIONARY.size() -1) {
			if (!nextCarNumber.getSecond().equals(DICTIONARY.get(0))) {
				return false;
			}
		} else if (nextCarNumber.getSecond().equals(DICTIONARY.get(DICTIONARY.indexOf(carNumber.getSecond()) + 1))) {
			return false;
		}

		if (DICTIONARY.indexOf(carNumber.getThird()) == DICTIONARY.size() -1) {
			if (!nextCarNumber.getSecond().equals(DICTIONARY.get(0))) {
				return false;
			}
		} else if (nextCarNumber.getThird().equals(DICTIONARY.get(DICTIONARY.indexOf(carNumber.getThird()) + 1))) {
			return false;
		}

		return carNumber.getRegion().equals(nextCarNumber.getRegion());
	}

	/**
	 * метод возвращает объект CarNumber из строкового представления автомобильного номера
	 * @param carNumberStr строковое представление автомобильного номера
	 * @return объект CarNumber
	 */
	private CarNumber parsedCarNumber(String carNumberStr) {
		return new CarNumber(
				carNumberStr.substring(0, 1),
				Integer.parseInt(carNumberStr.substring(1, 4)),
				carNumberStr.substring(4, 5),
				carNumberStr.substring(5, 6),
				carNumberStr.substring(6, 14)
				);
	}

}
