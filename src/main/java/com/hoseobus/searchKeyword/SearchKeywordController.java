package com.hoshuttle.backend.searchKeyword;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchKeywordController {

    private final SearchKeywordService searchKeywordService;

    @PostMapping
    public SearchKeyword saveKeyword(@RequestBody String keyword) {
        return searchKeywordService.saveKeyword(keyword);
    }

    @GetMapping("/recent")
    public List<SearchKeyword> getRecentKeywords() {
        return searchKeywordService.getRecentKeywords();
    }

    @DeleteMapping("/{id}")
    public void deleteKeyword(@PathVariable Long id) {
        searchKeywordService.deleteKeyword(id);
    }
}
