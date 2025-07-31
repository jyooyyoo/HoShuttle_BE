package com.hoshuttle.searchKeyword;

import java.util.List;

public interface SearchKeywordService {
    SearchKeyword saveKeyword(String keyword);
    List<SearchKeyword> getRecentKeywords();
    void deleteKeyword(Long id);
}
