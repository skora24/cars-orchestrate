package com.kacpers.cars.feign.response;

import java.util.List;

public record LotImagesResponse(
    Integer returnCode,
    String returnCodeDesc,
    Data data
) {
    public record Data(
        ImagesList imagesList
    ) {}

    public record ImagesList(
        Integer totalElements,
        List<Content> content,
        List<String> facetFields,
        Object spellCheckList,
        Object suggestions,
        Boolean realTime
    ) {}

    public record Content(
        Boolean swiftFlag,
        Integer frameCount,
        String status,
        String imageTypeDescription,
        String fullUrl,
        String thumbnailUrl,
        String highResUrl,
        Integer imageSeqNumber,
        String imageTypeCode,
        String imageWorkflowStatus,
        String solrHighResUrl,
        String solrFullUrl,
        String lotNumberStr,
        String imageTypeEnum,
        Boolean highRes,
        Long ln
    ) {}
}
