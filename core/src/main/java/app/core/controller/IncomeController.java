package app.core.controller;

import app.core.api.IncomeService;
import app.core.model.Income;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//TODO: Нужен еще рест чтобы получать все существующие доходы без id
//TODO: Хз насчет нужно ли в DELETE возвращать саму сущность которую мы стерли. Как будто 200 или 204 с пустым боди достаточно

@RestController
@RequiredArgsConstructor
@RequestMapping("/income")
@Tag(name = "Контроллер доходов", description = "Контроллер для доходов")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Запрос выполнен успешно"),
@ApiResponse(responseCode = "401", description = "Ошибка доступа(пользователь не авторизован)")})
public class IncomeController {

    private final IncomeService incomeService;
    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о доходе")
    public Income get(@PathVariable Long id) {
        return incomeService.get(id);
    }

    @PostMapping
    @Operation(summary = "Добавить доход")
    public Income create(@RequestBody Income income) {
        return incomeService.create(income);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить доход")
    public Income delete(@PathVariable Long id) {
        return incomeService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить доход")
    public Income update(@PathVariable Long id, @RequestBody Income updateIncome) {
        return incomeService.update(id, updateIncome);
    }
}
