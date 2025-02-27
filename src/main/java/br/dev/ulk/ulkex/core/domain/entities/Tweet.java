package br.dev.ulk.ulkex.core.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TWEETS")
@Entity(name = "TWEET")
public class Tweet extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CONTENT")
    private String content;

}