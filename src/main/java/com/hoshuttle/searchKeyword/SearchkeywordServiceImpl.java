package com.hoshuttle.backend.searchKeyword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchKeywordServiceImpl implements SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    @Override
    public SearchKeyword saveKeyword(String keyword) {
        SearchKeyword searchKeyword = SearchKeyword.builder()
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .build();
        return searchKeywordRepository.save(searchKeyword);
    }

    @Override
    public List<SearchKeyword> getRecentKeywords() {
        return searchKeywordRepository.findTop10ByOrderBySearchedAtDesc();
    }

    @Override
    public void deleteKeyword(Long id) {
        searchKeywordRepository.deleteById(id);
    }
}
