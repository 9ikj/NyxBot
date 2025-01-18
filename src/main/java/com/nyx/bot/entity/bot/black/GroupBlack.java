package com.nyx.bot.entity.bot.black;

import com.fasterxml.jackson.annotation.JsonView;
import com.nyx.bot.annotation.InternationalizedNotEmpty;
import com.nyx.bot.core.Views;
import com.nyx.bot.core.dao.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"group"}))
@JsonView(Views.View.class)
public class GroupBlack extends BaseEntity {
    @Id
    @GeneratedValue
    Long id;
    @InternationalizedNotEmpty(message = "bot.not.empty")
    Long botUid;
    @InternationalizedNotEmpty(message = "group.not.empty")
    Long groupUid;

}
