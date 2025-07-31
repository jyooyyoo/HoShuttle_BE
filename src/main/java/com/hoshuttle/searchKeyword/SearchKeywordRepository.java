package com.hoshuttle.searchKeyword;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderBySearchedAtDesc();
}
