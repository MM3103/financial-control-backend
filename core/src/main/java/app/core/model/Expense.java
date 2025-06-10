package app.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "expenses", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenses_seq")
    @SequenceGenerator(name = "expenses_seq", sequenceName = "expenses_seq", allocationSize = 1)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "category", length = 128)
    private String category;

    //TODO: надо нам над форматом подумать, 6 знаков милисекунд не нужны по моему (в скриптах создания таблицы)
    @Column(name = "create_date", nullable = false, updatable = false)
    private OffsetDateTime createDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;
}
