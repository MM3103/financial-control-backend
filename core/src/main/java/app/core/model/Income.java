package app.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "income")
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_seq")
    @SequenceGenerator(name = "income_seq", sequenceName = "income_seq", allocationSize = 1)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "category", length = 128)
    private String category;

    //TODO: надо нам над форматом подумать, 6 знаков милисекунд не нужны по моему (в скриптах создания таблицы)
    @Column(name = "create_date", nullable = false, updatable = false)
    private OffsetDateTime createDate;
}
