package com.webbfontaine.task.githubanalytics.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    @NotNull
    @NotEmpty
    private String queryString;
    private String sort = "stats";
    private SortOrder sortOrder = SortOrder.DESC;

    @Min(value = 1,message = "Minimum page number is 1")
    private int page = 1;
    @Min(value = 1, message = "Minimum value per page is 1")
    @Max(value = 1000,message = "Maximum value per page is 1000 per request")
    private int perPage = 30;

    public static enum SortOrder {
        ASC,DESC
    }
}
