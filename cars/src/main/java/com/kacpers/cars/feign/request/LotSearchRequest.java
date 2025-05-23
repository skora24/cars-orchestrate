package com.kacpers.cars.feign.request;

import java.util.List;
import java.util.Map;

public record LotSearchRequest(
    List<String> query,
    Filter filter,
    List<String> sort,
    Integer page,
    Integer size,
    Integer start,
    Boolean watchListOnly,
    Boolean freeFormSearch,
    Boolean hideImages,
    Boolean defaultSort,
    Boolean specificRowProvided,
    String displayName,
    String searchName,
    String backUrl,
    Map<String, List<String>> includeTagByField,
    Map<String, Object> rawParams
) {

    public record Filter(
        List<String> NLTS
    ) {
    }

    public static LotSearchRequest latest(int page) {
        return new LotSearchRequest(
            List.of("*"),
            new LotSearchRequest.Filter(List.of()),
            List.of("salelight_priority asc", "member_damage_group_priority asc", "auction_date_type desc", "auction_date_utc asc"),
            page,
            100,
            0,
            false,
            true,
            false,
            false,
            false,
            "",
            "",
            "",
            Map.of(),
            Map.of()
        );
    }
}
