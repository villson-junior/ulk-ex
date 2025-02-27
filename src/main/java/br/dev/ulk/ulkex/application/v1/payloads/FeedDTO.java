package br.dev.ulk.ulkex.application.v1.payloads;

import java.util.List;
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
public class FeedDTO {

    private List<FeedItemDTO> items;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long tatalElements;

}