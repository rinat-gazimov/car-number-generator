package ru.carnumber.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.carnumber.exception.AllNumbersUsedException;
import ru.carnumber.exception.EmptyCarNumberListException;
import ru.carnumber.service.CarNumberService;

@RestController
@RequestMapping("/number")
@RequiredArgsConstructor
public class Api {

    private final CarNumberService service;

    @Operation(summary = "Random car number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get random car number", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping(value = "/random")
    String getRandomCarNumber() throws AllNumbersUsedException {
        return service.getRandomCarNumber();
    }

    @Operation(summary = "Next car number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get next car number", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping(value = "/next")
    String getNextCarNumber() throws AllNumbersUsedException, EmptyCarNumberListException {
        return service.getNextCarNumber();
    }


}
