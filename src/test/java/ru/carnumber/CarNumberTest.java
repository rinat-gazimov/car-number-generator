package ru.carnumber;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.carnumber.service.CarNumberService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarNumberTest {

	@Autowired
	private CarNumberService service;

	@Test
	void testGenerator() {
		String carNumber = service.getRandomCarNumber();
		Assert.isTrue(carNumber.matches("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2} 116 RUS"));
	}

}
